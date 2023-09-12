package utils;

import model.Reservation;
import model.ReservationList;
import model.Room;
import model.RoomList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ModelManager implements Serializable {
    private final String ROOM_FILE = "Rooms.bin";
    private final String RESERVATION_FILE = "Reservations.bin";

    private final String ROOM_XML_FILE = "RoomsXML.txt";
    private final String RESERVATION_XML_FILE = "ReservationsXML.txt";


    /**
     * Save the room list to a binary file.
     *
     * @param roomList The RoomList object that you want to save.
     */
    public void saveRoomListToFile(RoomList roomList) {
        try {
            MyFileHandler.writeToBinaryFile(ROOM_FILE, roomList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * It loads all the room list from the binary file.
     *
     * @return A RoomList object.
     */
    public RoomList loadRoomListFromFile() {
        try {
            RoomList roomList = (RoomList) MyFileHandler.readFromBinaryFile(ROOM_FILE);
            return roomList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This function saves the reservation list to a binary file.
     *
     * @param reservationList The ReservationList object that you want to save.
     */
    public void saveReservationListToFile(ReservationList reservationList) {
        try {
            MyFileHandler.writeToBinaryFile(RESERVATION_FILE, reservationList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * It tries to read the reservation list from a file. If it fails, it returns null
     *
     * @return ReservationList
     */
    public ReservationList loadReservationListFromFile() {
        try {
            return (ReservationList) MyFileHandler.readFromBinaryFile(RESERVATION_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * > This function returns a list of rooms that are available between the given dates and of the given room type
     *
     * @param arrival   The date the guest is arriving.
     * @param departure The date the guest is leaving the hotel.
     * @param roomType  The type of room you want to search for.
     * @return A list of rooms that are available between the given dates.
     */
    public List<Room> searchAllAvailableRooms(LocalDate arrival, LocalDate departure, Room.RoomType roomType) {
        List<Reservation> reservationBetweenDates = getAllReservationsBetweenDates(arrival, departure);
        RoomList roomList = loadRoomListFromFile();

        List<Room> roomsToReturn = new ArrayList<>();

        for (Room room : roomList.getRooms()
        ) {
            if (!(room.getRoomType().equals(roomType))) continue; // if the room type doesn't match no need to go ahead.
            boolean isBooked = false;
            for (Reservation reservation : reservationBetweenDates
            ) {
                if (reservation.getRoom().equals(room)) {
                    isBooked = true;
                    break;
                }
            }
            if (!isBooked) roomsToReturn.add(room);

        }

        return roomsToReturn;
    }

    /**
     * > This function takes two LocalDate objects as parameters and returns a list of reservations that have an arrival
     * date or departure date that is equal to one of the parameters or is between the two parameters
     *
     * @param arrival   the date the user wants to arrive
     * @param departure the date the guest is leaving
     * @return A list of reservations
     */
    private List<Reservation> getAllReservationsBetweenDates(LocalDate arrival, LocalDate departure) {
        ReservationList reservationList = loadReservationListFromFile();
        List<Reservation> reservations = new ArrayList<>();
        for (Reservation reservation : reservationList.getReservations()
        ) {
            if (arrival.isEqual(reservation.getArrival()) || arrival.isEqual(reservation.getDeparture())) {
                reservations.add(reservation);

            } else if (departure.isEqual(reservation.getArrival()) || departure.isEqual(reservation.getDeparture())) {
                reservations.add(reservation);
            } else if (arrival.isAfter(reservation.getArrival()) && arrival.isBefore(reservation.getDeparture())) {
                reservations.add(reservation);
            } else if (departure.isAfter(reservation.getArrival()) && departure.isBefore(reservation.getDeparture())) {
                reservations.add(reservation);
            }
        }
        return reservations;
    }


    /**
     * Add a reservation to the reservation list and save the reservation list to file.
     *
     * @param reservation The reservation object to be added to the list.
     */
    public void addReservation(Reservation reservation) {
        ReservationList reservationList = loadReservationListFromFile();
        reservationList.addReservation(reservation);
        saveReservationListToFile(reservationList);
    }

    /**
     * "Check in a reservation by loading the reservation list from file, looping through the reservations, checking if the
     * reservation to check in is in the list, setting the reservation to checked in, and saving the reservation list to
     * file."
     * <p>
     * The function is doing too many things. It's doing the following:
     * <p>
     * * Loading the reservation list from file
     * * Looping through the reservations
     * * Checking if the reservation to check in is in the list
     * * Setting the reservation to checked in
     * * Saving the reservation list to file
     *
     * @param reservationToCheckIn The reservation to check in.
     */
    public void checkIn(Reservation reservationToCheckIn) {
        ReservationList reservationList = loadReservationListFromFile();

        for (Reservation reservation : reservationList.getReservations()) {
            if (reservation.equals(reservationToCheckIn)) {
                reservation.setCheckedIn(true);
                break;
            }
        }

        saveReservationListToFile(reservationList);
    }

    /**
     * It loads the reservation list from the file, loops through the reservations, and if the reservation is equal to the
     * selected item, it removes it from the list
     *
     * @param selectedItem The reservation that is selected in the table view.
     */
    public void deleteReservation(Reservation selectedItem) {
        ReservationList reservationList = loadReservationListFromFile();

        for (Reservation reservation : reservationList.getReservations()) {

            if (reservation.equals(selectedItem)) {
                reservationList.getReservations().remove(reservation);
                break;
            }
        }
        saveReservationListToFile(reservationList);

    }

    /**
     * "Get all the reservations that are checked in."
     * <p>
     * The function is a little more complicated than that, but that's the gist of it
     *
     * @return A list of reservations that are checked in.
     */
    public List<Reservation> getCheckedInReservations() {
        ReservationList reservationList = loadReservationListFromFile();
        List<Reservation> reservations = new ArrayList<>();

        for (Reservation reservation : reservationList.getReservations()) {
            if (reservation.getIsCheckedIn()) {
                reservations.add(reservation);
            }
        }
        return reservations;
    }

    /**
     * The function takes the roomList object and writes it to the ROOM_XML_FILE
     */
    public void exportRooms() throws FileNotFoundException {
        RoomList roomList = loadRoomListFromFile();
        String appendRooms = "";

        FileHandlerXML.writeToTextFile(ROOM_XML_FILE, "<?xml version=\"1.0\" encoding =\"UTF-8\"?>");
        FileHandlerXML.appendToTextFile(ROOM_XML_FILE, "<roomList>");

        for (Room room : roomList.getRooms()) {
            appendRooms += "\n<room>\n   <roomNumber>" + room.getRoomNumber() + "</roomNumber>"
                    + "\n   <roomType>" + room.getRoomType() + "</roomType>"
                    + "\n   <pricePerNight>" + room.getPricePerNight() + "</pricePerNight>"
                    + "\n    <isCheckedIn>" + room.getIsCheckedIn() + "</isCheckedIn>\n</room>";
        }
        FileHandlerXML.appendToTextFile(ROOM_XML_FILE, appendRooms);
        FileHandlerXML.appendToTextFile(ROOM_XML_FILE, "</roomList>");

    }

    /**
     * This function takes the reservation list from the file and appends it to the reservation XML file
     */
    public void exportReservations() throws FileNotFoundException {
        ReservationList reservationList = loadReservationListFromFile();
        String appendReservations = "";
        FileHandlerXML.writeToTextFile(RESERVATION_XML_FILE, "<?xml version=\"1.0\" encoding =\"UTF-8\"?>");
        FileHandlerXML.appendToTextFile(RESERVATION_XML_FILE, "<reservationList>");

        for (Reservation reservation : reservationList.getReservations()) {
            appendReservations += "\n<reservation>\n   <arrival>" + reservation.getArrival() + "</arrival>"
                    + "\n   <departure>" + reservation.getDeparture() + "</departure>"
                    + "\n   <roomNumber>" + reservation.getRoomNumber() + "</roomNumber>"
                    + "\n    <isCheckedIn>" + reservation.getIsCheckedIn() + "</isCheckedIn>\n</reservation>";

        }
        FileHandlerXML.appendToTextFile(RESERVATION_XML_FILE, appendReservations);
        FileHandlerXML.appendToTextFile(RESERVATION_XML_FILE, "</reservationList>");


    }

    /**
     * > This function searches for a reservation by phone number
     *
     * @param phoneToSearch The phone number to search for.
     * @return A list of reservations
     */
    public List<Reservation> searchReservationByPhoneNumber(String phoneToSearch) {
        ReservationList reservationList = loadReservationListFromFile();
        List<Reservation> reservationToReturn = new ArrayList<>();

        for (Reservation reservation : reservationList.getReservations()) {
            if (reservation.getBooker().getPhoneNumber().equalsIgnoreCase(phoneToSearch)) {
                reservationToReturn.add(reservation);
            }

        }

        return reservationToReturn;
    }
}
