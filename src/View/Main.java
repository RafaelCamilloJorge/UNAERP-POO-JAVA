package View;

import connection.Conexao;

public class Main {
    public static void main(String[] args) {
        Conexao.createDatabase();
        LoginView login = new LoginView();
        login.setVisible(true);
        login.setLocationRelativeTo(null);
    }
}