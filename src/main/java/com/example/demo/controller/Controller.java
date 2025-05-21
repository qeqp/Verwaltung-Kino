package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

import com.example.demo.model.*;
import com.example.demo.security.CurrentUser;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RequestMapping("/")
@org.springframework.stereotype.Controller
public class Controller {
    private final JdbcTemplate jdbcTemplate;

    public Controller(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private FilmDao filmDao;

    @Autowired
    private KundeDao kundeDao;

    @Autowired
    private VorstellungDao vorstellungDao;

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private MitarbeiterDao mitarbeiterDao;

    @GetMapping // GET fuer http://localhost:8080
    public String halloWelt(Model model) {
        List<MitarbeiterDTO> mitarbeiter = mitarbeiterDao.getAllMitarbeiter();
        model.addAttribute("mitarbeiter", mitarbeiter);

        //TODO: Aufgabe 4b) Rufe die Methode auf zur Erstellung einer Liste aller Kunden und
        // uebergebe diese an das model

        return "index";
    }

    @GetMapping("/film") // Diese Annotation definiert eine GET-Methode für die URL "/film".
    public String getFilme(Model model,
                           @RequestParam(name = "titel", required = false) String titel,
                           @RequestParam(name = "genre", required = false) String genre) {
        List<FilmDTO> filme;

        if ((genre != null && !genre.isEmpty()) && (titel != null && !titel.isEmpty())) {
            // Filtern nach Genre und Titel
            filme = filmDao.getFilmeByGenreAndTitel(genre, titel);
        } else if (genre != null && !genre.isEmpty()) {
            // Filtern nur nach Genre
            filme = filmDao.getFilmeByGenre(genre);

        }
        // TODO: Aufgabe 4a: Rufe die Methode getFilmeByTitel in einer weiteren else if Anweisung auf
        else { // Gib alle Filme aus, wenn keine Filter vorhanden sind
            filme = filmDao.getAllFilme();
        }

        // Fuege eine Liste von Filmen zum Model hinzu
        model.addAttribute("filme", filme);
        model.addAttribute("selectedGenre", genre);
        model.addAttribute("selectedTitel", titel);
        return "film";
    }

    @PostMapping(path = "/film", params = "btnSubmit")
    public String goToAddFilm(Model model, FilmDTO film){
        return "redirect:/addFilm";
    }

    @GetMapping("/addFilm")
    public String fillAddFilmFormular(Model model){
        model.addAttribute("film", new FilmDTO(0, null, null, 0, 0));
        return "addFilm";
    }

    @PostMapping("/addFilm")
    public String addFilm(@ModelAttribute("film")FilmDTO film, Model model){
        filmDao.saveFilm(film);
        model.addAttribute("message", "Film successfully added!");
        return "redirect:/film";
    }

    @GetMapping("/vorstellung")
    public String getVorstellungen(Model model,
                                   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate datum,
                                   @RequestParam(required = false) @DateTimeFormat(pattern = "HH:mm") LocalTime uhrzeit){

        List<VorstellungDTO> vorstellungen = null;

        //TODO: Aufgabe 4c) passende Vorstellungen abrufen

        model.addAttribute("vorstellungen", vorstellungen);
        return "vorstellung";
    }

    @PostMapping(path = "/vorstellung", params = "btnSubmit")
    public String goToaddVorstellung(){
        return "redirect:/addVorstellung";
    }

    @GetMapping("/addVorstellung")
    public String fillVorstellungFormular(Model model){
        model.addAttribute("vorstellung", new VorstellungDTO(0, null, null, "Saal..", 0));
        return "addVorstellung";
    }

    public String addVorstellung(@ModelAttribute("vorstellung")VorstellungDTO vorstellung, Model model){
        //TODO: Aufgabe 4e (return muss auch angepasst werden)
        return "";
    }

    @GetMapping(value = "/tickets")
    public String getTickets(Model model, @RequestParam(required = false) String email){
        // TODO: Aufgabe 4f)
        return "ticket";
    }

    @PostMapping("/registrierung")
    public String postRegistrierung() {
        return "redirect:registrierung";
    }

    @GetMapping("/registrierung")
    public String registrierung() {
        return "registrierung";
    }

    @GetMapping("/registrierungGast")
    public String showRegistrationForm(Model model) {
        model.addAttribute("kunde", new KundeDTO(null, null, null, null, null, null, null, null, null));
        return "registrierungGast";
    }

    @PostMapping("/registrierungGast")
    public String postRegistrierungGast(@ModelAttribute("kunde")KundeDTO kunde, Model model) {
        kundeDao.saveKunde(kunde);
        model.addAttribute("message", "Kunde successfully added!");
        return "redirect:/";
    }

    @PostMapping("/registrierungMitarbeiter")
    public String postRegistrierungMitarbeiter() {
        return "redirect:registrierungMitarbeiter";
    }

    @GetMapping("/registrierungMitarbeiter")
    public String registrierungMitarbeiter() {
        return "registrierungMitarbeiter";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("oldPasswort") String oldPasswort,
                                 @RequestParam("newPasswort") String newPasswort,
                                 @RequestParam("confirmNewPasswort") String confirmNewPasswort,
                                 HttpSession session, RedirectAttributes redirectAttributes) {

        // Ueberpruefe ob der Nutzer Kunde ist
        String role = (String) session.getAttribute("role");
        String email = (String) session.getAttribute("email");

        if (role == null || !role.equals("kunde") || email == null) {
            redirectAttributes.addFlashAttribute("error", "Nur Kunden können ihr Passwort ändern.");
            return "redirect:/";
        }

        // Ueberpruefe of passwort und erneute passwort Eingabe uebereinstimmen
        if (!newPasswort.equals(confirmNewPasswort)) {
            redirectAttributes.addFlashAttribute("error", "Die neuen Passwörter stimmen nicht überein.");
            return "redirect:/changePassword";
        }

        // Ueberpruefe, ob altes Passwort korrekt ist
        KundeDTO kunde = kundeDao.getKundeByEmail(email);
        if (kunde == null || !kunde.passwort().equals(oldPasswort)) {
            redirectAttributes.addFlashAttribute("error", "Das alte Passwort ist falsch.");
            return "redirect:/changePassword";
        }

        // Update das passwort in der Datenbank
        kundeDao.updatePassword(email, newPasswort);

        // Fuege Nachricht hinzu
        redirectAttributes.addFlashAttribute("success", "Passwort erfolgreich geändert!");

        // Redirect zu der passenden Seite
        return "redirect:/";
    }

    @GetMapping("/changePassword")
    public String showChangePasswordForm(HttpSession session, RedirectAttributes redirectAttributes) {
        // Check ob Nutzer ein "kunde" ist
        String role = (String) session.getAttribute("role");
        String email = (String) session.getAttribute("email");

        if (role == null || !role.equals("kunde") || email == null) {
            redirectAttributes.addFlashAttribute("error", "Nur Kunden können ihr Passwort ändern.");
            return "redirect:/";
        }

        return "changePassword";
    }

    @GetMapping("/kinosaal")
    public ResponseEntity<List<KinosaalDTO>> getAllKinosaele(@CurrentUser User user){
        String query = "SELECT * FROM Kinosaal";
        List<KinosaalDTO> kinosaele = jdbcTemplate.query(query,
                (rs, rowNum) -> new KinosaalDTO(
                        rs.getString("saalname"),
                        rs.getInt("kapazitaet")
                )).stream().toList();
        return new ResponseEntity<>(kinosaele, HttpStatus.OK);
    }

    @SuppressWarnings("null")
    @PostMapping(value = "/kunde")
    public ResponseEntity<String> newAd(@RequestParam(name="email", required = true) String email,
                                        @RequestParam(name="passwort", required = true) String passwort,
                                        @RequestParam(name="vorname", required = true) String vorname,
                                        @RequestParam(name="nachname", required = true) String nachname,
                                        @RequestParam(name="geburtsdatum", required = true) LocalDate geburtsdatum,
                                        @RequestParam(name="strasse", required = true) String strasse,
                                        @RequestParam(name="hausnummer", required = true) String hausnummer,
                                        @RequestParam(name="stadt", required = true) String stadt,
                                        @RequestParam(name="plz", required = true) Integer plz) {
        int row = jdbcTemplate.update("INSERT INTO Kunde(Email, Passwort, Vorname, Nachname, Geburtsdatum, Strasse, Hausnummer, Stadt, PLZ) VALUES (?,?,?,?,?,?,?,?,?)",
                email, passwort, vorname, nachname, geburtsdatum, strasse, hausnummer, stadt, plz);

        if (row != 0) {
            Integer maxRow = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Kunde", Integer.class);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.LOCATION, "/kunde/" + (maxRow));
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "error beim einfügen");
    }

    @GetMapping("/kunde")
    public ResponseEntity<List<KundeDTO>> getAllKunde(){
        String query = "SELECT * FROM Kunde";
        List<KundeDTO> kunden = jdbcTemplate.query(query,
                (rs, rowNum) -> new KundeDTO(
                        rs.getString("email"),
                        rs.getString("passwort"),
                        rs.getString("vorname"),
                        rs.getString("nachname"),
                        rs.getDate("geburtsdatum").toLocalDate(),
                        rs.getString("strasse"),
                        rs.getString("hausnummer"),
                        rs.getString("stadt"),
                        rs.getInt("plz")
                )).stream().toList();

        return new ResponseEntity<>(kunden, HttpStatus.OK);
    }

    @PatchMapping("/kunde/{email}")
    @RolesAllowed("KUNDE")
    public ResponseEntity<?> aenderKundePasswort(@PathVariable(name = "email") String email,
//                                @RequestParam(name = "passwort", required = false) String passwort,
                                @RequestParam(name = "newPasswort", required = false) String newPasswort) {
        try {
            int row = jdbcTemplate.update("UPDATE Kunde SET Passwort = ? WHERE Email = ?", newPasswort, email);
            if (row > 0) {
                return ResponseEntity.status(204).build();
            }
        } catch (NoSuchElementException e){
            return ResponseEntity.status(404).body(new ErrorControllerAdvice.ExceptionResponse(e.getLocalizedMessage(), 404));
        } catch (DataAccessException e){
            return ResponseEntity.status(400).body(new ErrorControllerAdvice.ExceptionResponse(e.getLocalizedMessage(), 400));
        } catch (Exception e){
            return ResponseEntity.status(400).body(new ErrorControllerAdvice.ExceptionResponse(e.getLocalizedMessage(), 400));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/kunde/{email}")
    public ResponseEntity<?> deleteKunde(@PathVariable(name = "email") String email) {
        try {
            int row = jdbcTemplate.update("DELETE FROM Kunde WHERE Email = ?", email);
            if (row > 0) {
                return ResponseEntity.status(204).build();
            }
        } catch (NoSuchElementException e){
            return ResponseEntity.status(404).body(new ErrorControllerAdvice.ExceptionResponse(e.getLocalizedMessage(), 404));
        } catch (DataAccessException e) {
            return ResponseEntity.status(400).body(new ErrorControllerAdvice.ExceptionResponse(e.getLocalizedMessage(), 400));
        } catch (Exception e){
            return ResponseEntity.status(500).body(new ErrorControllerAdvice.ExceptionResponse(e.getLocalizedMessage(), 500));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

}
