package Dal.Repositories;

import Dal.DataScope;
import Dal.Mappers.MapperJogo;
import dalGenericInterfaces.IRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Jogador;
import model.Jogo;

import java.util.List;

public class JogoRepository implements IRepository<Jogo, String> {

    //TODO: Manter com NamedQuery? Ou fazer com createQuery e fazer a query aqui?
    public List<Jogo> findAll(){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjetoBase");
        EntityManager em = emf.createEntityManager();

        return em.createNamedQuery("Jogo.getAll", Jogo.class).getResultList();

    }


    @Override
    public List<Jogo> getAll() throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            List<Jogo> l = em.createNamedQuery("Jogo.getAll", Jogo.class).getResultList();
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
    public Jogo find(String id) throws Exception {
        MapperJogo m = new MapperJogo();

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
    public void add(Jogo entity) throws Exception {
        MapperJogo mcp = new MapperJogo();

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
    public void delete(Jogo entity) throws Exception {
        MapperJogo mcp = new MapperJogo();

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
    public void save(Jogo entity) throws Exception {
        MapperJogo mcp = new MapperJogo();

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
