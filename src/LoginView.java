import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends JFrame {

    private JTextField dfsNome;
    private JPasswordField dfsSenha;
    private JButton btnLogin;
    private LoginController loginController;
    private UsuarioDAO UsuarioDAO;

    public LoginView() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLayout(new GridBagLayout());

        loginController = new LoginController(this, UsuarioDAO);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(2, 10, 5, 5);

        JLabel labelNome = new JLabel("Nome:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(labelNome, gbc);

        dfsNome = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(dfsNome, gbc);

        JLabel labelSenha = new JLabel("Senha:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(labelSenha, gbc);

        dfsSenha = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(dfsSenha, gbc);

        btnLogin = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnLogin, gbc);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = dfsNome.getText();
                String senha = new String(dfsSenha.getPassword());

                Usuario usuario = loginController.autenticarUsuario(nome, senha);

                if (usuario != null) {
                    openMainView(usuario.getNome(), usuario.getCargo());
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginView.this, "Usuário ou senha inválidos.");
                }
            }
        });
    }

    private void openMainView(String nomeUsuario, String cargo) {
        MainView telaPrincipal = new MainView(nomeUsuario, cargo);
        telaPrincipal.setVisible(true);
    }
}
