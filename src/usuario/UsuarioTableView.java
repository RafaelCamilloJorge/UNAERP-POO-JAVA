package usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class UsuarioTableView extends JDialog {

    private UsuarioSelectionListener usuarioSelectionListener;
    private JTable table;

    public UsuarioTableView(JDialog owner, boolean modal, UsuarioSelectionListener usuarioSelectionListener) {
        super(owner, modal);
        this.usuarioSelectionListener = usuarioSelectionListener;

        String[] columnNames = {"ID", "Nome", "Cargo"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<Usuario> usuarios = UsuarioDAO.buscarUsuarios();

        for (Usuario usuario : usuarios) {
            Object[] row = new Object[3];
            row[0] = usuario.getId();
            row[1] = usuario.getNome();
            row[2] = usuario.getCargo();
            model.addRow(row);
        }

        table = new JTable(model);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.rowAtPoint(e.getPoint());
                    Usuario usuario = usuarios.get(row);
                    usuarioSelectionListener.onUsuarioSelected(usuario);
                    dispose();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        pack();
    }
}