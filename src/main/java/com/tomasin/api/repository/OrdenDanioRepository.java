package com.tomasin.api.repository;

import com.tomasin.api.model.OrdenDanio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenDanioRepository extends JpaRepository<OrdenDanio, Long> {
    /*Lista todos los daños de una orden (tanto manuales como de IA).
     * Se muestra en el diagrama de daños.*/
    List<OrdenDanio> findByOrdenId(Long ordenId);
    /*Elimina todos los daños de una orden. Útil si se cancela la orden.*/
    void deleteByOrdenId(Long ordenId);
}