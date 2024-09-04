package Dal.Repositories;

import Dal.Mappers.MapperMensagem;
import dalGenericInterfaces.IRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Mensagem;

import java.util.List;

public class MensagemRepository implements IRepository<Mensagem,Integer> {

    //TODO: Manter com NamedQuery? Ou fazer com createQuery e fazer a query aqui?
    public List<Mensagem> findAll(){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjetoBase");
        EntityManager em = emf.createEntityManager();

        return em.createNamedQuery("Mensagem.getAll",Mensagem.class).getResultList();

    }


    @Override
    public List<Mensagem> getAll() throws Exception {
        return null;
    }

    @Override
    public Mensagem find(Integer id) throws Exception {
        MapperMensagem m = new MapperMensagem();

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
    public void add(Mensagem entity) throws Exception {
        MapperMensagem mcp = new MapperMensagem();

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
    public void delete(Mensagem entity) throws Exception {
        MapperMensagem mcp = new MapperMensagem();

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
    public void save(Mensagem entity) throws Exception {
        MapperMensagem mcp = new MapperMensagem();

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
