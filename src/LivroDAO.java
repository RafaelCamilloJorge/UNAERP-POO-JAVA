import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.Conexao;

public class LivroDAO {
    public static void adicionarLivro(Livro livro) {
        String sql = "INSERT INTO Livro (titulo, autor, categoria, isbn, disponivel, prazoEmprestimo) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = new Conexao().abrirConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, livro.getTitulo());
            pstmt.setString(2, livro.getAutor());
            pstmt.setString(3, livro.getCategoria());
            pstmt.setString(4, livro.getIsbn());
            pstmt.setBoolean(5, livro.isDisponivel());
            pstmt.setInt(6, livro.getPrazoEmprestimo());

            pstmt.executeUpdate();

            System.out.println("Livro adicionado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar livro: " + e.getMessage());
        }
    }

    public static void removerLivro(int id) {
        String sql = "DELETE FROM Livro WHERE id = ?";

        try (Connection conexao = new Conexao().abrirConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int linhasAfetadas = pstmt.executeUpdate();

            if (linhasAfetadas == 0) {
                System.out.println("Livro com ID " + id + " não encontrado para remoção.");
            } else {
                System.out.println("Livro removido com sucesso.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao remover livro: " + e.getMessage());
        }
    }

    public static List<Livro> getLivros() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM Livro";

        try (Connection conexao = new Conexao().abrirConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Livro livro = new Livro(
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("categoria"),
                        rs.getString("isbn"),
                        rs.getBoolean("disponivel"),
                        rs.getInt("prazoEmprestimo")
                );
                livro.setID(rs.getInt("id"));
                livros.add(livro);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter livros: " + e.getMessage());
        }

        return livros;
    }

    public static Livro getLivroPorID(int id) {
        String sql = "SELECT * FROM Livro WHERE id = ?";

        try (Connection conexao = new Conexao().abrirConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Livro livro = new Livro(
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("categoria"),
                        rs.getString("isbn"),
                        rs.getBoolean("disponivel"),
                        rs.getInt("prazoEmprestimo")
                );
                livro.setID(rs.getInt("id"));
                return livro;
            } else {
                System.out.println("Livro com ID " + id + " não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter livro por ID: " + e.getMessage());
        }

        return null;
    }

    public static List<Livro> buscarLivrosPorNome(String nomeLivro) {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM Livro WHERE LOWER(titulo) LIKE ?";

        try (Connection conexao = new Conexao().abrirConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, "%" + nomeLivro.toLowerCase() + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Livro livro = new Livro(
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("categoria"),
                        rs.getString("isbn"),
                        rs.getBoolean("disponivel"),
                        rs.getInt("prazoEmprestimo")
                );
                livro.setID(rs.getInt("id"));
                livros.add(livro);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar livros por nome: " + e.getMessage());
        }

        return livros;
    }

    public static List<Livro> buscarLivros(String nomeLivro, String autor, String genero, String ISBN) {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM Livro WHERE LOWER(titulo) LIKE ? AND LOWER(autor) LIKE ? AND LOWER(categoria) LIKE ? AND LOWER(isbn) LIKE ?";

        try (Connection conexao = new Conexao().abrirConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, "%" + nomeLivro.toLowerCase() + "%");
            pstmt.setString(2, "%" + autor.toLowerCase() + "%");
            pstmt.setString(3, "%" + genero.toLowerCase() + "%");
            pstmt.setString(4, "%" + ISBN.toLowerCase() + "%");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Livro livro = new Livro(
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("categoria"),
                        rs.getString("isbn"),
                        rs.getBoolean("disponivel"),
                        rs.getInt("prazoEmprestimo")
                );
                livro.setID(rs.getInt("id"));
                livros.add(livro);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar livros: " + e.getMessage());
        }

        return livros;
    }

    public static boolean isISBNUtilizado(String isbn) {
        String sql = "SELECT COUNT(*) FROM Livro WHERE isbn = ?";

        try (Connection conexao = new Conexao().abrirConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();
            rs.next();

            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao verificar se o ISBN é utilizado: " + e.getMessage());
        }

        return false;
    }

    public static void editarLivro(Livro livro) {
        String sql = "UPDATE livro SET titulo = ?, autor = ?, categoria = ?, isbn = ?, disponivel = ?, prazoEmprestimo = ? WHERE id = ?";

        try (Connection conexao = new Conexao().abrirConexao();
             PreparedStatement pstmt = conexao.prepareStatement(sql)) {

            pstmt.setString(1, livro.getTitulo());
            pstmt.setString(2, livro.getAutor());
            pstmt.setString(3, livro.getCategoria());
            pstmt.setString(4, livro.getIsbn());
            pstmt.setBoolean(5, livro.isDisponivel());
            pstmt.setInt(6, livro.getPrazoEmprestimo());
            pstmt.setInt(7, livro.getID());

            int linhasAfetadas = pstmt.executeUpdate();




            if (linhasAfetadas > 0) {
                System.out.println("Livro editado com sucesso.");
            } else {
                System.out.println("Nenhum livro foi editado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao editar livro: " + e.getMessage());
        }
    }

}
