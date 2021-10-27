package cinema.services;

import cinema.dto.ReturnedTicket;
import cinema.dto.Seats;
import cinema.dto.Statistic;
import cinema.exceptions.CinemaException;
import cinema.exceptions.CinemaPasswordException;
import cinema.Cinema;
import cinema.models.Seat;
import cinema.models.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CinemaService {
  private final Cinema cinema;

  @Autowired
  public CinemaService() {
    this.cinema = new Cinema();
  }

  public Seats getSeats() {
    return new Seats(cinema.getAvailableSeats(), cinema.getTotalRows(), cinema.getTotalColumns());
  }

  public Optional<Ticket> purchase(Seat seat) throws CinemaException {
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
        .findAny();
  }

  public ReturnedTicket returned(Ticket returnedTicket) throws CinemaException {
    Ticket ticket =
        cinema.getPurchasedTickets().stream()
            .filter(s -> Objects.equals(s.getToken(), returnedTicket.getToken()))
            .findAny()
            .orElseThrow(() -> new CinemaException("Wrong token!"));
    ticket.getTicket().setAvailable(true);
    cinema.getPurchasedTickets().remove(ticket);
    return new ReturnedTicket(ticket.getTicket());
  }

  public Statistic statistic(String password) throws CinemaException {
    if (!Objects.equals(password, "super_secret")) {
      throw new CinemaPasswordException("The password is wrong!");
    }
    long currentIncome =
        cinema.getPurchasedTickets().stream().mapToLong(s -> s.getTicket().getPrice()).sum();
    long numberOfAvailableSeats =
        cinema.getAvailableSeats().stream().filter(Seat::isAvailable).count();
    long numberOfPurchasedTickets =
        cinema.getAvailableSeats().stream().filter(s -> !s.isAvailable()).count();
     return new Statistic(numberOfAvailableSeats, numberOfPurchasedTickets, currentIncome);
  }
}
