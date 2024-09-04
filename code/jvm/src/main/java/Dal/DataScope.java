package Dal;

import Dal.Mappers.MapperCompra;
import Dal.Mappers.MapperCrachaJogador;
import Dal.Repositories.CompraRepository;
import model.Compra;
import model.CrachaJogador;

import java.util.List;

public class DataScope extends AbstractDataScope implements AutoCloseable {

    public DataScope() throws Exception {
        super();
    }

    public List<Compra> getAllCompras() throws Exception {
        return new CompraRepository().getAll();
    }

    public Compra findCompra( Integer jogador, String jogo) throws Exception {
        return new MapperCompra().read(jogador, jogo);
    }

    public CrachaJogador findCrachaJogador(String cracha, int jogador) throws Exception {
        return new MapperCrachaJogador().read(cracha, jogador);
    }


}
