DROP TABLE IF EXISTS cr_address_person;
DROP TABLE IF EXISTS cr_person;
DROP TABLE IF EXISTS cr_address;
DROP TABLE IF EXISTS cr_district;
DROP TABLE IF EXISTS cr_street;


CREATE TABLE cr_district (
    district_code integer not null,
    district_name varchar(300),
    PRIMARY KEY (district_code)
);

INSERT INTO cr_district(district_code, district_name)
VALUES (1, 'Северное Тушино');

CREATE TABLE cr_street (
    street_code integer not null,
    street_name varchar(300),
    PRIMARY KEY (street_code)
);

INSERT INTO cr_street(street_code, street_name)
VALUES (1, 'улица Свободы');

CREATE TABLE cr_address (
    address_id SERIAL,
    district_code integer not null,
    street_code integer not null,
    building varchar(10),
    extension varchar(10),
    apartment varchar(10),
    PRIMARY KEY (address_id),--первичный ключ
    FOREIGN KEY(street_code) REFERENCES cr_street(street_code) ON DELETE RESTRICT, --внешний ключ
    FOREIGN KEY(district_code) REFERENCES cr_district(district_code) ON DELETE RESTRICT --внешний ключ
);

INSERT INTO cr_address (district_code, street_code, building, extension, apartment)
VALUES (1, 1, 32, 1, 103);

CREATE TABLE cr_person (
    person_id SERIAL,
    sur_name varchar(100) not null,
    given_name varchar(100) not null,
    patronymic varchar(100) not null,
    date_of_birth date not null,
    passport_series varchar(10),
    passport_number varchar(10),
    passport_date date,
    certificate_number varchar(10) null,
    certificate_date date null,
    PRIMARY KEY (person_id)
);

INSERT INTO cr_person (sur_name, given_name, patronymic, date_of_birth, passport_series,
passport_number, passport_date, certificate_number, certificate_date)
VALUES ('Марш', 'Рэнди','Марвин', '1977-05-17', 3356, 694035, '1997-10-23', null, null);

INSERT INTO cr_person (sur_name, given_name, patronymic, date_of_birth, passport_series,
passport_number, passport_date, certificate_number, certificate_date)
VALUES ('Марш', 'Шерон', 'Эйприл', '1974-07-08', 4568, 453782, '1994-6-12', null, null);

INSERT INTO cr_person (sur_name, given_name, patronymic, date_of_birth, passport_series,
passport_number, passport_date, certificate_number, certificate_date)
VALUES ('Марш', 'Стенли', 'Рендалл', '2013-11-22', null, null, null, 684354, '2013-08-30');

INSERT INTO cr_person (sur_name, given_name, patronymic, date_of_birth, passport_series,
passport_number, passport_date, certificate_number, certificate_date)
VALUES ('Марш', 'Шелли', 'Рендалл', '2004-06-15', null, null, null, 786744, '2004-10-04');

CREATE TABLE cr_address_person (
    person_address_id SERIAL,
    address_id integer not null,
    person_id integer not null,
    start_date date not null,--дата прописки
    end_date date,
    temporal boolean DEFAULT false,--зарегистрирован временно
    PRIMARY KEY (person_address_id),
    FOREIGN KEY (address_id) REFERENCES cr_address(address_id),
    FOREIGN KEY (person_id) REFERENCES cr_person(person_id)
);

INSERT INTO cr_address_person(address_id, person_id, start_date, end_date, temporal)
VALUES(1, 1, '2010-02-03', null, false);

INSERT INTO cr_address_person(address_id, person_id, start_date, end_date)
VALUES(1, 2, '2010-02-03', null);

INSERT INTO cr_address_person(address_id, person_id, start_date, end_date)
VALUES(1, 3, '2014-04-11', null);

INSERT INTO cr_address_person(address_id, person_id, start_date, end_date)
VALUES(1, 4, '2010-09-30', null);


--1. Район
--2. Улица
--3. Адрес - район, улица, дом, корпус, квартира
--4. Персона - ФИО, дата рождения, паспорт (серия/номер, дата выдачи),
--свидетельство о рождении (номер/дата выдачи)
--5. Связь персоны и адреса - персона, адрес и диапазон дат, вид регистрации

