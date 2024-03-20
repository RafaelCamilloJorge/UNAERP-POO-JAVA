import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainView extends JFrame {
    private JPanel navPanel;
    private JLabel labelBemVindo;
    private JPanel panelTabela;
    private JTable tableLivros;

    private JButton btnCadastrarLivro;
    private JButton btnExcluirLivro;
    private JButton btnEditLivro;

    private JTextField dfsBusca;

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

        panelTabela = new JPanel(new BorderLayout());
        panelTabela.setPreferredSize(new Dimension(600, 400));

        tableLivros = new JTable();

        carregarLivros();

        panelTabela.add(new JScrollPane(tableLivros));
        add(panelTabela, BorderLayout.CENTER);
        setLocationRelativeTo(null);

        btnCadastrarLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CadastroLivro cadastroLivro = new CadastroLivro(MainView.this);
                cadastroLivro.setVisible(true);
                carregarLivros();
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
                    boolean disponivelLivro = (boolean) model.getValueAt(row, 5);
                    int prazoEmprestimoLivro = (int) model.getValueAt(row, 6);

                    EditarLivro editLivro = new EditarLivro(MainView.this, id, tituloLivro, autorLivro, categoriaLivro,
                            isbnLivro, disponivelLivro, prazoEmprestimoLivro);
                    editLivro.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(MainView.this, "Por favor, selecione uma linha para editar.");
                }
                carregarLivros();
            }
        });

        btnExcluirLivro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = tableLivros.getSelectedRow();
                if (row >= 0) {
                    DefaultTableModel model = (DefaultTableModel) tableLivros.getModel();
                    int id = (int) model.getValueAt(row, 0);
                    model.removeRow(row);
                    BancoDeDadosFake.removerLivro(id);
                } else {
                    JOptionPane.showMessageDialog(MainView.this, "Por favor, selecione uma linha para excluir.");
                }
            }
        });
    }

    private void carregarLivros() {
        List<Livro> livros = BancoDeDadosFake.getLivros();

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
                    livro.getIsbn(), livro.isDisponivel(), livro.getPrazoEmprestimo() };
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
