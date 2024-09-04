package Dal.Mappers;

import Dal.DataScope;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import model.PontuacoesKey;
import model.PontuacoesMultiJogador;

public class MapperPontuacoesMultiJogador {


    private EntityManagerFactory emf;
    private EntityManager em;

    public PontuacoesKey create(PontuacoesMultiJogador v) throws Exception {
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


    public PontuacoesMultiJogador read(String ref_jogo, int num ,  int jogador) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.flush();
            PontuacoesKey cmId = new PontuacoesKey();
            cmId.setNum_partida(num);
            cmId.setRef_jogo(ref_jogo+"");
            cmId.setId_jogador(jogador);
            PontuacoesMultiJogador a = em.find(PontuacoesMultiJogador.class, cmId,LockModeType.PESSIMISTIC_READ );
            ds.validateWork();
            return a;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void update(PontuacoesMultiJogador a) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.flush();
            PontuacoesMultiJogador a1 = em.find(PontuacoesMultiJogador.class,
                    a.getId(),LockModeType.PESSIMISTIC_WRITE );
            if (a1 == null)
                throw new java.lang.IllegalAccessException("Entidade inexistente");
            a1.setPartida(a.getPartida());
            a1.setPontos(a.getPontos());
            a1.setId_jogador(a.getId_jogador());
            ds.validateWork();
        }
    }

    public void delete(PontuacoesMultiJogador v) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            em.flush();
            PontuacoesMultiJogador v1 = em.find(PontuacoesMultiJogador.class, v.getId(), LockModeType.PESSIMISTIC_WRITE);
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
