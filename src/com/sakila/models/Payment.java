package com.sakila.models;

import java.time.LocalDateTime;

/**
 * Representa un pago o cobranza asociada a cliente y renta
 * @author Estudiante UASD
 * @version 1.0
 */
public final class Payment {
    private int paymentId;
    private int customerId;
    private int staffId;
    private Integer rentalId;
    private double amount;
    private LocalDateTime paymentDate;

    /** Constructor vacío por defecto. */
    public Payment() {}

    /**
     * Constructor con campos de Payment.
     * @param paymentId ID del pago.
     * @param customerId ID del cliente.
     * @param staffId ID del empleado.
     * @param rentalId ID de la renta (puede ser null).
     * @param amount Monto cobrado.
     * @param paymentDate Fecha del pago.
     */
    public Payment(int paymentId, int customerId, int staffId, Integer rentalId,
                   double amount, LocalDateTime paymentDate) {
        this.paymentId = paymentId;
        this.customerId = customerId;
        this.staffId = staffId;
        this.rentalId = rentalId;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public int getStaffId() { return staffId; }
    public void setStaffId(int staffId) { this.staffId = staffId; }
    public Integer getRentalId() { return rentalId; }
    public void setRentalId(Integer rentalId) { this.rentalId = rentalId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }

    @Override
    public String toString() {
        return "Payment{id=" + paymentId + ", customerId=" + customerId +
               ", rentalId=" + rentalId + ", amount=" + amount + "}";
    }
}