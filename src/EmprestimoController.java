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

    public void emprestar(int idCliente, int idLivro, String dataEmprestimo, String dataDevolucao, String dataDevolucaoPrevista) {
        Cliente cliente = clienteDAO.getClientePorID(idCliente);
        Livro livro = livroDAO.getLivroPorID(idLivro);
        if(!livro.isDisponivel()){
            JOptionPane.showMessageDialog(emprestimoModal, "Livro indisponível para emprestimo.");
            return;
        }
        if (cliente != null && livro != null) {
            Emprestimo emprestimo = new Emprestimo(cliente, livro, dataEmprestimo, dataDevolucao, dataDevolucaoPrevista);
            EmprestimoDAO.adicionarEmprestimo(emprestimo);
            livro.setDisponivel(false);
            LivroDAO.editarLivro(livro);
            JOptionPane.showMessageDialog(emprestimoModal, "Empréstimo realizado com sucesso.");
            emprestimoModal.dispose();
        } else {
            JOptionPane.showMessageDialog(emprestimoModal, "Cliente ou livro não encontrado.");
        }
    }
}