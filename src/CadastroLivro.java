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
    private JLabel labelPrazoEmprestimoLivro;

    private JTextField dfsTituloLivro;
    private JTextField dfsAutorLivro;
    private JTextField dfsCategoriaLivro;
    private JTextField dfsISBNLivro;
    private JTextField dfsDetalhesLivro;
    private JTextField dfnPrazoEmprestimoLivro;

    private JButton buttonCadastrar;

    public CadastroLivro(JFrame parent) {
        super(parent, "Cadastro de Livro", true);
        setSize(400, 300);

        labelTituloLivro = new JLabel("Título:");
        labelAutorLivro = new JLabel("Autor:");
        labelCategoriaLivro = new JLabel("Categoria:");
        labelISBNLivro = new JLabel("ISBN:");
        labelDetalhesLivro = new JLabel("Detalhes:");
        labelPrazoEmprestimoLivro = new JLabel("Prazo Emprestimo:");

        dfsTituloLivro = new JTextField();
        dfsAutorLivro = new JTextField();
        dfsCategoriaLivro = new JTextField();
        dfsISBNLivro = new JTextField();
        dfsDetalhesLivro = new JTextField();
        dfnPrazoEmprestimoLivro = new JTextField();

        buttonCadastrar = new JButton("Cadastrar");

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(labelTituloLivro);
        panel.add(dfsTituloLivro);
        panel.add(labelAutorLivro);
        panel.add(dfsAutorLivro);
        panel.add(labelCategoriaLivro);
        panel.add(dfsCategoriaLivro);
        panel.add(labelISBNLivro);
        panel.add(dfsISBNLivro);
        panel.add(labelDetalhesLivro);
        panel.add(dfsDetalhesLivro);
        panel.add(labelPrazoEmprestimoLivro);
        panel.add(dfnPrazoEmprestimoLivro);
        panel.add(buttonCadastrar);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        setLocationRelativeTo(parent);

        buttonCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarLivro();
            }
        });

    }

    private void cadastrarLivro() {
        String titulo = dfsTituloLivro.getText();
        String autor = dfsAutorLivro.getText();
        String categoria = dfsCategoriaLivro.getText();
        String isbn = dfsISBNLivro.getText();
        String detalhes = dfsDetalhesLivro.getText();
        String prazoEmprestimo = dfnPrazoEmprestimoLivro.getText();
    
        if (titulo.isEmpty() || autor.isEmpty() || categoria.isEmpty() || isbn.isEmpty() || detalhes.isEmpty()
                || prazoEmprestimo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos antes de cadastrar o livro.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Criar um novo livro sem a necessidade de passar o ID explicitamente
        Livro novoLivro = new Livro(titulo, autor, categoria, isbn, true, Integer.parseInt(prazoEmprestimo));
        BancoDeDadosFake.adicionarLivro(novoLivro);
    
        JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!");
    }
    

}
