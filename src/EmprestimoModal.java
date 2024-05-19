import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmprestimoModal extends JDialog implements ClienteSelectionListener {
    private JLabel lblLivro;
    private JLabel lblCliente;
    private JLabel lblDataDevolucao;

    private Livro livro;
    private int idCliente;

    private JTextField dfsLivro;
    private JTextField dfsCliente;
    private JFormattedTextField dfsDataDevolucao;

    private JButton btnBuscarCliente;
    private JButton btnEmprestar;

    private ClienteDAO clienteDAO;
    private LivroDAO livroDAO;

    public EmprestimoModal(JFrame owner, boolean modal, Livro livro, ClienteDAO clienteDAO, LivroDAO livroDAO) {
        super(owner, modal);
        this.livro = livro;
        this.clienteDAO = clienteDAO;
        this.livroDAO = livroDAO;
        setTitle("Empréstimo");
        setSize(500, 250);
        setLayout(new GridBagLayout());

        EmprestimoController emprestimoController = new EmprestimoController(this, clienteDAO, livroDAO);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 30, 10, 30);
        constraints.anchor = GridBagConstraints.WEST;

        lblCliente = new JLabel("Cliente:");
        dfsCliente = new JTextField();
        dfsCliente.setPreferredSize(new Dimension(200, 30));
        ImageIcon lupaIcon = new ImageIcon("src/img/lupa (1).png");
        btnBuscarCliente = new JButton(lupaIcon);
        btnBuscarCliente.setPreferredSize(new Dimension(30, 30));
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(lblCliente, constraints);
        constraints.gridx = 1;
        add(dfsCliente, constraints);
        constraints.gridx = 2;
        add(btnBuscarCliente, constraints);

        lblLivro = new JLabel("Livro:");
        dfsLivro = new JTextField();
        dfsLivro.setText(livro.getTitulo());
        dfsLivro.setPreferredSize(new Dimension(200, 30));
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(lblLivro, constraints);
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        add(dfsLivro, constraints);


        lblDataDevolucao = new JLabel("Devolução:");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        int prazoEmprestimo = livro.getPrazoEmprestimo();
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataFinal = dataAtual.plusDays(prazoEmprestimo);
        String dataFormatada = dataFinal.format(formatter);

        try {
            MaskFormatter mask = new MaskFormatter("##/##/####");
            dfsDataDevolucao = new JFormattedTextField(mask);
            dfsDataDevolucao.setPreferredSize(new Dimension(200, 30));
            dfsDataDevolucao.setText(dataFormatada);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        add(lblDataDevolucao, constraints);
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        add(dfsDataDevolucao, constraints);

        btnEmprestar = new JButton("Emprestar");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        add(btnEmprestar, constraints);

        dfsLivro.setEditable(false);
        dfsCliente.setEditable(false);
        dfsDataDevolucao.setEditable(false);

        btnBuscarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteTableModal clienteTableModal = new ClienteTableModal(null, true, clienteDAO, EmprestimoModal.this);
                clienteTableModal.setLocationRelativeTo(null);
                clienteTableModal.setVisible(true);
            }
        });

        btnEmprestar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dataEmprestimo = dataAtual.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                String dataDevolucao = dataFinal.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                emprestimoController.emprestar(idCliente, livro.getID(), dataEmprestimo, dataDevolucao);
                MainView.carregarLivros("", "", "", "");
            }
        });
    }

    @Override
    public void onClienteSelected(Cliente cliente) {
        idCliente = cliente.getId();
        dfsCliente.setText(cliente.getNome());
    }
}
