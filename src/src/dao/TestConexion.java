package dao;

import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        try (Connection con = Conexion.getConexion()) {
            System.out.println("CONEXIÓN EXITOSA A MYSQL (RAILWAY)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
