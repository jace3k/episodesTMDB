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

    public SearchElement(String name, String date, int[] genres, String mediaType, double rate, int id, String imgUrl) {
        this.name = new Label(name);
        this.date = new Label(date);
        //this.genres = new Label(genres);
        this.mediaType = new Label(mediaType);
        this.rate = new Label(rate + "");
        this.id = id;
        this.imgUrl = imgUrl;

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
        bottomPane.setRight(rate);
        setBottom(bottomPane);
    }

    private void createImage(String url) {
        imageView = new ImageView();
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
    }
}
