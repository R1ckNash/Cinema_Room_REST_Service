package cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

public class Cinema {
  private static final int TOTAL_ROWS = 9;
  private static final int TOTAL_COLUMNS = 9;
  private final List<Seat> availableSeats;
  @JsonIgnore
  private final List<Ticket> purchasedTickets;

  public Cinema() {
    availableSeats = new ArrayList<>();
    purchasedTickets = new ArrayList<>();
    for (int row = 1; row <= TOTAL_ROWS; row++) {
      for (int col = 1; col <= TOTAL_COLUMNS; col++) {
        Seat seat = new Seat(row, col);
        seat.setPrice(row <= 4 ? 10 : 8);
        availableSeats.add(seat);
      }
    }
  }

  public int getTotalRows() {
    return TOTAL_ROWS;
  }

  public int getTotalColumns() {
    return TOTAL_COLUMNS;
  }

  public List<Seat> getAvailableSeats() {
    return availableSeats;
  }

  public List<Ticket> getPurchasedTickets() {
    return purchasedTickets;
  }
}
