import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    public Button prev_page;
    public Button next_page;
    public Label current_page;
    public Label total_pages;
    public VBox fav_list;
    public Button refresh;
    private int page;
    private int totalPages;

    public void initialize(URL location, ResourceBundle resources) {
        prev_page.setDisable(true);
        next_page.setDisable(true);
        page = 1;
        totalPages = 1;
        Platform.runLater(()-> {
            discover();
            discoverTV();
        });
        next_page.setOnAction(event -> {
            page++;
            prev_page.setDisable(false);
            searchElements();
        });

        prev_page.setOnAction(event -> {
            page--;
            if (page == 1) prev_page.setDisable(true);
            searchElements();
        });

        searchButton.setOnAction(event -> {
            page = 1;
            searchElements();

        });

        refreshFavs();
        refresh.setOnAction(event -> refreshFavs());
    }

    private void searchElements() {
        Platform.runLater(()-> {
            ArrayList<Object> elementsAndTotal = RequestApi.search(searchField.getText(), page);
            ArrayList<SearchElement> searchElements = (ArrayList<SearchElement>) elementsAndTotal.get(0);
            totalPages = (int) elementsAndTotal.get(1);
            searchBox.getChildren().clear();
            searchBox.getChildren().addAll(searchElements);
            current_page.setText(page + "");
            total_pages.setText(totalPages + "");
            if (totalPages > page) next_page.setDisable(false);
            if (page == totalPages) next_page.setDisable(true);
        });
    }

    private void discover(String address, boolean isMovie) {
        ArrayList<HomeElement> homeElements = RequestApi.discover(address, isMovie);

        for(HomeElement element : homeElements) {
            if(isMovie) Platform.runLater(()->addMovieToBox(element));
            else Platform.runLater(()->addTVToBox(element));
        }
    }

    private void discover() {
        discover("https://api.themoviedb.org/3/discover/movie?&language=pl-PL&sort_by=popularity.desc&include_adult=false&include_video=false&page=1", true);
    }

    private void discoverTV(){
        discover("https://api.themoviedb.org/3/discover/tv?language=pl-PL&sort_by=popularity.desc&page=1&include_null_first_air_dates=false", false);
    }

    private void addMovieToBox(HomeElement element) {
        moviesBox.getChildren().add(element);
    }

    private void addTVToBox(HomeElement element) {
        tvBox.getChildren().add(element);
    }

    public void refreshFavs() {
        Platform.runLater(() -> {
            fav_list.getChildren().removeAll(fav_list.getChildren());
            for (Element item : Profile.getProfile().favorites) {
                fav_list.getChildren().add(new Label(item.getName() + " - " + item.getType() + " - " + item.getId()));
            }
        });
    }
}
