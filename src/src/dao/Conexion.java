package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL =
            "jdbc:mysql://root:RwVGQrjWEwDFnQciklRZdsrTwDCqodxS@tramway.proxy.rlwy.net:33551/railway";
    private static final String USER = "root";
    private static final String PASS = "RwVGQrjWEwDFnQciklRZdsrTwDCqodxS";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
