import javax.swing.*;
import javax.swing.border.Border;
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
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        Border border = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        panel.setBorder(border);

        panel.add(new JLabel("Nome Completo:"));
        txtNome = new JTextField(20);
        panel.add(txtNome);

        panel.add(new JLabel("CPF:"));
        txtCpf = new JTextField(20);
        panel.add(txtCpf);

        panel.add(new JLabel("Telefone:"));
        txtTelefone = new JTextField(20);
        panel.add(txtTelefone);

        btnCriar = new JButton("Criar");
        panel.add(btnCriar);
        btnCriar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarCliente();
            }
        });


        add(panel);
    }

    private void criarCliente() {
        String nome = txtNome.getText();
        String cpf = txtCpf.getText();
        String telefone = txtTelefone.getText();

        Cliente cliente = new Cliente(nome, cpf, telefone);
        clienteController.criarCliente(cliente);
    }
}