package com.tomasin.api.service;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz genérica para servicios CRUD (Create, Read, Update, Delete).
 * 
 * @param <C>  Tipo de datos para la operación de creación (Create).
 * @param <R>  Tipo de retorno para la operación de lectura (Read).
 * @param <U>  Tipo de datos para la operación de actualización (Update).
 * @param <ID> Tipo de datos para el identificador (ID) de la entidad
 */
public interface CrudService<C, R, U, ID> {

    /**
     * Lee una entidad por su identificador.
     * 
     * @param id El identificador de la entidad a leer.
     * @return Un optional con el valor de la entidad.
     */
    Optional<R> buscarById(ID id);

    /**
     * Lista todas los registros de la entidad.
     * 
     * @return Una lista de todas las entidades.
     */
    List<R> buscarTodos();

    /**
     * Guarda una nueva entidad.
     * 
     * @param req Request de la entidad a guardar
     * @return El id de la entidad creada, o null si no se pudo crear.
     */
    R guardar(C req);
    
    /**
     * Actualiza una entidad existente.
     * 
     * @param entity La entidad con los datos actualizados.
     * @param id     El id de la entidad a actualizar.
     * @return <strong>true</strong> si la actualización fue exitosa, <strong>false</strong> si no se pudo actualizar.
     */
    boolean actualizar(ID id, U req);

    /**
     * Elimina una entidad por su identificador.
     * 
     * @param id El identificador de la entidad a eliminar.
     * @return <strong>true</strong> si la eliminación fue exitosa, <strong>false</strong> si no se pudo eliminar.
     */
    boolean eliminar(ID id);

    /**
     * Interfaz genérica para servicios CRUD básicos, donde la entidad para
     * creación, lectura y actualización es el mismo. Se puede usar esta interfaz en
     * donde
     * no es necesario usar un DTO específico para cada operación.
     * 
     * @param <E>  La entidad a usarse.
     * @param <ID> Tipo de datos para el identificador (ID) de la entidad.
     */
    public interface CrudBasic<E, ID> extends CrudService<E, E, E, ID> {}

}