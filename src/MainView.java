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

    public MainView(String nomeUsuario) {
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        labelBemVindo = new JLabel("Bem-vindo, " + nomeUsuario + "!");
        labelBemVindo.setFont(new Font("Arial", Font.BOLD, 40));

        btnCadastrarLivro = new JButton("Cadastrar Livro");

        // Criar o navPanel com GridBagLayout
        navPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        navPanel.add(labelBemVindo, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0;
        navPanel.add(btnCadastrarLivro, gbc);

        add(navPanel, BorderLayout.NORTH);

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

        JButton btnExcluir = new JButton("Excluir Linha");
        btnExcluir.addActionListener(new ActionListener() {
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

        navPanel.add(btnExcluir);
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
            Object[] rowData = {livro.getID(), livro.getTitulo(), livro.getAutor(), livro.getCategoria(), livro.getIsbn(), livro.isDisponivel(), livro.getPrazoEmprestimo()};
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
