package Dal.Repositories;

import Dal.DataScope;
import Dal.Mappers.MapperCrachaJogador;
import dalGenericInterfaces.IRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.CrachaJogador;
import model.CrachaJogadorKey;
import model.Jogador;

import java.util.List;

public class CrachaJogadorRepository implements IRepository<CrachaJogador, CrachaJogadorKey> {

    //TODO: Manter com NamedQuery? Ou fazer com createQuery e fazer a query aqui?
    public List<CrachaJogador> findAll(){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjetoBase");
        EntityManager em = emf.createEntityManager();

        return em.createNamedQuery("CrachaJogador.getAll",CrachaJogador.class).getResultList();

    }


    @Override
    public List<CrachaJogador> getAll() throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            List<CrachaJogador> l = em.createNamedQuery("CrachaJogador.getAll", CrachaJogador.class).getResultList();
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
    public CrachaJogador find(CrachaJogadorKey id) throws Exception {
        MapperCrachaJogador m = new MapperCrachaJogador();

        try
        {
            return m.read(id.getCracha(),id.getJogador());
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void add(CrachaJogador entity) throws Exception {
        MapperCrachaJogador mcp = new MapperCrachaJogador();

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
    public void delete(CrachaJogador entity) throws Exception {
        MapperCrachaJogador mcp = new MapperCrachaJogador();

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
    public void save(CrachaJogador entity) throws Exception {
        MapperCrachaJogador mcp = new MapperCrachaJogador();

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
