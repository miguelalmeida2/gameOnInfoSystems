package Dal.Mappers;

import Dal.Repositories.CrachaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;
import model.Cracha;

public class MapperCracha {

    private EntityManagerFactory emf;
    private EntityManager em;

    public String create(Cracha v) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {

            em.getTransaction().begin();
            em.persist(v);
            em.getTransaction().commit();
            return v.getNome();

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
        finally
        {

            em.close();
            emf.close();
        }

    }


    public Cracha read(String m) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {
            return  em.find(Cracha .class, m);

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
        finally
        {

            em.close();
            emf.close();
        }

    }

    public void update(Cracha v) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {
            em.getTransaction().begin();
            Cracha v1 = em.find(Cracha.class, v.getNome(), LockModeType.PESSIMISTIC_WRITE );
            if (v1 == null)
                throw new java.lang.IllegalAccessException("Entidade inexistente");
            v1.setImagem(v.getImagem());
            v1.setJogo(v.getJogo());
            v1.setLimiteDePontos(v.getLimiteDePontos());
            em.getTransaction().commit();

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
        finally
        {

            em.close();
            emf.close();
        }

    }

    public void delete(Cracha v) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {
            em.getTransaction().begin();
            Cracha v1 = em.find(Cracha.class, v.getNome(),LockModeType.PESSIMISTIC_WRITE );
            if (v1 == null)
                throw new java.lang.IllegalAccessException("Entidade inexistente");
            em.remove(v1);
            em.getTransaction().commit();

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
        finally
        {

            em.close();
            emf.close();
        }
    }

    public String firstNome() throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {
            CrachaRepository rv = new CrachaRepository();
            return rv.getAll().get(0).getNome();

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
        finally
        {

            em.close();
            emf.close();
        }
    }

}
