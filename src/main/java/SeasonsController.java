import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class SeasonsController implements Initializable {

    public Label test;
    public VBox vbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JSONObject jsonData;
        try {
            jsonData = Profile.getProfile().currentJSON.getJSONObject("response");

            for (int i = 0; i < jsonData.getJSONArray("episodes").length(); i++) {
                String no = jsonData.getJSONArray("episodes").getJSONObject(i).getString("episode_number");
                String name = jsonData.getJSONArray("episodes").getJSONObject(i).getString("name");
                String air_date = jsonData.getJSONArray("episodes").getJSONObject(i).getString("air_date");
                vbox.getChildren().add(new Label(no + ". " + name + " - " + air_date));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
