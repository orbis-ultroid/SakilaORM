package com.sakila.models;

/**
 * Representa la entidad Store de la base de datos Sakila.
 * @author Estudiante UASD
 * @version 1.0
 */
public final class Store {
    private int storeId;
    private int managerStaffId;
    private int addressId;

    /** Constructor vacío por defecto. */
    public Store() {}

    /**
     * Constructor con campos de Store.
     * @param storeId Identificador de la tienda.
     * @param managerStaffId ID del empleado gerente.
     * @param addressId ID de dirección de la tienda.
     */
    public Store(int storeId, int managerStaffId, int addressId) {
        this.storeId = storeId;
        this.managerStaffId = managerStaffId;
        this.addressId = addressId;
    }

    public int getStoreId() { return storeId; }
    public void setStoreId(int storeId) { this.storeId = storeId; }
    public int getManagerStaffId() { return managerStaffId; }
    public void setManagerStaffId(int managerStaffId) { this.managerStaffId = managerStaffId; }
    public int getAddressId() { return addressId; }
    public void setAddressId(int addressId) { this.addressId = addressId; }

    @Override
    public String toString() {
        return "Store{storeId=" + storeId + ", managerStaffId=" + managerStaffId + ", addressId=" + addressId + "}";
    }
}