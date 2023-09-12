package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Reservation;
import utils.ModelManager;

import java.time.temporal.ChronoUnit;

public class CheckOutReceiptController {
    @FXML
    private Label stayedFrom, stayedTo, roomNumber, pricePerNight, totalNightsStayed, totalPrice;
    private Stage stage;
    private Reservation reservation;


    public void initialize(Reservation reservation, Stage stage) {
        this.reservation = reservation;
        this.stage = stage;
        initializeLabels();

    }

    private void initializeLabels() {
        stayedFrom.setText(reservation.getArrival().toString());
        stayedTo.setText(reservation.getDeparture().toString());
        roomNumber.setText(reservation.getRoomNumber() + "");
        pricePerNight.setText(reservation.getRoom().getPricePerNight() + "");
        totalNightsStayed.setText(ChronoUnit.DAYS.between(reservation.getArrival(), reservation.getDeparture()) + "");
        totalPrice.setText((reservation.getRoom().getPricePerNight() * ChronoUnit.DAYS.between(reservation.getArrival(), reservation.getDeparture())) + "");
    }


    @FXML
    private void cancel() {
        stage.close();
    }

    @FXML
    private void finish() {
        ModelManager modelManager = new ModelManager();
        modelManager.deleteReservation(reservation);
        stage.close();
        Alert.alert("Checked out successfully ...\n\n Reservation has been permanently removed from the system");
    }
}
