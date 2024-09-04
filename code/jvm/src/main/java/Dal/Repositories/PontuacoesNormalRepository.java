package Dal.Repositories;

import Dal.DataScope;
import Dal.Mappers.MapperPontuacoesNormal;
import dalGenericInterfaces.IRepository;
import jakarta.persistence.EntityManager;
import model.PontuacoesKey;
import model.PontuacoesNormal;

import java.util.List;

public class PontuacoesNormalRepository implements IRepository<PontuacoesNormal,PontuacoesKey> {
    @Override
    public List<PontuacoesNormal> getAll() throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();
            List<PontuacoesNormal> l = em.createNamedQuery("PontuacoesNormal.getAll", PontuacoesNormal.class).getResultList();
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
    public PontuacoesNormal find(PontuacoesKey id) throws Exception {
        MapperPontuacoesNormal m = new MapperPontuacoesNormal();
        try {
            return m.read(id.getRef_jogo(),  id.getNum_partida(), id.getId_jogador());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void add(PontuacoesNormal entity) throws Exception {
        MapperPontuacoesNormal mcp = new MapperPontuacoesNormal();
        try {
            mcp.create(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(PontuacoesNormal entity) throws Exception {
        MapperPontuacoesNormal mcp = new MapperPontuacoesNormal();
        try {
            mcp.delete(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void save(PontuacoesNormal entity) throws Exception {
        MapperPontuacoesNormal mcp = new MapperPontuacoesNormal();
        try {
            mcp.update(entity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
