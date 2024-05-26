import usuario.*;

public class LoginController {
    private UsuarioDAO usuarioDAO;
    private LoginView loginView;

    public LoginController(LoginView loginView, UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
    }

    public Usuario autenticarUsuario(String nome, String senha) {
        Usuario usuario = usuarioDAO.buscarUsuario(nome, senha);
        if (!nome.isEmpty() && !senha.isEmpty() && usuario != null) {
            return usuario;
        }
        return null;
    }
}