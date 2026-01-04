package dao;

import model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDao {

    // LOGIN (autenticación)
    public Usuario login(String username, String passwordHash) {

        Usuario usuario = null;

        String sql = """
                SELECT id, nombre, cedula, username, rol, activo
                FROM usuario
                WHERE username = ? AND password = ?
                """;

        try (
                Connection con = new Conexion().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, username);
            ps.setString(2, passwordHash);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCedula(rs.getString("cedula"));
                usuario.setUsername(rs.getString("username"));
                usuario.setRol(rs.getString("rol"));
                usuario.setActivo(rs.getBoolean("activo"));
            }

        } catch (SQLException e) {
            System.out.println("Error en login UsuarioDAO");
            System.out.println(e.getMessage());
        }

        return usuario;
    }

    // CREAR USUARIO (ADMIN)
    public boolean crear(Usuario u) {

        String sql = """
                INSERT INTO usuario
                (nombre, cedula, username, password, rol, activo)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection con = new Conexion().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getCedula());
            ps.setString(3, u.getUsername());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.getRol());
            ps.setBoolean(6, u.isActivo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al crear usuario");
            System.out.println(e.getMessage());
            return false;
        }
    }

    // ACTIVAR / DESACTIVAR
    public boolean cambiarEstado(int id, boolean activo) {

        String sql = "UPDATE usuario SET activo=? WHERE id=?";

        try (
                Connection con = new Conexion().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setBoolean(1, activo);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al cambiar estado usuario");
            System.out.println(e.getMessage());
            return false;
        }
    }

    // BUSCAR POR USERNAME (LOGIN)
    public Usuario buscarPorUsername(String username) {

        Usuario usuario = null;

        String sql = """
            SELECT id, nombre, cedula, username, password, rol, activo
            FROM usuario
            WHERE username = ?
            """;

        try (
                Connection con = new Conexion().getConexion();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCedula(rs.getString("cedula"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                usuario.setRol(rs.getString("rol"));
                usuario.setActivo(rs.getBoolean("activo"));
            }

        } catch (Exception e) {
            System.out.println("Error buscarPorUsername");
            System.out.println(e.getMessage());
        }

        return usuario;
    }

}
