package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReservationList implements Serializable {

    private final List<Reservation> reservations; //Variable Declaration

    public ReservationList() {
        reservations = new ArrayList<>(); // Constructor
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);  //Setter
    }

    public List<Reservation> getReservations() {
        return reservations; //Getter
    }
}
