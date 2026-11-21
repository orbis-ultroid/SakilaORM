package com.sakila;

import com.sakila.controllers.*;
import com.sakila.models.*;
import com.sakila.otros.Validator;
import com.sakila.reports.ReportService;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Punto de entrada de consola para Sakila ORM Data Manager.
 * @author Estudiante UASD
 * @version 1.0
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final FilmController filmCtrl = new FilmController();
    private static final InventoryController invCtrl = new InventoryController();
    private static final CustomerController custCtrl = new CustomerController();
    private static final RentalController rentalCtrl = new RentalController();

    /**
     * Inicia el menú interactivo en consola.
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        int option;
        do {
            System.out.println("\n==============================================");
            System.out.println("   SAKILA ORM DATA MANAGER - CONSOLA MVC");
            System.out.println("==============================================");
            System.out.println("1. Gestión de Películas (CRUD Film)");
            System.out.println("2. Gestión de Inventario (CRUD Inventory)");
            System.out.println("3. Gestión de Clientes (CRUD Customer)");
            System.out.println("4. Gestión de Rentas y Devoluciones (Rental)");
            System.out.println("5. Reportes, Estadísticas y Exportación CSV/JSON");
            System.out.println("6. Validar Datos con Expresiones Regulares");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            option = readInt();

            switch (option) {
                case 1: menuFilms(); break;
                case 2: menuInventory(); break;
                case 3: menuCustomers(); break;
                case 4: menuRentals(); break;
                case 5: menuReports(); break;
                case 6: menuRegexTest(); break;
                case 0: System.out.println("Saliendo del sistema..."); break;
                default: System.out.println("Opción inválida.");
            }
        } while (option != 0);
    }

    private static void menuFilms() {
        System.out.println("\n--- GESTIÓN DE PELÍCULAS ---");
        System.out.println("1. Listar películas (primeras 15)");
        System.out.println("2. Buscar película por título/descripción");
        System.out.println("3. Registrar nueva película (POST)");
        System.out.println("4. Actualizar tarifa de renta (PUT)");
        System.out.println("5. Eliminar película (DELETE)");
        System.out.print("Opción: ");
        int op = readInt();

        switch (op) {
            case 1:
                List<Film> films = filmCtrl.getAllFilms();
                ReportService.printGenericList("Películas", films.subList(0, Math.min(15, films.size())));
                break;
            case 2:
                System.out.print("Texto a buscar: ");
                String q = scanner.nextLine();
                ReportService.printGenericList("Resultados", filmCtrl.searchFilms(q));
                break;
            case 3:
                System.out.print("Título: ");
                String title = scanner.nextLine();
                System.out.print("Descripción: ");
                String desc = scanner.nextLine();
                System.out.print("Año de estreno: ");
                int year = readInt();
                System.out.print("Tarifa de renta (ej. 4.99): ");
                double rate = readDouble();
                Film f = new Film(0, title, desc, year, 1, 3, rate, 19.99);
                System.out.println(filmCtrl.createFilm(f) ? "Película creada con ID: " + f.getFilmId() : "Error al crear.");
                break;
            case 4:
                System.out.print("ID de película a actualizar: ");
                int idUpd = readInt();
                Film existing = filmCtrl.getFilmById(idUpd);
                if (existing != null) {
                    System.out.print("Nueva tarifa de renta: ");
                    existing.setRentalRate(readDouble());
                    System.out.println(filmCtrl.updateFilm(existing) ? "Actualizada." : "Error al actualizar.");
                } else {
                    System.out.println("Película no encontrada.");
                }
                break;
            case 5:
                System.out.print("ID de película a eliminar: ");
                int idDel = readInt();
                System.out.println(filmCtrl.deleteFilm(idDel) ? "Eliminada." : "No se pudo eliminar.");
                break;
        }
    }

    private static void menuInventory() {
        System.out.println("\n--- GESTIÓN DE INVENTARIO ---");
        System.out.println("1. Ver inventario (primeros 15)");
        System.out.println("2. Agregar copia de película a tienda");
        System.out.println("3. Eliminar copia de inventario");
        System.out.print("Opción: ");
        int op = readInt();

        switch (op) {
            case 1:
                List<Inventory> list = invCtrl.getAllInventory();
                ReportService.printGenericList("Inventario", list.subList(0, Math.min(15, list.size())));
                break;
            case 2:
                System.out.print("ID de Película (film_id): ");
                int filmId = readInt();
                System.out.print("ID de Tienda (1 o 2): ");
                int storeId = readInt();
                System.out.println(invCtrl.addCopyToStore(filmId, storeId) ? "Copia agregada." : "Error al agregar.");
                break;
            case 3:
                System.out.print("ID de Inventario a eliminar: ");
                int invId = readInt();
                System.out.println(invCtrl.removeCopy(invId) ? "Eliminado." : "No se pudo eliminar.");
                break;
        }
    }

    private static void menuCustomers() {
        System.out.println("\n--- GESTIÓN DE CLIENTES ---");
        System.out.println("1. Listar clientes (primeros 15)");
        System.out.println("2. Registrar cliente");
        System.out.println("3. Eliminar cliente");
        System.out.print("Opción: ");
        int op = readInt();

        switch (op) {
            case 1:
                List<Customer> list = custCtrl.getAllCustomers();
                ReportService.printGenericList("Clientes", list.subList(0, Math.min(15, list.size())));
                break;
            case 2:
                System.out.print("Nombre: ");
                String fn = scanner.nextLine();
                System.out.print("Apellido: ");
                String ln = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine();
                Customer c = new Customer(0, 1, fn, ln, email, 1, true);
                System.out.println(custCtrl.registerCustomer(c) ? "Cliente registrado con ID: " + c.getCustomerId() : "Error al registrar.");
                break;
            case 3:
                System.out.print("ID de Cliente a eliminar: ");
                System.out.println(custCtrl.deleteCustomer(readInt()) ? "Cliente eliminado." : "No se pudo eliminar.");
                break;
        }
    }

    private static void menuRentals() {
        System.out.println("\n--- GESTIÓN DE RENTAS ---");
        System.out.println("1. Rentar película (crea Renta y Pago)");
        System.out.println("2. Registrar devolución de película");
        System.out.println("3. Verificar disponibilidad de copia en inventario");
        System.out.print("Opción: ");
        int op = readInt();

        switch (op) {
            case 1:
                System.out.print("ID de Inventario (inventory_id): ");
                int invId = readInt();
                System.out.print("ID de Cliente (customer_id): ");
                int custId = readInt();
                System.out.print("ID de Empleado (staff_id, ej. 1): ");
                int staffId = readInt();
                System.out.println(rentalCtrl.rentMovie(invId, custId, staffId)
                        ? "Renta y pago registrados exitosamente."
                        : "No se pudo completar la renta.");
                break;
            case 2:
                System.out.print("ID de Renta (rental_id): ");
                int rId = readInt();
                System.out.println(rentalCtrl.returnMovie(rId) ? "Devolución registrada." : "Error al devolver.");
                break;
            case 3:
                System.out.print("ID de Inventario: ");
                int checkId = readInt();
                System.out.println(rentalCtrl.isInventoryAvailable(checkId) ? "DISPONIBLE" : "RENTADA ACTUALMENTE");
                break;
        }
    }

    private static void menuReports() {
        System.out.println("\n--- REPORTES Y ESTADÍSTICAS ---");
        System.out.println("1. Exportar tabla a CSV y JSON");
        System.out.println("2. Estadística: Inventario por película (HashMap)");
        System.out.println("3. Estadística: Películas por actor (HashMap)");
        System.out.println("4. Estadística: Rentas y Pagos por Tienda, Ciudad, País y Cliente");
        System.out.println("5. Estadística: Aging de Cuentas por Cobrar");
        System.out.print("Opción: ");
        int op = readInt();

        switch (op) {
            case 1:
                System.out.print("Nombre de tabla (ej. film, actor, customer, rental): ");
                String tbl = scanner.nextLine().trim();
                List data = ReportService.getTableSnapshot(tbl);
                ReportService.exportToCSV(tbl + "_report.csv", data);
                ReportService.exportToJSON(tbl + "_report.json", data);
                break;
            case 2:
                Map invStats = ReportService.getInventoryCountByFilm();
                for (Object keyObj : invStats.keySet()) {
                    System.out.println(keyObj + " -> " + invStats.get(keyObj) + " copias");
                }
                break;
            case 3:
                Map actorStats = ReportService.getFilmCountByActor();
                for (Object keyObj : actorStats.keySet()) {
                    System.out.println(keyObj + " -> " + actorStats.get(keyObj) + " películas");
                }
                break;
            case 4:
                ReportService.printRevenueStatsByDimension("store");
                ReportService.printRevenueStatsByDimension("city");
                ReportService.printRevenueStatsByDimension("country");
                ReportService.printRevenueStatsByDimension("customer");
                break;
            case 5:
                ReportService.printAccountsReceivableAging();
                break;
        }
    }

    private static void menuRegexTest() {
        System.out.println("\n--- PRUEBA DE EXPRESIONES REGULARES ---");
        System.out.print("Ingrese Fecha (YYYY-MM-DD): ");
        System.out.println("Válida: " + Validator.isValidDate(scanner.nextLine()));
        System.out.print("Ingrese Cédula (000-0000000-0) o SSN (000-00-0000): ");
        System.out.println("Válida: " + Validator.isValidCedulaOrSSN(scanner.nextLine()));
        System.out.print("Ingrese Teléfono (ej. 809-555-1234): ");
        System.out.println("Válido: " + Validator.isValidPhone(scanner.nextLine()));
    }

    private static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número entero válido: ");
            }
        }
    }

    private static double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número decimal válido: ");
            }
        }
    }
}