package cliente;

import javax.swing.*;

public class ClienteController {
    private ClienteDAO clienteDAO;
    private ManipularClienteView manipularClienteModal;

    public ClienteController(ClienteDAO clienteDAO, ManipularClienteView manipularClienteModal) {
        this.clienteDAO = clienteDAO;
        this.manipularClienteModal = manipularClienteModal;
    }

    public void criarCliente(Cliente cliente) {
        if (clienteDAO.isCPFUtilizado(cliente.getCpf())) {
            JOptionPane.showMessageDialog(manipularClienteModal, "CPF já utilizado");
            return;
        }

        if (cliente.getNome().isEmpty() || cliente.getCpf().isEmpty() || cliente.getTelefone().isEmpty()) {
            JOptionPane.showMessageDialog(manipularClienteModal, "Preencha todos os campos");
            return;
        }

        if (!isCPFValid(cliente.getCpf())) {
            JOptionPane.showMessageDialog(manipularClienteModal, "CPF inválido");
            return;
        }

        if (!isTelefoneValid(cliente.getTelefone())) {
            JOptionPane.showMessageDialog(manipularClienteModal, "Telefone inválido");
            return;
        }

        clienteDAO.adicionarCliente(cliente);
        JOptionPane.showMessageDialog(manipularClienteModal, "Cliente.Cliente criado com sucesso");
        manipularClienteModal.dispose();
    }

    public boolean isCPFValid(String cpf) {
        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit >= 10) {
            firstDigit = 0;
        }

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (cpf.charAt(i) - '0') * (11 - i);
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit >= 10) {
            secondDigit = 0;
        }

        return cpf.charAt(9) - '0' == firstDigit && cpf.charAt(10) - '0' == secondDigit;
    }

    public boolean isTelefoneValid(String telefone) {
        telefone = telefone.replaceAll("\\D", "");

        return telefone.length() == 10 || telefone.length() == 11;
    }

}
