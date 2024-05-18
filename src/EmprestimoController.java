import jakarta.persistence.*;

import javax.swing.*;

public class EmprestimoController {
    private EmprestimoModal emprestimoModal;
    private ClienteDAO clienteDAO;
    private LivroDAO livroDAO;

    public EmprestimoController(EmprestimoModal emprestimoModal, ClienteDAO clienteDAO, LivroDAO livroDAO) {
        this.emprestimoModal = emprestimoModal;
        this.clienteDAO = clienteDAO;
        this.livroDAO = livroDAO;
    }

    public void emprestar(int idCliente, int idLivro, String dataEmprestimo, String dataDevolucao) {
        Cliente cliente = clienteDAO.getClientePorID(idCliente);
        Livro livro = livroDAO.getLivroPorID(idLivro);
        if (cliente != null && livro != null) {
            Emprestimo emprestimo = new Emprestimo(cliente, livro, dataEmprestimo, dataDevolucao);
            EmprestimoDAO.adicionarEmprestimo(emprestimo);
        } else {
            JOptionPane.showMessageDialog(emprestimoModal, "Cliente ou livro não encontrado.");
        }
    }
}