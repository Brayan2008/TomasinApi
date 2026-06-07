package com.tomasin.api.repository;

import com.tomasin.api.model.OrdenChecklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenChecklistRepository extends JpaRepository<OrdenChecklist, Long> {
    /*Obtiene todos los ítems de checklist de una orden.*/
    List<OrdenChecklist> findByOrdenId(Long ordenId);
    /*Obtiene un ítem específico (por su índice) de una orden.*/
    Optional<OrdenChecklist> findByOrdenIdAndItemIndex(Long ordenId, Integer itemIndex);
    /*Cuenta cuántos ítems de una orden tienen respuesta NULL (pendientes).
    Si el resultado es 0, el checklist está completo.*/
    long countByOrdenIdAndRespuestaIsNull(Long ordenId); // para verificar pendientes
}