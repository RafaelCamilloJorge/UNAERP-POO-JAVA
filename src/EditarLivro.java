import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditarLivro extends JDialog {

    private JLabel labelTituloLivro;
    private JLabel labelAutorLivro;
    private JLabel labelCategoriaLivro;
    private JLabel labelISBNLivro;
    private JLabel labelPrazoEmprestimoLivro;

    private JTextField dfsTituloLivro;
    private JTextField dfsAutorLivro;
    private JTextField dfsCategoriaLivro;
    private JTextField dfsISBNLivro;
    private JTextField dfnPrazoEmprestimoLivro;

    private JButton buttonEditar;

    public EditarLivro(JFrame parent, int id, String titulo, String autor, String categoria, String isbn, boolean disponivel, int prazoEmprestimo) {
        super(parent, "Editar Livro", true);
        setSize(400, 300);

        labelTituloLivro = new JLabel("Título:");
        labelAutorLivro = new JLabel("Autor:");
        labelCategoriaLivro = new JLabel("Categoria:");
        labelISBNLivro = new JLabel("ISBN:");
        labelPrazoEmprestimoLivro = new JLabel("Prazo Emprestimo:");

        dfsTituloLivro = new JTextField();
        dfsAutorLivro = new JTextField();
        dfsCategoriaLivro = new JTextField();
        dfsISBNLivro = new JTextField();
        dfnPrazoEmprestimoLivro = new JTextField();

        buttonEditar = new JButton("Salvar");

        dfsTituloLivro.setText(titulo);
        dfsAutorLivro.setText(autor);
        dfsCategoriaLivro.setText(categoria);
        dfsISBNLivro.setText(isbn);
        dfnPrazoEmprestimoLivro.setText(String.valueOf(prazoEmprestimo));

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
        panel.add(labelPrazoEmprestimoLivro);
        panel.add(dfnPrazoEmprestimoLivro);
        panel.add(buttonEditar);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        setLocationRelativeTo(parent);

        buttonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarLivro(id);
            }
        });

    }

    private void editarLivro(int id) {
        String titulo = dfsTituloLivro.getText();
        String autor = dfsAutorLivro.getText();
        String categoria = dfsCategoriaLivro.getText();
        String isbn = dfsISBNLivro.getText();
        String prazoEmprestimo = dfnPrazoEmprestimoLivro.getText();
    
        if (titulo.isEmpty() || autor.isEmpty() || categoria.isEmpty() || isbn.isEmpty() || prazoEmprestimo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos antes de cadastrar o livro.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Livro editLivro = BancoDeDadosLivro.getLivroPorID(id);
        editLivro.setTitulo(titulo);
        editLivro.setAutor(autor);
        editLivro.setCategoria(categoria);
        editLivro.setIsbn(isbn);
        editLivro.setPrazoEmprestimo(Integer.parseInt(prazoEmprestimo));
        
    
        JOptionPane.showMessageDialog(this, "Livro editado com sucesso!");
    }
    

}
