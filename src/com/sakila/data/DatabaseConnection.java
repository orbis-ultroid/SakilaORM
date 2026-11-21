package com.sakila.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Administra la conexión JDBC hacia la base de datos MySQL Sakila.
 * @author Estudiante UASD
 * @version 1.0
 */
public final class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/sakila?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "123456789";
    private static Connection instance;

    private DatabaseConnection() {}

    /**
     * Obtiene una conexión activa hacia la base de datos sakila.
     * @return Objeto Connection listo para consultas.
     * @throws SQLException Si ocurre un error de conexión.
     */
    public static Connection getConnection() throws SQLException {
        if (instance == null || instance.isClosed()) {
            instance = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return instance;
    }
}