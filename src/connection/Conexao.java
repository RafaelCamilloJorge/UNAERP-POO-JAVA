package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

    public static Connection abrirConexao() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:tasks.db");
    }

    public static void createDatabase() {
        // Abrir uma conexão com banco de dados
        try(Connection connection = abrirConexao();
            Statement statement = connection.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS livro ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "titulo VARCHAR(255) NULL,"
                    + "autor VARCHAR(255) NULL,"
                    + "categoria VARCHAR(100) NULL,"
                    + "isbn VARCHAR(20) NULL,"
                    + "disponivel INTEGER NULL,"
                    + "prazoEmprestimo INTEGER NULL"
                    + ");";

            statement.execute(sql);


        } catch (SQLException err) {
            err.printStackTrace();
        }
    }
}