import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Conexao.createSessionFactory();

        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView("Rafael");
            mainView.setVisible(true);
        });
    }
}