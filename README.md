# Kino-Verwaltungssystem

Eine Spring Boot Webanwendung zur Verwaltung eines Kinos mit PostgreSQL Datenbankanbindung.

## 📋 Projektübersicht

Dieses Projekt ist ein vollständiges Kino-Verwaltungssystem, das es ermöglicht:
- Filme zu verwalten und zu durchsuchen
- Vorstellungen zu planen und anzuzeigen
- Kunden- und Ticketdaten zu verwalten
- Benutzerpasswörter zu ändern

## 🛠️ Technologien

- **Java 17** - Programmiersprache
- **Spring Boot** - Web-Framework
- **PostgreSQL** - Datenbank
- **Docker** - Containerisierung
- **Gradle** - Build-Management
- **JDBC Template** - Datenbankzugriff
- **Thymeleaf** - Template Engine für HTML

## 📁 Projektstruktur

```
src/
├── main/
│   ├── java/
│   │   └── demo/
│   │       ├── DemoApplication.java          # Hauptklasse
│   │       ├── controller/
│   │       │   └── Controller.java           # REST-Controller
│   │       ├── model/
│   │       │   ├── FilmDTO.java             # Film Datenmodell
│   │       │   ├── KundeDTO.java            # Kunden Datenmodell
│   │       │   ├── VorstellungDTO.java      # Vorstellungs Datenmodell
│   │       │   └── TicketDTO.java           # Ticket Datenmodell
│   │       └── persistence/
│   │           ├── FilmDao.java             # Film Datenzugriff
│   │           ├── KundeDao.java            # Kunden Datenzugriff
│   │           ├── VorstellungDao.java      # Vorstellungs Datenzugriff
│   │           └── TicketDao.java           # Ticket Datenzugriff
│   └── resources/
│       ├── application.properties           # Anwendungskonfiguration
│       ├── data/
│       │   ├── schema.sql                   # Datenbankschema (DDL)
│       │   └── data.sql                     # Beispieldaten (DML)
│       └── templates/                       # HTML-Templates
│           ├── index.html
│           ├── film.html
│           ├── vorstellung.html
│           ├── addVorstellung.html
│           └── tickets.html
```

## 🚀 Installation und Setup

### Voraussetzungen

- Java 17
- Docker Desktop
- Git

### Schritt-für-Schritt Installation

#### 1. Java 17 installieren

**Ubuntu/Debian:**
```bash
sudo apt-get install openjdk-17-jre openjdk-17-jdk
```

**Java Version überprüfen:**
```bash
java -version
```

**Bei mehreren Java-Versionen die richtige auswählen:**
```bash
sudo update-alternatives --config java
```

#### 2. Docker Desktop installieren

Laden Sie Docker Desktop von der offiziellen Website herunter und installieren Sie es. Ein Docker-Account ist nicht erforderlich.

#### 3. Projekt klonen und starten

```bash
# Repository klonen
git clone <repository-url>
cd cinema-management-system

# Projekt bauen
./gradlew build

# PostgreSQL Services stoppen (falls aktiv)
sudo systemctl stop postgresql

# Docker Desktop starten, dann Anwendung ausführen
./gradlew bootrun
```

#### 4. Anwendung aufrufen

Öffnen Sie Ihren Browser und navigieren Sie zu:
```
http://localhost:8080
```

## 🎯 Funktionalitäten

### Filme verwalten
- **GET** `/film` - Alle Filme anzeigen
- **GET** `/film?titel=<titel>` - Filme nach Titel suchen
- **GET** `/film?genre=<genre>` - Filme nach Genre filtern
- **GET** `/film?genre=<genre>&titel=<titel>` - Kombinierte Suche

### Vorstellungen verwalten
- **GET** `/vorstellung` - Alle Vorstellungen anzeigen
- **GET** `/vorstellung?datum=<datum>` - Vorstellungen nach Datum
- **GET** `/vorstellung?uhrzeit=<uhrzeit>` - Vorstellungen nach Uhrzeit
- **GET** `/addVorstellung` - Formular zum Hinzufügen neuer Vorstellungen
- **POST** `/addVorstellung` - Neue Vorstellung speichern

### Kunden und Tickets
- **GET** `/kunden` - Alle Kunden anzeigen
- **GET** `/tickets` - Alle Tickets anzeigen
- **GET** `/tickets?email=<email>` - Tickets nach Kunden-E-Mail
- **GET** `/changePassword` - Passwort ändern

## 🗃️ Datenbankschema

Das System verwendet folgende Hauptentitäten:

- **Film**: filmId, titel, genre, fsk, dauer
- **Vorstellung**: vorstellungId, datum, uhrzeit, filmId
- **Kunde**: kundeId, name, email, passwort
- **Ticket**: ticketId, preis, sitzplatz, vorstellungId, kundeId

## 🔧 Entwicklung

### Neue Änderungen anwenden

Nach Änderungen an Java-Dateien:
```bash
# PostgreSQL Services stoppen
sudo systemctl stop postgresql

# Anwendung neu starten
./gradlew bootrun
```

### Häufige Probleme

**Port 5432 bereits in Verwendung:**
```bash
sudo systemctl status postgresql
sudo systemctl stop postgresql
```

**Gradle Build Probleme:**
```bash
./gradlew --stop
./gradlew build
```

## 🏗️ Architektur

Das Projekt folgt dem **MVC (Model-View-Controller)** Pattern:

- **Model**: DTO-Klassen repräsentieren Datenstrukturen
- **View**: HTML-Templates mit Thymeleaf
- **Controller**: REST-Endpoints verwalten HTTP-Requests
- **DAO Pattern**: Trennung der Datenbankzugriffe

### Wichtige Design Patterns

- **Data Transfer Object (DTO)**: Für Datenübertragung zwischen Schichten
- **Data Access Object (DAO)**: Für Datenbankoperationen
- **Dependency Injection**: Mit Spring's @Autowired

## 📝 API Endpoints

| Method | Endpoint | Beschreibung |
|--------|----------|--------------|
| GET | `/` | Startseite |
| GET | `/film` | Filme anzeigen/filtern |
| GET | `/vorstellung` | Vorstellungen anzeigen/filtern |
| GET | `/addVorstellung` | Vorstellung hinzufügen (Form) |
| POST | `/addVorstellung` | Vorstellung speichern |
| GET | `/tickets` | Tickets anzeigen |
| GET | `/changePassword` | Passwort ändern |

## 🤝 Beitragen

1. Fork das Repository
2. Erstelle einen Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Committe deine Änderungen (`git commit -m 'Add some AmazingFeature'`)
4. Push zum Branch (`git push origin feature/AmazingFeature`)
5. Öffne einen Pull Request

## 📄 Lizenz

Dieses Projekt wurde als Abschlussprojekt für den Kurs "Datenbanken: Weiterführende Konzepte" entwickelt.

## 👥 Autoren

- Entwickelt als Universitätsprojekt
- Institut für Informatik - Datenbanken und Informationssysteme

## 🆘 Support

Bei Problemen oder Fragen:
1. Überprüfe die häufigen Probleme oben
2. Suche in den Issues nach ähnlichen Problemen
3. Erstelle ein neues Issue mit detaillierter Beschreibung
