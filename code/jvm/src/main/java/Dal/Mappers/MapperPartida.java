package Dal.Mappers;

import Dal.DataScope;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import model.Partida;
import model.PartidaKey;

public class MapperPartida {


    private EntityManagerFactory emf;
    private EntityManager em;




    public PartidaKey create(Partida v) throws Exception {
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


    public Partida read(int num, String ref_jogo) throws Exception {
        try (DataScope ds = new DataScope()) {
            if (ref_jogo == null) return null;
            EntityManager em = ds.getEntityManager();
            em.flush();
            PartidaKey cmId = new PartidaKey();
            cmId.setNum(num);
            cmId.setRef_jogo(ref_jogo);
            Partida a = em.find(Partida.class, cmId,LockModeType.PESSIMISTIC_READ );
            ds.validateWork();
            return a;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void update(Partida a) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.flush();
            Partida a1 = em.find(Partida.class,
                    a.getId(),LockModeType.PESSIMISTIC_WRITE );
            if (a1 == null)
                throw new java.lang.IllegalAccessException("Entidade inexistente");
            a1.setTipo(a.getTipo());
            a1.setRegiao(a.getRegiao());
            a1.setDataInicio(a.getDataInicio());
            a1.setDataFim(a.getDataFim());
            ds.validateWork();
        }
    }

    public void delete(Partida v) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.flush();
            Partida v1 = em.find(Partida.class, v.getId(), LockModeType.PESSIMISTIC_WRITE);
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
