import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class TVController implements Initializable {

    public ComboBox seasons_box;
    public Button season_button;
    public ImageView image;
    public Label title;
    public Label plot;
    public Label genre;
    public Label first_date;
    public Label last_date;
    public Label vote_average;

    private int id;
    public Button add_to_fav;
    public Button del_from_fav;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JSONObject jsonData;
        try {
            jsonData = Profile.getProfile().currentJSON.getJSONObject("response");

            id = jsonData.getInt("id");

            if (jsonData.has("backdrop_path")) image.setImage(new Image(RequestApi.base_url + RequestApi.size + jsonData.getString("backdrop_path")));
            else image.setImage(new Image(getClass().getResource("no_img.jpg").toString()));

            if (jsonData.has("name")) title.setText(jsonData.getString("name"));

            if (jsonData.has("overview")) plot.setText(jsonData.getString("overview"));
            plot.setWrapText(true);

            if (jsonData.has("first_air_date")) first_date.setText(jsonData.getString("first_air_date"));

            if (jsonData.has("last_air_date")) last_date.setText(jsonData.getString("last_air_date"));

            if (jsonData.has("vote_average")) vote_average.setText(jsonData.getString("vote_average"));


            if (jsonData.has("number_of_seasons"))
                for (int i = 1; i <= jsonData.getInt("number_of_seasons"); i++)
                    //noinspection unchecked
                    seasons_box.getItems().add(i);

            StringBuilder genres = new StringBuilder();
            if (jsonData.has("genres")) {
                for (int i = 0; i < jsonData.getJSONArray("genres").length(); i++) {
                    genres.append(jsonData.getJSONArray("genres").getJSONObject(i).getString("name")).append(" ");
                }
                genre.setText(genres.toString());
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        season_button.setOnAction(event -> {
            Profile.getProfile().currentJSON = RequestApi.getSeasonDetails(id, (int) seasons_box.getValue());
            Profile.getProfile().openWindow(event, "seasons.fxml");

        });

        //noinspection Duplicates
        if (Profile.getProfile().hasInFavs(id)) {
            add_to_fav.setVisible(false);
            del_from_fav.setVisible(true);
        } else {
            add_to_fav.setVisible(true);
            del_from_fav.setVisible(false);
        }

        //noinspection Duplicates
        add_to_fav.setOnAction(event -> {
            Profile.getProfile().addToFavs(new Element(title.getText(), id, "tv"));
            add_to_fav.setVisible(false);
            del_from_fav.setVisible(true);
            FavsRefresher.refresh();
        });

        //noinspection Duplicates
        del_from_fav.setOnAction(event -> {
            Profile.getProfile().removeFromFavs(id);
            del_from_fav.setVisible(false);
            add_to_fav.setVisible(true);
            FavsRefresher.refresh();
        });

    }
}
