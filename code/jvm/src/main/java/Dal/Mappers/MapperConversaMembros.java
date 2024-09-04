package Dal.Mappers;

import Dal.DataScope;
import Dal.Repositories.ConversaMembrosRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;
import model.ConversaMembros;
import model.ConversaMembrosKey;

public class MapperConversaMembros {

    private EntityManagerFactory emf;
    private EntityManager em;

    public ConversaMembrosKey create(ConversaMembros v) throws Exception {
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

    public ConversaMembros read(Integer conversa, Integer jogador) throws Exception {
        try (DataScope ds = new DataScope())
        {
            if (conversa == null && jogador == null) return null;
            EntityManager em = ds.getEntityManager();
            em.flush();
            ConversaMembrosKey cmId = new ConversaMembrosKey();
            cmId.setConversa(conversa);
            cmId.setJogador(jogador);
            ConversaMembros a = em.find(ConversaMembros.class, cmId);
            ds.validateWork();
            return a;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void update(ConversaMembros v) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            ConversaMembros v1 = em.find(ConversaMembros.class, v.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (v1 == null)
                throw new java.lang.IllegalAccessException("Entidade inexistente");
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    public void delete(ConversaMembros v) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            ConversaMembros v1 = em.find(ConversaMembros.class, v.getId(), LockModeType.PESSIMISTIC_WRITE);
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

    public ConversaMembrosKey firstId() throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try {
            ConversaMembrosRepository rv = new ConversaMembrosRepository();
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
