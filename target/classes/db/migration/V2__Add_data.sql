INSERT INTO city (city, country) VALUES ('Undefined', 'Undefined');
INSERT INTO city (city, country) VALUES ('Київ', 'Україна');
INSERT INTO city (city, country) VALUES ('Харків', 'Україна');
INSERT INTO city (city, country) VALUES ('Одеса', 'Україна');
INSERT INTO city (city, country) VALUES ('Дніпро', 'Україна');
INSERT INTO city (city, country) VALUES ('Запоріжжя', 'Україна');
INSERT INTO city (city, country) VALUES ('Львів', 'Україна');
INSERT INTO city (city, country) VALUES ('Кривий Ріг', 'Україна');
INSERT INTO city (city, country) VALUES ('Миколаїв', 'Україна');
INSERT INTO city (city, country) VALUES ('Вінниця', 'Україна');
INSERT INTO city (city, country) VALUES ('Херсон', 'Україна');
INSERT INTO city (city, country) VALUES ('Полтава', 'Україна');
INSERT INTO city (city, country) VALUES ('Черкаси', 'Україна');
INSERT INTO city (city, country) VALUES ('Хмельницький', 'Україна');
INSERT INTO city (city, country) VALUES ('Чернівці', 'Україна');
INSERT INTO city (city, country) VALUES ('Житомир', 'Україна');
INSERT INTO city (city, country) VALUES ('Суми', 'Україна');
INSERT INTO city (city, country) VALUES ('Рівне', 'Україна');

INSERT INTO type (name, total) VALUES ('Undefined', '0');
INSERT INTO type (name, total) VALUES ('Повна', '0');
INSERT INTO type (name, total) VALUES ('Неповна', '0');
INSERT INTO type (name, total) VALUES ('Дистанційна', '0');
INSERT INTO type (name, total) VALUES ('Часткова', '0');
INSERT INTO type (name, total) VALUES ('Проектна', '0');

INSERT INTO category (long_name, total) VALUES ('Undefined', '0');

INSERT INTO usr (name, username, password, activated, blocked) VALUES ('adminLog', 'adminLog', 'admin', true, false);
INSERT INTO user_role (user_id, roles) VALUES (1, 'ADMIN');