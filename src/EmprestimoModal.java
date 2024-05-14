import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class EmprestimoModal extends JDialog {
    private JLabel usuarioLabel;
    private JLabel livroLabel;
    private JLabel dataEmprestimoLabel;
    private JLabel dataDevolucaoLabel;

    private JTextField dfsUsuario;
    private JTextField dfsLivro;
    private JFormattedTextField dfdEmprestimo;
    private JFormattedTextField dfdDevolucao;
    private MaskFormatter dateFormatter;
    private JButton btnEmprestimo;


    public EmprestimoModal() {
        setTitle("Registrar Empréstimo");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        setLayout(new GridLayout(4, 2));

        usuarioLabel = new JLabel("Usuário:");
        dfsUsuario = new JTextField();

        livroLabel = new JLabel("Livro:");
        dfsLivro = new JTextField();

        try {
            dateFormatter = new MaskFormatter(" ##  /  ##  /  ####");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        dataEmprestimoLabel = new JLabel("Data de Empréstimo:");
        dfdEmprestimo = new JFormattedTextField(dateFormatter);

        dataDevolucaoLabel = new JLabel("Data de Devolução:");
        dfdDevolucao = new JFormattedTextField(dateFormatter);

        btnEmprestimo = new JButton("Emprestar");

        panel.add(usuarioLabel);
        panel.add(dfsUsuario);
        panel.add(livroLabel);
        panel.add(dfsLivro);
        panel.add(dataEmprestimoLabel);
        panel.add(dfdEmprestimo);
        panel.add(dataDevolucaoLabel);
        panel.add(dfdDevolucao);
        panel.add(btnEmprestimo);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmprestimoModal emprestimoModal = new EmprestimoModal();
            emprestimoModal.setVisible(true);
        });
    }
}
