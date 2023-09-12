package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Reservation;
import model.Room;
import utils.ModelManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class HotelController implements Initializable {


    // Booking tab start-----------------------------------------------------------------------------------------
    @FXML
    private TableView<Room> bookingTableView;
    @FXML
    private TableColumn<Room, Integer> bookingRoomNumber;
    @FXML
    private TableColumn<Room, Integer> bookingRoomPricePerNight;
    @FXML
    private TableColumn<Room, Room.RoomType> bookingRoomType;
    @FXML
    private ChoiceBox<Room.RoomType> bookingChoiceBox;

    @FXML
    private DatePicker bookingArrival, bookingDeparture;
    private ObservableList<Room> bookingRoomsToShow;

    // Booking tab ends here ----------------------------------------------------------------------------------


    // Check-in tab starts here---------------------------------------------------------------------------------

    @FXML
    private TableView<Reservation> checkInTable;
    @FXML
    private TableColumn<Reservation, String> checkInBookerName;
    @FXML
    private TableColumn<Reservation, LocalDate> checkInArrival, checkInDeparture;
    @FXML
    private TableColumn<Reservation, Boolean> checkInIsCheckedIn;
    @FXML
    private TableColumn<Reservation, Integer> checkInRoomNumber;

    @FXML
    private TextField checkInPhoneNumber;
    private ObservableList<Reservation> checkinReservationsToShow;

    @FXML
    private CheckBox checkInCheckBox;


    // Check- in tab ends here--------------------------------------------------------------------------------------------


    private ModelManager modelManager;


    // This method is called at the very start , thus initializing everything
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modelManager = new ModelManager();
        bookingRoomsToShow = FXCollections.observableArrayList();
        checkinReservationsToShow = FXCollections.observableArrayList();
        initializeDatePickersBooking();
        initializeBookingCheckBox();
        refreshAllTables();

    }


    private void initializeCheckInTable() {
        checkInArrival.setCellValueFactory(new PropertyValueFactory<>("arrival"));
        checkInBookerName.setCellValueFactory(new PropertyValueFactory<>("bookerName"));
        checkInDeparture.setCellValueFactory(new PropertyValueFactory<>("departure"));
        checkInRoomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        checkInIsCheckedIn.setCellValueFactory(new PropertyValueFactory<>("isCheckedIn"));
        checkInTable.setItems(checkinReservationsToShow);
        checkinReservationsToShow.setAll(modelManager.loadReservationListFromFile().getReservations());
    }

    private void initializeBookingCheckBox() {
        bookingChoiceBox.setItems(FXCollections.observableArrayList(Room.RoomType.values()));
        bookingChoiceBox.setValue(Room.RoomType.SINGLE_BEDROOM);  // setting default value to single-room
    }

    private void initializeDatePickersBooking() {
        bookingArrival.setValue(LocalDate.now());     // setting default value to the TODAY and tomorrow
        bookingDeparture.setValue(LocalDate.now().plusDays(1));
    }

    private void initializeTableBooking() {

        bookingRoomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        bookingRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        bookingRoomPricePerNight.setCellValueFactory(new PropertyValueFactory<>("pricePerNight"));
        bookingTableView.setItems(bookingRoomsToShow);
        searchBooking();

    }

    @FXML
    private void searchBooking() {
        LocalDate arrivalDate = bookingArrival.getValue();
        LocalDate departureDate = bookingDeparture.getValue();
        Room.RoomType selectedRoomType = bookingChoiceBox.getValue();

        if (arrivalDate == null || departureDate == null || selectedRoomType == null) {
            Alert.alert("All fields must be filled to search");
            return;
        }

        // getting the list of room available in provided dates through ModelManager and displaying that to the GUI.
        List<Room> searched = modelManager.searchAllAvailableRooms(arrivalDate, departureDate, selectedRoomType);
        bookingRoomsToShow.setAll(searched);

    }

    @FXML
    private void bookingBook() {
        Room room = bookingTableView.getSelectionModel().getSelectedItem();
        if (room == null) {
            Alert.alert("Select a room from table to book");
            return;
        }
        // Open another window to ask for guest details

        try {
            Stage window = new Stage();
            window.setResizable(false);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("BookRoomByGuest.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            window.setScene(scene);
            BookRoomByGuestController controller = loader.getController();
            controller.initialize(room, bookingArrival.getValue(), bookingDeparture.getValue(), window);
            window.showAndWait();
            refreshAllTables();
        } catch (IOException e) {
            Alert.alert("Error : \n\n" + e.getMessage());
            e.printStackTrace();
        }


    }

    @FXML
    private void checkIn() {
        Reservation selectedItem = checkInTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert.alert("Select a reservation to check-in");
            return;
        }

        try {
            Stage window = new Stage();
            window.setResizable(false);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AddGuests.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            window.setScene(scene);
            AddGuestsController controller = loader.getController();
            controller.initialize(selectedItem, window);
            window.showAndWait();
            refreshAllTables();
        } catch (IOException e) {
            Alert.alert("Error : \n\n" + e.getMessage());
            e.printStackTrace();
        }


    }

    @FXML
    private void checkInDelete() {
        Reservation selectedItem = checkInTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert.alert("Select a reservation to delete");
            return;
        }
        modelManager.deleteReservation(selectedItem);
        Alert.alert("Removed check-in successfully");
        refreshAllTables();


    }

    private void refreshAllTables() {
        initializeTableBooking();
        initializeCheckInTable();
    }

    @FXML
    private void checkInFilter() {
        if (checkInCheckBox.isSelected()) {
            checkinReservationsToShow.setAll(modelManager.getCheckedInReservations());
        } else {
            checkinReservationsToShow.setAll(modelManager.loadReservationListFromFile().getReservations());
        }


    }

    @FXML
    private void checkOut() {
        Reservation selectedItem = checkInTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert.alert("Select a reservation to check-out");
            return;
        }
        if (!selectedItem.getIsCheckedIn()) {
            Alert.alert("The reservation is not yet checked in. \n\n Click delete if you want to remove the reservation");
            return;
        }
        try {
            Stage window = new Stage();
            window.setResizable(false);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("CheckOutReceipt.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            window.setScene(scene);
            CheckOutReceiptController controller = loader.getController();
            controller.initialize(selectedItem, window);
            window.showAndWait();
            refreshAllTables();
        } catch (IOException e) {
            Alert.alert("Error : \n\n" + e.getMessage());
            e.printStackTrace();
        }

    }

    @FXML
    private void exportRooms() {
        try {
            modelManager.exportRooms();
            Alert.alert("Exported successfully");
        } catch (FileNotFoundException e) {
            Alert.alert(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void exportReservations() {
        try {
            modelManager.exportReservations();
            Alert.alert("Exported successfully");
        } catch (FileNotFoundException e) {
            Alert.alert(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void exportBoth() {
        try {
            modelManager.exportReservations();
            modelManager.exportRooms();
            Alert.alert("Exported successfully");
        } catch (FileNotFoundException e) {
            Alert.alert(e.getMessage());
            e.printStackTrace();
        }

    }


    @FXML
    private void checkinSearch( ) {
        String phoneToSearch = checkInPhoneNumber.getText().trim();
        if (phoneToSearch == null || phoneToSearch.equals("")){
            Alert.alert("Enter phone number to search with");
            checkInPhoneNumber.clear();
            return;
        }
        List<Reservation> searched = modelManager.searchReservationByPhoneNumber(phoneToSearch);
        if (searched.size()==0)
        {
            Alert.alert("No reservation found with the given phone number");
            checkinReservationsToShow.clear();
            checkInPhoneNumber.clear();
            return;
        }
        checkinReservationsToShow.setAll(searched);

    }
}
