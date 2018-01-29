import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Profile {
    public JSONObject currentJSON;
    public ArrayList<Element> favorites;

    private static Profile profile = new Profile();

    private Profile() {
        favorites = new ArrayList<>();
    }

    public static Profile getProfile() {
        return profile;
    }

    public void openWindow(Event event, String layout) {
        Parent root;
        try {
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
            root = FXMLLoader.load(getClass().getResource(layout));
            Scene scene = new Scene(root);
            dialog.setScene(scene);
            dialog.show();
        } catch (IOException e) {
            e.printStackTrace();
            Lo.g("Problem ze zmianą okna.");
        }
    }

    public boolean hasInFavs(int id) {
        for (Element item : favorites) {
            if (item.getId() == id) return true;
        }
        return false;
    }

    public void addToFavs(Element item) {
        favorites.add(item);
    }

    public void removeFromFavs(int id) {
        for (Element item : favorites) {
            if (item.getId() == id) Platform.runLater(() -> favorites.remove(item));
        }
    }
}
