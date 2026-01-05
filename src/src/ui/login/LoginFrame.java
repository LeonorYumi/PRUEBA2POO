package ui.login;

import dao.UsuarioDao;
import model.Usuario;
import org.mindrot.jbcrypt.BCrypt;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

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
        setSize(400, 500);
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        login = new JPanel();
        login.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(9, 18, new Insets(0, 0, 0, 0), -1, -1));
        login.setForeground(new Color(-7233353));
        final JLabel label1 = new JLabel();
        label1.setText("");
        login.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 16, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("");
        login.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Consolas", Font.PLAIN, 14, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setForeground(new Color(-15197666));
        label3.setText("Usuario");
        login.add(label3, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        login.add(textField1, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        passwordField1 = new JPasswordField();
        login.add(passwordField1, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$("Consolas", Font.PLAIN, 14, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setForeground(new Color(-15197666));
        label4.setText("Contraseña");
        login.add(label4, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnIngresar = new JButton();
        Font btnIngresarFont = this.$$$getFont$$$("Consolas", Font.PLAIN, 17, btnIngresar.getFont());
        if (btnIngresarFont != null) btnIngresar.setFont(btnIngresarFont);
        btnIngresar.setForeground(new Color(-10710529));
        btnIngresar.setText("Ingresar");
        login.add(btnIngresar, new com.intellij.uiDesigner.core.GridConstraints(8, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setIcon(new ImageIcon(getClass().getResource("/img.png")));
        label5.setText("");
        login.add(label5, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 3, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$("Yu Gothic UI Semibold", Font.PLAIN, 20, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setText("Inicio de Sesión");
        login.add(label6, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 10, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return login;
    }
}
