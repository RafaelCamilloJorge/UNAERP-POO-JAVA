package emprestimo;

import cliente.Cliente;
import jakarta.persistence.*;
import java.io.Serializable;

import livro.*;

@Entity
@Table(name = "emprestimos")
public class Emprestimo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_livro", nullable = false)
    private Livro livro;

    @Column(name = "data_emprestimo", nullable = false)
    private String dataEmprestimo;

    @Column(name = "data_devolucao", nullable = true)
    private String dataDevolucao;

    @Column(name = "data_devolucao_prevista", nullable = true)
    private String dataDevolucaoPrevista;

    public Emprestimo() {}

    public Emprestimo(Cliente cliente, Livro livro, String dataEmprestimo, String dataDevolucao, String dataDevolucaoPrevista) {
        this.cliente = cliente;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(String dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public void setDataDevolucaoPrevista(String dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }
    public String getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }
}
