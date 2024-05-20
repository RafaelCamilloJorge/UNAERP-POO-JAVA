import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HistoricoEmprestimoModal extends JDialog {
    private Livro livro;
    private JTable tblHistorico;

    public HistoricoEmprestimoModal(JFrame owner, boolean modal, Livro livro) {
        super(owner, modal);
        this.livro = livro;

        setTitle("Histórico de Empréstimos do Livro: " + livro.getTitulo());
        setSize(500, 300);
        setLayout(new BorderLayout());

        tblHistorico = new JTable();
        add(new JScrollPane(tblHistorico), BorderLayout.CENTER);

        carregarHistorico();
    }

    private void carregarHistorico() {
        List<Emprestimo> emprestimos = EmprestimoDAO.getEmprestimosPorLivro(livro);

        String[] columnNames = { "Cliente", "Telefone", "Data de Empréstimo", "Data de Devolução" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Emprestimo emprestimo : emprestimos) {
            Cliente cliente = ClienteDAO.getClientePorID(emprestimo.getCliente().getId());
            Object[] rowData = { cliente.getNome(), cliente.getTelefone(), emprestimo.getDataEmprestimo(), emprestimo.getDataDevolucao() };
            model.addRow(rowData);
        }

        tblHistorico.setModel(model);
    }
}