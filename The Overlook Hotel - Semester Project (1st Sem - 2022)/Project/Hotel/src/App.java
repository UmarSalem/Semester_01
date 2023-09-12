import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Booking software");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/Hotel.fxml")); //We built this class to load the files from FXML...
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }
}
