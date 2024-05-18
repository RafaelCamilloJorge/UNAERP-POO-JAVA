import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ClienteTableModal extends JDialog {

    private JLabel labelNome;
    private JTextField dfsNome;
    private JButton btnBuscar;
    private JButton btnCadastrarCliente;
    private JTable tblClientes;
    private ClienteDAO clienteDAO;
    private ClienteSelectionListener clienteSelectionListener;

    public ClienteTableModal(JFrame owner, boolean modal, ClienteDAO clienteDAO,  ClienteSelectionListener clienteSelectionListener) {
        super(owner, modal);
        this.clienteDAO = clienteDAO;
        this.clienteSelectionListener = clienteSelectionListener;

        setTitle("Buscar Cliente");
        setSize(500, 300);
        setLayout(new GridLayout(2, 4));

        JPanel pnlBuscar = new JPanel();
        labelNome = new JLabel("Nome:");
        dfsNome = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        btnCadastrarCliente = new JButton("Cadastrar Cliente");
        pnlBuscar.add(labelNome);
        pnlBuscar.add(dfsNome);
        pnlBuscar.add(btnBuscar);
        pnlBuscar.add(btnCadastrarCliente);
        add(pnlBuscar, BorderLayout.NORTH);

        tblClientes = new JTable();
        add(new JScrollPane(tblClientes));
        buscarClientes();

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarClientes();
            }
        });

        btnCadastrarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManipularClienteModal modal = new ManipularClienteModal(null, true);
                modal.setVisible(true);
                buscarClientes();
            }
        });

        tblClientes.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int selectedRow = table.getSelectedRow();
                    int idCliente = (int) table.getModel().getValueAt(selectedRow, 0);
                    Cliente cliente = clienteDAO.getClientePorID(idCliente);
                    clienteSelectionListener.onClienteSelected(cliente);
                    dispose();
                }
            }
        });
    }

    private void buscarClientes() {
        List<Cliente> clientes;
        clientes = clienteDAO.getClientes();
        String nomeCliente = dfsNome.getText();
        if(!nomeCliente.isEmpty()){
            clientes = clienteDAO.getClientePorNome(nomeCliente);
        }
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nome");
        model.addColumn("CPF");
        for (Cliente cliente : clientes) {
            model.addRow(new Object[]{cliente.getId(), cliente.getNome(), cliente.getCpf()});
        }
        tblClientes.setModel(model);
    }

}

