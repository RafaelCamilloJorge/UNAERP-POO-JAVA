import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainView extends JFrame {

    private JLabel labelBemVindo;
    private JButton botaoCadastroLivro;
    private JButton botaoListarLivros;

    public MainView(String nomeUsuario) {
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        labelBemVindo = new JLabel("Bem-vindo, " + nomeUsuario + "!");
        labelBemVindo.setFont(new Font("Arial", Font.BOLD, 40));

        setLayout(new BorderLayout());

        JPanel panelBotoes = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 0, 0);

        botaoCadastroLivro = new JButton("Cadastro de Livro");
        botaoCadastroLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirCadastroLivro();
            }
        });
        panelBotoes.add(botaoCadastroLivro, gbc);

        // Adicionando o novo botão "Listar Livros"
        botaoListarLivros = new JButton("Listar Livros");
        botaoListarLivros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarLivros();
            }
        });
        gbc.gridx++; // Incrementando a posição X para colocar o novo botão ao lado
        panelBotoes.add(botaoListarLivros, gbc);

        add(labelBemVindo, BorderLayout.NORTH);
        add(panelBotoes, BorderLayout.CENTER);
    }

    private void abrirCadastroLivro() {
        CadastroLivro cadastroLivro = new CadastroLivro(this);
        cadastroLivro.setVisible(true);
    }

    // Método para listar os livros (a ser implementado)
    private void listarLivros() {
        ListarLivros listarLivro = new ListarLivros();
        listarLivro.setVisible(true);
    }
}
