import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Objects;

public class SearchElement extends BorderPane {
    Label name;
    Label date;
    Label genres;
    Label mediaType;
    Label rate;
    int id;
    ImageView imageView;
    Image image;
    String imgUrl;

    public SearchElement(String name, String date, String mediaType, double rate, int id, String imgUrl) {
        this.name = new Label(name);
        this.date = new Label(date);
        this.mediaType = new Label(mediaType);
        this.rate = new Label(rate + "");
        this.id = id;
        this.imgUrl = imgUrl;

        setMouseEvents();
        setup();
    }

    public SearchElement(String errMsg) {
        this.name = new Label(errMsg);
        this.date = new Label("-");
        this.mediaType = new Label("-");
        this.rate = new Label("-");
        this.id = 0;
        this.imgUrl = "";

        setMouseEvents();
        setup();
    }

    private void setup() {
        String imgUrl;
        if(Objects.equals(this.imgUrl, "")) imgUrl = "no_img.jpg";
        else imgUrl = RequestApi.base_url + RequestApi.size + this.imgUrl;

        createImage(imgUrl);

        name.setFont(new Font(25));

        setPadding(new Insets(10, 10, 10, 10));
        setMargin(this, new Insets(20, 20, 20, 20));

        setPrefSize(1210, 200);
        setLeft(imageView);
        setCenter(name);
        setRight(date);

        BorderPane bottomPane = new BorderPane();
        bottomPane.setCenter(genres);
        bottomPane.setRight(mediaType);
        setBottom(bottomPane);

        setStyle("-fx-border-color: black;");

        //// TODO: klikajace elementy w profile
        //// TODO: kalendarz gugla - eksport
        //// TODO: serializacja
    }

    private void createImage(String url) {
        imageView = new ImageView();
        imageView.setFitHeight(200);
        imageView.setFitWidth(120);
        image = new Image(url, 120, 200, false, false);
        imageView.setImage(image);
    }

    private void setMouseEvents() {
        setOnMouseEntered(event -> {
            setEffect(new DropShadow(10, Color.BLACK));
            sceneProperty().get().setCursor(Cursor.HAND);
        });
        setOnMouseExited(event -> {
            setEffect(new DropShadow(0, Color.ALICEBLUE));
            sceneProperty().get().setCursor(Cursor.DEFAULT);
        });

        setOnMouseClicked(event -> {
            Lo.g("Media type: " + mediaType);
            if (mediaType.getText().equals("tv")) openTVWindow(event);
            else openMovieWindow(event);
        });
    }

    private void openTVWindow(Event event) {
        Profile.getProfile().currentJSON = RequestApi.getTVDetails(id);
        Profile.getProfile().openWindow(event, "tv.fxml");
    }

    private void openMovieWindow(Event event) {
        Profile.getProfile().currentJSON = RequestApi.getMovieDetails(id);
        Lo.g("ID FILMU: " + id);
        Profile.getProfile().openWindow(event, "movie.fxml");
    }





}
