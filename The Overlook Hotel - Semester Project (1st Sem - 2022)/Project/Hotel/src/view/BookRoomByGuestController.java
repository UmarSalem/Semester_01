package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Guest;
import model.Reservation;
import model.Room;
import utils.ModelManager;

import java.time.LocalDate;

public class BookRoomByGuestController {

    private Room roomToBook;
    private ModelManager modelManager;
    private LocalDate arrival, departure;
    @FXML
    private TextField firstname, lastname, nationality, phoneNumber;
    private Stage stage;


    public void initialize(Room room, LocalDate arrival, LocalDate departure, Stage stage) {
        roomToBook = room;
        this.arrival = arrival;
        this.departure = departure;
        modelManager = new ModelManager();
        this.stage = stage;

    }

    @FXML
    private void book() {
        String firstName = firstname.getText().trim();
        String lastName = lastname.getText().trim();
        String nationalityGuest = nationality.getText().trim();
        String phone = phoneNumber.getText().trim();

        if (firstName == null || lastName == null || nationalityGuest == null || phone == null) {
            Alert.alert("All fields must me entered");
            return;
        }
        if (firstName.equals("") || lastName.equals("") || nationalityGuest.equals("") || phone.equals("")) {
            Alert.alert("All fields must be entered");
            return;
        }

        Guest guest = new Guest(firstName, lastName, nationalityGuest, phone);
        Reservation reservation = new Reservation(arrival, departure, guest, roomToBook);
        modelManager.addReservation(reservation);
        stage.close();
        Alert.alert("Room has been booked successfully");

    }

    @FXML
    private void cancel() {
        stage.close();
    }
}
