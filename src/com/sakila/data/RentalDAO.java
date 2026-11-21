package com.sakila.data;

import com.sakila.models.Rental;
import java.sql.*;

/**
 * Implementación concreta y final de DataContext para la tabla rental.
 * @author Estudiante UASD
 * @version 1.0
 */
public final class RentalDAO extends DataContext<Rental> {

    public RentalDAO() {
        super("rental", "rental_id");
    }

    @Override
    protected String buildInsertSQL(Rental entity) {
        return "INSERT INTO rental (rental_date, inventory_id, customer_id, return_date, staff_id) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected void bindInsertParams(PreparedStatement stmt, Rental entity) throws SQLException {
        stmt.setTimestamp(1, Timestamp.valueOf(entity.getRentalDate()));
        stmt.setInt(2, entity.getInventoryId());
        stmt.setInt(3, entity.getCustomerId());
        if (entity.getReturnDate() != null) {
            stmt.setTimestamp(4, Timestamp.valueOf(entity.getReturnDate()));
        } else {
            stmt.setNull(4, Types.TIMESTAMP);
        }
        stmt.setInt(5, entity.getStaffId());
    }

    @Override
    protected void assignGeneratedId(Rental entity, int generatedId) {
        entity.setRentalId(generatedId);
    }

    @Override
    protected String buildUpdateSQL(Rental entity) {
        return "UPDATE rental SET rental_date = ?, inventory_id = ?, customer_id = ?, return_date = ?, staff_id = ? WHERE rental_id = ?";
    }

    @Override
    protected void bindUpdateParams(PreparedStatement stmt, Rental entity) throws SQLException {
        bindInsertParams(stmt, entity);
        stmt.setInt(6, entity.getRentalId());
    }

    @Override
    protected String buildSearchSQL(String filter) {
        return "SELECT * FROM rental WHERE customer_id = ?";
    }

    @Override
    protected void bindSearchParams(PreparedStatement stmt, String filter) throws SQLException {
        stmt.setInt(1, Integer.parseInt(filter));
    }

    @Override
    protected Rental mapResultSet(ResultSet rs) throws SQLException {
        Timestamp ret = rs.getTimestamp("return_date");
        return new Rental(
            rs.getInt("rental_id"),
            rs.getTimestamp("rental_date").toLocalDateTime(),
            rs.getInt("inventory_id"),
            rs.getInt("customer_id"),
            ret != null ? ret.toLocalDateTime() : null,
            rs.getInt("staff_id")
        );
    }

    @Override
    protected boolean validateForeignKeys(Rental entity) {
        return existsForeignKey("inventory", "inventory_id", entity.getInventoryId()) &&
               existsForeignKey("customer", "customer_id", entity.getCustomerId()) &&
               existsForeignKey("staff", "staff_id", entity.getStaffId());
    }
}