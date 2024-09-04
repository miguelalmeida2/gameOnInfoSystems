package Dal.Repositories;

import Dal.DataScope;
import Dal.Mappers.MapperCracha;
import dalGenericInterfaces.IRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Cracha;
import model.Jogador;

import java.util.List;

public class CrachaRepository implements IRepository<Cracha,String> {

    //TODO: Manter com NamedQuery? Ou fazer com createQuery e fazer a query aqui?
    public List<Cracha> findAll(){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjetoBase");
        EntityManager em = emf.createEntityManager();

        return em.createNamedQuery("Cracha.getAll",Cracha.class).getResultList();

    }


    @Override
    public List<Cracha> getAll() throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            List<Cracha> l = em.createNamedQuery("Cracha.getAll",Cracha.class).getResultList();
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
    public Cracha find(String id) throws Exception {
        MapperCracha m = new MapperCracha();

        try
        {
            return m.read(id);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void add(Cracha entity) throws Exception {
        MapperCracha mcp = new MapperCracha();

        try
        {
            mcp.create(entity);

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(Cracha entity) throws Exception {
        MapperCracha mcp = new MapperCracha();

        try
        {
            mcp.delete(entity);

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void save(Cracha entity) throws Exception {
        MapperCracha mcp = new MapperCracha();

        try
        {
            mcp.update(entity);

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
    }

}
