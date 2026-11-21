package com.sakila.reports;

import com.sakila.data.DatabaseConnection;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Servicio de reportes, estadísticas con HashMap, wildcards (?) y exportación a CSV/JSON.
 * @author Estudiante UASD
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public final class ReportService {

    private ReportService() {}

    /**
     * Imprime en consola cualquier lista genérica usando wildcards (?).
     * @param title Título del listado.
     * @param items Lista de cualquier tipo de objeto.
     */
    public static void printGenericList(String title, List<?> items) {
        System.out.println("\n=== " + title + " (Total: " + items.size() + ") ===");
        for (Object item : items) {
            System.out.println("  -> " + item.toString());
        }
    }

    /**
     * Exporta una colección de filas (Map) a un archivo CSV.
     * @param filePath Ruta de salida (.csv).
     * @param rows Filas a exportar.
     */
    public static void exportToCSV(String filePath, List rows) {
        if (rows == null || rows.isEmpty()) {
            System.out.println("[Reporte] No hay registros para exportar a CSV.");
            return;
        }
        Map firstRow = (Map) rows.get(0);
        List headers = new ArrayList(firstRow.keySet());
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println(joinStrings(headers, ","));
            for (Object item : rows) {
                Map row = (Map) item;
                List values = new ArrayList();
                for (Object hObj : headers) {
                    String h = hObj.toString();
                    Object val = row.get(h);
                    String clean = (val == null) ? "" : val.toString().replace(",", ";");
                    values.add("\"" + clean + "\"");
                }
                writer.println(joinStrings(values, ","));
            }
            System.out.println("[OK] CSV exportado en: " + filePath);
        } catch (IOException e) {
            System.err.println("[Error CSV]: " + e.getMessage());
        }
    }

    private static String joinStrings(List list, String sep) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) sb.append(sep);
            sb.append(list.get(i));
        }
        return sb.toString();
    }

    /**
     * Exporta una colección de filas (Map) a un archivo JSON.
     * @param filePath Ruta de salida (.json).
     * @param rows Filas a exportar.
     */
    public static void exportToJSON(String filePath, List rows) {
        if (rows == null) {
            System.out.println("[Reporte] No hay registros para exportar a JSON.");
            return;
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("[");
            for (int i = 0; i < rows.size(); i++) {
                Map row = (Map) rows.get(i);
                writer.print("  {");
                int j = 0;
                for (Object keyObj : row.keySet()) {
                    String key = keyObj.toString();
                    Object val = row.get(keyObj);
                    String formatted = (val instanceof Number || val instanceof Boolean)
                            ? val.toString()
                            : "\"" + (val == null ? "" : val.toString().replace("\"", "\\\"")) + "\"";
                    writer.print("\"" + key + "\": " + formatted);
                    if (j < row.size() - 1) writer.print(", ");
                    j++;
                }
                writer.println(i < rows.size() - 1 ? "}," : "}");
            }
            writer.println("]");
            System.out.println("[OK] JSON exportado en: " + filePath);
        } catch (IOException e) {
            System.err.println("[Error JSON]: " + e.getMessage());
        }
    }

    /**
     * Obtiene un listado de filas de una tabla de Sakila para exportar.
     * @param tableName Nombre de la tabla.
     * @return Lista de mapas columna -> valor.
     */
    public static List getTableSnapshot(String tableName) {
        List rows = new ArrayList();
        String sql = "SELECT * FROM " + tableName + " LIMIT 500";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();
            while (rs.next()) {
                Map row = new LinkedHashMap();
                for (int i = 1; i <= cols; i++) {
                    row.put(meta.getColumnLabel(i), rs.getObject(i));
                }
                rows.add(row);
            }
        } catch (SQLException e) {
            System.err.println("[Error Listado Tabla]: " + e.getMessage());
        }
        return rows;
    }

    /**
     * Estadística de inventario: cantidad de copias por película usando HashMap.
     * @return Mapa título de película -> cantidad en inventario.
     */
    public static Map getInventoryCountByFilm() {
        Map stats = new HashMap();
        String sql = "SELECT f.title, COUNT(i.inventory_id) AS total " +
                     "FROM film f LEFT JOIN inventory i ON f.film_id = i.film_id " +
                     "GROUP BY f.film_id, f.title";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                stats.put(rs.getString("title"), rs.getInt("total"));
            }
        } catch (SQLException e) {
            System.err.println("[Error Stats Inventario]: " + e.getMessage());
        }
        return stats;
    }

    /**
     * Estadística de actores: cantidad de películas en las que participa cada actor.
     * @return Mapa nombre de actor -> cantidad de películas.
     */
    public static Map getFilmCountByActor() {
        Map stats = new HashMap();
        String sql = "SELECT CONCAT(a.first_name, ' ', a.last_name) AS actor_name, COUNT(fa.film_id) AS total_films " +
                     "FROM actor a JOIN film_actor fa ON a.actor_id = fa.actor_id " +
                     "GROUP BY a.actor_id";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                stats.put(rs.getString("actor_name"), rs.getInt("total_films"));
            }
        } catch (SQLException e) {
            System.err.println("[Error Stats Actores]: " + e.getMessage());
        }
        return stats;
    }

    /**
     * Imprime totales y promedios de rentas y pagos por tienda, ciudad, país o cliente.
     * @param dimension "store", "city", "country" o "customer".
     */
    public static void printRevenueStatsByDimension(String dimension) {
        String groupCol;
        switch (dimension.toLowerCase()) {
            case "country": groupCol = "co.country"; break;
            case "city": groupCol = "ci.city"; break;
            case "customer": groupCol = "CONCAT(c.first_name, ' ', c.last_name)"; break;
            default: groupCol = "CONCAT('Tienda #', s.store_id)"; break;
        }
        String sql = "SELECT " + groupCol + " AS grupo, COUNT(r.rental_id) AS total_rentas, " +
                     "SUM(p.amount) AS total_cobrado, AVG(p.amount) AS promedio_pago " +
                     "FROM payment p " +
                     "JOIN customer c ON p.customer_id = c.customer_id " +
                     "JOIN store s ON c.store_id = s.store_id " +
                     "JOIN address a ON c.address_id = a.address_id " +
                     "JOIN city ci ON a.city_id = ci.city_id " +
                     "JOIN country co ON ci.country_id = co.country_id " +
                     "LEFT JOIN rental r ON p.rental_id = r.rental_id " +
                     "GROUP BY grupo ORDER BY total_cobrado DESC LIMIT 20";

        System.out.println("\n=== RENTAS Y PAGOS POR " + dimension.toUpperCase() + " ===");
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.printf("%-25s | Rentas: %4d | Total: $%10.2f | Promedio: $%6.2f%n",
                        rs.getString("grupo"),
                        rs.getInt("total_rentas"),
                        rs.getDouble("total_cobrado"),
                        rs.getDouble("promedio_pago"));
            }
        } catch (SQLException e) {
            System.err.println("[Error Stats Cobranzas]: " + e.getMessage());
        }
    }

    /**
     * Calcula el Aging de Cuentas por Cobrar sobre rentas sin devolver (return_date IS NULL).
     */
    public static void printAccountsReceivableAging() {
        Map aging = new LinkedHashMap();
        aging.put("0-30 días", 0.0);
        aging.put("31-60 días", 0.0);
        aging.put("61-90 días", 0.0);
        aging.put("> 90 días (Vencido)", 0.0);

        String sql = "SELECT r.rental_date, f.replacement_cost " +
                     "FROM rental r " +
                     "JOIN inventory i ON r.inventory_id = i.inventory_id " +
                     "JOIN film f ON i.film_id = f.film_id " +
                     "WHERE r.return_date IS NULL";

        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            LocalDateTime now = LocalDateTime.now();
            while (rs.next()) {
                LocalDateTime rentalDate = rs.getTimestamp("rental_date").toLocalDateTime();
                double cost = rs.getDouble("replacement_cost");
                long days = ChronoUnit.DAYS.between(rentalDate, now);

                if (days <= 30) {
                    aging.put("0-30 días", ((Double) aging.get("0-30 días")) + cost);
                } else if (days <= 60) {
                    aging.put("31-60 días", ((Double) aging.get("31-60 días")) + cost);
                } else if (days <= 90) {
                    aging.put("61-90 días", ((Double) aging.get("61-90 días")) + cost);
                } else {
                    aging.put("> 90 días (Vencido)", ((Double) aging.get("> 90 días (Vencido)")) + cost);
                }
            }
        } catch (SQLException e) {
            System.err.println("[Error Aging CxC]: " + e.getMessage());
        }

        System.out.println("\n=== AGING DE CUENTAS POR COBRAR ===");
        for (Object keyObj : aging.keySet()) {
            String key = keyObj.toString();
            System.out.printf("Rango %-22s : $%10.2f%n", key, aging.get(keyObj));
        }
    }
}