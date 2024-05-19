import jakarta.persistence.*;

import javax.swing.*;
import java.time.LocalDate;

public class EmprestimoController {
    private EmprestimoModal emprestimoModal;
    private ClienteDAO clienteDAO;
    private LivroDAO livroDAO;

    public EmprestimoController(EmprestimoModal emprestimoModal, ClienteDAO clienteDAO, LivroDAO livroDAO) {
        this.emprestimoModal = emprestimoModal;
        this.clienteDAO = clienteDAO;
        this.livroDAO = livroDAO;
    }

    public void emprestar(int idCliente, int idLivro, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        Cliente cliente = clienteDAO.getClientePorID(idCliente);
        Livro livro = livroDAO.getLivroPorID(idLivro);
        if (cliente != null && livro != null) {
            java.sql.Date sqlDataEmprestimo = java.sql.Date.valueOf(dataEmprestimo);
            java.sql.Date sqlDataDevolucao = java.sql.Date.valueOf(dataDevolucao);
            Emprestimo emprestimo = new Emprestimo(cliente, livro, sqlDataEmprestimo, sqlDataDevolucao);
            EmprestimoDAO.adicionarEmprestimo(emprestimo);
            JOptionPane.showMessageDialog(emprestimoModal, "Empréstimo realizado com sucesso.");
            emprestimoModal.dispose();
        } else {
            JOptionPane.showMessageDialog(emprestimoModal, "Cliente ou livro não encontrado.");
        }
    }
}