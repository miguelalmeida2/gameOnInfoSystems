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

