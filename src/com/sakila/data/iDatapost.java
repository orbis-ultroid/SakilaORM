package com.sakila.data;

import java.util.List;

/**
 * Interfaz para el estándar CRUD de la base de datos Sakila.
 * @param <T> Tipo de entidad del modelo.
 * @author Estudiante UASD
 * @version 1.0
 */
public interface iDatapost<T> {

    /**
     * Inserta un nuevo registro en la base de datos.
     * @param entity Entidad a insertar.
     * @return true si la operación fue exitosa.
     */
    boolean post(T entity);

    /**
     * Obtiene un registro por su clave primaria.
     * @param id Identificador del registro.
     * @return Entidad encontrada o null si no existe.
     */
    T get(int id);

    /**
     * Obtiene todos los registros de la entidad.
     * @return Lista de entidades.
     */
    List<T> get();

    /**
     * Busca registros según un criterio o filtro (sobrecarga de Get).
     * @param filter Criterio de búsqueda.
     * @return Lista de entidades que coinciden con el filtro.
     */
    List<T> get(String filter);

    /**
     * Actualiza un registro existente en la base de datos.
     * @param entity Entidad con los datos actualizados.
     * @return true si se actualizó correctamente.
     */
    boolean put(T entity);

    /**
     * Elimina un registro por su clave primaria.
     * @param id Identificador del registro a eliminar.
     * @return true si se eliminó correctamente.
     */
    boolean delete(int id);
}