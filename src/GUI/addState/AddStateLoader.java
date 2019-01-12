package GUI.addState;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AddStateLoader {

    AnchorPane pane;
    AddStateController addStateController;

    public AddStateLoader() {
        FXMLLoader loader = new FXMLLoader(AddStateLoader.class.getResource("AddStateWindow.fxml"));
        try {
            pane = loader.load();
            addStateController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AnchorPane getPane() {
        return pane;
    }

    public AddStateController getAddStateController() {
        return addStateController;
    }
}
