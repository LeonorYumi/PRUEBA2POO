package ui;

import model.Usuario;
import service.UsuariosService;

import javax.swing.*;

public class LoginFrame extends JFrame {

    private JPanel Login;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton ingresarButton;

    private final UsuariosService usuarioService = new UsuariosService();
    private int intentos = 0;

    public LoginFrame() {
        setTitle("Login - Sistema de Licencias");
        setContentPane(Login);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ingresarButton.addActionListener(e -> login());
    }

    private void login() {

        String username = textField1.getText();
        String password = new String(passwordField1.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese usuario y contraseña",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usuario = usuarioService.login(username, password);

        if (usuario == null) {
            intentos++;
            JOptionPane.showMessageDialog(this,
                    "Credenciales incorrectas",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

            if (intentos >= 3) {
                JOptionPane.showMessageDialog(this,
                        "Demasiados intentos fallidos",
                        "Bloqueado",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Bienvenido " + usuario.getNombre());

        dispose();
    }
}
