---------------- Ponto D -----------------
CREATE OR REPLACE PROCEDURE insert_jogador(in jogador_id int, IN newUsername varchar, IN newEmail varchar, IN newRegion varchar)
LANGUAGE plpgsql
AS $$
BEGIN
    -- Check if the username or email already exist in the Jogador table
    IF EXISTS(
        SELECT
            1
        FROM
            Jogador
        WHERE
            username = newUsername
            OR email = newEmail) THEN
    RAISE EXCEPTION 'Jogador já existe';
END IF;
            -- Check if the region exists
            IF NOT EXISTS(
                SELECT
                    1
                FROM
                    Regiao
                WHERE
                    nome = newRegion) THEN
            RAISE EXCEPTION 'Região não existe';
END IF;
INSERT INTO jogador(id,username, email, estado, regiao)
    VALUES(jogador_id, newUsername, newEmail, 'Ativo', newRegion);
END;
$$;

CREATE OR REPLACE PROCEDURE update_jogador_inativo(IN userId int)
LANGUAGE 'plpgsql'
AS $$
DECLARE
    v_count integer;
BEGIN
  IF NOT EXISTS(SELECT 1 FROM Jogador WHERE id = userId) THEN
        RAISE EXCEPTION 'Jogador nao existe';
    END IF;
    UPDATE jogador
    SET estado = 'Inativo'
    WHERE id = userId;
END;
$$;

CREATE OR REPLACE PROCEDURE update_jogador_banido(IN userId int)
LANGUAGE 'plpgsql'
AS $$
DECLARE
    v_count integer;
BEGIN
     IF NOT EXISTS(SELECT 1 FROM Jogador WHERE id = userId) THEN
        RAISE EXCEPTION 'Jogador nao existe';
    END IF;
    UPDATE jogador
    SET estado = 'Banido'
    WHERE id = userId;
END;
$$;

---------------- Ponto E ----------------
CREATE OR REPLACE FUNCTION totalPontosJogador(IN jogador_id int)
    RETURNS int
    AS $$
DECLARE
    total_pontos int := 0;
BEGIN
    SELECT
        SUM(pontos) INTO total_pontos
    FROM (
        SELECT
            pontos
        FROM
            pontuacoes_normal
        WHERE
            id_jogador = jogador_id
        UNION ALL
        SELECT
            pontos
        FROM
            pontuacoes_multi_jogador
        WHERE
            id_jogador = jogador_id) AS pontos_jogador;
    RETURN total_pontos;
END;
$$
LANGUAGE plpgsql;

---------------- Ponto F ----------------
CREATE OR REPLACE FUNCTION totalJogosJogador(jogador_id int)
    RETURNS integer
    AS $$
BEGIN
    RETURN(
        SELECT
            COUNT(DISTINCT num_partida)
        FROM
            Pontuacoes_Normal
        WHERE
            id_jogador = $1) +(
        SELECT
            COUNT(DISTINCT num_partida)
        FROM
            Pontuacoes_Multi_Jogador
        WHERE
            id_jogador = $1);
END;
$$
LANGUAGE plpgsql;

---------------- Ponto G ----------------
CREATE OR REPLACE FUNCTION PontosJogoPorJogador(ref_jogo varchar(10))
    RETURNS TABLE(
        id int,
        total_pontos int
    )
    AS $$
BEGIN
    RETURN QUERY
    SELECT
        p.id_jogador AS id,
        cast(SUM(p.pontos) AS integer) AS total_pontos
    FROM(
        SELECT
            pn.id_jogador,
            pontos
        FROM
            Pontuacoes_Normal pn
        WHERE
            pn.ref_jogo = $1
        UNION ALL
        SELECT
            pmj.id_jogador,
            pontos
        FROM
            Pontuacoes_Multi_Jogador pmj
        WHERE
            pmj.ref_jogo = $1) AS p
GROUP BY
    id;
END;
$$
LANGUAGE 'plpgsql';

---------------- Ponto H ----------------

CREATE OR REPLACE PROCEDURE associarCracha(IN userId int, IN numero_partida int, IN nome_cracha varchar(64))
LANGUAGE 'plpgsql'
AS $$
DECLARE
pontos_requeridos integer;
    pontos_obtidos integer;
    ref varchar;
    tipo_partida varchar;

BEGIN
    IF NOT doesGameExist(numero_partida) THEN RAISE EXCEPTION 'Partida nao existe'; END IF;

SELECT * INTO ref, tipo_partida FROM getGameDetails(numero_partida);

IF NOT doesPlayerHaveGame(userId, ref) THEN RAISE EXCEPTION 'Jogador nao tem o jogo'; END IF;

    IF NOT isBadgeInGame(nome_cracha, ref) THEN RAISE EXCEPTION 'Cracha nao esta associado ao dado jogo'; END IF;

    pontos_requeridos := getBadgePointsLimit(nome_cracha, ref);
    pontos_obtidos := getPlayerPoints(userId, ref, numero_partida, tipo_partida);

    IF pontos_obtidos < pontos_requeridos THEN
        RAISE EXCEPTION 'Jogador nao atingiu o numero de pontos necessarios para obter cracha';
    ELSIF doesPlayerAlreadyHaveBadge(userId, nome_cracha) THEN
        RAISE EXCEPTION 'Jogador jÃ¡ tem o cracha';
ELSE
        PERFORM assignBadgeToPlayer(userId, nome_cracha);
END IF;
END;
$$;

------------------ FUNCTION 1 ------------------
-- Verifies if the game exists
CREATE OR REPLACE FUNCTION doesGameExist(IN numero_partida int)
RETURNS BOOLEAN AS $$
DECLARE
exists BOOLEAN;
BEGIN
SELECT EXISTS(
               SELECT 1 FROM Partida WHERE num = numero_partida
           ) INTO exists;
RETURN exists;
END;
$$ LANGUAGE 'plpgsql';


------------------ FUNCTION 2 ------------------
-- Obtains the reference of the game and its type
CREATE OR REPLACE FUNCTION getGameDetails(IN numero_partida int)
RETURNS TABLE(ref varchar, tipo_partida varchar) AS $$
BEGIN
RETURN QUERY
SELECT ref_jogo, tipo FROM Partida WHERE num = numero_partida;
END;
$$ LANGUAGE 'plpgsql';


------------------ FUNCTION 3 ------------------
-- Verifies if the player has the game
CREATE OR REPLACE FUNCTION doesPlayerHaveGame(IN userId int, IN ref_jogo varchar)
RETURNS BOOLEAN AS $$
DECLARE
hasGame BOOLEAN;
BEGIN
SELECT EXISTS(
               SELECT 1 FROM compra WHERE id_jogador = userId AND id_jogo = ref_jogo
           ) INTO hasGame;
RETURN hasGame;
END;
$$ LANGUAGE 'plpgsql';


------------------ FUNCTION 4 ------------------
-- Verifies if the badge is associated with the game
CREATE OR REPLACE FUNCTION isBadgeInGame(IN nome_cracha varchar, IN ref varchar)
RETURNS BOOLEAN AS $$
DECLARE
badgeExists BOOLEAN;
BEGIN
SELECT EXISTS(
               SELECT 1 FROM cracha WHERE nome = nome_cracha AND ref_jogo = ref
           ) INTO badgeExists;
RETURN badgeExists;
END;
$$ LANGUAGE 'plpgsql';


------------------ FUNCTION 5 ------------------
-- Retrieves the limit of points for the badge
CREATE OR REPLACE FUNCTION getBadgePointsLimit(IN nome_cracha varchar, IN ref varchar)
RETURNS int AS $$
DECLARE
pointsLimit int;
BEGIN
SELECT limite_de_pontos FROM cracha WHERE nome = nome_cracha AND ref_jogo = ref INTO pointsLimit;
RETURN pointsLimit;
END;
$$ LANGUAGE 'plpgsql';


------------------ FUNCTION 6 ------------------
-- Retrieves the points the player obtained in the game
CREATE OR REPLACE FUNCTION getPlayerPoints(IN userId int, IN ref varchar, IN numero_partida int, IN tipo_partida varchar)
RETURNS int AS $$
DECLARE
points int;
BEGIN
    IF (tipo_partida = 'Normal') THEN
SELECT pontos FROM Pontuacoes_Normal WHERE id_jogador = userId AND ref_jogo = ref INTO points;
ELSE
SELECT pontos FROM Pontuacoes_Multi_Jogador WHERE num_partida = numero_partida AND ref_jogo = ref AND id_jogador = userId INTO points;
END IF;
RETURN points;
END;
$$ LANGUAGE 'plpgsql';


------------------ FUNCTION 7 ------------------
-- Checks if the player already has the badge
CREATE OR REPLACE FUNCTION doesPlayerAlreadyHaveBadge(IN userId int, IN nome_cracha varchar)
RETURNS BOOLEAN AS $$
DECLARE
hasBadge BOOLEAN;
BEGIN
SELECT EXISTS(
               SELECT 1 FROM CrachaJogador WHERE id_jogador = userId AND nome = nome_cracha
           ) INTO hasBadge;
RETURN hasBadge;
END;
$$ LANGUAGE 'plpgsql';


------------------ FUNCTION 8 ------------------
-- Assigns the badge to the player
CREATE OR REPLACE FUNCTION assignBadgeToPlayer(IN userId int, IN nome_cracha varchar)
RETURNS VOID AS $$
BEGIN
INSERT INTO CrachaJogador(nome, id_jogador) VALUES (nome_cracha, userId);
END;
$$ LANGUAGE 'plpgsql';


---------------- Ponto I ----------------
------Usar um trigger--------

CREATE OR REPLACE FUNCTION finiciarConversa(userId IN INT, nome_nova_conversa IN VARCHAR) RETURNS INT AS $$
DECLARE
 nova_conversa_id INT;
BEGIN
  -- Generate a random nova_conversa_id
  nova_conversa_id := CAST(random() * 1000000 AS INT);

  -- Call iniciarConversa procedure with the generated nova_conversa_id
  CALL iniciarConversa(nova_conversa_id, userId, nome_nova_conversa);

  -- Return the generated nova_conversa_id
  RETURN nova_conversa_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE iniciarConversa(INOUT nova_conversa_id int, IN userId int, IN nome_nova_conversa varchar)
LANGUAGE 'plpgsql'
AS $$
DECLARE
    nova_conversa_id int;
BEGIN
    INSERT INTO Conversa(nome)
        VALUES (nome_nova_conversa)
    RETURNING
        id INTO nova_conversa_id;
    INSERT INTO ConversaMembros(id_conversa, id_jogador)
        VALUES (nova_conversa_id, 9999);
    CALL insere_conversamembros(nova_conversa_id, userId);
    CALL send_join_message(nova_conversa_id, userId);
END;
$$;

CREATE OR REPLACE PROCEDURE insere_conversamembros(IN conv_id int, IN jogador_id int)
LANGUAGE 'plpgsql'
AS $$
BEGIN
    INSERT INTO ConversaMembros(id_conversa, id_jogador)
        VALUES(conv_id, jogador_id);
END;
$$;

CREATE OR REPLACE PROCEDURE send_join_message(IN conv_id int, IN jogador_id int)
LANGUAGE 'plpgsql'
AS $$
DECLARE
    nome_jogador varchar;
BEGIN
    SELECT
        username INTO nome_jogador
    FROM
        jogador
    WHERE
        id = jogador_id;
    INSERT INTO Mensagem(id_conversa, data, hora, texto, id_jogador)
        VALUES (conv_id, CURRENT_DATE, CURRENT_TIME, 'User || nome_jogador || has joined the chat', 9999);
END;
$$;

---------------- Ponto J ----------------
------Usar um trigger--------
CREATE OR REPLACE PROCEDURE juntarConversa(IN userId int, IN conversaId int)
LANGUAGE 'plpgsql'
AS $$
BEGIN
    --Verificar se a conversa existe
    IF(conversaId NOT IN(
        SELECT
            id
        FROM
            conversa
        WHERE
            id = conversaId)) THEN
        RAISE EXCEPTION 'Conversa nao existe';
    END IF;
    CALL insere_conversamembros(conversaId, userId);
    CALL send_join_message(conversaId, userId);
END;
$$;

---------------- Ponto K ----------------
CREATE OR REPLACE PROCEDURE enviarMensagem(IN userId int, IN conversaId int, IN texto varchar)
LANGUAGE 'plpgsql'
AS $$
BEGIN
    --Verificar se o jogador existe
    IF(userId NOT IN(
        SELECT
            id
        FROM
            Jogador
        WHERE
            id = userId)) THEN
        RAISE EXCEPTION 'Jogador nao existe';
    END IF;
    --Verificar se a conversa existe
    IF(conversaId NOT IN(
        SELECT
            id
        FROM
            conversa
        WHERE
            id = conversaId)) THEN
        RAISE EXCEPTION 'Conversa nao existe';
    END IF;
    --Verificar se o jogador esta na conversa
    IF(userId NOT IN(
        SELECT
            id_jogador
        FROM
            conversamembros
        WHERE
            id_conversa = conversaId)) THEN
        RAISE EXCEPTION 'Jogador nao esta na conversa';
    END IF;
    INSERT INTO Mensagem(id_conversa, data, hora, texto, id_jogador)
        VALUES(conversaId, CURRENT_DATE, CURRENT_TIME, texto, userId);
END;
$$;
---------------- Ponto L ----------------
CREATE OR REPLACE FUNCTION totalPontosJogadorForView(IN userId int)
    RETURNS integer
    LANGUAGE 'plpgsql'
    AS $$
BEGIN
    RETURN(
        SELECT
            SUM(pontos)
        FROM(
            SELECT
                pontos
            FROM
                Pontuacoes_Normal
            WHERE
                id_jogador = userId
            UNION ALL
            SELECT
                pontos
            FROM
                Pontuacoes_Multi_Jogador
            WHERE
                id_jogador = userId) AS subquery);
END;
$$;

CREATE OR REPLACE FUNCTION totalPartidasJogador(IN userId int)
    RETURNS integer
    LANGUAGE 'plpgsql'
    AS $$
DECLARE
    total_partidas integer;
BEGIN
    SELECT
        COUNT(DISTINCT num_partida) INTO total_partidas
    FROM (
        SELECT
            num_partida
        FROM
            Pontuacoes_Normal
        WHERE
            id_jogador = userId
        UNION
        SELECT
            num_partida
        FROM
            Pontuacoes_Multi_Jogador
        WHERE
            id_jogador = userId) AS total;
    RETURN total_partidas;
END;
$$;

CREATE OR REPLACE FUNCTION totalJogosJogados(IN userId int)
    RETURNS integer
    LANGUAGE 'plpgsql'
    AS $$
DECLARE
    total_jogos integer;
BEGIN
    SELECT
        COUNT(DISTINCT ref_jogo) INTO total_jogos
    FROM (
        SELECT
            num_partida
        FROM
            Pontuacoes_Normal
        WHERE
            id_jogador = userId
        UNION
        SELECT
            num_partida
        FROM
            Pontuacoes_Multi_Jogador
        WHERE
            id_jogador = userId) AS total_partidas
    JOIN partida ON num = num_partida;
    RETURN total_jogos;
END;
$$;

--View
CREATE VIEW jogadorTotalInfo AS
SELECT
    j.id AS identificador,
    j.estado,
    j.email,
    j.username,
    totalJogosJogados(j.id) AS total_jogos,
    totalPartidasJogador(j.id) AS total_partidas,
    totalPontosJogadorForView(j.id) AS total_pontos
FROM
    Jogador j
WHERE
    j.estado != 'Banido';

--Atribuir a single player tambem
---------------- Ponto M ----------------
CREATE OR REPLACE FUNCTION atribuirCrachas()
    RETURNS TRIGGER
    AS $$
DECLARE
    rec_partida Partida%ROWTYPE;
    rec_pontuacao_multi_jogador Pontuacoes_Multi_Jogador%ROWTYPE;
    rec_pontuacao_normal Pontuacoes_Normal%ROWTYPE;
    rec_cracha Cracha%ROWTYPE;
    partida_ref_jogo varchar;
    rec_cracha_loop Cracha%ROWTYPE;
BEGIN
    -- Recuperando os dados da partida que acabou de terminar
    SELECT
        * INTO rec_partida
    FROM
        partida
    WHERE
        num = NEW.num;
    SELECT
        ref_jogo INTO partida_ref_jogo
    FROM
        rec_partida;
    --select ref_jogo into partida_ref_jogo from partida where num = NEW.num;
    RAISE NOTICE 'Beggining of Procedure, retrieved Match and Game information';
    --Verificar o tipo de partida
    IF rec_partida.tipo = 'Multi-jogador' THEN
        -- Loop para verificar a pontuação de cada jogador
        FOR rec_pontuacao_multi_jogador IN
        SELECT
            *
        FROM
            Pontuacoes_Multi_Jogador
        WHERE
            num_partida = NEW.num LOOP
                -- Recuperando os dados do crachá correspondente ao jogo
                RAISE NOTICE 'rec_po: %', badge_count;
                --select * INTO rec_cracha FROM Cracha WHERE ref_jogo = partida_ref_jogo AND rec_cracha.limite_de_pontos <= rec_pontuacao.pontos;
                --SELECT COUNT(*) into cracha_count from (select * FROM Cracha WHERE ref_jogo = partida_ref_jogo AND rec_cracha.limite_de_pontos <= rec_pontuacao.pontos) as all_crachas;
                --if cracha_count > 0 then
                FOR rec_cracha_loop IN
                SELECT
                    *
                FROM
                    Cracha
                WHERE
                    ref_jogo = partida_ref_jogo
                    AND limite_de_pontos <= rec_pontuacao_multi_jogador.pontos LOOP
                        -- Verificando se o jogador já possui o crachá
                        IF NOT EXISTS (
                            SELECT
                                *
                            FROM
                                CrachaJogador
                            WHERE
                                nome = rec_cracha.nome
                                AND id_jogador = rec_pontuacao_multi_jogador.id_jogador) THEN
                        -- Inserindo o registro na tabela CrachaJogador
                        INSERT INTO CrachaJogador(nome, id_jogador)
                            VALUES (rec_cracha.nome, rec_pontuacao_multi_jogador.id_jogador);
                        RAISE NOTICE 'Crachá % atribuído para o jogador % na partida %', rec_cracha_loop.nome, rec_pontuacao_multi_jogador.id_jogador, NEW.num;
                    END IF;
            END LOOP;
        RAISE NOTICE 'rec_cracha_loop: %', rec_cracha_loop;
    END LOOP;
ELSE
    SELECT
        * INTO rec_pontuacao_normal
    FROM
        Pontuacoes_Normal
    WHERE
        num_partida = NEW.num;
    -- Loop para verificar cada crachá válido para a pontuação
    FOR rec_cracha_loop IN
    SELECT
        *
    FROM
        Cracha
    WHERE
        ref_jogo = partida_ref_jogo
        AND limite_de_pontos <= rec_pontuacao_normal.pontos LOOP
            -- Verificando se o jogador já possui o crachá
            IF NOT EXISTS (
                SELECT
                    *
                FROM
                    CrachaJogador
                WHERE
                    nome = rec_cracha_loop.nome
                    AND id_jogador = rec_pontuacao_normal.id_jogador) THEN
            -- Inserindo o registro na tabela CrachaJogador
            INSERT INTO CrachaJogador(nome, id_jogador)
                VALUES (rec_cracha_loop.nome, rec_pontuacao_normal.id_jogador);
            RAISE NOTICE 'Crachá % atribuído para o jogador % na partida %', rec_cracha_loop.nome, rec_pontuacao_multi_jogador.id_jogador, NEW.num;
        END IF;
END LOOP;
    RAISE NOTICE 'rec_cracha_loop: %', rec_cracha_loop;
END IF;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER atribuirCrachas
    AFTER UPDATE OF data_fim ON Partida
    FOR EACH ROW
    --quando o timestamp fim é inserido
    WHEN(OLD.data_fim = NULL AND NEW.data_fim IS NOT NULL)
    EXECUTE FUNCTION atribuirCrachas();

-- Executar função para dar update às estatisticas?
---------------- Ponto N ----------------
CREATE OR REPLACE FUNCTION banir_jogadores()
    RETURNS TRIGGER
    AS $$
DECLARE
    id_to_ban int = OLD.id;
BEGIN
    UPDATE
        jogador
    SET
        estado = 'Banido'
    WHERE
        id = id_to_ban;
    RETURN new;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER banir_jogadores
    INSTEAD OF DELETE ON jogadorTotalInfo
    FOR EACH ROW
    EXECUTE FUNCTION banir_jogadores();

