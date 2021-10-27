package cinema.dto;

import cinema.models.Seat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Seats {
    @JsonProperty("available_seats")
    private final List<Seat> availableSeats;
    @JsonProperty("total_rows")
    private final int totalRows;
    @JsonProperty("total_columns")
    private final int totalColumns;

    public Seats(List<Seat> availableSeats, int totalRows, int totalColumns) {
        this.availableSeats = availableSeats;
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }
}
