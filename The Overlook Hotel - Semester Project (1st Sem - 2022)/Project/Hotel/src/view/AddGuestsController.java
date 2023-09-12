package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Guest;
import model.Reservation;
import utils.ModelManager;

import java.util.ArrayList;
import java.util.List;

public class AddGuestsController {
    private Stage stage;
    private Reservation reservationToCheckIn;

    @FXML
    private TextField firstName, lastName, nationality, phone;
    private List<Guest> guestsToAdd;


    public void initialize(Reservation reservation, Stage stage) {
        this.stage = stage;
        this.reservationToCheckIn = reservation;
        guestsToAdd = new ArrayList<>();

    }


    public void cancel() {
        clearTextFields();
        guestsToAdd.clear();
        stage.close();

    }

    public void checkIn() {
        ModelManager modelManager = new ModelManager();
        modelManager.checkIn(reservationToCheckIn);
        clearTextFields();
        stage.close();
        Alert.alert("Checked-in sucessfully");

    }

    public void add() {
        String firstname = firstName.getText().trim();
        String lastname = lastName.getText().trim();
        String nation = nationality.getText().trim();
        String ph = phone.getText().trim();
        Guest guest = new Guest(firstname, lastname, nation, ph);
        reservationToCheckIn.getAllGuests().add(guest);
        clearTextFields();
        Alert.alert("Guest " + firstname + " " + lastname + " added to the reservation.\n Add more or check-in");

    }

    private void clearTextFields() {
        firstName.clear();
        lastName.clear();
        nationality.clear();
        phone.clear();
    }
}
