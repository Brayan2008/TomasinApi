package com.tomasin.api.service.ia;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@Profile("!dev")
public class FlaskIAProcesador implements IIAProcesador {

    private final String flaskUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public FlaskIAProcesador(@Value("${app.ia.flask.url}") String flaskUrl) {
        this.flaskUrl = flaskUrl;
    }

    @Override
    public List<DeteccionDanioDTO> procesar(byte[] imagen, String contentType) {
        var resource = new ByteArrayResource(imagen) {
            @Override
            public String getFilename() {
                return contentType.startsWith("image/png") ? "imagen.png" : "imagen.jpg";
            }
        };

        var body = new LinkedMultiValueMap<String, Object>();
        body.add("imagen", resource);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        var request = new HttpEntity<>(body, headers);

        log.info("Enviando imagen a Flask: {}", flaskUrl);

        DeteccionDanioDTO[] respuesta;

        try {
            respuesta = restTemplate.postForObject(flaskUrl, request, DeteccionDanioDTO[].class);
        } catch (Exception e) {
            log.error("Error al comunicar con Flask", e);
            return Collections.emptyList();
        }

        if (respuesta == null || respuesta.length == 0) {
            log.info("Flask no devolvio detecciones");
            return Collections.emptyList();
        }

        log.info("Detecciones recibidas: {}", respuesta.length);
        return List.of(respuesta);
    }
}
