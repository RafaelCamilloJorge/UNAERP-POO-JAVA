package livro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ManipularLivroView extends JDialog {

    private LivroController livroController;
    private LivroDAO livroDAO;

    private String tituloTela;

    private JLabel labelTituloLivro;
    private JLabel labelAutorLivro;
    private JLabel labelCategoriaLivro;
    private JLabel labelISBNLivro;
    private JLabel labelPrazoEmprestimoLivro;
    private JLabel labelDisponivel;

    private JTextField dfsTituloLivro;
    private JTextField dfsAutorLivro;
    private JComboBox dfsCategoriaLivro;
    private JTextField dfsISBNLivro;
    private JTextField dfnPrazoEmprestimoLivro;
    private JCheckBox cbxDisponivel;

    private JButton btnButton;

    public ManipularLivroView(JFrame parent, String tituloTela, Livro livro) {
        super(parent, tituloTela, true);
        setSize(400, 300);

        livroController = new LivroController(this, livroDAO);


        labelTituloLivro = new JLabel("Título:");
        labelAutorLivro = new JLabel("Autor:");
        labelCategoriaLivro = new JLabel("Categoria:");
        labelISBNLivro = new JLabel("ISBN:");
        labelPrazoEmprestimoLivro = new JLabel("Prazo Emprestimo:");
        labelDisponivel = new JLabel("Disponível:");

        dfsTituloLivro = new JTextField();
        dfsAutorLivro = new JTextField();
        String[] generos = {"", "Ação", "Romance", "Ficção", "Terror"};
        dfsCategoriaLivro = new JComboBox(generos);
        dfsISBNLivro = new JTextField();
        dfnPrazoEmprestimoLivro = new JTextField();
        cbxDisponivel = new JCheckBox();


        btnButton = new JButton("Salvar");

        dfsTituloLivro.setText(livro.getTitulo());
        dfsAutorLivro.setText(livro.getAutor());
        dfsCategoriaLivro.setSelectedItem(livro.getCategoria());
        dfsISBNLivro.setText(livro.getIsbn());
        dfnPrazoEmprestimoLivro.setText(String.valueOf(livro.getPrazoEmprestimo()));
        cbxDisponivel.setSelected(livro.isDisponivel());

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
        panel.add(labelDisponivel);
        panel.add(cbxDisponivel);
        panel.add(btnButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        setLocationRelativeTo(parent);

        if (tituloTela.equals("Cadastrar Livro")){
            limpaCampos();
        }

        dfnPrazoEmprestimoLivro.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e){
                String numeros = "0123456789";
                if (!numeros.contains(e.getKeyChar() + "")) {
                    e.consume();
                }
            }
        });

        btnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tituloTela.equals("Editar Livro")) {
                    editarLivro(livro.getID());
                } else{
                    cadastrarLivro();
                }
            }
        });

    }


    private void cadastrarLivro() {
        String titulo = dfsTituloLivro.getText();
        String autor = dfsAutorLivro.getText();
        String categoria = (String) dfsCategoriaLivro.getSelectedItem();
        String isbn = dfsISBNLivro.getText();
        String prazoEmprestimo = dfnPrazoEmprestimoLivro.getText();
        boolean disponivel = cbxDisponivel.isSelected();

        Livro novoLivro = new Livro(titulo, autor, categoria, isbn, disponivel, Integer.parseInt(prazoEmprestimo));
        livroController.cadastrarLivro(novoLivro);

    }

    private void editarLivro(int id) {
        String titulo = dfsTituloLivro.getText();
        String autor = dfsAutorLivro.getText();
        String categoria = (String) dfsCategoriaLivro.getSelectedItem();
        String isbn = dfsISBNLivro.getText();
        String prazoEmprestimo = dfnPrazoEmprestimoLivro.getText();
        boolean disponivel = cbxDisponivel.isSelected();

        Livro novoLivro = new Livro(id, titulo, autor, categoria, isbn, disponivel, Integer.parseInt(prazoEmprestimo));
        livroController.editarLivro(novoLivro);

    }
    public void limpaCampos(){
        dfnPrazoEmprestimoLivro.setText("");
        dfsTituloLivro.setText("");
        dfsAutorLivro.setText("");
        dfsCategoriaLivro.setSelectedIndex(-1);
        dfsISBNLivro.setText("");
    }

}
