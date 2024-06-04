package livro;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.Conexao;

import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    private static final List<LivroListener> listeners = new ArrayList<>();

    public static void subscribe(LivroListener livroListener) {
        listeners.add(livroListener);
    }

    private static void notifyDataChanged() {
        for(LivroListener listener : listeners) {
            listener.carregarTabela();
        }
    }

    public static void adicionarLivro(Livro livro) {
        Transaction transaction = null;
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(livro);
            transaction.commit();
            System.out.println("Book inserted successfully.");
            notifyDataChanged();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error inserting book: " + e.getMessage());
        }
    }

    public static void removerLivro(int id) {
        Transaction transaction = null;
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Livro livro = session.get(Livro.class, id);
            livro.setAtivo(false);
            if (livro == null) {
                System.out.println("Livro.Livro com ID " + id + " não encontrado para remoção.");
            } else {
                session.persist(livro);
                transaction.commit();
                System.out.println("Livro.Livro removido com sucesso.");
                notifyDataChanged();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Erro ao remover livro: " + e.getMessage());
        }
    }

    public static List<Livro> getLivros() {
        List<Livro> livros = new ArrayList<>();
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            Query<Livro> query = session.createQuery("FROM Livro where ativo = true", Livro.class);
            livros = query.list();
        } catch (Exception e) {
            System.out.println("Erro ao obter livros: " + e.getMessage());
        }
        return livros;
    }

    public static Livro getLivroPorID(int id) {
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            Livro livro = session.get(Livro.class, id);
            if (livro == null) {
                System.out.println("Livro.Livro com ID " + id + " não encontrado.");
            }
            return livro;
        } catch (Exception e) {
            System.out.println("Erro ao obter livro por ID: " + e.getMessage());
            return null;
        }
    }

    public static List<Livro> buscarLivrosPorNome(String nomeLivro) {
        List<Livro> livros = new ArrayList<>();
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            Query<Livro> query = session.createQuery("FROM Livro WHERE lower(titulo) LIKE :nomeLivro", Livro.class);
            query.setParameter("nomeLivro", "%" + nomeLivro.toLowerCase() + "%");
            livros = query.list();
        } catch (Exception e) {
            System.out.println("Erro ao buscar livros por nome: " + e.getMessage());
        }
        return livros;
    }

    public static List<Livro> buscarLivros(String nomeLivro, String autor, String genero, String ISBN) {
        List<Livro> livros = new ArrayList<>();
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("FROM Livro WHERE lower(titulo) LIKE :nomeLivro AND ativo = true ");

            if (autor != null && !autor.isEmpty()) {
                hql.append("AND lower(autor) LIKE :autor ");
            }
            if (genero != null && !genero.isEmpty()) {
                hql.append("AND lower(categoria) LIKE :genero ");
            }
            if (ISBN != null && !ISBN.isEmpty()) {
                hql.append("AND lower(isbn) LIKE :ISBN ");
            }

            Query<Livro> query = session.createQuery(hql.toString(), Livro.class);
            query.setParameter("nomeLivro", "%" + nomeLivro.toLowerCase() + "%");
            if (autor != null && !autor.isEmpty()) {
                query.setParameter("autor", "%" + autor.toLowerCase() + "%");
            }
            if (genero != null && !genero.isEmpty()) {
                query.setParameter("genero", "%" + genero.toLowerCase() + "%");
            }
            if (ISBN != null && !ISBN.isEmpty()) {
                query.setParameter("ISBN", "%" + ISBN.toLowerCase() + "%");
            }

            livros = query.list();
        } catch (Exception e) {
            System.out.println("Erro ao buscar livros: " + e.getMessage());
        }
        return livros;
    }

    public static Livro isISBNUtilizado(String isbn) {
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Livro WHERE isbn = :isbn", Livro.class);
            query.setParameter("isbn", isbn);
            Livro livro = (Livro) query.uniqueResult();
            return livro;
        } catch (Exception e) {
            System.out.println("Erro ao verificar se o ISBN é utilizado: " + e.getMessage());
            return null;
        }
    }

    public static boolean isDisponivel(int id) {
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(*) FROM Livro WHERE id = :id", Long.class);
            query.setParameter("id", id);
            Long count = query.uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            System.out.println("Erro ao verificar se o livro é disponivel: " + e.getMessage());
            return false;
        }
    }



    public static void editarLivro(Livro livro) {
        Transaction transaction = null;
        try (Session session = Conexao.getDatabaseSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(livro);
            transaction.commit();
            System.out.println("Livro.Livro editado com sucesso.");
            notifyDataChanged();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Erro ao editar livro: " + e.getMessage());
        }
    }


}