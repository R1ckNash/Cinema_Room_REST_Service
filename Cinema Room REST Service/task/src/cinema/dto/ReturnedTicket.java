package cinema.dto;

import cinema.models.Seat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ReturnedTicket {
  @JsonProperty("returned_ticket")
  private final Map<String, Integer> returnedTicket;

  public ReturnedTicket(Seat seat) {
    this.returnedTicket =
        Map.of("row", seat.getRow(),
               "column", seat.getColumn(),
               "price", seat.getPrice());
  }

  public Map<String, Integer> getReturnedTicket() {
    return returnedTicket;
  }
}
