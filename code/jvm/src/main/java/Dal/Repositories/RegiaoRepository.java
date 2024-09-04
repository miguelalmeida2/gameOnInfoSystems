package Dal.Repositories;

import Dal.Mappers.MapperRegiao;
import dalGenericInterfaces.IRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Regiao;

import java.util.List;

public class RegiaoRepository implements IRepository<Regiao,String> {

    //TODO: Manter com NamedQuery? Ou fazer com createQuery e fazer a query aqui?
    public List<Regiao> findAll(){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjetoBase");
        EntityManager em = emf.createEntityManager();

        return em.createNamedQuery("Mensagem.getAll", Regiao.class).getResultList();

    }


    @Override
    public List<Regiao> getAll() throws Exception {
        return null;
    }

    @Override
    public Regiao find(String id) throws Exception {
        MapperRegiao m = new MapperRegiao();

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
    public void add(Regiao entity) throws Exception {
        MapperRegiao mcp = new MapperRegiao();

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
    public void delete(Regiao entity) throws Exception {
        MapperRegiao mcp = new MapperRegiao();

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
    public void save(Regiao entity) throws Exception {
        MapperRegiao mcp = new MapperRegiao();

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
