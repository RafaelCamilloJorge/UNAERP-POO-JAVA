package usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManipularUsuarioView extends JDialog implements UsuarioSelectionListener{

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
    private int idUser;

    public ManipularUsuarioView(JFrame parent) {
        super(parent, true);
        setSize(400, 250);
        setTitle("Usuarios");

        usuarioController = new UsuarioController(this, usuarioDAO);

        labelNome = new JLabel("Nome:");
        labelSenha = new JLabel("Senha:");
        labelCargo = new JLabel("Cargo:");

        dfsNome = new JTextField();
        dfsSenha = new JPasswordField();
        String[] cargos = {"Administrador", "Funcionário"};
        cbxCargo = new JComboBox<>(cargos);

        btnCreate = new JButton("Cadastrar");
        btnDelete = new JButton("Excluir");
        btnUpdate = new JButton("Atualizar");
        btnTabela = new JButton("Tabela");

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
        panel.add(btnTabela);

        btnDelete.setEnabled(false);
        btnUpdate.setEnabled(false);


        setLayout(new BorderLayout());
        add(panel, BorderLayout.PAGE_END);

        setLocationRelativeTo(parent);

        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarUsuario();
                limpaCampos();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarUsuario(idUser);
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirUsuario(idUser);
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
        UsuarioTableView tabelaDialog = new UsuarioTableView(this, true, this);
        tabelaDialog.setLocationRelativeTo(null);
        tabelaDialog.setVisible(true);
    }

    @Override
    public void onUsuarioSelected(Usuario usuario) {
        idUser = usuario.getId();
        dfsNome.setText(usuario.getNome());
        dfsSenha.setText(usuario.getSenha());
        cbxCargo.setSelectedItem(usuario.getCargo());

        btnDelete.setEnabled(true);
        btnUpdate.setEnabled(true);
    }

    public void limpaCampos(){
        dfsNome.setText("");
        dfsSenha.setText("");
        cbxCargo.setSelectedIndex(-1);

        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }
}
