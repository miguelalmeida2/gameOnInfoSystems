package Dal.Mappers;

import Dal.DataScope;
import Dal.Repositories.CompraRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;
import model.CompraKey;
import model.Jogo;

public class MapperJogo{

    private EntityManagerFactory emf;
    private EntityManager em;

    public String create(Jogo v) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.flush();
            em.persist(v);
            ds.validateWork();
            return v.getId_jogo();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }


    public Jogo read(String ref_jogo) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.flush();
            Jogo a = em.find(Jogo.class, ref_jogo,LockModeType.PESSIMISTIC_READ );
            ds.validateWork();
            return a;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
    }


    public void update(Jogo a) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.flush();
            Jogo a1 = em.find(Jogo.class,
                    a.getId_jogo(),LockModeType.PESSIMISTIC_WRITE );
            if (a1 == null)
                throw new java.lang.IllegalAccessException("Entidade inexistente");
            a1.setUrl(a.getUrl());
            a1.setNome(a.getNome());
            ds.validateWork();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }



    public void delete(Jogo v) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.flush();
            Jogo v1 = em.find(Jogo.class, v.getId_jogo(), LockModeType.PESSIMISTIC_WRITE);
            if (v1 == null)
                throw new java.lang.IllegalAccessException("Entidade inexistente");
            em.remove(v1);
            ds.validateWork();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }


    public CompraKey firstId() throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try {
            CompraRepository rv = new CompraRepository();
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
