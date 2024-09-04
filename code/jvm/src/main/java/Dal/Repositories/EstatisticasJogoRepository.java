package Dal.Repositories;

import Dal.Mappers.MapperEstatisticasJogo;
import dalGenericInterfaces.IRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.EstatisticasJogo;

import java.util.List;

public class EstatisticasJogoRepository implements IRepository<EstatisticasJogo, String> {

    //TODO: Manter com NamedQuery? Ou fazer com createQuery e fazer a query aqui?
    public List<EstatisticasJogo> findAll(){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjetoBase");
        EntityManager em = emf.createEntityManager();

        return em.createNamedQuery("EstatisticasJogo.getAll",EstatisticasJogo.class).getResultList();

    }


    @Override
    public List<EstatisticasJogo> getAll() throws Exception {
        return null;
    }

    @Override
    public EstatisticasJogo find(String id) throws Exception {
        MapperEstatisticasJogo m = new MapperEstatisticasJogo();

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
    public void add(EstatisticasJogo entity) throws Exception {
        MapperEstatisticasJogo mcp = new MapperEstatisticasJogo();

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
    public void delete(EstatisticasJogo entity) throws Exception {
        MapperEstatisticasJogo mcp = new MapperEstatisticasJogo();

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
    public void save(EstatisticasJogo entity) throws Exception {
        MapperEstatisticasJogo mcp = new MapperEstatisticasJogo();

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
