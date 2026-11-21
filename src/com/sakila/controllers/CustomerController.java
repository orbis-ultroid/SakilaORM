package com.sakila.controllers;

import com.sakila.data.CustomerDAO;
import com.sakila.models.Customer;
import com.sakila.otros.Validator;
import java.util.List;

/**
 * Controlador MVC para la gestión de clientes (Customer) con validación por Regex.
 * @author Estudiante UASD
 * @version 1.0
 */
public class CustomerController {
    private final CustomerDAO customerDAO;
    private List<Customer> customerCache;

    /** Inicializa el controlador y carga la lista de clientes. */
    public CustomerController() {
        this.customerDAO = new CustomerDAO();
        refresh();
    }

    /** Actualiza la lista de clientes en memoria. */
    public void refresh() {
        this.customerCache = customerDAO.get();
    }

    /**
     * Devuelve la lista de clientes.
     * @return Lista de Customer.
     */
    public List<Customer> getAllCustomers() {
        return customerCache;
    }

    /**
     * Registra un cliente validando previamente su correo electrónico.
     * @param customer Cliente a registrar.
     * @return true si se registró correctamente.
     */
    public boolean registerCustomer(Customer customer) {
        if (!Validator.isValidEmail(customer.getEmail())) {
            System.err.println("[Validación] Correo electrónico inválido.");
            return false;
        }
        boolean ok = customerDAO.post(customer);
        if (ok) refresh();
        return ok;
    }

    /**
     * Actualiza un cliente validando su correo electrónico.
     * @param customer Cliente con datos actualizados.
     * @return true si se actualizó.
     */
    public boolean updateCustomer(Customer customer) {
        if (!Validator.isValidEmail(customer.getEmail())) {
            System.err.println("[Validación] Correo electrónico inválido.");
            return false;
        }
        boolean ok = customerDAO.put(customer);
        if (ok) refresh();
        return ok;
    }

    /**
     * Elimina un cliente por ID.
     * @param id Identificador del cliente.
     * @return true si fue eliminado.
     */
    public boolean deleteCustomer(int id) {
        boolean ok = customerDAO.delete(id);
        if (ok) refresh();
        return ok;
    }
}