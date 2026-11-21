package com.sakila.data;

import com.sakila.models.Customer;
import java.sql.*;

/**
 * Implementación concreta y final de DataContext para la tabla customer.
 * @author Estudiante UASD
 * @version 1.0
 */
public final class CustomerDAO extends DataContext<Customer> {

    public CustomerDAO() {
        super("customer", "customer_id");
    }

    @Override
    protected String buildInsertSQL(Customer entity) {
        return "INSERT INTO customer (store_id, first_name, last_name, email, address_id, active) VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected void bindInsertParams(PreparedStatement stmt, Customer entity) throws SQLException {
        stmt.setInt(1, entity.getStoreId());
        stmt.setString(2, entity.getFirstName());
        stmt.setString(3, entity.getLastName());
        stmt.setString(4, entity.getEmail());
        stmt.setInt(5, entity.getAddressId());
        stmt.setBoolean(6, entity.isActive());
    }

    @Override
    protected void assignGeneratedId(Customer entity, int generatedId) {
        entity.setCustomerId(generatedId);
    }

    @Override
    protected String buildUpdateSQL(Customer entity) {
        return "UPDATE customer SET store_id = ?, first_name = ?, last_name = ?, email = ?, address_id = ?, active = ? WHERE customer_id = ?";
    }

    @Override
    protected void bindUpdateParams(PreparedStatement stmt, Customer entity) throws SQLException {
        bindInsertParams(stmt, entity);
        stmt.setInt(7, entity.getCustomerId());
    }

    @Override
    protected String buildSearchSQL(String filter) {
        return "SELECT * FROM customer WHERE first_name LIKE ? OR last_name LIKE ? OR email LIKE ?";
    }

    @Override
    protected void bindSearchParams(PreparedStatement stmt, String filter) throws SQLException {
        String p = "%" + filter + "%";
        stmt.setString(1, p);
        stmt.setString(2, p);
        stmt.setString(3, p);
    }

    @Override
    protected Customer mapResultSet(ResultSet rs) throws SQLException {
        return new Customer(
            rs.getInt("customer_id"),
            rs.getInt("store_id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email"),
            rs.getInt("address_id"),
            rs.getBoolean("active")
        );
    }

    @Override
    protected boolean validateForeignKeys(Customer entity) {
        return existsForeignKey("store", "store_id", entity.getStoreId()) &&
               existsForeignKey("address", "address_id", entity.getAddressId());
    }
}