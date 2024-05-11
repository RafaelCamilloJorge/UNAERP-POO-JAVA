public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private String categoria;
    private String isbn;
    private boolean disponivel;
    private Integer prazoEmprestimo;

    public Livro(String titulo, String autor, String categoria, String isbn, boolean disponivel, int prazoEmprestimo) {
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.isbn = isbn;
        this.disponivel = disponivel;
        this.prazoEmprestimo = prazoEmprestimo;
    }

    public Livro(Integer id, String titulo, String autor, String categoria, String isbn, boolean disponivel, int prazoEmprestimo){
        this.id = id;
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

    public void setID(int id){
        this.id = id;
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

    public Integer getPrazoEmprestimo() {
        return prazoEmprestimo;
    }

    public void setPrazoEmprestimo(int prazoEmprestimo) {
        this.prazoEmprestimo = prazoEmprestimo;
    }
}
