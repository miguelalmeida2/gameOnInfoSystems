package Dal.Mappers;

import Dal.Repositories.AmizadeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;
import model.Amizade;
import model.AmizadeKey;

public class MapperAmizade {

    private EntityManagerFactory emf;
    private EntityManager em;

    public AmizadeKey create(Amizade v) throws Exception {
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

    public Amizade read(AmizadeKey m) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try {
            return em.find(Amizade.class, m);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    public void update(Amizade v) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Amizade v1 = em.find(Amizade.class, v.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (v1 == null)
                throw new java.lang.IllegalAccessException("Entidade inexistente");
            v1.setJogador1(v.getJogador1());
            v1.setJogador2(v.getJogador2());
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    public void delete(Amizade v) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Amizade v1 = em.find(Amizade.class, v.getId(), LockModeType.PESSIMISTIC_WRITE);
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

    public AmizadeKey firstId() throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try {
            AmizadeRepository rv = new AmizadeRepository();
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
