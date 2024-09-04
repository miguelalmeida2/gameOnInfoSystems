package Dal.Repositories;

import Dal.DataScope;
import Dal.Mappers.MapperPontuacoesMultiJogador;
import dalGenericInterfaces.IRepository;
import jakarta.persistence.EntityManager;
import model.PontuacoesKey;
import model.PontuacoesMultiJogador;

import java.util.List;

public class PontuacoesMultiJogadorRepository implements IRepository<PontuacoesMultiJogador, PontuacoesKey> {
    @Override
    public List<PontuacoesMultiJogador> getAll() throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            List<PontuacoesMultiJogador> l = em.createNamedQuery("PontuacoesMultiJogador.getAll", PontuacoesMultiJogador.class).getResultList();
            ds.validateWork();
            return l;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
    }


    @Override
    public PontuacoesMultiJogador find(PontuacoesKey id) throws Exception {
        MapperPontuacoesMultiJogador m = new MapperPontuacoesMultiJogador();
        try {
            return m.read(id.getRef_jogo(),  id.getNum_partida(), id.getId_jogador());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void add(PontuacoesMultiJogador entity) throws Exception {
        MapperPontuacoesMultiJogador mcp = new MapperPontuacoesMultiJogador();
        try {
            mcp.create(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(PontuacoesMultiJogador entity) throws Exception {
        MapperPontuacoesMultiJogador mcp = new MapperPontuacoesMultiJogador();
        try {
            mcp.delete(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void save(PontuacoesMultiJogador entity) throws Exception {
        MapperPontuacoesMultiJogador mcp = new MapperPontuacoesMultiJogador();
        try {
            mcp.update(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
