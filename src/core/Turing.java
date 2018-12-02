package core;

import controllers.*;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Turing extends Application {

    private ArrayList<Character> alph;
    private String name;
    private String initState;
    private String tapeEx;
    private final ObservableList<State> states = FXCollections.observableArrayList(new State("state1"));
    private MainWindowController mainWindowController;
    private AddStateController addStateController;
    private EditStateController editStateController;
    private AddRuleController addRuleController;
    private DeleteRuleController deleteRuleController;

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

        setAlph("");

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
        for(int i = 0; i < states.size(); i++){
            for(int j = 0; j < alph.size(); j++){
                states.get(i).addAlph(alph.get(j));
            }
        }
        mainWindowController.getTableRules().getColumns().clear();
        TableColumn statesCol = new TableColumn("state");
        statesCol.setCellValueFactory(new PropertyValueFactory<State, String>("name"));
        mainWindowController.getTableRules().getColumns().addAll(statesCol);
        for(int i = 0; i < getAlph().size(); i++){
            final int j = i;
            TableColumn tc = new TableColumn("" + getAlph().get(i));
            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<State,String>,ObservableValue<String>>(){
                public ObservableValue<String> call(TableColumn.CellDataFeatures<State, String> param) {
                    return new SimpleStringProperty(param.getValue().mapPropertyProperty().get(getAlph().get(j)));
                }
            });
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
        for(int j = 0; j < alph.size(); j++){
            states.get(states.size() - 1).addAlph(alph.get(j));
        }
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
            stage.setTitle("Turing Machine - add/edit rule");

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
                State state = (State)mainWindowController.getTableRules().getItems().get(i);
                state.setValue(readSymbol, "" + nextState + " , " + writeSymbol + " , " + tapeMove);
            }
        }
        GUIUtils.autoResizeColumns(mainWindowController.getTableRules());
        mainWindowController.getTableRules().refresh();
    }

    public void deleteRuleBtn(String actualState){
        try {
            FXMLLoader loader = new FXMLLoader(Turing.class.getResource("../windows/DeleteRuleWindow.fxml"));
            AnchorPane pane = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Turing Machine - add/edit rule");

            deleteRuleController = loader.getController();
            deleteRuleController.setTuring(this);
            deleteRuleController.setStage(stage);
            deleteRuleController.setlState(actualState);
            deleteRuleController.getCbReadSymbol().setItems(FXCollections.observableArrayList(alph));
            deleteRuleController.getCbReadSymbol().getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    deleteRuleController.setReadSymbol(alph.get(newValue.intValue()));
                }
            });
            stage.setScene(new Scene(pane, 250, 220));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteRule(String actState, char readSymbol){
        for (int i = 0; i < states.size(); i++) {
            if (states.get(i).getName().equals(actState)) {
                State state = (State)mainWindowController.getTableRules().getItems().get(i);
                state.setValue(readSymbol, "null");
            }
        }
        GUIUtils.autoResizeColumns(mainWindowController.getTableRules());
        mainWindowController.getTableRules().refresh();
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
