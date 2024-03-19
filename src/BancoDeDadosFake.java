import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BancoDeDadosFake {
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
}
