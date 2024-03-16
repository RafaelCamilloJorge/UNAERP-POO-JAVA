import java.util.ArrayList;
import java.util.List;

public class BancoDeDadosFake {
    private static List<Livro> bancoDeDados = new ArrayList<>();

    public static void adicionarLivro(Livro livro) {
        bancoDeDados.add(livro);
    }

    public static void removerLivro(Livro livro) {
        bancoDeDados.remove(livro);
    }

    public static List<Livro> getLivros() {
        return bancoDeDados;
    }
}
