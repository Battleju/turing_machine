package GUI.editState;

import GUI.main.MainScene;
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
        if(!tfName.getText().trim().equals("")){
            turing.editState(tfName.getText());
            stage.close();
        }else {
            tfName.setStyle("-fx-background-color: #ff5b5b");
        }
    }
}
