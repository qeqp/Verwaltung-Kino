Abschlussprojekt: Datenbanken und Informationssysteme
Dieses Repository enthält die Lösung für das Abschlussprojekt im Kurs "Datenbanken und Informationssysteme" an der Heinrich-Heine-Universität Düsseldorf, Wintersemester 2024/2025. Das Projekt umfasst die Entwicklung eines RESTful Web Services mit JDBC zur Kommunikation mit einer PostgreSQL-Datenbank.

Inhaltsverzeichnis
Projektbeschreibung
Einrichtung und Installation
Voraussetzungen
Anwendung bauen und starten
Aufruf der Webanwendung
Projektstruktur
Implementierte Features
Fehlerbehebung
Projektbeschreibung
Ziel dieses Projekts ist die Erstellung einer Webanwendung, die Benutzern die Interaktion mit einer PostgreSQL-Datenbank ermöglicht. Die Anwendung ist als REST-API mit JDBC für die Datenbankkommunikation aufgebaut. Das bereitgestellte Relationenschema wurde für dieses Projekt leicht angepasst und vereinfacht. Gültige GET-Requests geben ein JSON-Array von JSON-Objekten zurück, wobei ein JSON-Objekt eine Entität darstellt.




Einrichtung und Installation
Voraussetzungen

Java 17: Das Projekt erfordert Java 17. 
Für Debian-basierte Systeme (z.B. Ubuntu, Kubuntu, Xubuntu, Manjaro) installieren Sie es mit:
Bash
sudo apt-get install openjdk-17-jre openjdk-17-jdk [cite: 10]
Überprüfen Sie Ihre Java-Installation:
Bash
java -version [cite: 11]
Sollten Sie mehrere Java-Versionen installiert haben, können Sie Java 17 auswählen mit:
Bash
sudo update-alternatives --config java [cite: 12]
Docker Desktop: Installieren Sie Docker Desktop. Sie benötigen keinen Account und können die Schritte zur Accounterstellung überspringen.

Anwendung bauen und starten

Projekt öffnen: Öffnen Sie das Projekt in einer IDE (IntelliJ IDEA oder VSCode werden empfohlen). Bei VSCode sollten Sie passende Plugins wie Gradle for Java, Language Support for Java(TM) by Red Hat und Extension Pack for Java installieren.

Projekt bauen: Sobald die IDE anzeigt, dass das Projekt fehlerfrei aufgesetzt wurde, können Sie es im Terminal bauen mit:
Bash
./gradlew build [cite: 17]
Sie sollten die Rückmeldung build successful erhalten. 
Probleme mit Gradle Cache: Sollten Fehlermeldungen erscheinen, löschen Sie den Cache im Terminal mit:
Bash
./gradlew --stop [cite: 19]
und bauen Sie das Projekt erneut.
Docker Desktop starten: Stellen Sie sicher, dass Docker Desktop zuerst gestartet ist.
Anwendung starten: Geben Sie im Terminal ein:
Bash
./gradlew bootrun [cite: 20]
Wenn alles funktioniert, sollten Sie im Terminal Folgendes sehen:
Application availability state ReadinessState changed to ACCEPTING_TRAFFIC
> : boot Run [cite: 21]
Probleme mit Portbelegung: Falls "Port nicht verfügbar oder Port bereits in Verwendung" erscheint, ist möglicherweise ein PostgreSQL-Service auf Port 5432 aktiv. 
Überprüfen Sie den PostgreSQL-Status (Ubuntu):
Bash
sudo systemctl status postgresql [cite: 24]
Stoppen Sie alle Instanzen:
Bash
sudo systemctl stop postgresql [cite: 24]
Führen Sie dann zuerst ./gradlew build und anschließend ./gradlew bootrun erneut aus.
Sonstige Fehlermeldungen: Suchen Sie im Internet nach einer Lösung. Fragen Sie sonst im ILIAS-Forum nach und lassen Sie sich von Kommilitonen ausschließlich bei der Installation helfen.
Änderungen an Java/PostgreSQL-Dateien: Wenn Sie Java-Dateien oder PostgreSQL-Dateien ändern, müssen Sie nur alle PostgreSQL-Services stoppen mit sudo systemctl stop postgresql und anschließend ./gradlew bootrun im Terminal erneut ausführen.
Aufruf der Webanwendung

Nachdem die Anwendung gestartet ist, rufen Sie die Webanwendung im Webbrowser auf, indem Sie die URL http://localhost:8080 besuchen. Die Anwendung muss per HTTP auf Port 8080 lauschen.


Projektstruktur
Die wichtigsten Dateien und deren Funktionalitäten sind:

src/main/java/..demo/DemoApplication.java: Hauptklasse der Anwendung.
src/main/java/..controller/Controller.java: Steuert die gesamte Webanwendung, indem festgelegt wird, welche URLs erreichbar sind und welche Aktionen ausgeführt werden sollen.

src/main/java/..model/DTO.java: Ein DTO (Data Transfer Object) ist ein einfaches Objekt, das zum Übertragen von Daten zwischen der Datenbank und der Benutzeroberfläche verwendet wird. In unserer Anwendung repräsentiert es eine Entität in der Datenbank. Die Attributnamen und Datentypen müssen entsprechend angepasst werden. Die Namen innerhalb von @JsonProperty sind die Attributnamen aus den Relationen in der schema.sql-Datei. 

Beispiel FilmDTO.java:
Java
public record FilmDTO(@JsonProperty("filmId") int filmId,
                      @JsonProperty("titel") String titel,
                      @JsonProperty("genre") String genre,
                      @JsonProperty("fsk") int fsk,
                      @JsonProperty("dauer") int dauer) {}

src/main/java/..Dao.java: Ein DAO (Data Access Object) ist ein Entwurfsmuster zur Implementierung von Datenbankzugriffen. 
Methoden wie saveFilm verwenden jdbcTemplate.update für INSERT, UPDATE, DELETE-Operationen. Parameter im SQL-Befehl werden mit ? markiert und durch die übergebenen Parameter ersetzt.


Methoden wie getFilmsByGenreAndTitel liefern eine Liste von DTO-Objekten. jdbcTemplate.query erhält einen SQL-Befehl und eine Liste von Objekten, die vom Typ des DTOs sind. Werte werden aus Spalten mit rs.getInt("columnName"), rs.getString("columnName") etc. ausgelesen.


src/main/resources/application.properties: Einstellungen wie der Port.
src/main/resources/data/data.sql: Beispieleinträge mit DML-Anweisungen.
src/main/resources/data/schema.sql: Relationenschemata mit DDL-Anweisungen. Schauen Sie bei der Bearbeitung der Aufgaben regelmäßig nach, welche Attribute die Relationen in schema.sql haben.

src/main/resources/templates/: Ordner mit HTML-Seiten. 

index.html: Startseite der Anwendung.
HTML-Seiten wie film.html werden von Controller-Methoden angezeigt (z.B. return "film").
compose.yaml: Docker-Compose-Datei.
Implementierte Features
Das Projekt umfasst die Implementierung mehrerer Funktionalitäten zur Datenbankinteraktion:

Filme nach Titel ausgeben:
FilmDao.java: Implementierung der Methode getFilmeByTitel, die eine Liste von FilmDTO-Objekten liefert.
Controller.java: Einfügen einer passenden Else-If-Anweisung in der Methode getFilme, sodass getFilmeByTitel von FilmDao.java ausgeführt wird.
Kunden auflisten:
KundenDao.java: Implementierung der Methode getAllKunden, die eine Liste von KundeDTO-Objekten liefert. Orientierung an MitarbeiterDao.java.

Controller.java: Erstellung einer Liste aller Kunden (KundeDTO-Objekte) in der Methode halloWelt, die dem Model als Attribut hinzugefügt wird.
Vorstellungen auflisten: Orientierung an FilmDao.java. 
VorstellungDao.java:
Implementierung der Methode getAllVorstellungen, die eine Liste von VorstellungDTO-Objekten liefert, um alle Vorstellungen aufzulisten.
Implementierung der Methode getVorstellungenByUhrzeit, die eine Liste von VorstellungDTO-Objekten mit der passenden Uhrzeit liefert.
Implementierung der Methode getVorstellungenByDatum, die eine Liste von VorstellungDTO-Objekten mit dem passenden Datum liefert.
Implementierung der Methode getVorstellungenByDatumAndUhrzeit, die eine Liste von VorstellungDTO-Objekten mit dem passenden Datum und der Uhrzeit liefert.
Controller.java: In der Methode getVorstellungen wird eine Liste der Vorstellungen angezeigt und dem Model als Attribut hinzugefügt. Passende If-Else-Anweisungen geben die Vorstellungen nach Datum bzw. Uhrzeit aus oder alle Vorstellungen, wenn weder Datum noch Uhrzeit angegeben wurden.

Neue Vorstellungen hinzufügen:
src/main/resources/templates/addVorstellung.html: Erstellung einer Formularseite, auf der alle Attribute einer Vorstellung eingegeben und gespeichert werden können. Beachten Sie die Attribute und Datentypen der Relation Vorstellung.

VorstellungDao.java: Implementierung der Methode saveVorstellung, die mit einem INSERT-Befehl eine neue Vorstellung in die Datenbank einfügt.
Controller.java: Implementierung eines PostMapping addVorstellung für Vorstellung. Dieses PostMapping wird ausgelöst, wenn auf der Seite localhost:8080/addVorstellung der Button "Vorstellung hinzufügen" geklickt wird. Eine neue Vorstellung wird gespeichert  und anschließend die Seite localhost:8080/vorstellung angezeigt.



Tickets nach Kunden-E-Mail ausgeben:
Controller.java: Implementierung der Methode getTickets, die eine Liste von TicketDTO-Objekten liefert. Fälle, in denen keine E-Mail angegeben wird, werden abgefangen, wobei dann alle Tickets ausgegeben werden. Folgende Informationen sollen ausgegeben werden: TicketID, Preis, Sitzplatz, VID, Email.


Kundenpasswort ändern:
KundeDao.java: Implementierung der Methode updatePassword, die einen UPDATE-Befehl implementiert. Dies ist auf der Seite localhost:8080/changePassword möglich.

Fehlerbehebung
"Port not available or Port already in use": Dies bedeutet in der Regel, dass ein anderer PostgreSQL-Dienst auf Port 5432 läuft. Stoppen Sie alle Instanzen mit sudo systemctl stop postgresql, bevor Sie die Anwendung erneut starten.
Probleme mit Gradle Cache: Wenn beim Buildvorgang Fehler auftreten, versuchen Sie, den Gradle-Cache zu leeren, indem Sie ./gradlew --stop im Terminal ausführen und das Projekt dann neu bauen.
Java-Version: Stellen Sie sicher, dass Java 17 korrekt installiert und als Standardversion ausgewählt ist. Sie können sudo update-alternatives --config java verwenden, um Java 17 auszuwählen, falls mehrere Versionen vorhanden sind.

Allgemeine Fehler: Bei anderen Fehlermeldungen suchen Sie online nach Lösungen oder nutzen Sie das ILIAS-Forum für Unterstützung durch Kommilitonen, insbesondere bei installationsbezogenen Problemen.
