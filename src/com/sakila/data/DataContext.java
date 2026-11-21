package com.sakila.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Padre abstracto híbrido ORM con métodos CRUD concretos marcados como final
 * para impedir que los hijos los sobrescriban.
 * @param <T> Tipo de entidad gestionada.
 * @author Estudiante UASD
 * @version 1.0
 */
public abstract class DataContext<T> implements iDatapost<T> {

    protected final String tableName;
    protected final String primaryKeyColumn;

    /**
     * Constructor protegido para inicializar tabla y clave primaria.
     * @param tableName Nombre de la tabla en Sakila.
     * @param primaryKeyColumn Nombre de la columna clave primaria.
     */
    protected DataContext(String tableName, String primaryKeyColumn) {
        this.tableName = tableName;
        this.primaryKeyColumn = primaryKeyColumn;
    }

    /**
     * Inserta un registro validando claves foráneas y recuperando PK autoincremental.
     * @param entity Entidad a insertar.
     * @return true si se insertó correctamente.
     */
    @Override
    public final boolean post(T entity) {
        if (!validateForeignKeys(entity)) {
            System.err.println("[ORM] Violación de clave foránea al insertar en " + tableName);
            return false;
        }
        String sql = buildInsertSQL(entity);
        try (PreparedStatement stmt = DatabaseConnection.getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bindInsertParams(stmt, entity);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        assignGeneratedId(entity, keys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("[ORM Post Error en " + tableName + "]: " + e.getMessage());
        }
        return false;
    }

    /**
     * Actualiza un registro existente validando claves foráneas.
     * @param entity Entidad a actualizar.
     * @return true si se actualizó correctamente.
     */
    @Override
    public final boolean put(T entity) {
        if (!validateForeignKeys(entity)) {
            System.err.println("[ORM] Violación de clave foránea al actualizar en " + tableName);
            return false;
        }
        String sql = buildUpdateSQL(entity);
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            bindUpdateParams(stmt, entity);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ORM Put Error en " + tableName + "]: " + e.getMessage());
        }
        return false;
    }

    /**
     * Elimina un registro por PK respetando reglas de integridad referencial.
     * @param id Identificador del registro.
     * @return true si fue eliminado.
     */
    @Override
    public final boolean delete(int id) {
        String sql = "DELETE FROM " + tableName + " WHERE " + primaryKeyColumn + " = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLIntegrityConstraintViolationException fkEx) {
            System.err.println("[ORM Integridad] No se puede eliminar ID " + id + " de " + tableName + " por dependencias.");
        } catch (SQLException e) {
            System.err.println("[ORM Delete Error en " + tableName + "]: " + e.getMessage());
        }
        return false;
    }

    /**
     * Obtiene un registro por su clave primaria.
     * @param id Identificador del registro.
     * @return Entidad encontrada o null.
     */
    @Override
    public final T get(int id) {
        String sql = "SELECT * FROM " + tableName + " WHERE " + primaryKeyColumn + " = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("[ORM GetById Error en " + tableName + "]: " + e.getMessage());
        }
        return null;
    }

    /**
     * Obtiene todos los registros de la tabla.
     * @return Lista de entidades.
     */
    @Override
    public final List<T> get() {
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("[ORM GetAll Error en " + tableName + "]: " + e.getMessage());
        }
        return list;
    }

    /**
     * Busca registros según un criterio o filtro.
     * @param filter Criterio de búsqueda.
     * @return Lista de entidades filtradas.
     */
    @Override
    public final List<T> get(String filter) {
        List<T> list = new ArrayList<>();
        String sql = buildSearchSQL(filter);
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            bindSearchParams(stmt, filter);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("[ORM Search Error en " + tableName + "]: " + e.getMessage());
        }
        return list;
    }

    /**
     * Verifica si existe una clave en otra tabla.
     * @param targetTable Tabla referenciada.
     * @param targetColumn Columna PK de la tabla referenciada.
     * @param fkValue Valor a comprobar.
     * @return true si existe el registro padre.
     */
    protected final boolean existsForeignKey(String targetTable, String targetColumn, int fkValue) {
        String sql = "SELECT 1 FROM " + targetTable + " WHERE " + targetColumn + " = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, fkValue);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }

    protected abstract String buildInsertSQL(T entity);
    protected abstract void bindInsertParams(PreparedStatement stmt, T entity) throws SQLException;
    protected abstract void assignGeneratedId(T entity, int generatedId);
    protected abstract String buildUpdateSQL(T entity);
    protected abstract void bindUpdateParams(PreparedStatement stmt, T entity) throws SQLException;
    protected abstract String buildSearchSQL(String filter);
    protected abstract void bindSearchParams(PreparedStatement stmt, String filter) throws SQLException;
    protected abstract T mapResultSet(ResultSet rs) throws SQLException;
    protected abstract boolean validateForeignKeys(T entity);
}