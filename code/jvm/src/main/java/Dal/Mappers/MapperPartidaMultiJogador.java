package Dal.Mappers;

import Dal.DataScope;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import model.PartidaKey;
import model.PartidaMultiJogador;

public class MapperPartidaMultiJogador {


    private EntityManagerFactory emf;
    private EntityManager em;


    public PartidaKey create(PartidaMultiJogador v) throws Exception {
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


    public PartidaMultiJogador read(int num, String ref_jogo) throws Exception {
        try (DataScope ds = new DataScope()) {
            if (ref_jogo == null) return null;
            EntityManager em = ds.getEntityManager();
            em.flush();
            PartidaKey cmId = new PartidaKey();
            cmId.setNum(num);
            cmId.setRef_jogo(ref_jogo);
            PartidaMultiJogador a = em.find(PartidaMultiJogador.class, cmId,LockModeType.PESSIMISTIC_READ );
            ds.validateWork();
            return a;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void update(PartidaMultiJogador a) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.flush();
            PartidaMultiJogador a1 = em.find(PartidaMultiJogador.class,
                    a.getId(),LockModeType.PESSIMISTIC_WRITE );
            if (a1 == null)
                throw new java.lang.IllegalAccessException("Entidade inexistente");
            a1.setPartida(a.getPartida());
            a1.setEstado(a.getEstado());
            ds.validateWork();
        }
    }

    public void delete(PartidaMultiJogador v) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.flush();
            PartidaMultiJogador v1 = em.find(PartidaMultiJogador.class, v.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (v1 == null)
                throw new java.lang.IllegalAccessException("Entidade inexistente");
            em.remove(v1);
            ds.validateWork();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }


}
