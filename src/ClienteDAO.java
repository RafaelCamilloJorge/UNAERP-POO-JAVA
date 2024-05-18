import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ClienteDAO {
    public static void adicionarCliente(Cliente cliente) {
        Session session = Conexao.getDatabaseSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(cliente);
        transaction.commit();
        session.close();
    }

    public static void editarCliente(Cliente cliente) {
        Session session = Conexao.getDatabaseSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(cliente);
        transaction.commit();
        session.close();
    }

    public static void deletarCliente(Cliente cliente) {
        Session session = Conexao.getDatabaseSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(cliente);
        transaction.commit();
        session.close();
    }

    public static List<Cliente> getClientes() {
        Session session = Conexao.getDatabaseSessionFactory().openSession();
        List<Cliente> clientes = session.createQuery("from Cliente").list();
        session.close();
        return clientes;
    }

    public static Cliente getClientePorID(int id) {
        Session session = Conexao.getDatabaseSessionFactory().openSession();
        Cliente cliente = session.get(Cliente.class, id);
        session.close();
        return cliente;
    }

    public static boolean isCPFUtilizado(String cpf) {
        Session session = Conexao.getDatabaseSessionFactory().openSession();
        Query query = session.createQuery("from Cliente where cpf = :cpf");
        query.setParameter("cpf", cpf);
        List<Cliente> clientes = query.list();
        session.close();
        return !clientes.isEmpty();
    }
}
