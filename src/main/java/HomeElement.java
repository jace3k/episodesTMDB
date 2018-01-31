import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class HomeElement extends Element {
    private ImageView imageView;
    private Label label;
    private VBox vbox;


    public HomeElement(String imgUrl, String label, int id, boolean isMovie) {
        super(label, id, isMovie);

        createImage(RequestApi.base_url + RequestApi.size + imgUrl);
        createLabelAndVBox(label);
        setup();
    }

    private void createImage(String url) {
        imageView = new ImageView();
        Image image = new Image(url, 180, 280, false, false);
        imageView.setImage(image);
    }

    private void createLabelAndVBox(String label) {
        this.label = new Label(label);
        this.vbox = new VBox();
    }

    private void setup() {
        vbox.getChildren().add(this.imageView);
        vbox.getChildren().add(this.label);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        this.setCenter(vbox);
    }

}
