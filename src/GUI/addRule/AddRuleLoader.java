package GUI.addRule;

import GUI.addState.AddStateLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AddRuleLoader {

    private AddRuleController addRuleController;
    private AnchorPane pane;

    public AddRuleLoader() {
        FXMLLoader loader = new FXMLLoader(AddRuleLoader.class.getResource("AddRuleWindow.fxml"));
        try {
            pane = loader.load();
            addRuleController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AddRuleController getAddRuleController() {
        return addRuleController;
    }

    public AnchorPane getPane() {
        return pane;
    }
}
