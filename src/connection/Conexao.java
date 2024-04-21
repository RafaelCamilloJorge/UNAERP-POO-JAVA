package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private Connection conexao;

    public Connection abrirConexao(){
        String url = "jdbc:mysql://localhost:3306/biblioteca";
        String user = "root";
        String pass = "root";

        try {
            conexao = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return conexao;
    }

    public void fecharConexao() {
        if (conexao != null) {
            try {
                conexao.close();
            } catch (SQLException e) {
                // Lidar com o erro de fechamento da conexão, se necessário
                e.printStackTrace();
            }
        }
    }
}
