import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UsuarioTableModal extends JTable {

    public UsuarioTableModal(DefaultTableModel model) {
        super(model);
        initialize();
    }

    private void initialize() {

        String[] columnNames = {"ID", "Nome", "Cargo"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        setModel(tableModel);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = rowAtPoint(e.getPoint());
                    // Faça algo com a linha que foi clicada duplamente, por exemplo:
                    System.out.println("Linha " + row + " clicada duplamente.");
                    // Você pode acessar os dados da linha usando o modelo da tabela
                    // Por exemplo, para obter o ID da linha clicada:
                    // int id = (int) getValueAt(row, 0);
                }
            }
        });

        // Adicione código adicional aqui para configurar a tabela conforme necessário
    }

    // Métodos adicionais podem ser adicionados para manipular os dados da tabela conforme necessário
}
