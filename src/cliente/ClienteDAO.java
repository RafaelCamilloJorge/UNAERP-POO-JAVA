package cliente;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.Conexao;

import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private static final List<ClienteListener> listeners = new ArrayList<>();

    public static void subscribe(ClienteListener clienteListener) {
        listeners.add(clienteListener);
    }

    private static void notifyDataChanged() {
        for(ClienteListener listener : listeners) {
            listener.carregarTabela();
        }
    }

    public static void adicionarCliente(Cliente cliente) {
        Transaction transaction = null;
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(cliente);
            transaction.commit();
            System.out.println("Client inserted successfully.");
            notifyDataChanged();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error inserting client: " + e.getMessage());
        }
    }

    public static void editarCliente(Cliente cliente) {
        Transaction transaction = null;
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(cliente);
            transaction.commit();
            System.out.println("Client updated successfully.");
            notifyDataChanged();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error updating client: " + e.getMessage());
        }
    }

    public static void deletarCliente(Cliente cliente) {
        Transaction transaction = null;
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(cliente);
            transaction.commit();
            System.out.println("Client deleted successfully.");
            notifyDataChanged();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error deleting client: " + e.getMessage());
        }
    }

    public static List<Cliente> getClientes() {
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            return session.createQuery("FROM Cliente", Cliente.class).list();
        } catch (Exception e) {
            System.out.println("Error fetching clients: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static Cliente getClientePorID(int id) {
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            return session.get(Cliente.class, id);
        } catch (Exception e) {
            System.out.println("Error fetching client by ID: " + e.getMessage());
            return null;
        }
    }

    public static List<Cliente> getClientePorNome(String nomeCliente) {
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            Query<Cliente> query = session.createQuery("FROM Cliente WHERE lower(nome) LIKE :nome", Cliente.class);
            query.setParameter("nome", "%" + nomeCliente.toLowerCase() + "%");
            return query.list();
        } catch (Exception e) {
            System.out.println("Error fetching clients by name: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static boolean isCPFUtilizado(String cpf) {
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            Query<Cliente> query = session.createQuery("from Cliente where cpf = :cpf", Cliente.class);
            query.setParameter("cpf", cpf);
            return !query.list().isEmpty();
        } catch (Exception e) {
            System.out.println("Error checking CPF: " + e.getMessage());
            return false;
        }
    }

}
