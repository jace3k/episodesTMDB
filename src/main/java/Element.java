import javafx.event.Event;
import javafx.scene.Cursor;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class Element extends BorderPane implements Serializable {
    private String name;
    private int id;
    private String type;


    public Element(String name, int id, String type) {
        super();
        this.name = name;
        this.id = id;
        this.type = type;

        setMouseEvents();
    }

    public Element(String name, int id, boolean type) {
        super();
        this.name = name;
        this.id = id;
        String mType;
        mType = type ? "movie" : "tv";
        this.type = mType;

        setMouseEvents();
    }


    public String getName() {
        return name;
    }

    public int get_Id() {
        return id;
    }

    public String getType() {
        return type;
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
            if (type.equals("tv")) openTVWindow(event);
            else openMovieWindow(event);
        });
    }

    private void openTVWindow(Event event) {
        Profile.getProfile().currentJSON = RequestApi.getTVDetails(id);
        Profile.getProfile().openWindow(event, "tv.fxml");
    }

    private void openMovieWindow(Event event) {
        Profile.getProfile().currentJSON = RequestApi.getMovieDetails(id);
        Profile.getProfile().openWindow(event, "movie.fxml");
    }

}


