import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EmprestimoModal extends JDialog {
    private JLabel lblLivro;
    private JLabel lblCliente;
    private JLabel lblDataEmprestimo;

    private int idLivro;

    private JTextField dfsLivro;
    private JTextField dfsCliente;
    private JFormattedTextField dfsDataEmprestimo;

    private JButton btnBuscarCliente;

    private JButton btnEmprestar;

    public EmprestimoModal(JFrame owner, boolean modal, int idLivro){
        super(owner, modal);
        this.idLivro = idLivro;
        setTitle("Emprestimo");
        setSize(500, 250);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 30, 10, 30);
        constraints.anchor = GridBagConstraints.WEST;

        lblCliente = new JLabel("Cliente:");
        dfsCliente = new JTextField();
        dfsCliente.setPreferredSize(new Dimension(200, 30));
        btnBuscarCliente = new JButton("");
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
        dfsLivro.setPreferredSize(new Dimension(200, 30));
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(lblLivro, constraints);
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        add(dfsLivro, constraints);

        lblDataEmprestimo = new JLabel("Devolução:");
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        MaskFormatter mask;
        try {
            mask = new MaskFormatter("##/##/####");
            dfsDataEmprestimo = new JFormattedTextField(mask);
            dfsDataEmprestimo.setPreferredSize(new Dimension(200, 30));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        add(lblDataEmprestimo, constraints);
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        add(dfsDataEmprestimo, constraints);

        btnEmprestar = new JButton("Emprestar");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        add(btnEmprestar, constraints);



        btnEmprestar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cliente = dfsCliente.getText();
                String livro = dfsLivro.getText();
                String dataEmprestimo = dfsDataEmprestimo.getText();
            }
        });
    }
    
}