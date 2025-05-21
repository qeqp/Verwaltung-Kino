CREATE TABLE IF NOT EXISTS Kunde (
    Email varchar(128) PRIMARY KEY CHECK(Email ~* '^[0-9\-_]+@[A-Za-z]+\.db$'),
    Passwort varchar(256) NOT NULL CHECK(
        length(Passwort) >= 8 AND
        Passwort ~ '[A-Z]' AND
        Passwort ~ '[a-z]' AND
        Passwort ~ '[0-9]' AND
        Passwort ~ '[!#]'
    ),
    Vorname varchar(32) NOT NULL CHECK(Vorname ~ '^[A-Za-z]+$'),
    Nachname varchar(32) NOT NULL CHECK(Nachname ~ '^[A-Za-z]+$'),
    Geburtsdatum date NOT NULL,
    Strasse varchar(128) NOT NULL CHECK(Strasse ~ '^[A-Za-z]+$'),
    Hausnummer varchar(4) NOT NULL CHECK(Hausnummer ~* '^[A-Za-z0-9]+$'),
    Stadt varchar(128) NOT NULL,
    PLZ bigint NOT NULL CHECK(length(CAST(PLZ AS text)) <= 12 AND PLZ > 0)
    );

-- b)
CREATE TABLE IF NOT EXISTS Film (
    FilmID serial PRIMARY KEY,
    Titel varchar(128) NOT NULL,
    Genre varchar(32) NOT NULL,
    FSK int NOT NULL,
    Dauer int NOT NULL CHECK(Dauer > 0)
    );

-- c)
CREATE TABLE IF NOT EXISTS Kinosaal (
    Saalname varchar(32) PRIMARY KEY CHECK(Saalname ~ '^Saal[A-Za-z]+$'),
    Kapazitaet int NOT NULL CHECK(Kapazitaet >= 10 AND Kapazitaet <= 1000)
    );

-- d)
CREATE TABLE IF NOT EXISTS Vorstellung (
    VID serial PRIMARY KEY,
    Datum date NOT NULL,
    Uhrzeit time NOT NULL,
    Saalname varchar(32) NOT NULL REFERENCES Kinosaal(Saalname),
    FilmID serial NOT NULL REFERENCES Film(FilmID)
    );

-- e)
CREATE TABLE IF NOT EXISTS Mitarbeiter (
    Mitarbeiterkennung varchar(32) PRIMARY KEY,
    Vorname varchar(32) NOT NULL CHECK(Vorname ~ '^[A-Za-z]+$'),
    Nachname varchar(32) NOT NULL CHECK(Nachname ~ '^[A-Za-z]+$'),
    Gehalt numeric(10,2) NOT NULL CHECK(Gehalt >= 100),
    Einstellungsdatum date NOT NULL,
    Passwort varchar(256) NOT NULL CHECK(
        length(Passwort) >= 8 AND
        Passwort ~ '[A-Z]' AND
        Passwort ~ '[a-z]' AND
        Passwort ~ '[0-9]' AND
        Passwort ~ '[!#]')
    );

-- g)
CREATE TABLE IF NOT EXISTS Techniker (
    Mitarbeiterkennung varchar(32) PRIMARY KEY REFERENCES Mitarbeiter(Mitarbeiterkennung),
    Fachgebiet varchar(16) NOT NULL CHECK(
    Fachgebiet IN ('Sound', 'Bild', 'Beleuchtung', 'Projektion'))
    );

-- h)
CREATE TABLE IF NOT EXISTS Ticket (
    TicketID serial PRIMARY KEY,
    Preis int NOT NULL CHECK(Preis >= 100 AND Preis <= 999999),
    Sitzplatz varchar(3) NOT NULL CHECK(Sitzplatz ~ '^[A-Z][0-9]{1,2}$'),
    VID serial NOT NULL REFERENCES Vorstellung(VID),
    Email varchar(128) NOT NULL REFERENCES Kunde(Email)
    );

-- i)
CREATE TABLE IF NOT EXISTS wartet (
    Mitarbeiterkennung varchar(32) NOT NULL REFERENCES Mitarbeiter(Mitarbeiterkennung),
    Saalname varchar(32) NOT NULL REFERENCES Kinosaal(Saalname),
    PRIMARY KEY (Mitarbeiterkennung, Saalname)
    );

-- j)
CREATE TABLE IF NOT EXISTS storniert (
    TicketID int PRIMARY KEY REFERENCES Ticket(TicketID),
    Email varchar(128) NOT NULL REFERENCES Kunde(Email),
    Gebuehr int NOT NULL CHECK(Gebuehr >= 0)
    );