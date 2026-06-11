package com.tomasin.api.service.jpa;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tomasin.api.dto.FotoOrdenDTO.FotoOrdenRequestDTO;
import com.tomasin.api.dto.FotoOrdenDTO.FotoOrdenResponseDTO;
import com.tomasin.api.enums.OrigenDanio;
import com.tomasin.api.model.FotoOrden;
import com.tomasin.api.model.OrdenDanio;
import com.tomasin.api.repository.FotoOrdenRepository;
import com.tomasin.api.repository.OrdenDanioRepository;
import com.tomasin.api.repository.OrdenServicioRepository;
import com.tomasin.api.service.IFotoOrdenService;
import com.tomasin.api.service.ia.IIAProcesador;

@Service
@Transactional
public class FotoOrdenService implements IFotoOrdenService {

    @Autowired
    private FotoOrdenRepository fotoOrdenRepository;

    @Autowired
    private OrdenServicioRepository ordenServicioRepository;

    @Autowired
    private OrdenDanioRepository ordenDanioRepository;

    @Autowired
    private IIAProcesador iaProcesador;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Override
    public Optional<FotoOrdenResponseDTO> buscarById(Long id) {
        return fotoOrdenRepository.findById(id).map(FotoOrdenResponseDTO::toResponse);
    }

    @Override
    public List<FotoOrdenResponseDTO> buscarTodos() {
        return fotoOrdenRepository.findAll().stream().map(FotoOrdenResponseDTO::toResponse).toList();
    }

    @Deprecated
    @Override
    public FotoOrdenResponseDTO guardar(FotoOrdenRequestDTO req) {
        var f = req.toEntity();
        f.setOrden(ordenServicioRepository.findById(req.idOrden())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada")));
        var saved = fotoOrdenRepository.save(f);
        return FotoOrdenResponseDTO.toResponse(saved);
    }

    @Override
    public FotoOrdenResponseDTO guardarConArchivo(MultipartFile archivo, Long idOrden, boolean procesadoIa) {
        validarImagen(archivo);

        String nombreOriginal = archivo.getOriginalFilename();
        String extension = obtenerExtension(nombreOriginal);
        String nombreUnico = UUID.randomUUID() + extension;
        String rutaRelativa = uploadDir + "/" + nombreUnico;

        try {
            Path dir = Path.of(uploadDir);
            Files.createDirectories(dir);
            byte[] bytes = archivo.getBytes();
            Files.write(dir.resolve(nombreUnico), bytes);

            String hash = calcularSha256(bytes);

            var orden = ordenServicioRepository.findById(idOrden)
                    .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

            FotoOrden foto = new FotoOrden();
            foto.setNombreArchivo(nombreOriginal);
            foto.setRuta(rutaRelativa);
            foto.setHashArchivo(hash);
            foto.setProcesadoIa(procesadoIa);
            foto.setOrden(orden);

            var saved = fotoOrdenRepository.save(foto);

            if (procesadoIa) {
                procesarConIA(saved, bytes);
            }

            return FotoOrdenResponseDTO.toResponse(saved);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen", e);
        }
    }

    @Override
    public FotoOrdenResponseDTO actualizarConArchivo(Long id, MultipartFile archivo, Long idOrden,
            boolean procesadoIa) {
        var foto = fotoOrdenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foto no encontrada"));

        foto.setOrden(ordenServicioRepository.findById(idOrden)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada")));

        boolean necesitaProcesar = false;

        if (archivo != null && !archivo.isEmpty()) {
            validarImagen(archivo);
            eliminarArchivoDisco(foto.getRuta());

            String nombreOriginal = archivo.getOriginalFilename();
            String extension = obtenerExtension(nombreOriginal);
            String nombreUnico = UUID.randomUUID() + extension;
            String rutaRelativa = uploadDir + "/" + nombreUnico;

            try {
                Path dir = Path.of(uploadDir);
                Files.createDirectories(dir);
                byte[] bytes = archivo.getBytes();
                Files.write(dir.resolve(nombreUnico), bytes);

                foto.setNombreArchivo(nombreOriginal);
                foto.setRuta(rutaRelativa);
                foto.setHashArchivo(calcularSha256(bytes));
                necesitaProcesar = true;
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar la nueva imagen", e);
            }
        }

        if (procesadoIa && !foto.isProcesadoIa()) {
            necesitaProcesar = true;
        }

        foto.setProcesadoIa(procesadoIa);
        var saved = fotoOrdenRepository.save(foto);

        if (necesitaProcesar) {
            try {
                Path filePath = Path.of(saved.getRuta());
                if (Files.exists(filePath)) {
                    byte[] bytes = Files.readAllBytes(filePath);
                    procesarConIA(saved, bytes);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error al leer la imagen para procesar IA", e);
            }
        }

        return FotoOrdenResponseDTO.toResponse(saved);
    }

    @Deprecated
    @Override
    public boolean actualizar(Long id, FotoOrdenRequestDTO req) {
        return fotoOrdenRepository.findById(id).map(f -> {
            f.setNombreArchivo(req.nombreArchivo());
            f.setRuta(req.ruta());
            f.setHashArchivo(req.hashArchivo());
            f.setProcesadoIa(req.procesadoIa());
            f.setOrden(ordenServicioRepository.findById(req.idOrden())
                    .orElseThrow(() -> new RuntimeException("Orden no encontrada")));
            fotoOrdenRepository.save(f);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean eliminar(Long id) {
        var fotoOpt = fotoOrdenRepository.findById(id);
        if (fotoOpt.isEmpty()) {
            return false;
        }
        var foto = fotoOpt.get();
        eliminarArchivoDisco(foto.getRuta());
        fotoOrdenRepository.delete(foto);
        return true;
    }

    @Override
    public Resource obtenerArchivo(String filename) {
        Path filePath = Path.of(uploadDir, filename);
        if (!Files.exists(filePath)) {
            throw new RuntimeException("Archivo no encontrado: " + filename);
        }
        return new FileSystemResource(filePath);
    }

    private void validarImagen(MultipartFile archivo) {
        if (archivo.isEmpty()) {
            throw new RuntimeException("El archivo esta vacio");
        }
        String contentType = archivo.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("Solo se permiten imagenes");
        }
    }

    private String obtenerExtension(String nombreOriginal) {
        if (nombreOriginal == null) {
            return ".jpg";
        }
        int i = nombreOriginal.lastIndexOf('.');
        if (i < 0) {
            return ".jpg";
        }
        return nombreOriginal.substring(i);
    }

    private String calcularSha256(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(bytes);
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al calcular SHA-256", e);
        }
    }

    private void eliminarArchivoDisco(String rutaRelativa) {
        try {
            Files.deleteIfExists(Path.of(rutaRelativa));
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar el archivo del disco", e);
        }
    }

    private void procesarConIA(FotoOrden foto, byte[] bytes) {
        ordenDanioRepository.deleteByFotoId(foto.getId());

        var detecciones = iaProcesador.procesar(bytes);
        for (var d : detecciones) {
            OrdenDanio danio = new OrdenDanio();
            danio.setTipoDanio(d.tipo());
            danio.setPosX(d.pos_X() != null ? d.pos_X() : BigDecimal.ZERO);
            danio.setPosY(d.pos_Y() != null ? d.pos_Y() : BigDecimal.ZERO);
            danio.setOrigen(OrigenDanio.IA);
            danio.setObservacion("Detectado por IA");
            danio.setFoto(foto);
            danio.setOrden(foto.getOrden());
            ordenDanioRepository.save(danio);
        }
    }
}
