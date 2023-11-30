INSERT INTO public.user(email, password)
VALUES ('test1@mail.ru', 'qwerty1');
INSERT INTO public.user(email, password)
VALUES ('test2@mail.ru', 'qwerty2');


INSERT INTO public.company(user_id, org_name, inn, ogrn, phone)
VALUES (6, 'FARM', '1111111111', '1111111111111', '89000000000');

INSERT INTO public.company(user_id, org_name, inn, ogrn, phone)
VALUES (6, 'KEK', '4444444444', '4444444444444', '89000000001');
INSERT INTO public.company(user_id, org_name, inn, ogrn, phone)
VALUES (6, 'FARM', '5555555555', '5555555555555', '89000000002');

TRUNCATE table public.user CASCADE;
TRUNCATE table public.company;
TRUNCATE table public.refresh_token;

DROP FUNCTION update_acceptable() CASCADE;

CREATE
OR REPLACE FUNCTION update_acceptable()
    RETURNS TRIGGER AS $$
BEGIN
    IF
NEW.rating = 7 THEN
        NEW.acceptable = true; -- Здесь укажите желаемое новое значение
END IF;
RETURN NEW;
END;
$$
LANGUAGE plpgsql;

-- Создаем триггер, который вызывает функцию при обновлении таблицы
CREATE TRIGGER update_acceptable_trigger
    BEFORE UPDATE
    ON company -- Замените your_table_name на имя вашей таблицы
    FOR EACH ROW
    --WHEN (NEW.rating = 7)
    EXECUTE FUNCTION update_acceptable();

UPDATE public.company
SET rating = 15
WHERE id = 9;

CREATE
OR REPLACE FUNCTION update_belief()
    RETURNS TRIGGER AS $$
BEGIN
    IF
NEW.rating = 15 THEN
        NEW.belief = 'HIGH';
    elsif
NEW.rating = 10 THEN
        NEW.belief = 'MID';
    elsif
NEW.rating = 5 THEN
        NEW.belief = 'LOW';
END IF;
RETURN NEW;
END;
$$
LANGUAGE plpgsql;


CREATE TRIGGER update_belief_trigger
    BEFORE UPDATE
    ON company
    FOR EACH ROW
    --WHEN (NEW.rating = 7)
    EXECUTE FUNCTION update_belief();