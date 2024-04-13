import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ManipularLivroModal extends JDialog {


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

    public ManipularLivroModal(JFrame parent, String tituloTela, int id, String titulo, String autor, String categoria, String isbn, boolean disponivel, int prazoEmprestimo) {
        super(parent, tituloTela, true);
        setSize(400, 300);

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

        dfsTituloLivro.setText(titulo);
        dfsAutorLivro.setText(autor);
        dfsCategoriaLivro.setSelectedItem(categoria);
        dfsISBNLivro.setText(isbn);
        dfnPrazoEmprestimoLivro.setText(String.valueOf(prazoEmprestimo));
        cbxDisponivel.setSelected(disponivel);

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

        if (tituloTela == "Cadastrar Livro"){
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
                    editarLivro(id);
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

        if (titulo.isEmpty() || autor.isEmpty() || categoria.isEmpty() || isbn.isEmpty() || prazoEmprestimo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos antes de cadastrar o livro.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(!validaISBN(isbn)){
            JOptionPane.showMessageDialog(this, "ISBN inválido.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (BancoDeDadosLivro.isISBNUtilizado(isbn)) {
            JOptionPane.showMessageDialog(this, "ISBN já foi utilizado por outro livro.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Livro novoLivro = new Livro(titulo, autor, categoria, isbn, disponivel, Integer.parseInt(prazoEmprestimo));
        BancoDeDadosLivro.adicionarLivro(novoLivro);

        JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!");
        limpaCampos();

    }

    private boolean validaISBN(String isbn) {
        isbn = isbn.replaceAll("\\s|-", "");

        if (isbn.length() != 13) {
            return false;
        }

        for (int i = 0; i < 12; i++) {
            if (!Character.isDigit(isbn.charAt(i))) {
                return false;
            }
        }

        char ultimoCaracter = isbn.charAt(12);
        if (!Character.isDigit(ultimoCaracter) && ultimoCaracter != 'X') {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }

        int checkDigit = (10 - (sum % 10)) % 10;
        char expectedCheckDigit = (checkDigit == 10) ? '0' : (char) (checkDigit + '0');

        return ultimoCaracter == expectedCheckDigit;
    }

    private void editarLivro(int id) {
        String titulo = dfsTituloLivro.getText();
        String autor = dfsAutorLivro.getText();
        String categoria = (String) dfsCategoriaLivro.getSelectedItem();
        String isbn = dfsISBNLivro.getText();
        String prazoEmprestimo = dfnPrazoEmprestimoLivro.getText();
        boolean disponivel = cbxDisponivel.isSelected();

        if (titulo.isEmpty() || autor.isEmpty() || categoria.isEmpty() || isbn.isEmpty() || prazoEmprestimo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos antes de cadastrar o livro.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(!validaISBN(isbn)){
            JOptionPane.showMessageDialog(this, "ISBN inválido.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isbn.equals(BancoDeDadosLivro.getLivroPorID(id).getIsbn())) {
            if (BancoDeDadosLivro.isISBNUtilizado(isbn)) {
                JOptionPane.showMessageDialog(this, "ISBN já foi utilizado por outro livro.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Livro editLivro = BancoDeDadosLivro.getLivroPorID(id);
        editLivro.setTitulo(titulo);
        editLivro.setAutor(autor);
        editLivro.setCategoria(categoria);
        editLivro.setIsbn(isbn);
        editLivro.setPrazoEmprestimo(Integer.parseInt(prazoEmprestimo));
        editLivro.setDisponivel(disponivel);


        JOptionPane.showMessageDialog(this, "Livro editado com sucesso!");

    }
    public void limpaCampos(){
        dfnPrazoEmprestimoLivro.setText("");
        dfsTituloLivro.setText("");
        dfsAutorLivro.setText("");
        dfsCategoriaLivro.setSelectedIndex(-1);
        dfsISBNLivro.setText("");
    }

}
