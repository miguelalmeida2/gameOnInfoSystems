package Dal.Repositories;

import Dal.DataScope;
import Dal.Mappers.MapperPartidaMultiJogador;
import dalGenericInterfaces.IRepository;
import jakarta.persistence.EntityManager;
import model.PartidaKey;
import model.PartidaMultiJogador;

import java.util.List;

public class PartidaMultiJogadorRepository implements IRepository<PartidaMultiJogador, PartidaKey> {
    @Override
    public List<PartidaMultiJogador> getAll() throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            List<PartidaMultiJogador> l = em.createNamedQuery("PartidaMultiJogador.getAll", PartidaMultiJogador.class).getResultList();
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
    public PartidaMultiJogador find(PartidaKey id) throws Exception {
        MapperPartidaMultiJogador  m = new MapperPartidaMultiJogador();
        try {
            return m.read(id.getNum(),id.getRef_jogo());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void add(PartidaMultiJogador entity) throws Exception {
        MapperPartidaMultiJogador mcp = new MapperPartidaMultiJogador();
        try {
            mcp.create(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(PartidaMultiJogador entity) throws Exception {
        MapperPartidaMultiJogador mcp = new MapperPartidaMultiJogador();
        try {
            mcp.delete(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void save(PartidaMultiJogador entity) throws Exception {
        MapperPartidaMultiJogador mcp = new MapperPartidaMultiJogador();
        try {
            mcp.update(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

}
