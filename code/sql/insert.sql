BEGIN TRANSACTION;
INSERT INTO jogo (id_jogo, nome, url)
    VALUES ('0000000001', 'Battlefield 3', 'https://www.ea.com/pt-br/games/battlefield/battlefield-3'), ('0000000002', 'Call Of Duty: Modern Warfare 3', 'https://www.callofduty.com/mw3');
INSERT INTO regiao (nome)
    VALUES ('EuropeWest');
INSERT INTO jogador (id, username, email, estado, regiao)
    VALUES (9999,'system','geral@system.com','Ativo','EuropeWest'), (1, 'miguelalmeida', 'miguelao@gmail.com', 'Ativo', 'EuropeWest'), (2, 'ricardobernardino', 'rickyzao@gmail.com', 'Ativo', 'EuropeWest');
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

