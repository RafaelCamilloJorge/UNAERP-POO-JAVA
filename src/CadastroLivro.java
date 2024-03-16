import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class CadastroLivro extends JDialog {

    private JLabel labelTituloLivro;
    private JLabel labelAutorLivro;
    private JLabel labelCategoriaLivro;
    private JLabel labelISBNLivro;
    private JLabel labelDetalhesLivro;

    private JTextField dfsTituloLivro;
    private JTextField dfsAutorLivro;
    private JTextField dfsCategoriaLivro;
    private JTextField dfsISBNLivro;
    private JTextField dfsDetalhesLivro;

    private JButton buttonCadastrar;


    public CadastroLivro(JFrame parent) {
        super(parent, "Cadastro de Livro", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.anchor = GridBagConstraints.NORTHWEST;
        gbcLabel.insets = new Insets(5, 5, 5, 5);

        GridBagConstraints gbcTextField = new GridBagConstraints();
        gbcTextField.anchor = GridBagConstraints.NORTHWEST;
        gbcTextField.fill = GridBagConstraints.HORIZONTAL;
        gbcTextField.weightx = 1.0;
        gbcTextField.insets = new Insets(5, 5, 5, 5);
        gbcTextField.gridwidth = GridBagConstraints.REMAINDER;

        // Criando e adicionando as JLabels e JTextFields
        labelTituloLivro = new JLabel("Título:");
        add(labelTituloLivro, gbcLabel);

        dfsTituloLivro = new JTextField(20);
        add(dfsTituloLivro, gbcTextField);

        labelAutorLivro = new JLabel("Autor:");
        add(labelAutorLivro, gbcLabel);

        dfsAutorLivro = new JTextField(20);
        add(dfsAutorLivro, gbcTextField);

        labelCategoriaLivro = new JLabel("Categoria:");
        add(labelCategoriaLivro, gbcLabel);

        dfsCategoriaLivro = new JTextField(20);
        add(dfsCategoriaLivro, gbcTextField);

        labelISBNLivro = new JLabel("ISBN:");
        add(labelISBNLivro, gbcLabel);

        dfsISBNLivro = new JTextField(20);
        add(dfsISBNLivro, gbcTextField);

        labelDetalhesLivro = new JLabel("Detalhes:");
        add(labelDetalhesLivro, gbcLabel);

        dfsDetalhesLivro = new JTextField(20);
        add(dfsDetalhesLivro, gbcTextField);

        buttonCadastrar = new JButton("Cadastrar Livro");

        // Adicionando um listener de evento ao botão cadastrar
        buttonCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarLivro();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(buttonCadastrar, gbc);
    }

    // Método para cadastrar um livro
    private void cadastrarLivro() {
        String titulo = dfsTituloLivro.getText();
        String autor = dfsAutorLivro.getText();
        String categoria = dfsCategoriaLivro.getText();
        String isbn = dfsISBNLivro.getText();
        String detalhes = dfsDetalhesLivro.getText();
        
        Livro novoLivro = new Livro(titulo, autor, categoria, isbn, detalhes);
        
        BancoDeDadosFake.adicionarLivro(novoLivro);
    
        JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!");
    
        limparCampos();
    }

    // Método para limpar os campos de texto após o cadastro
    private void limparCampos() {
        dfsTituloLivro.setText("");
        dfsAutorLivro.setText("");
        dfsCategoriaLivro.setText("");
        dfsISBNLivro.setText("");
        dfsDetalhesLivro.setText("");
    }

}
