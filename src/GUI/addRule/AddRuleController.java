package GUI.addRule;

import core.MainScene;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddRuleController {

    private MainScene turing;
    private Stage stage;

    public enum tape {
        L, H, R
    }
    private tape tapeMovement;
    private char writeSymbol;
    private char readSymbol;
    private String nextState;

    //Label
    @FXML private Label lState;

    //Buttons
    @FXML private Button bL;
    @FXML private Button bH;
    @FXML private Button bR;

    //ChoiseBoxes
    @FXML private ChoiceBox cbReadSymbol;
    @FXML private ChoiceBox cbNextState;
    @FXML private ChoiceBox cbWriteSymbol;

    public void setlState(String lStateName){
        lState.setText(lStateName);
    }

    public void setTuring(MainScene turing) {
        this.turing = turing;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public ChoiceBox getCbReadSymbol() {
        return cbReadSymbol;
    }

    public ChoiceBox getCbNextState() {
        return cbNextState;
    }

    public ChoiceBox getCbWriteSymbol() {
        return cbWriteSymbol;
    }

    public void setWriteSymbol(char writeSymbol) {
        this.writeSymbol = writeSymbol;
    }

    public void setReadSymbol(char readSymbol) {
        this.readSymbol = readSymbol;
    }

    public void setNextState(String nextState) {
        this.nextState = nextState;
    }

    @FXML
    public void closeStage(){
        stage.close();
    }

    @FXML
    public void actionOK(){
        turing.addRule(lState.getText(), readSymbol, writeSymbol, nextState, tapeMovement);
        stage.close();
    }

    @FXML
    public void actionL(){
        bL.setStyle("-fx-background-color: #0078ff");
        bH.setStyle(null);
        bR.setStyle(null);
        tapeMovement = tape.L;
    }

    @FXML
    public void actionH(){
        bH.setStyle("-fx-background-color: #0078ff");
        bL.setStyle(null);
        bR.setStyle(null);
        tapeMovement = tape.H;
    }

    @FXML
    public void actionR(){
        bR.setStyle("-fx-background-color: #0078ff");
        bH.setStyle(null);
        bL.setStyle(null);
        tapeMovement = tape.R;
    }
}
