package com.sakila.data;

import com.sakila.models.Payment;
import java.sql.*;

/**
 * Implementación concreta y final de DataContext para la tabla payment.
 * @author Estudiante UASD
 * @version 1.0
 */
public final class PaymentDAO extends DataContext<Payment> {

    public PaymentDAO() {
        super("payment", "payment_id");
    }

    @Override
    protected String buildInsertSQL(Payment entity) {
        return "INSERT INTO payment (customer_id, staff_id, rental_id, amount, payment_date) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected void bindInsertParams(PreparedStatement stmt, Payment entity) throws SQLException {
        stmt.setInt(1, entity.getCustomerId());
        stmt.setInt(2, entity.getStaffId());
        if (entity.getRentalId() != null) {
            stmt.setInt(3, entity.getRentalId());
        } else {
            stmt.setNull(3, Types.INTEGER);
        }
        stmt.setDouble(4, entity.getAmount());
        stmt.setTimestamp(5, Timestamp.valueOf(entity.getPaymentDate()));
    }

    @Override
    protected void assignGeneratedId(Payment entity, int generatedId) {
        entity.setPaymentId(generatedId);
    }

    @Override
    protected String buildUpdateSQL(Payment entity) {
        return "UPDATE payment SET customer_id = ?, staff_id = ?, rental_id = ?, amount = ?, payment_date = ? WHERE payment_id = ?";
    }

    @Override
    protected void bindUpdateParams(PreparedStatement stmt, Payment entity) throws SQLException {
        bindInsertParams(stmt, entity);
        stmt.setInt(6, entity.getPaymentId());
    }

    @Override
    protected String buildSearchSQL(String filter) {
        return "SELECT * FROM payment WHERE customer_id = ?";
    }

    @Override
    protected void bindSearchParams(PreparedStatement stmt, String filter) throws SQLException {
        stmt.setInt(1, Integer.parseInt(filter));
    }

    @Override
    protected Payment mapResultSet(ResultSet rs) throws SQLException {
        int rId = rs.getInt("rental_id");
        Integer rentalId = rs.wasNull() ? null : rId;
        return new Payment(
            rs.getInt("payment_id"),
            rs.getInt("customer_id"),
            rs.getInt("staff_id"),
            rentalId,
            rs.getDouble("amount"),
            rs.getTimestamp("payment_date").toLocalDateTime()
        );
    }

    @Override
    protected boolean validateForeignKeys(Payment entity) {
        boolean baseValid = existsForeignKey("customer", "customer_id", entity.getCustomerId()) &&
                            existsForeignKey("staff", "staff_id", entity.getStaffId());
        if (entity.getRentalId() != null) {
            return baseValid && existsForeignKey("rental", "rental_id", entity.getRentalId());
        }
        return baseValid;
    }
}