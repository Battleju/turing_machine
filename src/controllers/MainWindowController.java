package controllers;

import core.State;
import core.Turing;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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

    public void setTuring(Turing turing){
        this.turing = turing;
    }

    @FXML
    public void handleRun(){

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

    public TableView getTableRules() {
        return tableRules;
    }

}
