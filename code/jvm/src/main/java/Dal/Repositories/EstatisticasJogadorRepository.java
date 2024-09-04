package Dal.Repositories;

import Dal.Mappers.MapperEstatisticasJogador;
import dalGenericInterfaces.IRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.EstatisticasJogador;
import model.Jogador;

import java.util.List;

public class EstatisticasJogadorRepository implements IRepository<EstatisticasJogador, Jogador> {

    //TODO: Manter com NamedQuery? Ou fazer com createQuery e fazer a query aqui?
    public List<EstatisticasJogador> findAll(){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjetoBase");
        EntityManager em = emf.createEntityManager();

        return em.createNamedQuery("EstatisticasJogador.getAll",EstatisticasJogador.class).getResultList();

    }


    @Override
    public List<EstatisticasJogador> getAll() throws Exception {
        return null;
    }

    @Override
    public EstatisticasJogador find(Jogador id) throws Exception {
        MapperEstatisticasJogador m = new MapperEstatisticasJogador();

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
    public void add(EstatisticasJogador entity) throws Exception {
        MapperEstatisticasJogador mcp = new MapperEstatisticasJogador();

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
    public void delete(EstatisticasJogador entity) throws Exception {
        MapperEstatisticasJogador mcp = new MapperEstatisticasJogador();

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
    public void save(EstatisticasJogador entity) throws Exception {
        MapperEstatisticasJogador mcp = new MapperEstatisticasJogador();

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
