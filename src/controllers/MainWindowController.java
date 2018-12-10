package controllers;

import core.State;
import core.Turing;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class MainWindowController {

    private Turing turing;

    //Labels
    @FXML private Label lName;
    @FXML private Label lAlph;
    @FXML private Label lInitState;
    @FXML private Label lTapeEx;
    @FXML private Label lHeading;

    //TextFields
    @FXML private TextField tfName;
    @FXML private TextField tfAlph;
    @FXML private TextField tfInitState;
    @FXML private TextField tfTapeEx;
    @FXML private TextField tfAddState;

    //other
    @FXML private TableView tableRules;
    @FXML private ChoiceBox cbInitState;
    @FXML private AnchorPane pane;

    public void setTuring(Turing turing){
        this.turing = turing;
    }

    @FXML
    public void handleRun(){
        turing.startRunScene();
    }

    @FXML
    public void setValueName(){
        tfName.setStyle("-fx-text-fill: green");
        turing.setName(tfName.getText());
    }

    @FXML
    public void setValueAlph(){
        tfAlph.setStyle("-fx-text-fill: green");
        turing.setAlph(tfAlph.getText());
    }

    @FXML
    public void setValueInitState(){
        tfInitState.setStyle("-fx-text-fill: green");
        turing.setInitState(tfInitState.getText());
    }

    @FXML
    public void setValueTapeEx(){
        tfTapeEx.setStyle("-fx-text-fill: green");
        turing.setTapeEx(tfTapeEx.getText());
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
    public void handleClose(){
        System.exit(0);
    }

    @FXML
    public void handleMax(){

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
}
