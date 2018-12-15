package core;

import GUI.addRule.AddRuleController;
import GUI.addState.AddStateController;
import GUI.deleteRule.DeleteRuleController;
import GUI.editState.EditStateController;
import GUI.main.MainController;
import GUI.run.RunScene;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import utils.GUIUtils;

import java.io.IOException;
import java.util.ArrayList;

public class MainScene extends Application {

    private ArrayList<Character> alph;
    private String name;
    private String initState;
    private String tapeEx;
    private final ObservableList<State> states = FXCollections.observableArrayList(new State("state1"));
    private MainController mainWindowController;
    private AddStateController addStateController;
    private EditStateController editStateController;
    private AddRuleController addRuleController;
    private DeleteRuleController deleteRuleController;
    private Stage primaryStage;
    private AnchorPane pane;
    private double xOffset = 0;
    private double yOffset = 0;

    //Initial-----------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(MainScene.class.getResource("../GUI/main/MainWindow.fxml"));
        pane = loader.load();
        primaryStage.setTitle("Turing Machine");

        mainWindowController = loader.getController();
        mainWindowController.setTuring(this);

        /*
        mainWindowController.getPane().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        mainWindowController.getPane().setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
        */

        primaryStage.setScene(new Scene(pane, 900, 600));
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

        initTuring();
    }

    private void initTuring(){
        alph = new ArrayList<Character>();
        alph.add('#');
        name = "noname";
        initState = "";
        tapeEx = "#";
        setAlph("");

        mainWindowController.getTableRules().setItems(states);
        mainWindowController.getCbInitState().setItems(FXCollections.observableArrayList(states));
        mainWindowController.getCbInitState().getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                try{
                    initState = states.get(newValue.intValue()).getName();
                }catch (ArrayIndexOutOfBoundsException ex){

                }

            }
        });
    }

    //Edit Table--------------------------------------------------------------------------------------------------------
    public void refreshCBInitState(){
        mainWindowController.getCbInitState().getItems().clear();
        for (int i = 0; i < states.size(); i++){
            mainWindowController.getCbInitState().getItems().add(states.get(i));
        }
        State selectedState = null;
        for (int i = 0; i < states.size(); i++){
            if(states.get(i).getName().equals(initState)){
                selectedState = states.get(i);
            }
        }
        if(selectedState != null){
            mainWindowController.getCbInitState().getSelectionModel().select(selectedState);
        }
    }

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
            FXMLLoader loader = new FXMLLoader(MainScene.class.getResource("../GUI/addState/AddStateWindow.fxml"));
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
        refreshCBInitState();
    }

    public void editStateBtn(){
        try {
            FXMLLoader loader = new FXMLLoader(MainScene.class.getResource("../GUI/editState/EditStateWindow.fxml"));
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
        refreshCBInitState();
    }

    public void addRuleBtn(String actualState){
        try {
            FXMLLoader loader = new FXMLLoader(MainScene.class.getResource("../GUI/addRule/AddRuleWindow.fxml"));
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
            FXMLLoader loader = new FXMLLoader(MainScene.class.getResource("../GUI/deleteRule/DeleteRuleWindow.fxml"));
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

    //Run Stage---------------------------------------------------------------------------------------------------------
    public void startRunScene(){
        try {
            name = mainWindowController.getValueName();
            setAlph(mainWindowController.getValueAlph());
            tapeEx = mainWindowController.getValueTapeEx();
            RunScene runScene = new RunScene(this, pane, name, alph, initState, tapeEx, mainWindowController.getTableRules());
            primaryStage.getScene().setRoot(runScene.getPane());
        } catch (Exception e) {
            e.printStackTrace();
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

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ObservableList<State> getStates() {
        return states;
    }

    public double getxOffset() {
        return xOffset;
    }

    public void setxOffset(double xOffset) {
        this.xOffset = xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }

    public void setyOffset(double yOffset) {
        this.yOffset = yOffset;
    }
}
