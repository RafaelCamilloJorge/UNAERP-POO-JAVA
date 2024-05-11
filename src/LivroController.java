import javax.swing.*;

    public class LivroController {
        private final ManipularLivroModal manipularLivroModal;
        private final LivroDAO livroDAO;

        public LivroController(ManipularLivroModal manipularLivroModal, LivroDAO livroDAO){
            this.manipularLivroModal = manipularLivroModal;
            this.livroDAO = livroDAO;
        }

        public void cadastrarLivro(Livro livro){
            if (livro.getTitulo().isEmpty() || livro.getAutor().isEmpty() || livro.getCategoria() == null || livro.getCategoria().isBlank() || livro.getIsbn().isEmpty() || livro.getPrazoEmprestimo() == null) {
                JOptionPane.showMessageDialog(manipularLivroModal, "Por favor, preencha todos os campos antes de cadastrar o livro.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(!validaISBN(livro.getIsbn())){
                JOptionPane.showMessageDialog(manipularLivroModal, "O ISBN deve conter apenas números e hífens.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int prazoEmprestimoInt = Integer.parseInt(String.valueOf(livro.getPrazoEmprestimo()));

                if (livroDAO.isISBNUtilizado(livro.getIsbn())) {
                    JOptionPane.showMessageDialog(manipularLivroModal, "Este ISBN já foi utilizado por outro livro.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                livroDAO.adicionarLivro(livro);

                JOptionPane.showMessageDialog(manipularLivroModal, "Livro cadastrado com sucesso!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(manipularLivroModal, "O prazo de empréstimo deve ser um número inteiro.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }


        public void editarLivro(Livro livro){
            Livro novoLivro = new Livro(livro.getID(), livro.getTitulo(), livro.getAutor(), livro.getCategoria(), livro.getIsbn(), livro.isDisponivel(), livro.getPrazoEmprestimo());

            if (livro.getTitulo().isEmpty() || livro.getAutor().isEmpty() || livro.getCategoria() == null || livro.getCategoria().isBlank() || livro.getIsbn().isEmpty() || livro.getPrazoEmprestimo() == null) {
                JOptionPane.showMessageDialog(manipularLivroModal, "Por favor, preencha todos os campos antes de cadastrar o livro.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(!validaISBN(livro.getIsbn())){
                JOptionPane.showMessageDialog(manipularLivroModal, "ISBN inválido.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!livro.getIsbn().equals(LivroDAO.getLivroPorID(livro.getID()).getIsbn())) {
                if (LivroDAO.isISBNUtilizado(livro.getIsbn())) {
                    JOptionPane.showMessageDialog(manipularLivroModal, "ISBN já foi utilizado por outro livro.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            livroDAO.editarLivro(livro);


            JOptionPane.showMessageDialog(manipularLivroModal, "Livro editado com sucesso!");
        }



        public boolean validaISBN(String isbn) {
            isbn = isbn.replaceAll("\\s|-", "");

            if (isbn.length() != 13) {
                return false;
            }

            for (int i = 0; i < 12; i++) {
                if (!Character.isDigit(isbn.charAt(i))) {
                    return false;
                }
            }

            char ultimoCaracter = isbn.charAt(12);
            if (!Character.isDigit(ultimoCaracter) && ultimoCaracter != 'X') {
                return false;
            }

            int sum = 0;
            for (int i = 0; i < 12; i++) {
                int digit = Character.getNumericValue(isbn.charAt(i));
                sum += (i % 2 == 0) ? digit : digit * 3;
            }

            int checkDigit = (10 - (sum % 10)) % 10;
            char expectedCheckDigit = (checkDigit == 10) ? '0' : (char) (checkDigit + '0');

            return ultimoCaracter == expectedCheckDigit;
        }
    }




