package com.tomasin.api.repository;

import com.tomasin.api.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    /*Busca un vehículo por su placa.*/
    Optional<Vehiculo> findByPlaca(String placa);
    /*Busca un vehículo por su número de chasis (único de 17 caracteres).
    si deseas lo usas, aunqye no lo veo necesario*/
    Optional<Vehiculo> findByChasis(String chasis);
    /*Lista todos los vehículos pertenecientes a un cliente (por su id).*/
    List<Vehiculo> findByClienteId(Long clienteId);
    /*Verifica si ya existe un vehículo con esa placa (unicidad).*/
    boolean existsByPlaca(String placa);
    /*Verifica si ya existe un vehículo con ese chasis (unicidad).*/
    boolean existsByChasis(String chasis);
}