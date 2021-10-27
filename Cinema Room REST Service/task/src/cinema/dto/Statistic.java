package cinema.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Statistic {
    @JsonProperty("number_of_available_seats")
    private final Long seats;
    @JsonProperty("number_of_purchased_tickets")
    private final Long tickets;
    @JsonProperty("current_income")
    private final Long income;

    public Statistic(Long seats, Long tickets, Long income) {
        this.seats = seats;
        this.tickets = tickets;
        this.income = income;
    }

    public Long getSeats() {
        return seats;
    }

    public Long getTickets() {
        return tickets;
    }

    public Long getIncome() {
        return income;
    }
}
