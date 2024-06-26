package usuario;

import javax.swing.*;

public class UsuarioController {
    private final ManipularUsuarioView manipularUsuarioModal;
    private final UsuarioDAO usuarioDAO;

    public UsuarioController(ManipularUsuarioView manipularUsuarioModal, UsuarioDAO usuarioDAO){
        this.manipularUsuarioModal = manipularUsuarioModal;
        this.usuarioDAO = usuarioDAO;
    }

    public void cadastrarUsuario(Usuario usuario){
        if (!validaUsuario(usuario)) {
            return;
        }

        usuarioDAO.cadastrarUsuario(usuario);
        JOptionPane.showMessageDialog(manipularUsuarioModal, "Usuário cadastrado com sucesso!");
        manipularUsuarioModal.limpaCampos();
    }

    public void editarUsuario(Usuario usuario){
        if (!validaUsuario(usuario)) {
            return;
        }

        usuarioDAO.editarUsuario(usuario);
        JOptionPane.showMessageDialog(manipularUsuarioModal, "Usuário editado com sucesso!");
        manipularUsuarioModal.limpaCampos();
    }

    public void excluirUsuario(int id){
        usuarioDAO.removerUsuario(id);
        JOptionPane.showMessageDialog(manipularUsuarioModal, "Usuário excluído com sucesso!");
        manipularUsuarioModal.limpaCampos();
    }

    private boolean validaUsuario(Usuario usuario) {
        if (usuario.getNome().isEmpty()) {
            JOptionPane.showMessageDialog(manipularUsuarioModal, "O nome do usuário não pode estar vazio.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (usuario.getSenha().isEmpty()) {
            JOptionPane.showMessageDialog(manipularUsuarioModal, "A senha do usuário não pode estar vazia.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (usuario.getCargo() == null || usuario.getCargo().isBlank()) {
            JOptionPane.showMessageDialog(manipularUsuarioModal, "O cargo do usuário não pode estar vazio.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}