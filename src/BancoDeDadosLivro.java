import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BancoDeDadosLivro {
    private static List<Livro> bancoDeDados = new ArrayList<>();

    public static void adicionarLivro(Livro livro) {
        bancoDeDados.add(livro);
    }

    public static void removerLivro(int id) {
        Iterator<Livro> iterator = bancoDeDados.iterator();
        while (iterator.hasNext()) {
            Livro livro = iterator.next();
            if (livro.getID() == id) {
                iterator.remove();
                return;
            }
        }
        System.out.println("Livro com ID " + id + " não encontrado para remoção.");
    }

    public static List<Livro> getLivros() {
        return bancoDeDados;
    }

    public static Livro getLivroPorID(int id) {
        for (Livro livro : bancoDeDados) {
            if (livro.getID() == id) {
                return livro;
            }
        }
        return null;
    }

    public static List<Livro> buscarLivrosPorNome(String nomeLivro) {
        List<Livro> livrosFiltrados = new ArrayList<>();
        for (Livro livro : getLivros()) {
            if (livro.getTitulo().toLowerCase().contains(nomeLivro.toLowerCase())) {
                livrosFiltrados.add(livro);
            }
        }
        return livrosFiltrados;
    }

    public static List<Livro> buscarLivros(String nomeLivro, String autor, String genero, String ISBN) {
        List<Livro> livrosFiltrados = new ArrayList<>();

        for (Livro livro : getLivros()) {
            if ((!nomeLivro.isBlank() && livro.getTitulo().toLowerCase().contains(nomeLivro.toLowerCase())) ||
                    (!autor.isBlank() && livro.getAutor().toLowerCase().contains(autor.toLowerCase())) ||
                    (!genero.isBlank() && livro.getCategoria().toLowerCase().contains(genero.toLowerCase())) ||
                    (!ISBN.isBlank() && livro.getIsbn().toLowerCase().contains(ISBN.toLowerCase()))) {
                livrosFiltrados.add(livro);
            }
        }

        return livrosFiltrados;
    }
}
