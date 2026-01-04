package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL =
            "jdbc:mysql://tramway.proxy.rlwy.net:33551/railway?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "RwVGQrjWEwDFnQciklRZdsrTwDCqodxS";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
