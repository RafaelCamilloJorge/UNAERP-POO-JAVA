import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class UsuarioTableModal extends JTable {

    private UsuarioSelectionListener usuarioSelectionListener;

    public UsuarioTableModal(DefaultTableModel model, UsuarioSelectionListener usuarioSelectionListener) {
        this.usuarioSelectionListener = usuarioSelectionListener;
        String[] columnNames = {"ID", "Nome", "Cargo"};

        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Todas as células são inalteráveis
                return false;
            }
        };

        // Buscar usuários
        List<Usuario> usuarios = UsuarioDAO.buscarUsuarios();

        // Adicionar usuários ao modelo da tabela
        for (Usuario usuario : usuarios) {
            Object[] row = new Object[3];
            row[0] = usuario.getId();
            row[1] = usuario.getNome();
            row[2] = usuario.getCargo();
            model.addRow(row);
        }

        setModel(model);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = rowAtPoint(e.getPoint());
                    Usuario usuario = usuarios.get(row);
                    usuarioSelectionListener.onUsuarioSelected(usuario);
                    setVisible(false);
                }
            }
        });

        // Adicione código adicional aqui para configurar a tabela conforme necessário
    }
}