package cinema.controllers;

import cinema.models.Cinema;
import cinema.services.CinemaService;
import cinema.models.Seat;
import cinema.models.Ticket;
import cinema.exceptions.CinemaException;
import cinema.exceptions.CinemaPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CinemaController {
  private final CinemaService cinemaService = new CinemaService();

  @GetMapping("/seats")
  public ResponseEntity<Cinema> getSeats() {
    return ResponseEntity.ok(cinemaService.getCinema());
  }

  @PostMapping("/purchase")
  public ResponseEntity<Ticket> purchase(@RequestBody Seat seat) {
    return ResponseEntity.ok(cinemaService.purchase(seat));
  }

  @PostMapping("/return")
  public ResponseEntity<Map<String, Seat>> returned(@RequestBody Ticket ticket) {
    return ResponseEntity.ok(cinemaService.returned(ticket));
  }

  @PostMapping("/stats")
  public ResponseEntity<Map<String, Long>> statistic(
      @RequestParam(value = "password", required = false) String password) {
    return ResponseEntity.ok(cinemaService.statistic(password));
  }

  @ExceptionHandler({CinemaException.class, CinemaPasswordException.class})
  public ResponseEntity<Map<String, String>> handler(RuntimeException exception) {
    Map<String, String> exceptionMap = new HashMap<>();
    exceptionMap.put("error", exception.getMessage());
    ResponseEntity.BodyBuilder bodyBuilder;
    if (exception instanceof CinemaException) {
      bodyBuilder = ResponseEntity.status(HttpStatus.BAD_REQUEST);
    } else {
      bodyBuilder = ResponseEntity.status(HttpStatus.UNAUTHORIZED);
    }
    return bodyBuilder.body(exceptionMap);
  }
}
