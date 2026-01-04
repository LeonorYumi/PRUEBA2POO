package service;

import dao.UsuarioDao;
import model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

public class UsuariosService {

    private final UsuarioDao usuarioDAO = new UsuarioDao();

    // LOGIN
    public Usuario login(String username, String passwordPlano) {

        //  buscar usuario solo por username
        Usuario usuario = usuarioDAO.buscarPorUsername(username);

        if (usuario == null) {
            return null;
        }

        // usuario inactivo
        if (!usuario.isActivo()) {
            return null;
        }

        // validar contraseña con BCrypt
        if (BCrypt.checkpw(passwordPlano, usuario.getPassword())) {
            return usuario;
        }

        return null;
    }

    // CREAR USUARIO (ADMIN)
    public boolean crearUsuario(Usuario u) {

        // hash de contraseña
        String hash = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());
        u.setPassword(hash);

        u.setActivo(true);

        return usuarioDAO.crear(u);
    }
}
