package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Reservation implements Serializable {
    private LocalDate arrival, departure;
    private final List<Guest> allGuests;  // The one who books will be at the 0th index of this list
    private Room room;
    private boolean isCheckedIn;

    public Reservation(LocalDate arrival, LocalDate departure, Guest booker, Room room) {
        this.arrival = arrival;
        this.departure = departure;
        allGuests = new ArrayList<>();
        allGuests.add(booker);
        this.room = room;
    }


    public void setCheckedIn(boolean checkedIn) {
        isCheckedIn = checkedIn;
    }

    public boolean getIsCheckedIn() {
        return isCheckedIn;
    }

    public LocalDate getArrival() {
        return arrival;
    }

    public void setArrival(LocalDate arrival) {
        this.arrival = arrival;
    }

    public LocalDate getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDate departure) {
        this.departure = departure;
    }

    public Guest getBooker() {
        return allGuests.get(0);
    }


    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Guest> getAllGuests() {
        return allGuests;
    }

    // Used by tableview from JAVAFX--DO NOT DELETE THE METHOD
    public int getRoomNumber() {
        return room.getRoomNumber();
    }

    // Used by tableview from JAVAFX-- DO NOT delete the method
    public String getBookerName() {
        return allGuests.get(0).getFirstname() + " " + allGuests.get(0).getLastname();
    }

    /**
     * If the object is not a Reservation, return false. If it is, check if the first guest, arrival, departure, and room
     * are equal
     *
     * @param obj The object to compare to.
     * @return The hashcode of the object.
     */
    @Override
    public boolean equals(Object obj) {                     //Equals method
        if (!(obj instanceof Reservation)) return false;
        Reservation reservation = (Reservation) obj;

        return reservation.getAllGuests().get(0).equals(allGuests.get(0)) && reservation.arrival.equals(arrival) && reservation.departure.equals(departure) && reservation.room.equals(room);
    }
}
