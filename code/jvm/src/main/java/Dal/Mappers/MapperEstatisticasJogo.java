package Dal.Mappers;

import Dal.Repositories.EstatisticasJogoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;
import model.EstatisticasJogo;
import model.Jogo;

public class MapperEstatisticasJogo {

    private EntityManagerFactory emf;
    private EntityManager em;

    public Jogo create(EstatisticasJogo v) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {

            em.getTransaction().begin();
            em.persist(v);
            em.getTransaction().commit();
            return v.getJogo();

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


    public EstatisticasJogo read(String m) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {
            return  em.find(EstatisticasJogo.class, m);

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

    public void update(EstatisticasJogo v) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {
            em.getTransaction().begin();
            EstatisticasJogo v1 = em.find(EstatisticasJogo.class, v.getJogo(), LockModeType.PESSIMISTIC_WRITE );
            if (v1 == null)
                throw new java.lang.IllegalAccessException("Entidade inexistente");
            v1.setNumeroDeJogadores(v.getNumeroDeJogadores());
            v1.setNumeroDePartidas(v.getNumeroDePartidas());
            v1.setTotalDePontos(v.getTotalDePontos());
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

    public void delete(EstatisticasJogo v) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {
            em.getTransaction().begin();
            EstatisticasJogo v1 = em.find(EstatisticasJogo.class, v.getJogo(),LockModeType.PESSIMISTIC_WRITE );
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

    public Jogo firstId() throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {
            EstatisticasJogoRepository rv = new EstatisticasJogoRepository();
            return rv.getAll().get(0).getJogo();

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
