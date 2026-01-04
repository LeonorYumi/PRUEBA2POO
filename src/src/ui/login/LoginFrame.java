package ui.login;
import service.UsuariosService;
import javax.swing.*;

public class LoginFrame extends JFrame {
    private JPanel login;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton btnIngresar;

    public LoginFrame(){
        setTitle("Inicio de Sesión");
        setContentPane(login);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,300);
        btnIngresar.addActionListener(e -> validarLogin());

    }
    private void validarLogin() {

        String usuario = textField1.getText().trim();
        String password = new String(passwordField1.getPassword());

        if (usuario.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Ingrese usuario y contraseña");
            return;
        }

    }
}


