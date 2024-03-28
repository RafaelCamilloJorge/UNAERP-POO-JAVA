import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;



public class MainView extends JFrame {
    private JPanel navPanel;
    private JPanel panelTabela;
    private JTable tableLivros;

    private JButton btnCadastrarLivro;
    private JButton btnExcluirLivro;
    private JButton btnEditLivro;
    private JButton btnBuscar;
    private JLabel tituloLabel;
    private JTextField dfsTitulo;
    private JLabel generoLabel;
    private JComboBox cbxGenero;
    private JLabel ISBNLabel;
    private JTextField dfsISBN;
    private JLabel autorLabel;
    private JTextField dfsAutor;
    public MainView(String nomeUsuario) {
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        navPanel = new JPanel(new FlowLayout());
        add(navPanel, BorderLayout.PAGE_START);

        btnCadastrarLivro = new JButton("Cadastrar Livro");
        btnCadastrarLivro.setBackground(new Color(65, 105, 225));
        navPanel.add(btnCadastrarLivro);

        btnEditLivro = new JButton("Editar Livro");
        btnEditLivro.setBackground(new Color(34, 139, 34));
        navPanel.add(btnEditLivro);

        btnExcluirLivro = new JButton("Excluir Linha");
        btnExcluirLivro.setBackground(new Color(220, 20, 60)); // Cor vermelha
        navPanel.add(btnExcluirLivro);

        tituloLabel = new JLabel("Titulo:");
        dfsTitulo = new JTextField();
        dfsTitulo.setPreferredSize(new Dimension(200, 30));
        navPanel.add(tituloLabel);
        navPanel.add(dfsTitulo);

        String[] generos = {"", "Ação", "Romance", "Ficção", "Terror"};
        cbxGenero = new JComboBox(generos);
        generoLabel = new JLabel("Genero:");
        navPanel.add(generoLabel);
        navPanel.add(cbxGenero);

        autorLabel = new JLabel("Autor:");
        dfsAutor = new JTextField();
        dfsAutor.setPreferredSize(new Dimension(200, 30));
        navPanel.add(autorLabel);
        navPanel.add(dfsAutor);

        ISBNLabel = new JLabel("ISBN:");
        dfsISBN = new JTextField();
        dfsISBN.setPreferredSize(new Dimension(200, 30));
        navPanel.add(ISBNLabel);
        navPanel.add(dfsISBN);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(255, 255, 0));
        navPanel.add(btnBuscar);


        panelTabela = new JPanel(new BorderLayout());
        panelTabela.setPreferredSize(new Dimension(600, 400));

        tableLivros = new JTable();

        carregarLivros("", "", "", "");

        panelTabela.add(new JScrollPane(tableLivros));
        add(panelTabela, BorderLayout.CENTER);
        setLocationRelativeTo(null);

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeLivro = dfsTitulo.getText();
                String genero = (String) cbxGenero.getSelectedItem();
                String ISBN = dfsISBN.getText();
                String autor = dfsAutor.getText();
                carregarLivros(nomeLivro, autor, genero, ISBN);
            }
        });

        btnCadastrarLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CadastroLivro cadastroLivro = new CadastroLivro(MainView.this);
                cadastroLivro.setVisible(true);
            carregarLivros("", "", "", "");
            }
        });


        btnEditLivro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = tableLivros.getSelectedRow();
                if (row >= 0) {
                    DefaultTableModel model = (DefaultTableModel) tableLivros.getModel();
                    int id = (int) model.getValueAt(row, 0);
                    String tituloLivro = (String) model.getValueAt(row, 1);
                    String autorLivro = (String) model.getValueAt(row, 2);
                    String categoriaLivro = (String) model.getValueAt(row, 3);
                    String isbnLivro = (String) model.getValueAt(row, 4);
                    String disponivelLivroString = (String) model.getValueAt(row, 5);
                    boolean disponivelLivro = false;
                    int prazoEmprestimoLivro = (int) model.getValueAt(row, 6);
                    if(disponivelLivroString.equals("Disponivel")){
                        disponivelLivro = true;
                    }
                    EditarLivro editLivro = new EditarLivro(MainView.this, id, tituloLivro, autorLivro, categoriaLivro,
                            isbnLivro, disponivelLivro, prazoEmprestimoLivro);
                    editLivro.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(MainView.this, "Por favor, selecione uma linha para editar.");
                }
                carregarLivros("", "", "", "");
            }
        });

        btnExcluirLivro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = tableLivros.getSelectedRow();
                if (row >= 0) {
                    DefaultTableModel model = (DefaultTableModel) tableLivros.getModel();
                    int id = (int) model.getValueAt(row, 0);
                    model.removeRow(row);
                    BancoDeDadosLivro.removerLivro(id);
                } else {
                    JOptionPane.showMessageDialog(MainView.this, "Por favor, selecione uma linha para excluir.");
                }
            }
        });

        dfsTitulo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeLivro = dfsTitulo.getText();
                carregarLivros(nomeLivro, "", "", "");
            }
        });
    }

    private void carregarLivros(String nomeLivro, String autor, String genero, String ISBN) {
        List<Livro> livros = BancoDeDadosLivro.getLivros();

        if((!nomeLivro.contains("buscar...") && !nomeLivro.isBlank()) || !autor.isBlank() || !genero.isBlank() || !ISBN.isBlank()) {
            livros = BancoDeDadosLivro.buscarLivros(nomeLivro, autor, genero, ISBN);
        }
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Título");
        model.addColumn("Autor");
        model.addColumn("Categoria");
        model.addColumn("ISBN");
        model.addColumn("Status");
        model.addColumn("Prazo Emprestimo");

        for (Livro livro : livros) {
            Object[] rowData = { livro.getID(), livro.getTitulo(), livro.getAutor(), livro.getCategoria(),
                    livro.getIsbn(), livro.isDisponivel() ? "Disponivel" : "Indisponivel", livro.getPrazoEmprestimo() };
            model.addRow(rowData);
        }

        tableLivros.setModel(model);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView("Usuário");
            mainView.setVisible(true);
        });
    }

    
}
