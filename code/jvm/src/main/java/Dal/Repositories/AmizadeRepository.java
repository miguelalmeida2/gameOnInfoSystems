package Dal.Repositories;

import Dal.Mappers.MapperAmizade;
import dalGenericInterfaces.IRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Amizade;
import model.AmizadeKey;

import java.util.List;

public class AmizadeRepository implements IRepository<Amizade, AmizadeKey> {

    public List<Amizade> findAll() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjetoBase");
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("Amizade.getAll", Amizade.class).getResultList();
    }

    @Override
    public List<Amizade> getAll() throws Exception {
        return null;
    }

    @Override
    public Amizade find(AmizadeKey id) throws Exception {
        MapperAmizade m = new MapperAmizade();
        try {
            return m.read(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void add(Amizade entity) throws Exception {
        MapperAmizade mcp = new MapperAmizade();
        try {
            mcp.create(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(Amizade entity) throws Exception {
        MapperAmizade mcp = new MapperAmizade();
        try {
            mcp.delete(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void save(Amizade entity) throws Exception {
        MapperAmizade mcp = new MapperAmizade();
        try {
            mcp.update(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }



}
