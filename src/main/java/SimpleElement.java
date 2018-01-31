import javafx.geometry.Insets;
import javafx.scene.control.Label;

public class SimpleElement extends Element {
    public SimpleElement(String name, int id, String type) {
        super(name, id, type);
        setup();
    }

    private void setup() {
        setPadding(new Insets(10, 10, 10, 10));
        setMargin(this, new Insets(20, 20, 20, 20));

        setPrefSize(600, 50);
        setLeft(new Label(this.getName()));
        setCenter(new Label(this.getType()));
        setRight(new Label(this.get_Id() + ""));

        setStyle("-fx-border-color: black");
    }

}
