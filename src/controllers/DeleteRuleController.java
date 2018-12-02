package controllers;

import core.Turing;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DeleteRuleController {

    private Turing turing;
    private Stage stage;
    private char readSymbol;

    //Label
    @FXML
    private Label lState;

    //ChoiseBoxes
    @FXML private ChoiceBox cbReadSymbol;

    public void setlState(String lStateName){
        lState.setText(lStateName);
    }
    public void setTuring(Turing turing) {
        this.turing = turing;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public ChoiceBox getCbReadSymbol() {
        return cbReadSymbol;
    }
    public void setReadSymbol(char readSymbol) {
        this.readSymbol = readSymbol;
    }



    @FXML
    public void closeStage(){
        stage.close();
    }

    @FXML
    public void actionOK(){
        turing.deleteRule(lState.getText(),readSymbol);
        stage.close();
    }

    @FXML
    public void closeState(){
        stage.close();
    }
}
