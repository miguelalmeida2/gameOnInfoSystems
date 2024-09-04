package Dal.Mappers;

import Dal.Repositories.EstatisticasJogadorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;
import model.EstatisticasJogador;
import model.Jogador;

public class MapperEstatisticasJogador {

    private EntityManagerFactory emf;
    private EntityManager em;

    public Jogador create(EstatisticasJogador v) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {

            em.getTransaction().begin();
            em.persist(v);
            em.getTransaction().commit();
            return v.getJogador();

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


    public EstatisticasJogador read(Jogador m) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {
            return  em.find(EstatisticasJogador.class, m);

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

    public void update(EstatisticasJogador v) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {
            em.getTransaction().begin();
            EstatisticasJogador v1 = em.find(EstatisticasJogador.class, v.getJogador(), LockModeType.PESSIMISTIC_WRITE );
            if (v1 == null)
                throw new java.lang.IllegalAccessException("Entidade inexistente");
            v1.setNumeroDeJogos(v.getNumeroDeJogos());
            v1.setNumeroDePartidas(v.getNumeroDePartidas());
            v1.setNumeroTotalDePontos(v.getNumeroTotalDePontos());
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

    public void delete(EstatisticasJogador v) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {
            em.getTransaction().begin();
            EstatisticasJogador v1 = em.find(EstatisticasJogador.class, v.getJogador(),LockModeType.PESSIMISTIC_WRITE );
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

    public Jogador firstId() throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {
            EstatisticasJogadorRepository rv = new EstatisticasJogadorRepository();
            return rv.getAll().get(0).getJogador();

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
