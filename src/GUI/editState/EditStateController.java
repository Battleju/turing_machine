package GUI.editState;

import core.MainScene;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditStateController {

    private MainScene turing;
    private Stage stage;

    //TextFields
    @FXML private TextField tfName;

    public void setTuring(MainScene turing) {
        this.turing = turing;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void closeStage(){
        stage.close();
    }

    @FXML
    public void actionOK(){
        turing.editState(tfName.getText());
        stage.close();
    }
}
