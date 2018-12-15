package GUI.run;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

    public class RunController {

    private RunScene runScene;

    //Labels
    @FXML private Label lName;
    @FXML private Label lFinished;

    //TextAreas
    @FXML private TextField ta0;
    @FXML private TextField ta1;
    @FXML private TextField ta2;
    @FXML private TextField ta3;
    @FXML private TextField ta4;
    @FXML private TextField ta5;
    @FXML private TextField ta6;
    @FXML private TextField ta7;
    @FXML private TextField ta8;
    @FXML private TextField ta9;
    @FXML private TextField ta10;
    @FXML private TextField ta11;
    @FXML private TextField ta12;
    @FXML private TextField ta13;
    @FXML private TextField ta14;
    @FXML private TextField ta15;

    //other
    @FXML private TableView tableRules;
    @FXML private Slider slSpeed;

    @FXML
    public void actionStartPause(){
        runScene.actionStartPause();
    }

    @FXML
    public void goBack(){
        runScene.goToOrigin();
    }

    @FXML
    public void setSpeed(){
        runScene.setSpeed(slSpeed.getValue());
    }

    @FXML
    public void reset(){
        runScene.reset();
    }

    public void setlFinished(String text){
        Platform.runLater(() -> lFinished.setText(text));
    }

    @FXML
    public void nextStep(){
        runScene.getMachine().runAlgorithm();
    }

    @FXML
    public void nextState(){
        runScene.actionStartPause();
        runScene.setJustUntilNextState(true);
    }

    public void setTa(int index, char text){
        switch (index){
            case 0:
                ta0.setText(" " + text);
                break;
            case 1:
                ta1.setText(" " + text);
                break;
            case 2:
                ta2.setText(" " + text);
                break;
            case 3:
                ta3.setText(" " + text);
                break;
            case 4:
                ta4.setText(" " + text);
                break;
            case 5:
                ta5.setText(" " + text);
                break;
            case 6:
                ta6.setText(" " + text);
                break;
            case 7:
                ta7.setText(" " + text);
                break;
            case 8:
                ta8.setText(" " + text);
                break;
            case 9:
                ta9.setText(" " + text);
                break;
            case 10:
                ta10.setText(" " + text);
                break;
            case 11:
                ta11.setText(" " + text);
                break;
            case 12:
                ta12.setText(" " + text);
                break;
            case 13:
                ta13.setText(" " + text);
                break;
            case 14:
                ta14.setText(" " + text);
                break;
            case 15:
                ta15.setText(" " + text);
                break;
        }
    }

    public void setTaPointer(int index){
        ta0.setStyle(null);
        ta1.setStyle(null);
        ta2.setStyle(null);
        ta3.setStyle(null);
        ta4.setStyle(null);
        ta5.setStyle(null);
        ta6.setStyle(null);
        ta7.setStyle(null);
        ta8.setStyle(null);
        ta9.setStyle(null);
        ta10.setStyle(null);
        ta11.setStyle(null);
        ta12.setStyle(null);
        ta13.setStyle(null);
        ta14.setStyle(null);
        ta15.setStyle(null);
        switch (index){
            case 0:
                ta0.setStyle("-fx-background-color: #2c84ff");
                break;
            case 1:
                ta1.setStyle("-fx-background-color: #2c84ff");
                break;
            case 2:
                ta2.setStyle("-fx-background-color: #2c84ff");
                break;
            case 3:
                ta3.setStyle("-fx-background-color: #2c84ff");
                break;
            case 4:
                ta4.setStyle("-fx-background-color: #2c84ff");
                break;
            case 5:
                ta5.setStyle("-fx-background-color: #2c84ff");
                break;
            case 6:
                ta6.setStyle("-fx-background-color: #2c84ff");
                break;
            case 7:
                ta7.setStyle("-fx-background-color: #2c84ff");
                break;
            case 8:
                ta8.setStyle("-fx-background-color: #2c84ff");
                break;
            case 9:
                ta9.setStyle("-fx-background-color: #2c84ff");
                break;
            case 10:
                ta10.setStyle("-fx-background-color: #2c84ff");
                break;
            case 11:
                ta11.setStyle("-fx-background-color: #2c84ff");
                break;
            case 12:
                ta12.setStyle("-fx-background-color: #2c84ff");
                break;
            case 13:
                ta13.setStyle("-fx-background-color: #2c84ff");
                break;
            case 14:
                ta14.setStyle("-fx-background-color: #2c84ff");
                break;
            case 15:
                ta15.setStyle("-fx-background-color: #2c84ff");
                break;
        }
    }

    public void setRunScene(RunScene runScene) {
        this.runScene = runScene;
    }

    public void setlName(String name){
        lName.setText("Run: " + name);
    }

    public void setTableRulesCol(TableColumn col){
        tableRules.getColumns().addAll(col);
    }

    public void setTableRulesRow(ObservableList row){
        tableRules.setItems(row);
    }

    public TableView getTableRules() {
        return tableRules;
    }
}
