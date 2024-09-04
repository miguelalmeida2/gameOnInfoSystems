package Dal.Mappers;

import Dal.Repositories.RegiaoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;
import model.Regiao;

public class MapperRegiao{

    private EntityManagerFactory emf;
    private EntityManager em;

    public String create(Regiao v) throws Exception {
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


    public Regiao read(String m) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {
            return  em.find(Regiao.class, m);

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

    public void update(Regiao v) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {
            em.getTransaction().begin();
            Regiao v1 = em.find(Regiao.class, v.getNome(), LockModeType.PESSIMISTIC_WRITE );
            if (v1 == null)
                throw new java.lang.IllegalAccessException("Entidade inexistente");
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

    public void delete(Regiao v) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {
            em.getTransaction().begin();
            Regiao v1 = em.find(Regiao.class, v.getNome(),LockModeType.PESSIMISTIC_WRITE );
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

    public String firstName() throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {
            RegiaoRepository rv = new RegiaoRepository();
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
