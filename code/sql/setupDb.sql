DROP VIEW IF EXISTS jogadorTotalInfo;

DROP TRIGGER IF EXISTS banir_jogadores ON jogadortotalinfo CASCADE;

DROP TRIGGER IF EXISTS atribuircrachas ON partida CASCADE;

-- Drop all tests
DROP PROCEDURE IF EXISTS insert_jogador_test();

DROP PROCEDURE IF EXISTS update_jogador_inativo_test();

DROP PROCEDURE IF EXISTS update_jogador_banido_test();

DROP PROCEDURE IF EXISTS associar_cracha_test();

DROP PROCEDURE IF EXISTS atribuirCrachas_test();

DROP PROCEDURE IF EXISTS test_banir_jogadores();

DROP PROCEDURE IF EXISTS iniciar_conversa_test();

DROP PROCEDURE IF EXISTS juntar_conversa_test();

DROP PROCEDURE IF EXISTS enviar_mensagem_test();

DROP PROCEDURE IF EXISTS total_pontos_jogador_test();

DROP PROCEDURE IF EXISTS total_jogos_jogador_test();

DROP PROCEDURE IF EXISTS pontos_jogo_por_jogador_test();

DROP PROCEDURE IF EXISTS jogadortotalinfo_test();

-- Drop all procedures
DROP PROCEDURE IF EXISTS insert_jogador;

DROP PROCEDURE IF EXISTS update_jogador_inativo;

DROP PROCEDURE IF EXISTS update_jogador_banido;

DROP PROCEDURE IF EXISTS associarCracha;

DROP PROCEDURE IF EXISTS insere_conversamembros;

DROP PROCEDURE IF EXISTS iniciarconversa;

DROP PROCEDURE IF EXISTS juntarconversa;

DROP PROCEDURE IF EXISTS send_join_message;

DROP PROCEDURE IF EXISTS enviarmensagem;

-- Drop all functions
DROP FUNCTION IF EXISTS totalPontosJogador(int);

DROP FUNCTION IF EXISTS totalJogosJogador(int);

DROP FUNCTION IF EXISTS totalJogosJogados(int);

DROP FUNCTION IF EXISTS PontosJogoPorJogador(varchar);

DROP FUNCTION IF EXISTS totalPontosJogadorForView(int);

DROP FUNCTION IF EXISTS banir_jogadores();

DROP FUNCTION IF EXISTS atribuirCrachas();

DROP FUNCTION IF EXISTS totalPartidasJogador(int);

-----------------TABLES-----------------
DROP TABLE IF EXISTS Estatisticas_Jogador;

DROP TABLE IF EXISTS Estatisticas_Jogo;

DROP TABLE IF EXISTS CrachaJogador;

DROP TABLE IF EXISTS Cracha;

DROP TABLE IF EXISTS Pontuacoes_Normal;

DROP TABLE IF EXISTS Pontuacoes_Multi_Jogador;

DROP TABLE IF EXISTS Partida_Normal;

DROP TABLE IF EXISTS Partida_Multi_Jogador;

DROP TABLE IF EXISTS Partida;

DROP TABLE IF EXISTS Compra;

DROP TABLE IF EXISTS Jogo;

DROP TABLE IF EXISTS Mensagem;

DROP TABLE IF EXISTS ConversaMembros;

DROP TABLE IF EXISTS Conversa;

DROP TABLE IF EXISTS Amizade;

DROP TABLE IF EXISTS Jogador;

DROP TABLE IF EXISTS Regiao;

DROP DOMAIN IF EXISTS email CASCADE;

DROP DOMAIN IF EXISTS alfanumeric_10 CASCADE;

DROP DOMAIN IF EXISTS url CASCADE;


BEGIN TRANSACTION;
CREATE DOMAIN email AS varchar(32) CHECK (value ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$');
CREATE DOMAIN alfanumeric_10 AS varchar(10) CHECK (value ~* '^[A-Za-z0-9]{10}$');
CREATE DOMAIN url AS text CHECK (value ~* '^(http?|https?|ftp)://[^\s/$.?#].[^\s]*$');
--Regiao(id, regiao)
CREATE TABLE IF NOT EXISTS Regiao(
    nome varchar(32) PRIMARY KEY
);
--Jogador(id, email, username, estado, região)
CREATE TABLE IF NOT EXISTS Jogador(
    id serial PRIMARY KEY,
    username varchar(32) UNIQUE NOT NULL,
    email email UNIQUE NOT NULL,
    estado varchar(32) NOT NULL,
    regiao varchar(32),
    FOREIGN KEY (regiao) REFERENCES Regiao(nome) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT nomeEstadoJogador CHECK (estado IN ('Ativo', 'Inativo', 'Banido'))
);
--Amizade(id_jogador1, id_jogador2)
CREATE TABLE IF NOT EXISTS Amizade(
    id_jogador1 int NOT NULL,
    id_jogador2 int NOT NULL,
    FOREIGN KEY (id_jogador1) REFERENCES Jogador(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_jogador2) REFERENCES Jogador(id) ON DELETE CASCADE ON UPDATE CASCADE
);
--Conversa(id, nome, id_jogador)
CREATE TABLE IF NOT EXISTS Conversa(
    id serial PRIMARY KEY,
    nome varchar(32) NOT NULL
);
--ConversaMembros(id_conversa,id_jogador)
CREATE TABLE IF NOT EXISTS ConversaMembros(
    id_conversa int NOT NULL,
    FOREIGN KEY (id_conversa) REFERENCES Conversa(id) ON DELETE CASCADE ON UPDATE CASCADE,
    id_jogador int NOT NULL,
    FOREIGN KEY (id_jogador) REFERENCES Jogador(id) ON DELETE CASCADE ON UPDATE CASCADE
);
--Mensagem(id_conversa, data, hora, texto, id_jogador)
CREATE TABLE IF NOT EXISTS Mensagem(
    num serial PRIMARY KEY,
    id_conversa int NOT NULL,
    FOREIGN KEY (id_conversa) REFERENCES Conversa(id) ON DELETE CASCADE ON UPDATE CASCADE,
    data date NOT NULL,
    hora time NOT NULL,
    texto varchar(150),
    id_jogador int NOT NULL,
    FOREIGN KEY (id_jogador) REFERENCES Jogador(id) ON DELETE CASCADE ON UPDATE CASCADE
);
--Jogo(ref, nome, url)
CREATE TABLE IF NOT EXISTS Jogo(
    id_jogo alfanumeric_10 PRIMARY KEY,
    nome varchar(64) UNIQUE NOT NULL,
    url url NOT NULL
);
--Compra(id_jogo, id_jogador,data,preço)
CREATE TABLE IF NOT EXISTS Compra(
    id_jogo varchar(10) NOT NULL,
    FOREIGN KEY (id_jogo) REFERENCES Jogo(id_jogo) ON DELETE CASCADE ON UPDATE CASCADE,
    id_jogador int NOT NULL,
    FOREIGN KEY (id_jogador) REFERENCES Jogador(id) ON DELETE CASCADE ON UPDATE CASCADE,
    data date NOT NULL,
    preco int NOT NULL CHECK (preco > 0)
);
--Partida(num, ref_jogo, data_inicio, data_fim, região)
--Chave composta entre num e ref_jogo, nao precisa de ser serial.
CREATE TABLE IF NOT EXISTS Partida(
    num int NOT NULL,
    ref_jogo varchar(10) NOT NULL,
    PRIMARY KEY (num, ref_jogo),
    data_inicio date NOT NULL,
    data_fim date,
    tipo varchar(20) NOT NULL,
    CONSTRAINT dataIniLowerThanFim CHECK (data_fim >= data_inicio),
    regiao varchar(32) NOT NULL,
    FOREIGN KEY (regiao) REFERENCES Regiao(nome) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (ref_jogo) REFERENCES Jogo(id_jogo) ON DELETE CASCADE ON UPDATE CASCADE
);
--Partida_Normal(num, ref_jogo, data_inicio, data_fim, grau_de_dificuldade, estado, região)
--Recebe a chave composta num e ref_jogo.
CREATE TABLE IF NOT EXISTS Partida_Normal(
    num int NOT NULL,
    ref_jogo varchar(10) NOT NULL,
    FOREIGN KEY (num, ref_jogo) REFERENCES Partida(num, ref_jogo) ON DELETE CASCADE ON UPDATE CASCADE,
    grau_de_dificuldade varchar(32) NOT NULL
);
--Partida_Multi_Jogador(num, ref_jogo, data_inicio, data_fim, grau_de_dificuldade, estado, região)
--Recebe a chave composta num e ref_jogo.
CREATE TABLE IF NOT EXISTS Partida_Multi_Jogador(
    num int NOT NULL,
    ref_jogo varchar(10) NOT NULL,
    FOREIGN KEY (num, ref_jogo) REFERENCES Partida(num, ref_jogo) ON DELETE CASCADE ON UPDATE CASCADE,
    estado varchar(32),
    CONSTRAINT nomeEstadoPartida CHECK (estado IN ('Por Iniciar', 'A Aguardar Jogadores', 'Em Curso', 'Terminada'))
);
--Pontuações_Normal(num_partida, id_jogador, pontos)
--Chave composta entre num e ref_jogo, nao precisa de ser serial.
CREATE TABLE IF NOT EXISTS Pontuacoes_Normal(
    num_partida int NOT NULL,
    ref_jogo varchar(10) NOT NULL,
    FOREIGN KEY (num_partida, ref_jogo) REFERENCES Partida(num, ref_jogo) ON DELETE CASCADE ON UPDATE CASCADE,
    id_jogador int NOT NULL,
    FOREIGN KEY (id_jogador) REFERENCES Jogador(id) ON DELETE CASCADE ON UPDATE CASCADE,
    pontos int NOT NULL CHECK (pontos >= 0)
);
--Pontuações_Multi_Jogador(num_partida, id_jogador, pontos)
--Chave composta entre num e ref_jogo, nao precisa de ser serial.
CREATE TABLE IF NOT EXISTS Pontuacoes_Multi_Jogador(
    num_partida int NOT NULL,
    ref_jogo varchar(10) NOT NULL,
    FOREIGN KEY (num_partida, ref_jogo) REFERENCES Partida(num, ref_jogo) ON DELETE CASCADE ON UPDATE CASCADE,
    id_jogador int NOT NULL,
    FOREIGN KEY (id_jogador) REFERENCES Jogador(id) ON DELETE CASCADE ON UPDATE CASCADE,
    pontos int NOT NULL CHECK (pontos >= 0)
);
--Crachá(nome, ref_jogo, limite_de_pontos, imagem, id_jogador)
CREATE TABLE IF NOT EXISTS Cracha(
    nome varchar(64) PRIMARY KEY,
    ref_jogo varchar(10) NOT NULL,
    FOREIGN KEY (ref_jogo) REFERENCES Jogo(id_jogo) ON DELETE CASCADE ON UPDATE CASCADE,
    limite_de_pontos int NOT NULL CHECK (limite_de_pontos > 0),
    imagem url NOT NULL
);
--CrachaJogador(nome,id_jogador)
CREATE TABLE IF NOT EXISTS CrachaJogador(
    nome varchar(64) NOT NULL,
    FOREIGN KEY (nome) REFERENCES Cracha(nome) ON DELETE CASCADE ON UPDATE CASCADE,
    id_jogador int NOT NULL,
    FOREIGN KEY (id_jogador) REFERENCES Jogador(id) ON DELETE CASCADE ON UPDATE CASCADE
);
--Estatisticas_Jogador(id_jogador, numero_de_jogos, numero_de_partidas, numero_total_de_pontos)
CREATE TABLE IF NOT EXISTS Estatisticas_Jogador(
    id_jogador int NOT NULL,
    FOREIGN KEY (id_jogador) REFERENCES Jogador(id) ON DELETE CASCADE ON UPDATE CASCADE,
    numero_de_jogos int NOT NULL CHECK (numero_de_jogos >= 0),
    numero_de_partidas int NOT NULL CHECK (numero_de_partidas >= 0),
    numero_total_de_pontos int NOT NULL CHECK (numero_total_de_pontos >= 0)
);
--Estatisticas_Jogo(ref, numero_de_partidas, número_de_jogadores, total_de_pontos)
CREATE TABLE IF NOT EXISTS Estatisticas_Jogo(
    ref varchar(10) NOT NULL,
    FOREIGN KEY (ref) REFERENCES Jogo(id_jogo) ON DELETE CASCADE ON UPDATE CASCADE,
    numero_de_partidas int NOT NULL CHECK (numero_de_partidas >= 0),
    numero_de_jogadores int NOT NULL CHECK (numero_de_jogadores >= 0),
    total_de_pontos int NOT NULL CHECK (total_de_pontos >= 0)
);
COMMIT TRANSACTION;

BEGIN TRANSACTION;
INSERT INTO jogo (id_jogo, nome, url)
    VALUES ('0000000001', 'Battlefield 3', 'https://www.ea.com/pt-br/games/battlefield/battlefield-3'), ('0000000002', 'Call Of Duty: Modern Warfare 3', 'https://www.callofduty.com/mw3');
INSERT INTO regiao (nome)
    VALUES ('EuropeWest');
INSERT INTO jogador (id, username, email, estado, regiao)
    VALUES (0,'system','geral@system.com','Ativo',null), (1, 'miguelalmeida', 'miguelao@gmail.com', 'Ativo', 'EuropeWest'), (2, 'ricardobernardino', 'rickyzao@gmail.com', 'Ativo', 'EuropeWest');
INSERT INTO amizade (id_jogador1, id_jogador2)
    VALUES (1, 2);
INSERT INTO conversa (id, nome)
    VALUES (1, 'o nosso chat');
INSERT INTO conversamembros (id_conversa, id_jogador)
    VALUES (1, 1), (1, 2);
INSERT INTO mensagem (num,id_conversa, data, hora, texto, id_jogador)
    VALUES (1,1, '2022-03-01', '14:30:00', 'Olá, como estás?', 1), (2,1, '2022-03-02', '15:00:00', 'Tudo bem, e contigo?', 2);
INSERT INTO compra (id_jogo, id_jogador, data, preco)
    VALUES ('0000000001', 1, '2022-03-01', 59), ('0000000002', 1, '2022-03-02', 49), ('0000000001', 2, '2022-03-01', 59);
INSERT INTO partida (num, ref_jogo, data_inicio, data_fim,tipo, regiao)
    VALUES (230, '0000000001', '2022-03-02', '2022-03-02','Normal' ,'EuropeWest'), (229, '0000000001', '2022-03-02', '2022-03-02', 'Multi-jogador','EuropeWest'), (231,'0000000001', '2022-03-02', NULL,'Multi-jogador', 'EuropeWest');
INSERT INTO partida_normal (num,ref_jogo,grau_de_dificuldade)
    VALUES (230,'0000000001', 2);
INSERT INTO partida_multi_jogador  (num,ref_jogo, estado)
    VALUES (229, '0000000001','Terminada'), (231,'0000000001','A Aguardar Jogadores');
INSERT INTO pontuacoes_normal (num_partida,ref_jogo, id_jogador, pontos)
    VALUES (230,'0000000001', 1, 10);
INSERT INTO pontuacoes_multi_jogador (num_partida,ref_jogo, id_jogador, pontos)
    VALUES (229,'0000000001', 2, 42600), (229,'0000000001', 1, 1100), (231,'0000000001', 1, 0);
INSERT INTO cracha (nome, ref_jogo, limite_de_pontos, imagem)
    VALUES ('50 Kills', '0000000001', 50, 'https://www.imaginaryURL50.com'), ('100 Kills', '0000000001', 100, 'https://www.imaginaryURL100.com');
INSERT INTO crachajogador (nome, id_jogador)
    VALUES ('50 Kills', 1), ('100 Kills', 1), ('50 Kills', 2);
INSERT INTO estatisticas_jogador (id_jogador, numero_de_jogos, numero_de_partidas, numero_total_de_pontos)
    VALUES (2, 1, 1, 42600), (1, 2, 2, 1100);
INSERT INTO estatisticas_jogo (ref, numero_de_partidas, numero_de_jogadores, total_de_pontos)
    VALUES ('0000000001', 2, 2, 42600), ('0000000002', 0, 1, 0);
COMMIT TRANSACTION;

