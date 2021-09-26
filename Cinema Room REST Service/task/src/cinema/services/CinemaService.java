package cinema.services;

import cinema.exceptions.CinemaException;
import cinema.exceptions.CinemaPasswordException;
import cinema.models.Cinema;
import cinema.models.Seat;
import cinema.models.Ticket;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class CinemaService {
  private final Cinema cinema;

  public CinemaService() {
    this.cinema = new Cinema();
  }

  public Cinema getCinema() {
    return cinema;
  }

  public Ticket purchase(Seat seat) throws CinemaException {
    Seat chosenSeat =
        cinema.getAvailableSeats().stream()
            .filter(s -> s.equals(seat))
            .findAny()
            .orElseThrow(
                () -> new CinemaException("The number of a row or a column is out of bounds!"));
    if (!chosenSeat.isAvailable()) {
      throw new CinemaException("The ticket has been already purchased!");
    }
    chosenSeat.setAvailable(false);
    cinema.getPurchasedTickets().add(new Ticket(UUID.randomUUID().toString(), chosenSeat));
    return cinema.getPurchasedTickets().stream()
        .filter(s -> s.getTicket().equals(chosenSeat))
        .findAny()
        .orElse(null);
  }

  public Map<String, Seat> returned(Ticket returnedTicket) throws CinemaException {
    Ticket ticket =
        cinema.getPurchasedTickets().stream()
            .filter(s -> Objects.equals(s.getToken(), returnedTicket.getToken()))
            .findAny()
            .orElseThrow(() -> new CinemaException("Wrong token!"));
    ticket.getTicket().setAvailable(true);
    cinema.getPurchasedTickets().remove(ticket);
    return Map.of("returned_ticket", ticket.getTicket());
  }

  public Map<String, Long> statistic(String password) throws CinemaException {
    if (!Objects.equals(password, "super_secret")) {
      throw new CinemaPasswordException("The password is wrong!");
    }
    long currentIncome =
        cinema.getPurchasedTickets().stream().mapToLong(s -> s.getTicket().getPrice()).sum();
    long numberOfAvailableSeats =
        cinema.getAvailableSeats().stream().filter(Seat::isAvailable).count();
    long numberOfPurchasedTickets =
        cinema.getAvailableSeats().stream().filter(s -> !s.isAvailable()).count();

    return Map.of(
        "current_income",
        currentIncome,
        "number_of_available_seats",
        numberOfAvailableSeats,
        "number_of_purchased_tickets",
        numberOfPurchasedTickets);
  }
}
