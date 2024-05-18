import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManipularClienteModal extends JDialog {

    private ClienteController clienteController;

    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtTelefone;
    private JButton btnCriar;

    public ManipularClienteModal(JFrame owner, boolean modal) {
        super(owner, modal);

        clienteController = new ClienteController(new ClienteDAO(), this);

        setTitle("Criar Cliente");
        setSize(300, 200);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Nome:"));
        txtNome = new JTextField(20);
        add(txtNome);

        add(new JLabel("CPF:"));
        txtCpf = new JTextField(20);
        add(txtCpf);

        add(new JLabel("Telefone:"));
        txtTelefone = new JTextField(20);
        add(txtTelefone);

        btnCriar = new JButton("Criar");
        btnCriar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarCliente();
            }
        });
        add(btnCriar);
    }

    private void criarCliente() {
        String nome = txtNome.getText();
        String cpf = txtCpf.getText();
        String telefone = txtTelefone.getText();

        Cliente cliente = new Cliente(nome, cpf, telefone);
        clienteController.criarCliente(cliente);
    }

    public static void main(String[] args) {
        ManipularClienteModal modal = new ManipularClienteModal(null, true);
        modal.setVisible(true);
    }
}