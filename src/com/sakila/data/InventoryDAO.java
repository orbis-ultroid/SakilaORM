package com.sakila.data;

import com.sakila.models.Inventory;
import java.sql.*;

/**
 * Implementación concreta y final de DataContext para la tabla inventory.
 * @author Estudiante UASD
 * @version 1.0
 */
public final class InventoryDAO extends DataContext<Inventory> {

    public InventoryDAO() {
        super("inventory", "inventory_id");
    }

    @Override
    protected String buildInsertSQL(Inventory entity) {
        return "INSERT INTO inventory (film_id, store_id) VALUES (?, ?)";
    }

    @Override
    protected void bindInsertParams(PreparedStatement stmt, Inventory entity) throws SQLException {
        stmt.setInt(1, entity.getFilmId());
        stmt.setInt(2, entity.getStoreId());
    }

    @Override
    protected void assignGeneratedId(Inventory entity, int generatedId) {
        entity.setInventoryId(generatedId);
    }

    @Override
    protected String buildUpdateSQL(Inventory entity) {
        return "UPDATE inventory SET film_id = ?, store_id = ? WHERE inventory_id = ?";
    }

    @Override
    protected void bindUpdateParams(PreparedStatement stmt, Inventory entity) throws SQLException {
        stmt.setInt(1, entity.getFilmId());
        stmt.setInt(2, entity.getStoreId());
        stmt.setInt(3, entity.getInventoryId());
    }

    @Override
    protected String buildSearchSQL(String filter) {
        return "SELECT * FROM inventory WHERE film_id = ?";
    }

    @Override
    protected void bindSearchParams(PreparedStatement stmt, String filter) throws SQLException {
        stmt.setInt(1, Integer.parseInt(filter));
    }

    @Override
    protected Inventory mapResultSet(ResultSet rs) throws SQLException {
        return new Inventory(
            rs.getInt("inventory_id"),
            rs.getInt("film_id"),
            rs.getInt("store_id")
        );
    }

    @Override
    protected boolean validateForeignKeys(Inventory entity) {
        return existsForeignKey("film", "film_id", entity.getFilmId()) &&
               existsForeignKey("store", "store_id", entity.getStoreId());
    }
}