package GUI.main;

import core.MainScene;
import core.State;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import utils.Utils;

public class MainController {

    private MainScene turing;

    //Labels
    @FXML private Label lName;
    @FXML private Label lAlph;
    @FXML private Label lInitState;
    @FXML private Label lTapeEx;

    //TextFields
    @FXML private TextField tfName;
    @FXML private TextField tfAlph;
    @FXML private TextField tfTapeEx;

    //other
    @FXML private TableView tableRules;
    @FXML private ChoiceBox cbInitState;
    @FXML private AnchorPane pane;

    public void setTuring(MainScene turing){
        this.turing = turing;
    }

    @FXML
    public void handleRun(){
        turing.startRunScene();
    }

    @FXML
    public void setValueAlph(){
        turing.setAlph(tfAlph.getText());
    }

    @FXML
    public void addState(){
        turing.addStateBtn();
    }

    @FXML
    public void editState(){
        turing.editStateBtn();
    }

    @FXML
    public void deleteState(){
        State item = (State) tableRules.getSelectionModel().getSelectedItem();
        if(item != null) {
            tableRules.getItems().remove(item);
        }
    }

    @FXML
    public void addRule(){
        State item = (State) tableRules.getSelectionModel().getSelectedItem();
        turing.addRuleBtn(item.getName());
    }

    @FXML
    public void deleteRule(){
        State item = (State) tableRules.getSelectionModel().getSelectedItem();
        turing.deleteRuleBtn(item.getName());
    }

    @FXML
    public void checkText(){
        if(Utils.StringWhitelist(turing.getAlph(), tfTapeEx.getText())){
            tfTapeEx.setStyle("");
        }else {
            tfTapeEx.setStyle("-fx-text-inner-color: red");
        }
    }

    @FXML
    public void handleClose(){
        System.exit(0);
    }

    @FXML
    public void handleMin(){

    }

    public TableView getTableRules() {
        return tableRules;
    }

    public ChoiceBox getCbInitState() {
        return cbInitState;
    }

    public AnchorPane getPane() {
        return pane;
    }

    public Label getlName() {
        return lName;
    }

    public Label getlAlph() {
        return lAlph;
    }

    public Label getlInitState() {
        return lInitState;
    }

    public Label getlTapeEx() {
        return lTapeEx;
    }

    public String getValueName(){
        return tfName.getText();
    }

    public String getValueAlph(){
        return tfAlph.getText();
    }

    public String getValueTapeEx(){
        return tfTapeEx.getText();
    }
}
