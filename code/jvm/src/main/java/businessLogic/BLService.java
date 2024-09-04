
package businessLogic;

import Dal.DataScope;
import Dal.Mappers.MapperCracha;
import Dal.Mappers.MapperCrachaJogador;
import Dal.Mappers.MapperJogador;
import Dal.Repositories.*;
import jakarta.persistence.*;
import model.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;


public class BLService {

    public void inserirJogador(String username, String email, String regiao) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Query q = em.createNativeQuery("call insert_jogador(?1,?2,?3,?4)");
            q.setParameter(1, new Random().nextInt(9999));
            q.setParameter(2, username);
            q.setParameter(3, email);
            q.setParameter(4, regiao);

            // Execute the stored procedure
            q.executeUpdate();
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    public void inactivarJogador(int jogadorId) throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Query q = em.createNativeQuery("call update_jogador_inativo(?1)");
            q.setParameter(1, jogadorId);

            // Execute the stored procedure
            q.executeUpdate();
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }

    }

    public void banirJogador(int jogadorId) throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Query q = em.createNativeQuery("call update_jogador_banido(?1)");
            q.setParameter(1, jogadorId);

            // Execute the stored procedure
            q.executeUpdate();
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }

    }

    public void criarConversa(int jogadorId, String nomeConversa) throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            StoredProcedureQuery q = em.createStoredProcedureQuery("finiciarConversa");
            q.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
            q.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
            q.setParameter(1, jogadorId);
            q.setParameter(2, nomeConversa);
            q.execute();
            em.getTransaction().commit();
            Object[] result = (Object[]) q.getSingleResult();
            Integer nova_conversa_id = (Integer) result[0];
            System.out.println(nova_conversa_id);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }

    }

    public void juntarConversa(int conversaId, int jogadorId) throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Query q = em.createNativeQuery("call juntarConversa(?1,?2)");
            q.setParameter(1, jogadorId);
            q.setParameter(2, conversaId);

            // Execute the stored procedure
            q.executeUpdate();
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }

    }

    public void enviarMensagem(int jogadorId, int conversaId, String msg) throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Query q = em.createNativeQuery("call enviarMensagem(?1, ?2, ?3)");
            q.setParameter(1, jogadorId);
            q.setParameter(2, conversaId);
            q.setParameter(3, msg);

            // Execute the stored procedure
            q.executeUpdate();
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }

    }

    public void associarCracha(int userId, int num_partida, String nome_cracha) throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Query q = em.createNativeQuery("call associarCracha(?1, ?2, ?3)");
            q.setParameter(1, userId);
            q.setParameter(2, num_partida);
            q.setParameter(3, nome_cracha);

            // Execute the stored procedure
            q.executeUpdate();
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }

    }

    /**
     * 1 c)
     * @param userId
     * @param num_partida
     * @param nome_cracha
     */
    public void associarCrachaSeparated(int userId, int num_partida, String nome_cracha) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            if (!doesGameExist(em, num_partida)) {
                throw new Exception("Partida nao existe");
            }

            Object[] gameDetails = getGameDetails(em, num_partida);
            String ref = (String) gameDetails[0];
            String tipo_partida = (String) gameDetails[1];

            if (!doesPlayerHaveGame(em, userId, ref)) {
                throw new Exception("Jogador nao tem o jogo");
            }

            if (!isBadgeInGame(em, nome_cracha, ref)) {
                throw new Exception("Cracha nao esta associado ao dado jogo");
            }

            int pontos_requeridos = getBadgePointsLimit(em, nome_cracha, ref);
            int pontos_obtidos = getPlayerPoints(em, userId, ref, num_partida, tipo_partida);

            if (pontos_obtidos < pontos_requeridos) {
                throw new Exception("Jogador nao atingiu o numero de pontos necessarios para obter cracha");
            } else if (doesPlayerAlreadyHaveBadge(em, userId, nome_cracha)) {
                throw new Exception("Jogador já tem o cracha");
            } else {
                assignBadgeToPlayer(em, userId, nome_cracha);
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
    private boolean doesGameExist(EntityManager em, int numero_partida) {
        Query query = em.createNativeQuery("SELECT * FROM doesGameExist(?1)");
        query.setParameter(1, numero_partida);
        return (boolean) query.getSingleResult();
    }

    private Object[] getGameDetails(EntityManager em, int numero_partida) {
        Query query = em.createNativeQuery("SELECT * FROM getGameDetails(?1)");
        query.setParameter(1, numero_partida);
        List<Object[]> results = query.getResultList();
        if (!results.isEmpty()) {
            return results.get(0);
        }
        return null;
    }

    private boolean doesPlayerHaveGame(EntityManager em, int userId, String ref_jogo) {
        Query query = em.createNativeQuery("SELECT * FROM doesPlayerHaveGame(?1, ?2)");
        query.setParameter(1, userId);
        query.setParameter(2, ref_jogo);
        return (boolean) query.getSingleResult();
    }

    private boolean isBadgeInGame(EntityManager em, String nome_cracha, String ref) {
        Query query = em.createNativeQuery("SELECT * FROM isBadgeInGame(?1, ?2)");
        query.setParameter(1, nome_cracha);
        query.setParameter(2, ref);
        return (boolean) query.getSingleResult();
    }

    private int getBadgePointsLimit(EntityManager em, String nome_cracha, String ref) {
        Query query = em.createNativeQuery("SELECT * FROM getBadgePointsLimit(?1, ?2)");
        query.setParameter(1, nome_cracha);
        query.setParameter(2, ref);
        return (int) query.getSingleResult();
    }

    private int getPlayerPoints(EntityManager em, int userId, String ref, int numero_partida, String tipo_partida) {
        Query query = em.createNativeQuery("SELECT * FROM getPlayerPoints(?1, ?2, ?3, ?4)");
        query.setParameter(1, userId);
        query.setParameter(2, ref);
        query.setParameter(3, numero_partida);
        query.setParameter(4, tipo_partida);
        return (int) query.getSingleResult();
    }

    private boolean doesPlayerAlreadyHaveBadge(EntityManager em, int userId, String nome_cracha) {
        Query query = em.createNativeQuery("SELECT * FROM doesPlayerAlreadyHaveBadge(?1, ?2)");
        query.setParameter(1, userId);
        query.setParameter(2, nome_cracha);
        return (boolean) query.getSingleResult();
    }

    private void assignBadgeToPlayer(EntityManager em, int userId, String nome_cracha) {
        Query query = em.createNativeQuery("SELECT assignBadgeToPlayer(?1, ?2)");
        query.setParameter(1, userId);
        query.setParameter(2, nome_cracha);
        query.getSingleResult(); // Discard the result
    }

    /**
     * 1 b)
     * Realizar a funcionalidade 2h, descrita na fase 1 deste trabalho, sem usar qualquer
     * procedimento armazenado nem qualquer função pgSql;
     *
     * @throws Exception
     */
    public void associarCrachaCRUD(int userId, int num_partida, String nome_cracha) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            MapperCrachaJogador crachaJogadorMap = new MapperCrachaJogador();

            MapperJogador mapJog = new MapperJogador();
            MapperCracha mapCra = new MapperCracha();

            Jogador jogador;
            Cracha cracha;
            try {
                jogador = mapJog.read(userId);
                cracha = mapCra.read(nome_cracha);
            } catch (Exception e) {
                // Uma das chaves nao existe ou ocorreu um erro
                throw new IllegalArgumentException("Uma das chaves nao existe");
            }
            CrachaJogador newCrachaJogador = new CrachaJogador(cracha, jogador);
            crachaJogadorMap.create(newCrachaJogador);

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    public void associarCrachaCRUDWithErrorHandling(int userId, int num_partida, String nome_cracha) throws Exception {
        try (DataScope ds = new DataScope()) {
            EntityManager em = ds.getEntityManager();

            PartidaMultiJogadorRepository partidaRepo = new PartidaMultiJogadorRepository();
            CompraRepository compraRepo = new CompraRepository();
            CrachaRepository crachaRepo = new CrachaRepository();
            CrachaJogadorRepository crachaJogadorRepo = new CrachaJogadorRepository();
            PontuacoesMultiJogadorRepository pontuacoesRepo = new PontuacoesMultiJogadorRepository();
            JogadorRepository jogadorRepo = new JogadorRepository();

            Cracha cracha = crachaRepo.find(nome_cracha);
            String ref_jogo = cracha.getJogo().getId_jogo();
            PartidaMultiJogador partida = partidaRepo.find(new PartidaKey(num_partida,ref_jogo));
            if (partida == null) {
                throw new IllegalArgumentException("Partida nao existe para o jogo do Cracha");
            }

            Compra compraEfetuada = compraRepo.find(new CompraKey(ref_jogo, userId));
            if (compraEfetuada == null) {
                throw new IllegalArgumentException("Jogador nao tem o jogo");
            }

            int pontos_requeridos = cracha.getLimiteDePontos();
            List<PontuacoesMultiJogador> pontuacoes = pontuacoesRepo.getAll();
            Optional<PontuacoesMultiJogador> entrada = pontuacoes.stream().filter(pontuacao -> pontuacao.getId_jogador().getId() == userId && pontuacao.getPartida().getId().getNum() == num_partida).findFirst();
            int pontos_obtidos;
            if (entrada.isPresent()) {
                PontuacoesMultiJogador pontuacao = entrada.get();
                pontos_obtidos = pontuacao.getPontos();
            } else {
                throw new IllegalArgumentException("Cracha nao esta associado ao dado jogo");
            }

            if (pontos_obtidos >= pontos_requeridos) {
                CrachaJogador jogadorTemCracha = crachaJogadorRepo.find(new CrachaJogadorKey(cracha.getNome(), userId));
                if (jogadorTemCracha == null) {
                    CrachaJogador newCrachaJogador = new CrachaJogador(cracha, jogadorRepo.find(userId));
                    crachaJogadorRepo.save(newCrachaJogador);
                } else {
                    throw new IllegalArgumentException("Jogador já tem o cracha");
                }
            } else {
                throw new IllegalArgumentException("Jogador nao atingiu o numero de pontos necessarios para obter cracha");
            }

            ds.validateWork();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }


    public void totalPontosJogador(int jogadorId) throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            StoredProcedureQuery q = em.createStoredProcedureQuery("totalPontosJogador");
            q.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
            q.setParameter(1, jogadorId);
            q.execute();
            em.getTransaction().commit();
            Object[] result = (Object[]) q.getSingleResult();
            Integer pontos = (Integer) result[0];
            System.out.println("Pontos totais do jogador " + jogadorId + ":" + pontos);


        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }

    }

    public void PontosJogoPorJogador(String ref_jogo) throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            StoredProcedureQuery q = em.createStoredProcedureQuery("PontosJogoPorJogador");
            q.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
            q.setParameter(1, ref_jogo);
            q.execute();
            em.getTransaction().commit();

            List<Object[]> results = q.getResultList();
            for (Object[] result : results) {
                Integer id = (Integer) result[0];
                Integer totalPontos = (Integer) result[1];
                System.out.println("Jogador ID: " + id + ", Total de Pontos: " + totalPontos);
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }

    }

    public void viewJogadorTotalInfo() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Query q = em.createNativeQuery("select * from jogadortotalinfo");

            // Execute the stored procedure
            em.getTransaction().commit();

            List<Object[]> results = q.getResultList();
            for (Object[] result : results) {
                Integer id = (Integer) result[0];
                String estado = (String) result[1];
                String email = (String) result[2];
                String username = (String) result[3];
                Integer totaljogos = (Integer) result[4];
                Integer totalpartidas = (Integer) result[5];
                Integer totalpontos = (Integer) result[6];

                System.out.print("Id: " + id + "\t\t");
                System.out.print("Estado: " + estado + "\t\t");
                System.out.print("Email: " + email + "\t\t");
                System.out.print("Username: " + username + "\t\t");
                System.out.print("Total Jogos: " + totaljogos + "\t\t");
                System.out.print("Total Partidas: " + totalpartidas + "\t\t");
                System.out.print("Total Pontos: " + totalpontos + "\n");
                System.out.println("---------------------\n");

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }

    }

    public void totalJogosJogador(int userId) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            StoredProcedureQuery q = em.createStoredProcedureQuery("totalPontosJogador");
            q.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
            q.setParameter(1, userId);
            q.execute();
            em.getTransaction().commit();
            Object[] result = (Object[]) q.getSingleResult();
            Integer jogos = (Integer) result[0];
            System.out.println("Jogos totais do jogador " + userId + ":" + jogos);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    public void getAllPlayers() throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            List<Jogador> playerRepo = new JogadorRepository().getAll();

            em.getTransaction().commit();
            for(int i = 0; i < playerRepo.size(); i++){
                System.out.println(playerRepo.get(i));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }
    public void getAllJogo() throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            List<Jogo> jogoRepo = new JogoRepository().getAll();

            em.getTransaction().commit();
            for(int i = 0; i < jogoRepo.size(); i++){
                System.out.println(jogoRepo.get(i));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    public void getAllCompra() throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            List<Compra> compraRepo = new CompraRepository().getAll();

            em.getTransaction().commit();
            for(int i = 0; i < compraRepo.size(); i++){
                System.out.println(compraRepo.get(i));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    public void getAllPartida() throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            List<Partida> partidaRepo = new PartidaRepository().getAll();

            em.getTransaction().commit();
            for(int i = 0; i < partidaRepo.size(); i++){
                System.out.println(partidaRepo.get(i));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    public void getAllPartidaMultiJogador() throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            List<PartidaMultiJogador> partidaMultiJogRepo = new PartidaMultiJogadorRepository().getAll();

            em.getTransaction().commit();
            for(int i = 0; i < partidaMultiJogRepo.size(); i++){
                System.out.println(partidaMultiJogRepo.get(i));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    public void getAllPartidaNormal() throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            List<PartidaNormal> partidaRepo = new PartidaNormalRepository().getAll();

            em.getTransaction().commit();
            for(int i = 0; i < partidaRepo.size(); i++){
                System.out.println(partidaRepo.get(i));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    public void getAllPontuacosMultiJogador() throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            List<PontuacoesMultiJogador> pontRepo = new PontuacoesMultiJogadorRepository().getAll();

            em.getTransaction().commit();
            for(int i = 0; i < pontRepo.size(); i++){
                System.out.println(pontRepo.get(i));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    public void getAllPontuacoesNormal() throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            List<PontuacoesNormal> pontRepo = new PontuacoesNormalRepository().getAll();

            em.getTransaction().commit();
            for(int i = 0; i < pontRepo.size(); i++){
                System.out.println(pontRepo.get(i));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    public void getAllCracha() throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            List<Cracha> crachaRepo = new CrachaRepository().getAll();

            em.getTransaction().commit();
            for(int i = 0; i < crachaRepo.size(); i++){
                System.out.println(crachaRepo.get(i));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    public void getAllCrachaJogador() throws Exception {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            List<CrachaJogador> craJogRepo = new CrachaJogadorRepository().getAll();

            em.getTransaction().commit();
            for(int i = 0; i < craJogRepo.size(); i++){
                System.out.println(craJogRepo.get(i));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }


    public void optLockCracha(String nome_cracha, String ref_jogo) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            MapperCracha crachaMap = new MapperCracha();
            Cracha crachaSut = crachaMap.read(nome_cracha);
            Cracha cracha = em.find(Cracha.class, crachaSut);

            int observedValue = cracha.getLimiteDePontos();
            int newValue = (int) (observedValue + (0.20 * observedValue));
            if (cracha.getLimiteDePontos() == observedValue) {
                cracha.setLimiteDePontos(newValue);
                em.getTransaction().commit();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    public void pessLockCracha(String nome_cracha, String ref_jogo) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            CrachaRepository crachaRepo = new CrachaRepository();
            Cracha cracha = em.find(Cracha.class, nome_cracha, LockModeType.PESSIMISTIC_READ);

            int observedValue = cracha.getLimiteDePontos();
            int newValue = (int) (observedValue + (0.20 * observedValue));
            if (cracha.getLimiteDePontos() == observedValue) {
                cracha.setLimiteDePontos(newValue);
                em.getTransaction().commit();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();

        }

    }

    public void testOptLock(String nome_cracha, String ref_jogo) throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoBase");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Observar valor
            MapperCracha crachaMap = new MapperCracha();
            Cracha crachaSut = crachaMap.read(nome_cracha);
            int oldPontos = crachaSut.getLimiteDePontos();

            // Incrementar valor com função optimistic
            optLockCracha(nome_cracha, ref_jogo);

            // Observar se o valor foi alterado consoante o esperado
            Cracha sameCracha = crachaMap.read(nome_cracha);
            int newPontos = sameCracha.getLimiteDePontos();

            if (newPontos > oldPontos) {
                System.out.println("O Teste PASSOU, os pontos foram aumentados" +
                        "Com lock optimistic");
            } else {
                System.out.println("O Teste FALHOU, os pontos NÃO foram aumentados" +
                        "Com lock optimistic");
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}


