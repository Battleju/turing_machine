package controllers;

import core.Turing;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditStateController {

    private Turing turing;
    private Stage stage;

    //TextFields
    @FXML private TextField tfName;

    public void setTuring(Turing turing) {
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
