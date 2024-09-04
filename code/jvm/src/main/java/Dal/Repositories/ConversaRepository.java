package Dal.Repositories;

import Dal.Mappers.MapperConversa;
import dalGenericInterfaces.IRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Conversa;

import java.util.List;

public class ConversaRepository implements IRepository<Conversa, Integer> {

    public List<Conversa> findAll() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjetoBase");
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("Conversa.getAll", Conversa.class).getResultList();
    }

    @Override
    public List<Conversa> getAll() throws Exception {
        return null;
    }

    @Override
    public Conversa find(Integer id) throws Exception {
        MapperConversa m = new MapperConversa();
        try {
            return m.read(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void add(Conversa entity) throws Exception {
        MapperConversa mcp = new MapperConversa();
        try {
            mcp.create(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(Conversa entity) throws Exception {
        MapperConversa mcp = new MapperConversa();
        try {
            mcp.delete(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void save(Conversa entity) throws Exception {
        MapperConversa mcp = new MapperConversa();
        try {
            mcp.update(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }



}
