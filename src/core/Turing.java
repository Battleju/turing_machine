package core;

import controllers.AddRuleController;
import controllers.AddStateController;
import controllers.EditStateController;
import controllers.MainWindowController;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Turing extends Application {

    ArrayList<Character> alph;
    String name;
    String initState;
    String tapeEx;
    private final ObservableList<State> states = FXCollections.observableArrayList(new State("state1"));
    MainWindowController mainWindowController;
    AddStateController addStateController;
    EditStateController editStateController;
    AddRuleController addRuleController;

    //Initial-----------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(Turing.class.getResource("../windows/MainWindow.fxml"));
        AnchorPane pane = loader.load();
        primaryStage.setTitle("Turing Machine");

        mainWindowController = loader.getController();
        mainWindowController.setTuring(this);

        primaryStage.setScene(new Scene(pane, 900, 600));
        primaryStage.show();

        initTuring();
    }

    private void initTuring(){
        alph = new ArrayList<Character>();
        alph.add('#');
        name = "name";
        initState = "0";
        tapeEx = "";

        TableColumn statesCol = new TableColumn("state");
        statesCol.setCellValueFactory(new PropertyValueFactory<State, String>("name"));
        mainWindowController.getTableRules().getColumns().addAll(statesCol);

        TableColumn hashtagCol = new TableColumn("#");
        hashtagCol.setCellValueFactory(new PropertyValueFactory<State, String>("#"));
        mainWindowController.getTableRules().getColumns().addAll(hashtagCol);

        mainWindowController.getTableRules().setItems(states);
    }

    //Edit Table--------------------------------------------------------------------------------------------------------
    public void setAlph(String alphString){
        ArrayList<Character> alphNew = new ArrayList<Character>();
        for (char c : alphString.toCharArray()) {
            alphNew.add(c);
        }
        alph = alphNew;
        alph.add('#');
        mainWindowController.getTableRules().getColumns().clear();
        TableColumn statesCol = new TableColumn("state");
        statesCol.setCellValueFactory(new PropertyValueFactory<State, String>("name"));
        mainWindowController.getTableRules().getColumns().addAll(statesCol);
        for(int i = 0; i < getAlph().size(); i++){
            TableColumn tc = new TableColumn("" + getAlph().get(i));
            tc.setCellValueFactory(new PropertyValueFactory<State, String>("" + getAlph().get(i)));
            mainWindowController.getTableRules().getColumns().addAll(tc);
        }
    }

    public void addStateBtn(){
        try {
            FXMLLoader loader = new FXMLLoader(Turing.class.getResource("../windows/AddStateWindow.fxml"));
            AnchorPane pane = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Turing Machine - add state");

            addStateController = loader.getController();
            addStateController.setTuring(this);
            addStateController.setStage(stage);

            stage.setScene(new Scene(pane, 350, 150));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addState(String state){
        states.add(new State(state));
        mainWindowController.getTableRules().setItems(states);
    }

    public void editStateBtn(){
        try {
            FXMLLoader loader = new FXMLLoader(Turing.class.getResource("../windows/EditStateWindow.fxml"));
            AnchorPane pane = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Turing Machine - edit state");

            editStateController = loader.getController();
            editStateController.setTuring(this);
            editStateController.setStage(stage);

            stage.setScene(new Scene(pane, 350, 150));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editState(String state){
        State item = (State) mainWindowController.getTableRules().getSelectionModel().getSelectedItem();
        if(item != null) {
            item.setName(state);
        }
    }

    public void addRuleBtn(String actualState){
        try {
            FXMLLoader loader = new FXMLLoader(Turing.class.getResource("../windows/AddRuleWindow.fxml"));
            AnchorPane pane = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Turing Machine - add rule");

            addRuleController = loader.getController();
            addRuleController.setTuring(this);
            addRuleController.setStage(stage);
            addRuleController.setlState(actualState);
            addRuleController.getCbReadSymbol().setItems(FXCollections.observableArrayList(alph));
            addRuleController.getCbNextState().setItems(FXCollections.observableArrayList(states));
            addRuleController.getCbWriteSymbol().setItems(FXCollections.observableArrayList(alph));
            addRuleController.getCbReadSymbol().getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    addRuleController.setReadSymbol(alph.get(newValue.intValue()));
                }
            });
            addRuleController.getCbWriteSymbol().getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    addRuleController.setWriteSymbol(alph.get(newValue.intValue()));
                }
            });
            addRuleController.getCbNextState().getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    addRuleController.setNextState(states.get(newValue.intValue()).getName());
                }
            });

            stage.setScene(new Scene(pane, 350, 375));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRule(String actState, char readSymbol, char writeSymbol, String nextState, AddRuleController.tape tapeMove){
        for (int i = 0; i < states.size(); i++) {
            if (states.get(i).getName().equals(actState)) {
                mainWindowController.getTableRules().getItems().get(i).
            }
        }
    }

    //Getter & Setter---------------------------------------------------------------------------------------------------

    public void setName(String name){
        this.name = name;
    }

    public void setInitState(String initState){
        this.initState = initState;
    }

    public void setTapeEx(String tapeEx){
        this.tapeEx = tapeEx;
    }

    public ArrayList<Character> getAlph() {
        return alph;
    }

    public String getName() {
        return name;
    }

    public String getInitState() {
        return initState;
    }

    public String getTapeEx() {
        return tapeEx;
    }


}
