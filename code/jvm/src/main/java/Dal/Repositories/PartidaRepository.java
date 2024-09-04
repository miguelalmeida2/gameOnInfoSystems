package Dal.Repositories;

import Dal.DataScope;
import Dal.Mappers.MapperPartida;
import dalGenericInterfaces.IRepository;
import jakarta.persistence.EntityManager;
import model.Partida;
import model.PartidaKey;

import java.util.List;

public class PartidaRepository implements IRepository<Partida,PartidaKey> {
    @Override
    public List<Partida> getAll() throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            List<Partida> l = em.createNamedQuery("Partida.getAll", Partida.class).getResultList();
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
    public Partida find(PartidaKey id) throws Exception {
        MapperPartida m = new MapperPartida();
        try {
            return m.read(id.getNum(),id.getRef_jogo());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void add(Partida entity) throws Exception {
        MapperPartida mcp = new MapperPartida();
        try {
            mcp.create(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(Partida entity) throws Exception {
        MapperPartida mcp = new MapperPartida();
        try {
            mcp.delete(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void save(Partida entity) throws Exception {
        MapperPartida mcp = new MapperPartida();
        try {
            mcp.update(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}

