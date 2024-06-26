package util;

import cliente.Cliente;
import emprestimo.Emprestimo;
import livro.Livro;
import usuario.Usuario;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Conexao {

    private static SessionFactory sessionFactory;

    public static SessionFactory getDatabaseSessionFactory() {
        return sessionFactory;
    }

    public static void createSessionFactory() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .build();
        try {
            sessionFactory = new MetadataSources(registry)
                    .addAnnotatedClass(Livro.class)
                    .addAnnotatedClass(Usuario.class)
                    .addAnnotatedClass(Cliente.class)
                    .addAnnotatedClass(Emprestimo.class)
                    .buildMetadata()
                    .buildSessionFactory();

        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we
            // had trouble building the SessionFactory so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
