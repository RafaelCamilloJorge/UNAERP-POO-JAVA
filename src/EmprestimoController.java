public class EmprestimoController {
    private EmprestimoModal emprestimoModal;
    private ClienteDAO clienteDAO;

    public EmprestimoController(EmprestimoModal emprestimoModal, ClienteDAO clienteDAO) {
        this.emprestimoModal = emprestimoModal;
        this.clienteDAO = clienteDAO;
    }

    public void emprestar(int idUser, int idLivro, String dataEmprestimo, String dataDevolucao) {
        Cliente cliente = clienteDAO.getClientePorID(idUser);
        if (cliente != null) {
            Emprestimo emprestimo = new Emprestimo(idUser, idLivro, dataEmprestimo, dataDevolucao);
            EmprestimoDAO.adicionarEmprestimo(emprestimo);
        } else {
            JOptionPane.showMessageDialog(emprestimoModal, "Cliente não encontrado.");
        }

    }
}
