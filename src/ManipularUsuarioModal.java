import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManipularUsuarioModal extends JDialog {

    private UsuarioController usuarioController;
    private UsuarioDAO usuarioDAO;

    private JLabel labelNome;
    private JLabel labelSenha;
    private JLabel labelCargo;

    private JTextField dfsNome;
    private JPasswordField dfsSenha;
    private JComboBox<String> cbxCargo;

    private JButton btnCreate;
    private JButton btnDelete;
    private JButton btnUpdate;

    public ManipularUsuarioModal(JFrame parent) {
        super(parent, true);
        setSize(400, 300);

        usuarioController = new UsuarioController(this, usuarioDAO);

        labelNome = new JLabel("Nome:");
        labelSenha = new JLabel("Senha:");
        labelCargo = new JLabel("Cargo:");

        dfsNome = new JTextField();
        dfsSenha = new JPasswordField();
        String[] cargos = {"Administrador", "Funcionário", "Usuário Padrão"};
        cbxCargo = new JComboBox<>(cargos);

        btnCreate = new JButton("Cadastrar");
        btnDelete = new JButton("Excluir");
        btnUpdate = new JButton("Atualizar");

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(labelNome);
        panel.add(dfsNome);
        panel.add(labelSenha);
        panel.add(dfsSenha);
        panel.add(labelCargo);
        panel.add(cbxCargo);
        panel.add(btnCreate);
        panel.add(btnDelete);
        panel.add(btnUpdate);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        setLocationRelativeTo(parent);

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarUsuario();
            }
        });

//        btnDelete.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                excluirUsuario(usuario.getId());
//            }
//        });

//        btnUpdate.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                editarUsuario(usuario.getId());
//            }
//        });
    }

    private void cadastrarUsuario() {
        String nome = dfsNome.getText();
        String senha = new String(dfsSenha.getPassword());
        String cargo = (String) cbxCargo.getSelectedItem();

        Usuario novoUsuario = new Usuario(nome, senha, cargo);
        usuarioController.cadastrarUsuario(novoUsuario);
    }

    private void editarUsuario(int id) {
        String nome = dfsNome.getText();
        String senha = new String(dfsSenha.getPassword());
        String cargo = (String) cbxCargo.getSelectedItem();

        Usuario novoUsuario = new Usuario(id, nome, senha, cargo);
        usuarioController.editarUsuario(novoUsuario);
    }

    private void excluirUsuario(int id) {
        usuarioController.excluirUsuario(id);
    }

    public void limpaCampos(){
        dfsNome.setText("");
        dfsSenha.setText("");
        cbxCargo.setSelectedIndex(-1);
    }
}