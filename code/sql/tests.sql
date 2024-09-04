--------------- PONTO D Test ---------------
CREATE OR REPLACE PROCEDURE insert_jogador_test()
LANGUAGE 'plpgsql'
AS $$
DECLARE
    new_email varchar;
    new_username varchar;
    new_user int;
    jogador_id int;
begin
    -- Generate a unique email and username
	jogador_id := floor(random() * 1000000)::int;
    new_email := CONCAT('user', CAST(random() * 1000000 AS INTEGER), '@example.com');
    new_username := CONCAT('user', CAST(random() * 1000000 AS INTEGER));
    -- Call the insert_jogador procedure
    call insert_jogador(jogador_id,new_username, new_email, 'EuropeWest');
    -- Check if the user was inserted successfully
    SELECT
        count(id) INTO new_user
    FROM
        jogador
    WHERE
        email = new_email
        AND username = new_username;
    IF new_user = 1 THEN
        RAISE NOTICE 'insert_jogador: Resultado OK';
        DELETE FROM jogador
        WHERE email = new_email
            AND username = new_username;
    ELSE
        RAISE NOTICE 'insert_jogador: Resultado NOK';
    END IF;
END;
$$;

CREATE OR REPLACE PROCEDURE update_jogador_inativo_test()
LANGUAGE 'plpgsql'
AS $$
DECLARE
    new_id int;
    new_user int;
BEGIN
    -- Retrieve an id that has estado = 'Ativo'
    SELECT
        id INTO new_id
    FROM
        jogador
    WHERE
        username = 'miguelalmeida';
    IF new_id IS NOT NULL THEN
        -- Call the update_jogador_inativo procedure
        RAISE NOTICE 'id = % antes da chamada', new_id;
        CALL update_jogador_inativo(new_id);
        -- Check if the user was updated successfully
        SELECT
            count(id) INTO new_user
        FROM
            jogador
        WHERE
            id = new_id
            AND estado = 'Inativo';
        RAISE NOTICE 'TOU AQUI';
        IF new_user = 1 THEN
            RAISE NOTICE 'update_jogador_inativo: Resultado OK';
        ELSE
            RAISE NOTICE 'update_jogador_inativo: Resultado NOK';
        END IF;
    ELSE
        RAISE NOTICE 'No jogador found with estado = Activo';
    END IF;
END;
$$;

CREATE OR REPLACE PROCEDURE update_jogador_banido_test()
LANGUAGE 'plpgsql'
AS $$
DECLARE
    new_id int;
    new_user int;
BEGIN
    -- Retrieve an id that has estado = 'Ativo'
    SELECT
        id INTO new_id
    FROM
        jogador
    WHERE
        username = 'ricardobernardino';
    IF new_id IS NOT NULL THEN
        -- Call the update_jogador_banido procedure
        RAISE NOTICE 'new_id = %', new_id;
        CALL update_jogador_banido(new_id);
        -- Check if the user was updated successfully
        SELECT
            count(id) INTO new_user
        FROM
            jogador
        WHERE
            id = new_id
            AND estado = 'Banido';
        IF new_user = 1 THEN
            RAISE NOTICE 'update_jogador_banido: Resultado OK';
        ELSE
            RAISE NOTICE 'update_jogador_banido: Resultado NOK';
        END IF;
    ELSE
        RAISE NOTICE 'No jogador found with estado = Activo';
    END IF;
END;
$$;

--------------- PONTO E Test ---------------
CREATE OR REPLACE PROCEDURE total_pontos_jogador_test()
LANGUAGE 'plpgsql'
AS $$
DECLARE
    userId int;
    new_user int;
BEGIN
    -- Retrieve an id of player
    SELECT
        id INTO userId
    FROM
        jogador
    WHERE
        username = 'miguelalmeida';
    IF userId IS NOT NULL THEN
        -- Call the totalPontosJogador function
        new_user := totalPontosJogador(userId);
        IF new_user > 0 THEN
            RAISE NOTICE 'total_pontos_jogador: Resultado OK';
        ELSE
            RAISE NOTICE 'total_pontos_jogador: Resultado NOK';
        END IF;
    ELSE
        RAISE NOTICE 'No jogador found';
    END IF;
END;
$$;

--------------- PONTO F Test ---------------
CREATE OR REPLACE PROCEDURE total_jogos_jogador_test()
LANGUAGE 'plpgsql'
AS $$
DECLARE
    userId int;
    new_user int;
BEGIN
    -- Retrieve an id of player
    SELECT
        id INTO userId
    FROM
        jogador
    WHERE
        username = 'ricardobernardino';
    IF userId IS NOT NULL THEN
        -- Call the totalJogosJogador procedure
        new_user := totalJogosJogador(userId);
        -- Check if the procedure was successful
        IF new_user > 0 THEN
            RAISE NOTICE 'total_jogos_jogador: Resultado OK';
        ELSE
            RAISE NOTICE 'total_jogos_jogador: Resultado NOK';
        END IF;
    ELSE
        RAISE NOTICE 'No jogador found';
    END IF;
END;
$$;

--------------- PONTO G Test ---------------
CREATE OR REPLACE PROCEDURE pontos_jogo_por_jogador_test()
LANGUAGE 'plpgsql'
AS $$
DECLARE
    ROW int;
BEGIN
    -- Call the pontosjogoporjogador function and store the result in 'row'
    SELECT
        count(*) INTO ROW
    FROM
        PontosJogoPorJogador('0000000001');
    -- Check if any rows were returned by the function
    IF ROW > 0 THEN
        RAISE NOTICE 'pontos_jogo_por_jogador: Resultado OK';
    ELSE
        RAISE NOTICE 'pontos_jogo_por_jogador: Resultado NOK';
    END IF;
END;
$$;



--------------- PONTO H Test ---------------
CREATE OR REPLACE PROCEDURE associar_cracha_test()
LANGUAGE 'plpgsql'
AS $$
DECLARE
    new_cracha int;
BEGIN
    -- Call the associarCracha procedure
    CALL associarCracha(2, 229, '100 Kills');
    SELECT
        count(*) INTO new_cracha
    FROM
        crachajogador
    WHERE
        nome = '100 Kills'
        AND id_jogador = 2;
    IF new_cracha = 1 THEN
        RAISE NOTICE 'associar_cracha: Resultado OK';
    ELSE
        RAISE NOTICE 'associar_cracha: Resultado NOK';
    END IF;
END;
$$;

--------------- PONTO I Test ---------------
CREATE OR REPLACE PROCEDURE iniciar_conversa_test()
LANGUAGE 'plpgsql'
AS $$
DECLARE
    new_conversa int;
BEGIN
    CALL iniciarConversa(new_conversa, 1, 'Test Message Chat');
    IF new_conversa > 0 THEN
        RAISE NOTICE 'iniciar_conversa: Resultado OK';
    ELSE
        RAISE NOTICE 'iniciar_conversa: Resultado NOK';
    END IF;
END;
$$;

--------------- PONTO J Test ---------------
CREATE OR REPLACE PROCEDURE juntar_conversa_test()
LANGUAGE 'plpgsql'
AS $$
DECLARE
    new_conversa_member int;
BEGIN
    CALL juntarConversa(2, 2);
    SELECT
        count(*) INTO new_conversa_member
    FROM
        ConversaMembros
    WHERE
        id_conversa = 2
        AND id_jogador = 2;
    IF new_conversa_member = 1 THEN
        RAISE NOTICE 'juntar_conversa: Resultado OK';
    ELSE
        RAISE NOTICE 'juntar_conversa: Resultado NOK';
    END IF;
END;
$$;

--------------- PONTO K Test ---------------
CREATE OR REPLACE PROCEDURE enviar_mensagem_test()
LANGUAGE 'plpgsql'
AS $$
DECLARE
    newTexto varchar := 'Acabei de me juntar a esta conversa, esta e uma mensagem de teste';
    new_msg int;
BEGIN
    CALL enviarMensagem(2, 2, newTexto);
    SELECT
        count(*) INTO new_msg
    FROM
        Mensagem
    WHERE
        id_conversa = 2
        AND id_jogador = 2
        AND texto = newTexto;
    IF new_msg = 1 THEN
        RAISE NOTICE 'enviar_mensagem: Resultado OK';
    ELSE
        RAISE NOTICE 'enviar_mensagem: Resultado NOK';
    END IF;
END;
$$;

--------------- PONTO L Test ---------------
CREATE OR REPLACE PROCEDURE jogadorTotalInfo_test()
LANGUAGE 'plpgsql'
AS $$
DECLARE
    result_count int;
BEGIN
    CREATE TEMPORARY TABLE temp_table AS
    SELECT
        identificador,
        estado,
        email,
        username,
        total_jogos,
        total_partidas,
        total_pontos
    FROM
        jogadorTotalInfo;
    SELECT
        COUNT(*) INTO result_count
    FROM
        temp_table;
    IF result_count > 0 THEN
        RAISE NOTICE 'jogadorTotalInfo_test: Resultado OK';
    ELSE
        RAISE NOTICE 'jogadorTotalInfo_test: Resultado NOK';
    END IF;
END;
$$;

--------------- PONTO M Test ---------------

CREATE OR REPLACE PROCEDURE atribuirCrachas_test_part1()
LANGUAGE 'plpgsql'
AS $$
BEGIN
    -- Set up test data
        INSERT INTO jogador(id, username, email, estado, regiao)
            VALUES (24, 'alberto', 'alberto@system.com', 'Ativo', 'EuropeWest'),
        (42, 'toino', 'toino@gmail.com', 'Ativo', 'EuropeWest'),
        (84, 'carlos', 'carlos@gmail.com', 'Ativo', 'EuropeWest');
        INSERT INTO compra(id_jogo, id_jogador, data, preco)
            VALUES ('0000000001', 24, '2022-03-01', 20),
        ('0000000001', 42, '2022-03-01', 20),
        ('0000000001', 84, '2022-03-01', 20);
        INSERT INTO partida(num, ref_jogo, data_inicio, data_fim, tipo, regiao)
            VALUES (100, '0000000001', '2022-03-02', NULL, 'Multi-jogador', 'EuropeWest');
        INSERT INTO partida_multi_jogador(num, ref_jogo, estado)
            VALUES (100, '0000000001', 'Em Curso');
        INSERT INTO pontuacoes_multi_jogador(num_partida, ref_jogo, id_jogador, pontos)
            VALUES (100, '0000000001', 24, 1),
        (100, '0000000001', 42, 2),
        (100, '0000000001', 84, 5);
        INSERT INTO cracha(nome, ref_jogo, limite_de_pontos, imagem)
            VALUES ('Double Kill', '0000000001', 2, 'https://www.imaginaryURL2.com'),
        ('Penta Kill', '0000000001', 5, 'https://www.imaginaryURL5.com');
END;
$$;



   CREATE OR REPLACE PROCEDURE atribuirCrachas_test_part2()
LANGUAGE 'plpgsql'
AS $$
DECLARE
    result_count1 int;
    result_count2 int;
begin	
        -- Update data_fim of the Partida
        UPDATE
            Partida
        SET
            data_fim = '2022-03-03'
        WHERE
            num = 100;
        -- Assert that the CrachaJogador table has the expected data
        SELECT
            COUNT(nome) INTO result_count1
        FROM
            CrachaJogador
        WHERE
            nome = 'Double Kill'
            AND id_jogador = 42;
        SELECT
            COUNT(nome) INTO result_count2
        FROM
            CrachaJogador
        WHERE
            nome = 'Penta Kill'
            AND id_jogador = 84;
        -- Expected result: 1
        IF result_count1 = 1 AND result_count2 = 1 THEN
            RAISE NOTICE 'atribuirCrachas_test: Resultado OK';
        ELSE
            RAISE NOTICE 'atribuirCrachas_test: Resultado NOK';
    END IF;
END;
$$;

--------------- PONTO N Test ---------------
CREATE OR REPLACE PROCEDURE test_banir_jogadores()
LANGUAGE 'plpgsql'
AS $$
DECLARE
    jogador_id int;
    estado_jogador varchar;
begin
	jogador_id := floor(random() * 1000000)::int;
    -- Insert a player to test ban
    INSERT INTO Jogador(id,email, username, estado, regiao)
        VALUES (jogador_id,'ambrosio@example.com', 'ambrosio', 'Ativo', 'EuropeWest')
    RETURNING
        id INTO jogador_id;
    -- Execute the delete operation that should trigger the trigger function
    DELETE FROM jogadorTotalInfo;
    -- Check if the player was banned
    SELECT
        estado INTO estado_jogador
    FROM
        Jogador
    WHERE
        id = jogador_id;
    IF estado_jogador = 'Banido' THEN
        RAISE NOTICE 'Player was successfully banned:test_banir_jogadores() Resultado OK';
    ELSE
        RAISE EXCEPTION 'Player was not banned:test_banir_jogadores() Resultado NOK';
    END IF;
END;
$$
--------------- Tests Execution ---------------
-- Create Tables
-- Insert Data into Table
BEGIN TRANSACTION ISOLATION LEVEL REPEATABLE READ;
CALL insert_jogador_test();
CALL update_jogador_inativo_test();
CALL update_jogador_banido_test();
CALL associar_cracha_test();
CALL atribuirCrachas_test();
CALL test_banir_jogadores();
ROLLBACK;
BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;
CALL iniciar_conversa_test();
CALL juntar_conversa_test();
CALL enviar_mensagem_test();
ROLLBACK;
BEGIN TRANSACTION ISOLATION LEVEL READ COMMITTED;
CALL total_pontos_jogador_test();
CALL total_jogos_jogador_test();
CALL pontos_jogo_por_jogador_test();
CALL jogadortotalinfo_test();
ROLLBACK;
-- Delete Data from Table


begin transaction;
CALL  insert_jogador_test();
rollback;
