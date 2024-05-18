import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;



public class MainView extends JFrame {
    private JPanel navPanel;
    private JPanel panelTabela;
    private JPanel panelButtons;
    private JTable tableLivros;

    private JButton btnCadastrarLivro;
    private JButton btnExcluirLivro;
    private JButton btnEditLivro;
    private JButton btnEmprestarLivro;
    private JButton btnBuscar;
    private JButton btnUsuario;

    private JLabel tituloLabel;
    private JTextField dfsTitulo;
    private JLabel generoLabel;
    private JComboBox cbxGenero;
    private JLabel ISBNLabel;
    private JTextField dfsISBN;
    private JLabel autorLabel;
    private JTextField dfsAutor;

    public MainView(String nomeUsuario, String cargoUsuario) {
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        navPanel = new JPanel(new FlowLayout());
        add(navPanel, BorderLayout.PAGE_START);

        btnCadastrarLivro = new JButton("Cadastrar Livro");
        btnCadastrarLivro.setBackground(new Color(65, 105, 225));

        btnEditLivro = new JButton("Editar Livro");
        btnEditLivro.setBackground(new Color(34, 139, 34));

        btnExcluirLivro = new JButton("Excluir Livro");
        btnExcluirLivro.setBackground(new Color(220, 20, 60));

        btnEmprestarLivro = new JButton("Emprestar Livro");
        btnEmprestarLivro.setBackground(new Color(171, 48, 255));

        btnUsuario = new JButton("Usuário");
        btnUsuario.setBackground(new Color(255, 165, 0));


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
        panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));

        if(cargoUsuario.equals("Administrador")){
            panelButtons.add(btnCadastrarLivro);
            panelButtons.add(btnEditLivro);
            panelButtons.add(btnExcluirLivro);
            panelButtons.add(btnUsuario);
        }
        panelButtons.add(btnEmprestarLivro);
        add(panelButtons, BorderLayout.PAGE_END);
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
                Livro livro = new Livro("", "", "", "", true, 0);
                ManipularLivroModal cadastroLivro = new ManipularLivroModal(MainView.this, "Cadastrar Livro", livro);
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
                    String titulo = (String) model.getValueAt(row, 1);
                    String autor = (String) model.getValueAt(row, 2);
                    String categoria = (String) model.getValueAt(row, 3);
                    String isbn = (String) model.getValueAt(row, 4);
                    boolean disponivel = model.getValueAt(row, 5).equals("Disponivel");
                    int prazoEmprestimo = (int) model.getValueAt(row, 6);


                    Livro livro = new Livro(id, titulo, autor, categoria, isbn, disponivel, prazoEmprestimo);

                    ManipularLivroModal editLivro = new ManipularLivroModal(MainView.this,"Editar Livro", livro);
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
                    LivroDAO.removerLivro(id);
                } else {
                    JOptionPane.showMessageDialog(MainView.this, "Por favor, selecione uma linha para excluir.");
                }
            }
        });

        btnUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManipularUsuarioModal usuarioModal = new ManipularUsuarioModal(MainView.this);
                usuarioModal.setVisible(true);
            }
        });

        btnEmprestarLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idLivro = (int) tableLivros.getValueAt(tableLivros.getSelectedRow(), 0);
                EmprestimoModal emprestimoModal = new EmprestimoModal(MainView.this, true, idLivro);
                emprestimoModal.setLocationRelativeTo(null);
                emprestimoModal.setVisible(true);
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
        List<Livro> livros = LivroDAO.getLivros();

        if((!nomeLivro.contains("buscar...") && !nomeLivro.isBlank()) || !autor.isBlank() || !genero.isBlank() || !ISBN.isBlank()) {
            livros = LivroDAO.buscarLivros(nomeLivro, autor, genero, ISBN);
        }

        String[] columnNames = { "ID", "Título", "Autor", "Categoria", "ISBN", "Status", "Prazo Emprestimo" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Livro livro : livros) {
            Object[] rowData = { livro.getID(), livro.getTitulo(), livro.getAutor(), livro.getCategoria(),
                    livro.getIsbn(), livro.isDisponivel() ? "Disponivel" : "Indisponivel", livro.getPrazoEmprestimo() };
            model.addRow(rowData);
        }

        tableLivros.setModel(model);
    }
}
