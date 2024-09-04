package Dal.Repositories;

import Dal.Mappers.MapperConversaMembros;
import dalGenericInterfaces.IRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.ConversaMembros;
import model.ConversaMembrosKey;

import java.util.List;

public class ConversaMembrosRepository implements IRepository<ConversaMembros, ConversaMembrosKey> {

    public List<ConversaMembros> findAll() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjetoBase");
        EntityManager em = emf.createEntityManager();
        return em.createNamedQuery("ConversaMembros.getAll", ConversaMembros.class).getResultList();
    }

    @Override
    public List<ConversaMembros> getAll() throws Exception {
        return null;
    }

    @Override
    public ConversaMembros find(ConversaMembrosKey id) throws Exception {
        MapperConversaMembros m = new MapperConversaMembros();
        try {
            return m.read(id.getConversa(), id.getJogador());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void add(ConversaMembros entity) throws Exception {
        MapperConversaMembros mcp = new MapperConversaMembros();
        try {
            mcp.create(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(ConversaMembros entity) throws Exception {
        MapperConversaMembros mcp = new MapperConversaMembros();
        try {
            mcp.delete(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void save(ConversaMembros entity) throws Exception {
        MapperConversaMembros mcp = new MapperConversaMembros();
        try {
            mcp.update(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }



}
