public class Livro {
    private int id;
    private static int proximoId = 1;
    private String titulo;
    private String autor;
    private String categoria;
    private String isbn;
    private boolean disponivel;
    private int prazoEmprestimo;

    // Construtor
    public Livro(String titulo, String autor, String categoria, String isbn, boolean disponivel, int prazoEmprestimo) {
        this.id = proximoId;
        proximoId++;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.isbn = isbn;
        this.disponivel = disponivel;
        this.prazoEmprestimo = prazoEmprestimo;
    }

    public int getID(){
        return id;
    }

    // Getters e Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public int getPrazoEmprestimo() {
        return prazoEmprestimo;
    }

    public void setPrazoEmprestimo(int prazoEmprestimo) {
        this.prazoEmprestimo = prazoEmprestimo;
    }
}
