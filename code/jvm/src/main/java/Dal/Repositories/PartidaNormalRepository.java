package Dal.Repositories;

import Dal.DataScope;
import Dal.Mappers.MapperPartidaNormal;
import dalGenericInterfaces.IRepository;
import jakarta.persistence.EntityManager;
import model.PartidaKey;
import model.PartidaNormal;

import java.util.List;

public class PartidaNormalRepository implements IRepository<PartidaNormal,PartidaKey> {
    @Override
    public List<PartidaNormal> getAll() throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            List<PartidaNormal> l = em.createNamedQuery("PartidaNormal.getAll", PartidaNormal.class).getResultList();
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
    public PartidaNormal find(PartidaKey id) throws Exception {
        MapperPartidaNormal m = new MapperPartidaNormal();
        try {
            return m.read(id.getNum(),id.getRef_jogo());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void add(PartidaNormal entity) throws Exception {
        MapperPartidaNormal mcp = new MapperPartidaNormal();
        try {
            mcp.create(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(PartidaNormal entity) throws Exception {
        MapperPartidaNormal mcp = new MapperPartidaNormal();
        try {
            mcp.delete(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void save(PartidaNormal entity) throws Exception {
        MapperPartidaNormal mcp = new MapperPartidaNormal();
        try {
            mcp.update(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}

