package Dal.Mappers;

import Dal.DataScope;
import Dal.Repositories.CrachaJogadorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;
import model.CrachaJogador;
import model.CrachaJogadorKey;

public class MapperCrachaJogador {

    private EntityManagerFactory emf;
    private EntityManager em;

    public CrachaJogadorKey create(CrachaJogador v) throws Exception {
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

    public CrachaJogador read(String cracha, Integer jogador) throws Exception {
        try (DataScope ds = new DataScope())
        {
            if (cracha == null && jogador == null) return null;
            EntityManager em = ds.getEntityManager();
            em.flush();
            CrachaJogadorKey cpId = new CrachaJogadorKey();
            cpId.setCracha(cracha);
            cpId.setJogador(jogador);
            CrachaJogador a = em.find(CrachaJogador.class, cpId, LockModeType.PESSIMISTIC_READ);
            ds.validateWork();
            return a;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void update(CrachaJogador a) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.flush();
            CrachaJogador a1 = em.find(CrachaJogador.class,
                    a.getId(),LockModeType.PESSIMISTIC_WRITE );
            if (a1 == null)
                throw new java.lang.IllegalAccessException("Entidade inexistente");
            a1.setCracha(a.getCracha());
            a1.setJogador(a.getJogador());
            ds.validateWork();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void delete(CrachaJogador v) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.flush();
            CrachaJogador v1 = em.find(CrachaJogador.class, v.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (v1 == null)
                throw new java.lang.IllegalAccessException("Entidade inexistente");
            em.remove(v1);
            ds.validateWork();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public CrachaJogadorKey firstId() throws Exception {
        emf = Persistence.createEntityManagerFactory("projetoBase");
        em = emf.createEntityManager();

        try
        {
            CrachaJogadorRepository rv = new CrachaJogadorRepository();
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
