import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CadastroLivro extends JDialog {

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

    private JButton buttonCadastrar;

    public CadastroLivro(JFrame parent) {
        super(parent, "Cadastro de Livro", true);
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
        panel.add(labelPrazoEmprestimoLivro);
        panel.add(dfnPrazoEmprestimoLivro);
        panel.add(buttonCadastrar);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        setLocationRelativeTo(parent);

        dfnPrazoEmprestimoLivro.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent e){
                String numeros = "0123456789";
                if (!numeros.contains(e.getKeyChar() + "")) {
                    e.consume();
                }
            }

        });

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
        String prazoEmprestimo = dfnPrazoEmprestimoLivro.getText();
    
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

        Livro novoLivro = new Livro(titulo, autor, categoria, isbn, true, Integer.parseInt(prazoEmprestimo));
        BancoDeDadosLivro.adicionarLivro(novoLivro);
    
        JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!");
        limpaCampos();

    }

    public void limpaCampos(){
        dfsTituloLivro.setText("");
        dfsAutorLivro.setText("");
        dfsCategoriaLivro.setText("");
        dfsISBNLivro.setText("");
        dfnPrazoEmprestimoLivro.setText("");
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



}
