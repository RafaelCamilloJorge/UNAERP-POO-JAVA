package usuario;

import livro.LivroListener;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.Conexao;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private static final List<UsuarioListener> listeners = new ArrayList<>();

    public static void subscribe(UsuarioListener UsuarioListener) {
        listeners.add(UsuarioListener);
    }

    private static void notifyDataChanged() {
        for(UsuarioListener UsuarioListener : listeners) {
            UsuarioListener.carregarTabela();
        }
    }

    public static void cadastrarUsuario(Usuario usuario) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = Conexao.getDatabaseSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(usuario);
            transaction.commit();
            notifyDataChanged();
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
                notifyDataChanged();
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
            notifyDataChanged();
            System.out.println("Usuário editado com sucesso.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Erro ao editar usuário: " + e.getMessage());
        }
    }

    public static Usuario buscarUsuario(String nomeUsuario, String senha) {
        Usuario usuario = null;
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            Query<Usuario> query = session.createQuery("FROM Usuario WHERE lower(nome) = :nomeUsuario AND lower(senha) = :senha ", Usuario.class);
            query.setParameter("nomeUsuario", nomeUsuario.toLowerCase());
            query.setParameter("senha", senha.toLowerCase());
            usuario = query.uniqueResult();
        } catch (Exception e) {
            System.out.println("Erro ao buscar usuários por nome: " + e.getMessage());
        }
        return usuario;
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