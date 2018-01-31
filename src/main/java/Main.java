import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Profile pro = SerializationProfile.deserialize("profile.pro");
            Profile.setNewProfile(pro);
            Lo.g("Profile loaded");
        } catch (IOException | ClassNotFoundException e) {
            Lo.g("Profile not found");
        }

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("EPISODES");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() {
        Lo.g("Exiting");
        SerializationProfile.serialize(Profile.getProfile(), "profile.pro");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
