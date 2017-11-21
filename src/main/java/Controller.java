import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public HBox moviesBox;
    public HBox tvBox;
    public VBox searchBox;
    public TextField searchField;
    public Button searchButton;

    private RequestApi requestApi;

    public void initialize(URL location, ResourceBundle resources) {
        requestApi = new RequestApi();
        Platform.runLater(()-> {
            discover("https://api.themoviedb.org/3/discover/movie?&language=pl-PL&sort_by=popularity.desc&include_adult=false&include_video=false&page=1");
            discoverTV("https://api.themoviedb.org/3/discover/tv?language=pl-PL&sort_by=popularity.desc&page=1&include_null_first_air_dates=false");
        });

        searchButton.setOnAction(event -> searchElements());
    }

    private void searchElements() {
        Platform.runLater(()-> {
            ArrayList<SearchElement> searchElements = requestApi.getSearchResults(searchField.getText());
            searchBox.getChildren().clear();
            searchBox.getChildren().addAll(searchElements);
        });
    }

    private void discover(String address, boolean isMovie) {
        ArrayList<HomeElement> homeElements = requestApi.getDiscover(address);

        for(HomeElement element : homeElements) {
            if(isMovie) Platform.runLater(()->addMovieToBox(element));
            else Platform.runLater(()->addTVToBox(element));
        }
    }

    private void discover(String address) {
        discover(address, true);
    }

    private void discoverTV(String address){
        discover(address, false);
    }

    private void addMovieToBox(HomeElement element) {
        moviesBox.getChildren().add(element);
    }

    private void addTVToBox(HomeElement element) {
        tvBox.getChildren().add(element);
    }
}
