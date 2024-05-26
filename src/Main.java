import util.Conexao;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Conexao.createSessionFactory();
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            loginView.setVisible(true);
            loginView.setLocationRelativeTo(null);
        });
    }
}