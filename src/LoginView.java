import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends JFrame {

    private JLabel labelNome;
    private JLabel labelSenha;
    private JTextField dfsNome;
    private JPasswordField dfsSenha;
    private JButton buttonLogin;

    public LoginView() {

        // Configurações iniciais
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLayout(new GridBagLayout()); // Usando GridBagLayout

        // Criando e configurando o GridBagConstraints para alinhar à esquerda
        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.anchor = GridBagConstraints.WEST;
        gbcLeft.insets = new Insets(5, 5, 5, 5); // Adicionando margem

        // Criando e configurando o GridBagConstraints para alinhar à direita
        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.anchor = GridBagConstraints.WEST;
        gbcRight.insets = new Insets(5, 5, 5, 5);
        gbcRight.fill = GridBagConstraints.HORIZONTAL;
        gbcRight.weightx = 1.0; // Para esticar o campo de texto na horizontal

        // Adicionando o rótulo e o campo de texto para o nome
        labelNome = new JLabel("Nome:");
        gbcLeft.gridx = 0;
        gbcLeft.gridy = 0;
        add(labelNome, gbcLeft);

        dfsNome = new JTextField(15); // Especificando o tamanho do campo de texto
        gbcRight.gridx = 1;
        gbcRight.gridy = 0;
        add(dfsNome, gbcRight);

        labelSenha = new JLabel("Senha:");
        gbcLeft.gridx = 0;
        gbcLeft.gridy = 1;
        add(labelSenha, gbcLeft);

        dfsSenha = new JPasswordField(15); // Especificando o tamanho do campo de texto
        gbcRight.gridx = 1;
        gbcRight.gridy = 1;
        add(dfsSenha, gbcRight);


        // Adicionando o botão de login
        buttonLogin = new JButton("Login");
        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.gridx = 0;
        gbcButton.gridy = 4;
        gbcButton.gridwidth = 10; // Ocupa duas colunas
        gbcButton.insets = new Insets(10, 5, 5, 5); // Adicionando margem
        gbcButton.anchor = GridBagConstraints.CENTER; // Centralizando o botão
        add(buttonLogin, gbcButton);

        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = dfsNome.getText();
                String senha = new String(dfsSenha.getPassword());

                if (usuario.equals("administrador") && senha.equals("123")) {
                    JOptionPane.showMessageDialog(LoginView.this, "Login bem-sucedido!");
                } else {
                    JOptionPane.showMessageDialog(LoginView.this, "Usuário ou senha inválidos.");
                }
            }
        });


    }


}
