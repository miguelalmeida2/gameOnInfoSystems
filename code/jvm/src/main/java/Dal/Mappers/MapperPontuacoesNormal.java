package Dal.Mappers;

import Dal.DataScope;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import model.PontuacoesKey;
import model.PontuacoesNormal;

public class MapperPontuacoesNormal {


    private EntityManagerFactory emf;
    private EntityManager em;

    public PontuacoesKey create(PontuacoesNormal v) throws Exception {
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


    public PontuacoesNormal read(String ref_jogo, int num , int jogador) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.flush();
            PontuacoesKey cmId = new PontuacoesKey();
            cmId.setNum_partida(num);
            cmId.setRef_jogo(ref_jogo+"");
            cmId.setId_jogador(jogador);
            PontuacoesNormal a = em.find(PontuacoesNormal.class, cmId,LockModeType.PESSIMISTIC_READ );
            ds.validateWork();
            return a;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void update(PontuacoesNormal a) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.flush();
            PontuacoesNormal a1 = em.find(PontuacoesNormal.class,
                    a.getId(),LockModeType.PESSIMISTIC_WRITE );
            if (a1 == null)
                throw new java.lang.IllegalAccessException("Entidade inexistente");
            a1.setPartida(a.getPartida());
            a1.setPontos(a.getPontos());
            a1.setId_jogador(a.getId_jogador());
            ds.validateWork();
        }
    }

    public void delete(PontuacoesNormal v) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.flush();
            PontuacoesNormal v1 = em.find(PontuacoesNormal.class, v.getId(), LockModeType.PESSIMISTIC_WRITE);
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
