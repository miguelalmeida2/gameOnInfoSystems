package Dal.Mappers;

import Dal.DataScope;
import Dal.Repositories.CompraRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;
import model.Compra;
import model.CompraKey;

public class MapperCompra{

    private EntityManagerFactory emf;
    private EntityManager em;

    public CompraKey create(Compra v) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.flush();
            em.persist(v);
            ds.validateWork();
            return v.getId();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }


    public Compra read(Integer jogador, String jogo) throws Exception {
        try (DataScope ds = new DataScope()) {
            if (jogo == null && jogador == null) return null;
            EntityManager em = ds.getEntityManager();
            em.flush();
            CompraKey cmId = new CompraKey();
            cmId.setJogador(jogador);
            cmId.setJogo(jogo);
            Compra a = em.find(Compra.class, cmId,LockModeType.PESSIMISTIC_READ );
            ds.validateWork();
            return a;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
    }


    public void update(Compra a) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.flush();
            Compra a1 = em.find(Compra.class,
                    a.getId(),LockModeType.PESSIMISTIC_WRITE );
            if (a1 == null)
                throw new java.lang.IllegalAccessException("Entidade inexistente");
            a1.setPreco(a.getPreco());
            a1.setData(a.getData());
            ds.validateWork();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }



    public void delete(Compra v) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.flush();
            Compra v1 = em.find(Compra.class, v.getId(), LockModeType.PESSIMISTIC_WRITE);
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
