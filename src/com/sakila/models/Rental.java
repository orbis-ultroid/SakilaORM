package com.sakila.models;

import java.time.LocalDateTime;

/**
 * Representa una renta de película vinculada a Inventory y Customer
 * @author Estudiante UASD
 * @version 1.0
 */
public final class Rental {
    private int rentalId;
    private LocalDateTime rentalDate;
    private int inventoryId;
    private int customerId;
    private LocalDateTime returnDate;
    private int staffId;
    private Inventory inventory;
    private Customer customer;

    /** Constructor vacío por defecto. */
    public Rental() {}

    /**
     * Constructor con campos de Rental.
     * @param rentalId ID de la renta.
     * @param rentalDate Fecha y hora de renta.
     * @param inventoryId ID de copia en inventario.
     * @param customerId ID del cliente.
     * @param returnDate Fecha de devolución (puede ser null si sigue activa).
     * @param staffId ID del empleado.
     */
    public Rental(int rentalId, LocalDateTime rentalDate, int inventoryId, int customerId,
                  LocalDateTime returnDate, int staffId) {
        this.rentalId = rentalId;
        this.rentalDate = rentalDate;
        this.inventoryId = inventoryId;
        this.customerId = customerId;
        this.returnDate = returnDate;
        this.staffId = staffId;
    }

    public int getRentalId() { return rentalId; }
    public void setRentalId(int rentalId) { this.rentalId = rentalId; }
    public LocalDateTime getRentalDate() { return rentalDate; }
    public void setRentalDate(LocalDateTime rentalDate) { this.rentalDate = rentalDate; }
    public int getInventoryId() { return inventoryId; }
    public void setInventoryId(int inventoryId) { this.inventoryId = inventoryId; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public LocalDateTime getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDateTime returnDate) { this.returnDate = returnDate; }
    public int getStaffId() { return staffId; }
    public void setStaffId(int staffId) { this.staffId = staffId; }
    public Inventory getInventory() { return inventory; }
    public void setInventory(Inventory inventory) { this.inventory = inventory; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    @Override
    public String toString() {
        return "Rental{id=" + rentalId + ", inventoryId=" + inventoryId + ", customerId=" + customerId +
               ", rentalDate=" + rentalDate + ", returnDate=" + returnDate + "}";
    }
}