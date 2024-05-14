import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public static void cadastrarUsuario(Usuario usuario) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = Conexao.getDatabaseSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(usuario);
            transaction.commit();
            System.out.println("Usuário cadastrado com sucesso.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public static void removerUsuario(int id) {
        Transaction transaction = null;
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Usuario usuario = session.get(Usuario.class, id);
            if (usuario == null) {
                System.out.println("Usuário com ID " + id + " não encontrado para remoção.");
            } else {
                session.delete(usuario);
                transaction.commit();
                System.out.println("Usuário removido com sucesso.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Erro ao remover usuário: " + e.getMessage());
        }
    }

    public static void editarUsuario(Usuario usuario) {
        Transaction transaction = null;
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(usuario);
            transaction.commit();
            System.out.println("Usuário editado com sucesso.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Erro ao editar usuário: " + e.getMessage());
        }
    }

    public static List<Usuario> buscarUsuariosPorNome(String nomeUsuario) {
        List<Usuario> usuarios = new ArrayList<>();
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            Query<Usuario> query = session.createQuery("FROM Usuario WHERE lower(nome) LIKE :nomeUsuario", Usuario.class);
            query.setParameter("nomeUsuario", "%" + nomeUsuario.toLowerCase() + "%");
            usuarios = query.list();
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuários por nome: " + e.getMessage());
        }
        return usuarios;
    }

    public static List<Usuario> buscarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            Query<Usuario> query = session.createQuery("FROM Usuario", Usuario.class);
            usuarios = query.list();
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuários por nome: " + e.getMessage());
        }
        return usuarios;
    }
}