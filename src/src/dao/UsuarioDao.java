package dao;

import model.Usuario;
import java.sql.*;

public class UsuarioDao {

    /**
     * Busca un usuario por username
     */
    public Usuario buscarPorUsername(String username) {
        String sql = "SELECT u.id, u.nombre, u.cedula, u.username, u.password_hash, " +
                "u.rol_id, r.nombre as rol_nombre, u.activo " +
                "FROM usuarios u " +
                "INNER JOIN roles r ON u.rol_id = r.id " +
                "WHERE u.username = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setCedula(rs.getString("cedula"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPasswordHash(rs.getString("password_hash"));
                usuario.setRolId(rs.getInt("rol_id"));
                usuario.setRolNombre(rs.getString("rol_nombre"));
                usuario.setActivo(rs.getBoolean("activo"));

                return usuario;
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar usuario: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * Lista todos los usuarios
     */
    public void listarTodos() {
        String sql = "SELECT u.id, u.nombre, u.username, r.nombre as rol_nombre, u.activo " +
                "FROM usuarios u " +
                "INNER JOIN roles r ON u.rol_id = r.id " +
                "ORDER BY u.id";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            System.out.println("\n=== USUARIOS EN LA BASE DE DATOS ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Username: " + rs.getString("username"));
                System.out.println("Rol: " + rs.getString("rol_nombre"));
                System.out.println("Activo: " + (rs.getBoolean("activo") ? "Sí" : "No"));
                System.out.println("------------------------");
            }

        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Inserta un nuevo usuario
     */
    public boolean insertar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, cedula, username, password_hash, rol_id, activo) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCedula());
            stmt.setString(3, usuario.getUsername());
            stmt.setString(4, usuario.getPasswordHash());
            stmt.setInt(5, usuario.getRolId());
            stmt.setBoolean(6, usuario.isActivo());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al insertar usuario: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * Actualiza un usuario
     */
    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, cedula = ?, username = ?, " +
                "password_hash = ?, rol_id = ?, activo = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCedula());
            stmt.setString(3, usuario.getUsername());
            stmt.setString(4, usuario.getPasswordHash());
            stmt.setInt(5, usuario.getRolId());
            stmt.setBoolean(6, usuario.isActivo());
            stmt.setInt(7, usuario.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * Main para probar el DAO
     */
    public static void main(String[] args) {
        UsuarioDao dao = new UsuarioDao();

        // Listar usuarios
        dao.listarTodos();

        // Buscar admin
        System.out.println("\n=== BUSCANDO USUARIO ADMIN ===");
        Usuario admin = dao.buscarPorUsername("admin");
        if (admin != null) {
            System.out.println("Usuario encontrado: " + admin);
        } else {
            System.out.println("Usuario no encontrado");
        }
    }
}