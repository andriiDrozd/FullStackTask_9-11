Завдання до лекцій 9-11 (REST, Databases)


Обрати предметну область на свій розсуд. Книги, Товари, Клієнти, Туризм, або ін. (окрім "Студентів").
В рамках предметної області повинно бути 2 сутності, що співвідносяться одне до одного як багато-до-одного (Сутність 1 *-- Сутність 2, наприклад, Товар *-- Категорія).
Змоделювати цю предметну область в реляційній БД: створити відповідні таблиці, скрипт створення додати до вихідних кодів проекту.
Розробити бекенд web-застосунку з REST API, який би дозволяв виконувати такі функції з цими сутностями:
- створювати, читати, оновляти і видаляти записи Сутності 1
- робити пошук елементів Сутності 1 по 2-х полях і з постраничником
- повертати повний список елементів Сутності 2
Додати інтеграційні тести для всіх REST-endpoints.
В завданні використовувати одну з вказаних реляційних СКБД: MySQL, Postgre, H2.
Для взаємодії з СКБД можна використовувати будь-який спосіб в рамках технологій, що розглядаються в лекціях.



Створення та підклюення Бази Данних PostgreSQL:

Потрібно cтворити та підключити дві бази данних.
Перша для роботи додатку-"rest_api_db".
Друга для роботи тестів-"rest_api_db_test".

Скрипт для створення стобців та полів в "rest_api_db":

CREATE TABLE Vessel
(
    id            int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    vessel_name          varchar(100) NOT NULL,
    year_of_build int check ( year_of_build > 1800 ) check ( year_of_build<2024 )
);

CREATE TABLE Cargo
(
    cargo_id          int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    vessel_id   int          REFERENCES Vessel (id) ON DELETE SET NULL,
    cargo_name   varchar(100) NOT NULL,
    send_number int check ( send_number<2147483647 )  NOT NULL UNIQUE
);


INSERT INTO Vessel(vessel_name, year_of_build) VALUES ('M/V AAL Paris', 2010);
INSERT INTO Vessel(vessel_name, year_of_build) VALUES ('M/V Warnow Merkur', 2009);
INSERT INTO Vessel(vessel_name, year_of_build) VALUES ('M/V Warnow Moon', 2011);

INSERT INTO Cargo(vessel_id  , cargo_name, send_number) VALUES (1,'BMW x5',123456);
INSERT INTO Cargo(vessel_id  , cargo_name, send_number) VALUES (1,'Tesla Model 3',143446);
INSERT INTO Cargo(vessel_id  , cargo_name, send_number) VALUES (2,'Maybach S 560',222456);
INSERT INTO Cargo(vessel_id  , cargo_name, send_number) VALUES (2,'Porsche Cayman S 987',223426);
INSERT INTO Cargo(vessel_id  , cargo_name, send_number) VALUES (3,'Mercedes-Benz GLC',324456);
INSERT INTO Cargo(vessel_id  , cargo_name, send_number) VALUES (3,'Lamborghini Urus',329426);


Скрипт для створення стобців та полів в "rest_api_db_test":

CREATE TABLE Vessel
(
    id            int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    vessel_name          varchar(100) NOT NULL,
    year_of_build int check ( year_of_build > 1800 ) check ( year_of_build<2024 )
);

CREATE TABLE Cargo
(
    cargo_id          int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    vessel   int          REFERENCES Vessel (id) ON DELETE SET NULL,
    cargo_name   varchar(100) NOT NULL,
    send_number int check ( send_number<2147483647 )  NOT NULL UNIQUE
);


INSERT INTO Vessel(vessel_name, year_of_build) VALUES ('M/V AAL Paris', 2010);
INSERT INTO Vessel(vessel_name, year_of_build) VALUES ('M/V Warnow Merkur', 2009);
INSERT INTO Vessel(vessel_name, year_of_build) VALUES ('M/V Warnow Moon', 2011);

INSERT INTO Cargo(vessel_id, cargo_name, send_number) VALUES (1,'BMW x5',123456);
INSERT INTO Cargo(vessel_id, cargo_name, send_number) VALUES (1,'Tesla Model 3',143446);
INSERT INTO Cargo(vessel_id, cargo_name, send_number) VALUES (2,'Maybach S 560',222456);
INSERT INTO Cargo(vessel_id, cargo_name, send_number) VALUES (2,'Porsche Cayman S 987',223426);
INSERT INTO Cargo(vessel_id, cargo_name, send_number) VALUES (3,'Mercedes-Benz GLC',324456);
INSERT INTO Cargo(vessel_id, cargo_name, send_number) VALUES (3,'Lamborghini Urus',329426);

