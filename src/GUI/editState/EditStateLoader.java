package GUI.editState;

import GUI.addState.AddStateController;
import GUI.addState.AddStateLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EditStateLoader {

    private EditStateController editStateController;
    private AnchorPane pane;

    public EditStateLoader() {
        FXMLLoader loader = new FXMLLoader(EditStateLoader.class.getResource("EditStateWindow.fxml"));
        try {
            pane = loader.load();
            editStateController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public EditStateController getAddStateController() {
        return editStateController;
    }

    public AnchorPane getPane() {
        return pane;
    }
}
