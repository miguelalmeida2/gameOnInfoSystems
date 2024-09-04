package Dal.Mappers;

import Dal.Repositories.ConversaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;
import model.Conversa;

public class MapperConversa {

    private EntityManagerFactory emf;
    private EntityManager em;

    public int create(Conversa v) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(v);
            em.getTransaction().commit();
            return v.getId();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    public Conversa read(int m) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try {
            return em.find(Conversa.class, m);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    public void update(Conversa v) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Conversa v1 = em.find(Conversa.class, v.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (v1 == null)
                throw new java.lang.IllegalAccessException("Entidade inexistente");
            v1.setJogadores(v.getJogadores());
            v1.setNome(v.getNome());
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    public void delete(Conversa v) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Conversa v1 = em.find(Conversa.class, v.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (v1 == null)
                throw new java.lang.IllegalAccessException("Entidade inexistente");
            em.remove(v1);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    public int firstId() throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try {
            ConversaRepository rv = new ConversaRepository();
            return rv.getAll().get(0).getId();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }
}
