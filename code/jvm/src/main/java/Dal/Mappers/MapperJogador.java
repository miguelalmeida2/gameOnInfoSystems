package Dal.Mappers;

import Dal.Repositories.JogadorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;
import model.Jogador;

public class MapperJogador {

    private EntityManagerFactory emf;
    private EntityManager em;

    public int create(Jogador v) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {

            em.getTransaction().begin();
            em.persist(v);
            em.getTransaction().commit();
            return v.getId();

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


    public Jogador read(int m) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {
            return  em.find(Jogador.class, m);

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

    public void update(Jogador v) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {
            em.getTransaction().begin();
            Jogador v1 = em.find(Jogador.class, v.getId(), LockModeType.PESSIMISTIC_WRITE );
            if (v1 == null)
                throw new java.lang.IllegalAccessException("Entidade inexistente");
            v1.setUsername(v.getUsername());
            v1.setEmail(v.getEmail());
            v1.setRegiao(v.getRegiao());
            v1.setEstado(v.getEstado());
            v1.setAmigos(v.getAmigos());
            v1.setConversas(v.getConversas());
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

    public void delete(Jogador v) throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {
            em.getTransaction().begin();
            Jogador v1 = em.find(Jogador.class, v.getId(),LockModeType.PESSIMISTIC_WRITE );
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

    public int firstId() throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {
            JogadorRepository rv = new JogadorRepository();
            return rv.getAll().get(0).getId();

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
