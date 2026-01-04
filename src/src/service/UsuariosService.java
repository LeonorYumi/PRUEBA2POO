package service;

import dao.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuariosService {

    // ESTE MÉTODO ES EL QUE TE FALTA
    public boolean login(String usuario, String password) {

        String sql = "SELECT 1 FROM usuarios WHERE username = ? AND activo = true";

        try (
                Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, usuario);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (Exception e) {
            System.out.println("Error login: " + e.getMessage());
        }

        return false;
    }
}
