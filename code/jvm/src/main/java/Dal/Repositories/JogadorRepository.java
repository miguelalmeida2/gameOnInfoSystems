package Dal.Repositories;

import Dal.DataScope;
import Dal.Mappers.MapperJogador;
import dalGenericInterfaces.IRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Compra;
import model.Jogador;

import java.util.List;

public class JogadorRepository implements IRepository<Jogador,Integer> {

    //TODO: Manter com NamedQuery? Ou fazer com createQuery e fazer a query aqui?
    public List<Jogador> findAll(){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjetoBase");
        EntityManager em = emf.createEntityManager();

        return em.createNamedQuery("Jogador.getAll",Jogador.class).getResultList();

    }


    @Override
    public List<Jogador> getAll() throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            List<Jogador> l = em.createNamedQuery("Jogador.getAll", Jogador.class).getResultList();
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
    public Jogador find(Integer id) throws Exception {
        MapperJogador m = new MapperJogador();

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
    public void add(Jogador entity) throws Exception {
        MapperJogador mcp = new MapperJogador();

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
    public void delete(Jogador entity) throws Exception {
        MapperJogador mcp = new MapperJogador();

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
    public void save(Jogador entity) throws Exception {
        MapperJogador mcp = new MapperJogador();

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
