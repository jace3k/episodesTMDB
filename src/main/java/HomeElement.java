import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class HomeElement extends VBox {
    private ImageView imageView;
    private Image image;
    private Label label;

    private int id;
    private boolean isMovie;

    public HomeElement(String imgUrl, String label, int id, boolean isMovie) {
        super();

        this.id = id;
        this.isMovie = isMovie;

        createImage(RequestApi.base_url + RequestApi.size + imgUrl);
        createLabel(label);
        setup();
        setMouseEvents();
    }

    private void createImage(String url) {
        imageView = new ImageView();
        image = new Image(url, 180, 280, false, false);
        imageView.setImage(image);
    }

    private void createLabel(String label) {
        this.label = new Label(label);
    }

    private void setup() {
        getChildren().add(this.imageView);
        getChildren().add(this.label);
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(10, 10, 10, 10));
    }

    private void setMouseEvents() {
        setOnMouseEntered(event -> {
            imageView.setOpacity(0.7);
            sceneProperty().get().setCursor(Cursor.HAND);
        });
        setOnMouseExited(event -> {
            imageView.setOpacity(1);
            sceneProperty().get().setCursor(Cursor.DEFAULT);
        });
        setOnMouseClicked(event -> {
            if (isMovie) openMovieWindow(event);
            else openTVWindow(event);
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
