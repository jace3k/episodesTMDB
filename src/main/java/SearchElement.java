import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

import java.util.Objects;

public class SearchElement extends Element {
    private Label name;
    private Label date;
    private Label mediaType;
    private ImageView imageView;
    private String imgUrl;

    public SearchElement(String name, String date, String mediaType, int id, String imgUrl) {
        super(name, id, mediaType);
        this.name = new Label(name);
        this.date = new Label(date);
        this.mediaType = new Label(mediaType);
        this.imgUrl = imgUrl;

        setup();
    }

    public SearchElement(String errMsg) {
        super(errMsg, 0, "-");
        this.name = new Label(errMsg);
        this.date = new Label("-");
        this.mediaType = new Label("-");
        this.imgUrl = "";

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

        bottomPane.setRight(mediaType);
        setBottom(bottomPane);

        setStyle("-fx-border-color: black;");

        //// TODO: kalendarz gugla - eksport
    }

    private void createImage(String url) {
        imageView = new ImageView();
        imageView.setFitHeight(200);
        imageView.setFitWidth(120);
        Image image = new Image(url, 120, 200, false, false);
        imageView.setImage(image);
    }
}
