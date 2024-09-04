package Dal.Repositories;

import Dal.DataScope;
import Dal.Mappers.MapperCompra;
import dalGenericInterfaces.IRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Compra;
import model.CompraKey;

import java.util.List;

public class CompraRepository implements IRepository<Compra, CompraKey> {



    public List<Compra> findAll() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjetoBase");
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("Compra.getAll", Compra.class).getResultList();
    }
    @Override
    public List<Compra> getAll() throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            List<Compra> l = em.createNamedQuery("Compra.getAll", Compra.class).getResultList();
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
    public Compra find(CompraKey id) throws Exception {
        MapperCompra m = new MapperCompra();
        try {
            return m.read(id.getJogador(),id.getJogo());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void add(Compra entity) throws Exception {
        MapperCompra mcp = new MapperCompra();
        try {
            mcp.create(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(Compra entity) throws Exception {
        MapperCompra mcp = new MapperCompra();
        try {
            mcp.delete(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void save(Compra entity) throws Exception {
        MapperCompra mcp = new MapperCompra();
        try {
            mcp.update(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }



}
