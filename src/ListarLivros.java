import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ListarLivros extends JFrame {
    
    private JTable tabela;

    public ListarLivros() {
        setTitle("Lista de Livros");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);

        // Dados de exemplo para a tabela
        List<Livro> livros = BancoDeDadosFake.getLivros();
        Object[][] dados = new Object[livros.size()][5];

        for (int i = 0; i < livros.size(); i++) {
            Livro livro = livros.get(i);
            dados[i] = new Object[]{livro.titulo, livro.autor, livro.categoria, livro.isbn, livro.detalhes};
        }

        // Nomes das colunas
        String[] colunas = {"Título", "Autor", "Categoria", "ISBN", "Detalhes"};

        // Criar a tabela com os dados e as colunas
        tabela = new JTable(dados, colunas);
        tabela.setPreferredScrollableViewportSize(new Dimension(700, 400));
        tabela.setFillsViewportHeight(true);

        // Adicionar a tabela a um painel com barra de rolagem
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);
    }

}
