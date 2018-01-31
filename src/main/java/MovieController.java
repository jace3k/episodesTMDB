import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieController implements Initializable {

    public Label title;
    public ImageView image;
    public Label plot;
    public Label genre;
    public Label budget;
    public Label release_date;
    public Label vote_average;
    public Label production_countries;
    public int id;

    public Button add_to_fav;
    public Button del_from_fav;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JSONObject jsonData;
        try {
            jsonData = Profile.getProfile().currentJSON.getJSONObject("response");

            if (jsonData.has("backdrop_path")) image.setImage(new Image(RequestApi.base_url + RequestApi.size + jsonData.getString("backdrop_path")));
            else image.setImage(new Image(getClass().getResource("no_img.jpg").toString()));

            if (jsonData.has("title")) title.setText(jsonData.getString("title"));

            StringBuilder genres = new StringBuilder();
            if (jsonData.has("genres")) {
                for (int i = 0; i < jsonData.getJSONArray("genres").length(); i++) {
                    genres.append(jsonData.getJSONArray("genres").getJSONObject(i).getString("name")).append(" ");
                }
                genre.setText(genres.toString());
            }

            if (jsonData.has("overview")) plot.setText(jsonData.getString("overview"));
            plot.setWrapText(true);

            if (jsonData.has("budget")) budget.setText("$ " + jsonData.getString("budget"));

            genres = new StringBuilder();
            if (jsonData.has("production_countries")) {
                for (int i = 0; i < jsonData.getJSONArray("production_countries").length(); i++) {
                    genres.append(jsonData.getJSONArray("production_countries").getJSONObject(i).getString("name")).append(" ");
                }
                production_countries.setText(genres.toString());
            }

            if (jsonData.has("release_date")) release_date.setText(jsonData.getString("release_date"));

            if (jsonData.has("vote_average")) vote_average.setText(jsonData.getString("vote_average"));

            if (jsonData.has("id")) id = jsonData.getInt("id");

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
            Profile.getProfile().addToFavs(new Element(title.getText(), id, "FILM"));
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
