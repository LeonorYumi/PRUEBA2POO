package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de conexión a MySQL Railway
 */
public class Conexion {

    // Credenciales de Railway
    private static final String URL =
            "jdbc:mysql://tramway.proxy.rlwy.net:33551/railway";
    private static final String USER = "root";
    private static final String PASSWORD = "RwVGQrjWEwDFnQciklRZdsrTwDCqodxS";

    /**
     * Obtiene una conexión a la base de datos
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL no encontrado");
        }
    }

    // Alias del método (compatibilidad)
    public static Connection getConexion() throws SQLException {
        return getConnection();
    }

    /**
     * Prueba la conexión
     */
    public static boolean probarConexion() {
        try (Connection conn = getConnection()) {
            System.out.println("✓ Conexión exitosa a MySQL Railway");
            System.out.println("Base de datos: " );
            return true;
        } catch (SQLException e) {
            System.err.println("✗ Error de conexión: " + e.getMessage());
            return false;
        }
    }

    /**
     * Main para probar la conexión
     */
    public static void main(String[] args) {
        System.out.println("=== PROBANDO CONEXIÓN A RAILWAY ===");
        if (probarConexion()) {
            System.out.println("¡Todo OK! Puedes usar la conexión");
        } else {
            System.out.println("Revisa tus credenciales en Conexion.java");
        }
    }
}