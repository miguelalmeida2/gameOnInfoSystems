package presentation;

import businessLogic.BLService;

import java.util.Scanner;

public class App {
    protected interface ITest {
        void test();
    }


    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        BLService srv = new BLService();
        ITest tests[] = new ITest[]{() -> {
            try {
                Scanner scn = new Scanner(System.in);

                System.out.println("Insire o nome");
                String username = scn.next();

                System.out.println("Insire o email");
                String email = scn.next();

                System.out.println("Insire a regiao");
                String regiao = scn.next();
                srv.inserirJogador(username, email, regiao);
                scn.close();
            } catch (Exception e) {
            }
        }, () -> {
            try {
                Scanner scn = new Scanner(System.in);

                System.out.println("Insire o id do jogador que quer colocar no estado inativo");
                int userId = scn.nextInt();

                srv.inactivarJogador(userId);
                scn.close();
            } catch (Exception e) {
            }
        }, () -> {
            try {
                Scanner scn = new Scanner(System.in);

                System.out.println("Insire o id do jogador que quer banir");
                int userId = scn.nextInt();

                srv.banirJogador(userId);
                scn.close();
            } catch (Exception e) {
            }
        }, () -> {
            try {
                Scanner scn = new Scanner(System.in);

                System.out.println("Insire o id do jogador que vai criar a conversa");
                int userId = scn.nextInt();

                System.out.println("Insire o nome que quer dar à conversa");
                String conversaNome = scn.next();

                srv.criarConversa(userId, conversaNome);
                scn.close();
            } catch (Exception e) {
            }
        }, () -> {
            try {

                Scanner scn = new Scanner(System.in);

                System.out.println("Insire o id do jogador");
                int userId = scn.nextInt();

                System.out.println("Insire o id da conversa");
                int conversaId = scn.nextInt();

                srv.juntarConversa(conversaId, userId);
                scn.close();
            } catch (Exception e) {
            }
        }, () -> {
            try {

                Scanner scn = new Scanner(System.in);

                System.out.println("Insire o id do jogador");
                int userId = scn.nextInt();

                System.out.println("Insire o id da conversa");
                int conversaId = scn.nextInt();

                System.out.println("Insira a mensagem que quer enviar");
                String msg = scn.next();

                srv.enviarMensagem(userId, conversaId, msg);
            } catch (Exception e) {
            }
        }, () -> {
            try {

                Scanner scn = new Scanner(System.in);

                System.out.println("Insire o id do jogador");
                int userId = scn.nextInt();

                srv.totalPontosJogador(userId);
            } catch (Exception e) {
            }
        }, () -> {
            try {

                Scanner scn = new Scanner(System.in);

                System.out.println("Insire o id do jogador");
                int userId = scn.nextInt();

                srv.totalJogosJogador(userId);
            } catch (Exception e) {
            }
        }, () -> {
            try {

                Scanner scn = new Scanner(System.in);

                System.out.println("Insire a referência do jogo:");
                String ref_jogo = scn.nextLine();

                srv.PontosJogoPorJogador(ref_jogo);
            } catch (Exception e) {
            }
        }, () -> {
            try {
                Scanner scn = new Scanner(System.in);

                System.out.println("Insira o id do jogador");
                int userId = scn.nextInt();

                scn.nextLine(); // Consume the remaining newline character

                System.out.println("Insira o número da partida");
                int num_partida = scn.nextInt();

                scn.nextLine(); // Consume the remaining newline character

                System.out.println("Insira o nome do crachá");
                String nome_cracha = scn.nextLine();

                srv.associarCrachaSeparated(userId, num_partida, nome_cracha);

            } catch (Exception e) {
            }
        }, () -> {
            try {
                srv.viewJogadorTotalInfo();
            } catch (Exception e) {
            }
        }, () -> {
            try {
                srv.getAllPlayers();
            } catch (Exception e) {
            }
        }, () -> {
            try {
                srv.getAllJogo();
            } catch (Exception e) {
            }
        }, () -> {
            try {
                srv.getAllCompra();
            } catch (Exception e) {
            }
        }, () -> {
            try {
                srv.getAllPartida();
            } catch (Exception e) {
            }
        }, () -> {
            try {
                srv.getAllPartidaMultiJogador();
            } catch (Exception e) {
            }
        }, () -> {
            try {
                srv.getAllPartidaNormal();
            } catch (Exception e) {
            }
        },() -> {
            try {
                srv.getAllPontuacoesNormal();
            } catch (Exception e) {
            }
        }, () -> {
            try {
                srv.getAllPontuacosMultiJogador();
            } catch (Exception e) {
            }
        }, () -> {
            try {
                srv.getAllCracha();
            } catch (Exception e) {
            }
        }, () -> {
            try {
                srv.getAllCrachaJogador();
            } catch (Exception e) {
            }
        }, () -> {
            try {
                Scanner scn = new Scanner(System.in);

                System.out.println("Insire o id do jogador");
                int userId = scn.nextInt();

                System.out.println("Insire o num da partida");
                int num_partida = scn.nextInt();

                System.out.println("Insira o nome do cracha");
                String nome_cracha = scn.next();

                srv.associarCrachaCRUD(userId, num_partida, nome_cracha);
            } catch (Exception e) {
            }
        }, () -> {
            try {
                Scanner scn = new Scanner(System.in);

                System.out.println("Insira o nome do cracha");
                String nome_cracha = scn.next();

                System.out.println("Insire o ref do Jogo");
                String ref_jogo = scn.next();

                srv.optLockCracha(nome_cracha, ref_jogo);
            } catch (Exception e) {
            }
        }, () -> {
            try {
                srv.testOptLock("100 Kills", "0000000001");
            } catch (Exception e) {
            }
        }, () -> {
            try {
                Scanner scn = new Scanner(System.in);

                System.out.println("Insira o nome do cracha");
                String nome_cracha = scn.next();

                System.out.println("Insire o ref do Jogo");
                String ref_jogo = scn.next();

                srv.pessLockCracha(nome_cracha, ref_jogo);
            } catch (Exception e) {
            }
        }};

        Scanner imp = new Scanner(System.in);
        int option;
        while (true) {
            System.out.println("Commands");
            System.out.println("1 -> Inserir Jogador");
            System.out.println("2 -> Desativar Jogador");
            System.out.println("3 -> Banir Jogador");
            System.out.println("4 -> Criar Conversa");
            System.out.println("5 -> Juntar Conversa");
            System.out.println("6 -> Enviar Mensagem");
            System.out.println("7 -> Obter pontos de jogador");
            System.out.println("8 -> Obter diferentes jogos de jogador");
            System.out.println("9 -> Obter a lista de pontuaçao de jogadores num jogo");
            System.out.println("10 -> Associar crachá");
            System.out.println("11 -> Ver vista");
            System.out.println("12 -> getAllPlayers");
            System.out.println("13 -> getAllJogo");
            System.out.println("14 -> getAllCompra");
            System.out.println("15 -> getAllPartida");
            System.out.println("16 -> getAllPartidaMultiJogador");
            System.out.println("17 -> getAllPartidaNormal");
            System.out.println("18 -> getAllPontuacoesNormal");
            System.out.println("19 -> getAllPontuacoesMultiJogador");
            System.out.println("20 -> getAllCracha");
            System.out.println("21 -> getAllCrachaJogador");
            System.out.println("22 -> Associar Cracha CRUD");
            System.out.println("23 -> Opt Locking Cracha");
            System.out.println("24 -> Test Opt Locking");
            System.out.println("25 -> Pess Locking Cracha");
            System.out.println("99 -> Exit Application");
            System.out.print("Command? -> ");
            option = imp.nextInt();
            if (option == 99) {
                System.out.println("Exiting application...");
                break;
            }
            if (option >= 1 && option <= tests.length) tests[--option].test();
        }
    }
}
