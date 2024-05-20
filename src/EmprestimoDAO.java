import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmprestimoDAO {

    public static void adicionarEmprestimo(Emprestimo emprestimo) {
        Transaction transaction = null;
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(emprestimo);
            transaction.commit();
            System.out.println("Emprestimo adicionado com sucesso.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Erro ao adicionar emprestimo: " + e.getMessage());
        }
    }

    public static Emprestimo getEmprestimoPorID(int id) {
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            Emprestimo emprestimo = session.get(Emprestimo.class, id);
            if (emprestimo == null) {
                System.out.println("Emprestimo com ID " + id + " não encontrado.");
            }
            return emprestimo;
        } catch (Exception e) {
            System.out.println("Erro ao obter emprestimo por ID: " + e.getMessage());
            return null;
        }
    }

    public static List<Emprestimo> getEmprestimos() {
        List<Emprestimo> emprestimos = null;
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Emprestimo", Emprestimo.class);
            emprestimos = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro ao obter emprestimos: " + e.getMessage());
        }
        return emprestimos;
    }

    public static void atualizarEmprestimo(Emprestimo emprestimo) {
        Transaction transaction = null;
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(emprestimo);
            transaction.commit();
            System.out.println("Emprestimo atualizado com sucesso.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Erro ao atualizar emprestimo: " + e.getMessage());
        }
    }

    public static void removerEmprestimo(Emprestimo emprestimo) {
        Transaction transaction = null;
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(emprestimo);
            transaction.commit();
            System.out.println("Emprestimo removido com sucesso.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Erro ao remover emprestimo: " + e.getMessage());
        }
    }

    public static List<Emprestimo> getEmprestimosPorLivro(Livro livro) {
        List<Emprestimo> emprestimos = null;
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Emprestimo WHERE livro = :livro", Emprestimo.class);
            query.setParameter("livro", livro);
            emprestimos = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erro ao obter emprestimos por livro: " + e.getMessage());
        }
        return emprestimos;
    }
}