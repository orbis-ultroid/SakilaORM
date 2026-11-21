package com.sakila.models;

/**
 * Representa la entidad Customer de Sakila
 * @author Estudiante UASD
 * @version 1.0
 */
public final class Customer {
    private int customerId;
    private int storeId;
    private String firstName;
    private String lastName;
    private String email;
    private int addressId;
    private boolean active;

    /** Constructor vacío por defecto. */
    public Customer() {}

    /**
     * Constructor completo de Customer.
     * @param customerId ID del cliente.
     * @param storeId ID de tienda asignada.
     * @param firstName Nombre.
     * @param lastName Apellido.
     * @param email Correo electrónico.
     * @param addressId ID de dirección.
     * @param active Estado activo/inactivo.
     */
    public Customer(int customerId, int storeId, String firstName, String lastName,
                    String email, int addressId, boolean active) {
        this.customerId = customerId;
        this.storeId = storeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.addressId = addressId;
        this.active = active;
    }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public int getStoreId() { return storeId; }
    public void setStoreId(int storeId) { this.storeId = storeId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getAddressId() { return addressId; }
    public void setAddressId(int addressId) { this.addressId = addressId; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        return "Customer{id=" + customerId + ", name='" + firstName + " " + lastName +
               "', email='" + email + "', active=" + active + "}";
    }
}