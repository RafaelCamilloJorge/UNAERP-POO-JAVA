import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManipularUsuarioModal extends JDialog implements UsuarioSelectionListener{

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
    private JButton btnTabela;

    public ManipularUsuarioModal(JFrame parent) {
        super(parent, true);
        setSize(400, 250);
        setTitle("Usuarios");

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
        btnTabela = new JButton("Tabela"); // Inicializa o novo botão

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        panel.add(labelNome);
        panel.add(dfsNome);
        panel.add(labelSenha);
        panel.add(dfsSenha);
        panel.add(labelCargo);
        panel.add(cbxCargo);
        panel.add(btnCreate);
        panel.add(btnDelete);
        panel.add(btnUpdate);
        panel.add(btnTabela); // Adiciona o botão à interface

        setLayout(new BorderLayout());
        add(panel, BorderLayout.PAGE_END);

        setLocationRelativeTo(parent);

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarUsuario();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Usuario usuario = new Usuario();
                excluirUsuario(usuario.getId());
            }
        });

        btnTabela.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirTabela();
            }
        });
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

    private void exibirTabela() {
        // Crie uma instância da classe UsuarioTableModal
        DefaultTableModel tableModel = new DefaultTableModel();
        UsuarioTableModal tabela = new UsuarioTableModal(tableModel, this);

        // Crie um JDialog para exibir a tabela
        JDialog tabelaDialog = new JDialog(this, "Tabela de Usuários", true);
        tabelaDialog.setLayout(new BorderLayout());
        tabelaDialog.add(new JScrollPane(tabela), BorderLayout.CENTER);
        tabelaDialog.setSize(600, 400);
        tabelaDialog.setLocationRelativeTo(this);
        tabelaDialog.setVisible(true);
    }

    @Override
    public void onUsuarioSelected(Usuario usuario) {
        dfsNome.setText(usuario.getNome());
        dfsSenha.setText(usuario.getSenha());
        cbxCargo.setSelectedItem(usuario.getCargo());
    }

    public void limpaCampos(){
        dfsNome.setText("");
        dfsSenha.setText("");
        cbxCargo.setSelectedIndex(-1);
    }
}
