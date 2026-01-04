package ui.login;

import dao.UsuarioDao;
import model.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JPanel login;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton btnIngresar;

    private UsuarioDao usuarioDao;
    private int intentosFallidos = 0;
    private static final int MAX_INTENTOS = 3;

    public LoginFrame() {
        usuarioDao = new UsuarioDao();

        setTitle("Sistema de Licencias - Login");
        setContentPane(login);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        configurarEventos();
    }

    private void configurarEventos() {
        // Evento del botón
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });

        // Enter en password
        passwordField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });
    }

    private void iniciarSesion() {
        // Verificar intentos
        if (intentosFallidos >= MAX_INTENTOS) {
            JOptionPane.showMessageDialog(this,
                    "Máximo de intentos alcanzado.\nLa aplicación se cerrará.",
                    "Acceso Bloqueado",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            return;
        }

        // Obtener datos
        String username = textField1.getText().trim();
        String password = new String(passwordField1.getPassword());

        // Validar campos vacíos
        if (username.isEmpty()) {
            mostrarError("Por favor ingrese su usuario");
            textField1.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            mostrarError("Por favor ingrese su contraseña");
            passwordField1.requestFocus();
            return;
        }

        // Deshabilitar botón mientras procesa
        btnIngresar.setEnabled(false);
        btnIngresar.setText("Verificando...");

        // Buscar usuario
        Usuario usuario = usuarioDao.buscarPorUsername(username);

        if (usuario == null) {
            loginFallido("Usuario no encontrado");
            return;
        }

        if (!usuario.isActivo()) {
            loginFallido("Usuario inactivo. Contacte al administrador.");
            return;
        }

        // Verificar contraseña
        if (BCrypt.checkpw(password, usuario.getPasswordHash())) {
            loginExitoso(usuario);
        } else {
            loginFallido("Contraseña incorrecta");
        }
    }

    private void loginExitoso(Usuario usuario) {
        JOptionPane.showMessageDialog(this,
                "¡Bienvenido/a " + usuario.getNombre() + "!",
                "Login Exitoso",
                JOptionPane.INFORMATION_MESSAGE);

        abrirMenuPorRol(usuario);
        this.dispose();
    }

    private void loginFallido(String mensaje) {
        intentosFallidos++;
        int intentosRestantes = MAX_INTENTOS - intentosFallidos;

        String mensajeCompleto = mensaje;
        if (intentosRestantes > 0) {
            mensajeCompleto += "\n\nIntentos restantes: " + intentosRestantes;
        }

        mostrarError(mensajeCompleto);

        passwordField1.setText("");
        passwordField1.requestFocus();

        btnIngresar.setEnabled(true);
        btnIngresar.setText("INICIAR SESIÓN");

        if (intentosRestantes == 0) {
            iniciarSesion(); // Llamar de nuevo para cerrar app
        }
    }

    private void abrirMenuPorRol(Usuario usuario) {
        if (usuario.esAdmin()) {
            JOptionPane.showMessageDialog(this,
                    "Abriendo Menú de ADMINISTRADOR...\n(Por implementar)",
                    "Admin",
                    JOptionPane.INFORMATION_MESSAGE);
            // TODO: MenuAdminFrame menuAdmin = new MenuAdminFrame(usuario);
            // menuAdmin.setVisible(true);
        } else if (usuario.esAnalista()) {
            JOptionPane.showMessageDialog(this,
                    "Abriendo Menú de ANALISTA...\n(Por implementar)",
                    "Analista",
                    JOptionPane.INFORMATION_MESSAGE);
            // TODO: MenuAnalistaFrame menuAnalista = new MenuAnalistaFrame(usuario);
            // menuAnalista.setVisible(true);
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private void limpiarCampos() {
        textField1.setText("");
        passwordField1.setText("");
        textField1.requestFocus();
    }

}
