package GUI.deleteRule;

import GUI.addState.AddStateLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DeleteRuleLoader {

    private DeleteRuleController deleteRuleController;
    private AnchorPane pane;

    public DeleteRuleLoader() {
        FXMLLoader loader = new FXMLLoader(DeleteRuleLoader.class.getResource("DeleteRuleWindow.fxml"));
        try {
            pane = loader.load();
            deleteRuleController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DeleteRuleController getDeleteRuleController() {
        return deleteRuleController;
    }

    public AnchorPane getPane() {
        return pane;
    }
}
