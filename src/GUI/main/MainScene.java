/*
Final to do list:
-css styling
 */
package GUI.main;

import GUI.addRule.AddRuleController;
import GUI.addRule.AddRuleLoader;
import GUI.addState.AddStateLoader;
import GUI.addState.AddStateController;
import GUI.deleteRule.DeleteRuleController;
import GUI.deleteRule.DeleteRuleLoader;
import GUI.editState.EditStateController;
import GUI.editState.EditStateLoader;
import GUI.loadingState.LoadingScene;
import GUI.run.RunScene;
import core.Project;
import core.State;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import utils.GUIUtils;
import utils.SAVManager;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainScene extends Application {

    private ObservableList<Project> projects;
    private MainController mainWindowController;
    private AddRuleController addRuleController;
    private DeleteRuleController deleteRuleController;
    private Stage primaryStage;
    private AnchorPane pane;
    private TableView originRules;
    private SAVManager savManager;

    //Initial-----------------------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(MainScene.class.getResource("MainWindow.fxml"));
        pane = loader.load();
        primaryStage.setTitle("Turing Machine");

        mainWindowController = loader.getController();
        mainWindowController.setTuring(this);
        primaryStage.getIcons().add(new Image(MainScene.class.getResourceAsStream("turingIcon1.gif")));
        primaryStage.setScene(new Scene(pane, 1100, 600));
        initTuring();
    }

    private void initTuring(){
        originRules = mainWindowController.getTableRules();
        TableColumn tableColumn = (TableColumn) mainWindowController.getTableProjects().getColumns().get(0);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainWindowController.getTableProjects().setItems(projects);
        mainWindowController.getCbInitState().getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            try{
                getActualProject().setInitState(getActualProject().getStates().get(newValue.intValue()).getName());
            }catch (ArrayIndexOutOfBoundsException ignored){

            }
        });
        LoadingScene loadingScene = new LoadingScene(this, pane);
        loadingScene.execute();
        mainWindowController.getTableProjects().setRowFactory(tv -> new TableRow<Project>() {
            @Override
            public void updateItem(Project item, boolean empty) {
                super.updateItem(item, empty) ;
                if (item == null) {
                    setStyle("");
                } else if (item.isSaved()) {
                    setStyle("-fx-background-color: #0077ff;");
                } else {
                    setStyle("");
                }
            }
        });
        mainWindowController.getTableProjects().setPlaceholder(new Label("There are no projects"));
        mainWindowController.getTableRules().setPlaceholder(new Label("There are no rules"));
    }

    public void refreshGUI(){
        String alphString = "";
        for (int i = 0; i < getActualProject().getAlph().size(); i++){
            if(getActualProject().getAlph().get(i) != '#'){
                alphString += getActualProject().getAlph().get(i);
            }
        }
        mainWindowController.getTableProjects().setPlaceholder(new Label("There are no projects"));
        mainWindowController.getTableRules().setPlaceholder(new Label("There are no rules"));
        mainWindowController.getTfAlph().setText(alphString);
        mainWindowController.getTfName().setText(getActualProject().getName());
        mainWindowController.getTfTapeEx().setText(getActualProject().getTapeEx());
        refreshCBInitState();
        mainWindowController.getTableProjects().refresh();
        mainWindowController.getTableRules().refresh();
    }



    //Edit Table Projects-----------------------------------------------------------------------------------------------
    public void newProject(){
        if(projects != null){
            projects.add(new Project());
        }else {
            projects = FXCollections.observableArrayList(new Project());
        }
        projects.get(projects.size() - 1).setRules(originRules);
        mainWindowController.getTableProjects().setItems(projects);
        mainWindowController.getTableProjects().refresh();
    }

    public void setActualProject(){
        for (int i = 0; i < projects.size(); i++){
            projects.get(i).setActive(false);
        }
        Project project = (Project) mainWindowController.getTableProjects().getSelectionModel().getSelectedItem();

        mainWindowController.getTableRules().getColumns().clear();
        mainWindowController.getTableRules().getColumns().addAll(project.getRules().getColumns());
        mainWindowController.getTableRules().setItems(project.getRules().getItems());
        project.setActive(true);
        refreshGUI();
        String alphString = "";
        for (int i = 0; i < project.getAlph().size(); i++){
            if(project.getAlph().get(i) != '#'){
                alphString += project.getAlph().get(i);
            }
        }
        setAlph(alphString);
        refreshGUI();
    }

    public void deleteProject(){
        savManager.deleteFile(getActualProject().getName());
        projects.remove(getActualProject());
    }

    public void saveActualProject(){
        getActualProject().save();
    }

    public void saveAll(){
        for (Project project : projects) {
            project.save();
        }
    }

    public void exportAsSAV(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Project");
        fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter("Turing Machine Stimulator SAV files (*.tmsSAV)", "*.tmsSAV"));
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try {
                getActualProject().saveAt(file);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void exportAsTXT(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Project");
        fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try {
                getActualProject().saveAt(file);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void importSAV(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Project");
        fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter("Turing Machine Stimulator SAV files (*.tmsSAV)", "*.tmsSAV"));
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try {
                savManager.readData(file);
                projects.add(savManager.getProjects().get(savManager.getProjects().size() - 1));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    //Edit Table Rules--------------------------------------------------------------------------------------------------
    private void refreshCBInitState(){
        mainWindowController.getCbInitState().getItems().clear();
        for (State state : getActualProject().getStates()) {
            mainWindowController.getCbInitState().getItems().add(state);
        }
        State selectedState = null;
        for (State state : getActualProject().getStates()) {
            if (state.getName().equals(getActualProject().getInitState())) {
                selectedState = state;
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
        getActualProject().setAlph(alphNew);
        getActualProject().getAlph().add('#');
        for (State state : getActualProject().getStates()) {
            for (Character character : getActualProject().getAlph()) {
                state.addAlph(character);
            }
        }
        mainWindowController.getTableRules().getColumns().clear();
        TableColumn statesCol = new TableColumn("state");
        statesCol.setCellValueFactory(new PropertyValueFactory<State, String>("name"));
        mainWindowController.getTableRules().getColumns().addAll(statesCol);
        for(int i = 0; i < getActualProject().getAlph().size(); i++){
            final int j = i;
            TableColumn tc = new TableColumn("" + getActualProject().getAlph().get(i));
            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<State,String>,ObservableValue<String>>(){
                public ObservableValue<String> call(TableColumn.CellDataFeatures<State, String> param) {
                    return new SimpleStringProperty(param.getValue().mapPropertyProperty().get(getActualProject().getAlph().get(j)));
                }
            });
            mainWindowController.getTableRules().getColumns().addAll(tc);
        }
        getActualProject().setRules(mainWindowController.getTableRules());
        getActualProject().getRules().setItems(getActualProject().getStates());
    }

    public void addStateBtn(){
            AddStateLoader addStateLoader = new AddStateLoader();
            AnchorPane pane = addStateLoader.getPane();
            Stage stage = new Stage();
            stage.setTitle("Turing Machine - add state");

            AddStateController addStateController = addStateLoader.getAddStateController();
            addStateController.setTuring(this);
            addStateController.setStage(stage);

            stage.setScene(new Scene(pane, 350, 150));
            stage.show();
    }

    public void addState(String state){
        getActualProject().getStates().add(new State(state));
        for (Character character : getActualProject().getAlph()) {
            getActualProject().getStates().get(getActualProject().getStates().size() - 1).addAlph(character);
        }
        mainWindowController.getTableRules().setItems(getActualProject().getStates());
        refreshCBInitState();
        getActualProject().setRules(mainWindowController.getTableRules());
    }

    public void editStateBtn(){
            EditStateLoader editStateLoader = new EditStateLoader();
            AnchorPane pane = editStateLoader.getPane();
            Stage stage = new Stage();
            stage.setTitle("Turing Machine - edit state");

            EditStateController editStateController = editStateLoader.getAddStateController();
            editStateController.setTuring(this);
            editStateController.setStage(stage);

            stage.setScene(new Scene(pane, 350, 150));
            stage.show();
    }

    public void editState(String state){
        State item = (State) mainWindowController.getTableRules().getSelectionModel().getSelectedItem();
        if(item != null) {
            item.setName(state);
        }
        refreshCBInitState();
        getActualProject().setRules(mainWindowController.getTableRules());
        refreshGUI();
    }

    public void addRuleBtn(String actualState){
            AddRuleLoader addRuleLoader = new AddRuleLoader();
            AnchorPane pane = addRuleLoader.getPane();
            Stage stage = new Stage();
            stage.setTitle("Turing Machine - add/edit rule");

            addRuleController = addRuleLoader.getAddRuleController();
            addRuleController.setTuring(this);
            addRuleController.setStage(stage);
            addRuleController.setlState(actualState);
            addRuleController.getCbReadSymbol().setItems(FXCollections.observableArrayList(getActualProject().getAlph()));
            addRuleController.getCbNextState().setItems(FXCollections.observableArrayList(getActualProject().getStates()));
            addRuleController.getCbWriteSymbol().setItems(FXCollections.observableArrayList(getActualProject().getAlph()));
            addRuleController.getCbReadSymbol().getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    addRuleController.setReadSymbol(getActualProject().getAlph().get(newValue.intValue()));
                }
            });
            addRuleController.getCbWriteSymbol().getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    addRuleController.setWriteSymbol(getActualProject().getAlph().get(newValue.intValue()));
                }
            });
            addRuleController.getCbNextState().getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    addRuleController.setNextState(getActualProject().getStates().get(newValue.intValue()).getName());
                }
            });

            stage.setScene(new Scene(pane, 350, 375));
            stage.show();
    }

    public void addRule(String actState, char readSymbol, char writeSymbol, String nextState, AddRuleController.tape tapeMove){
        for (int i = 0; i < getActualProject().getStates().size(); i++) {
            if (getActualProject().getStates().get(i).getName().equals(actState)) {
                State state = (State)mainWindowController.getTableRules().getItems().get(i);
                state.setValue(readSymbol, "" + nextState + " , " + writeSymbol + " , " + tapeMove);
            }
        }
        GUIUtils.autoResizeColumns(mainWindowController.getTableRules());
        mainWindowController.getTableRules().refresh();
        getActualProject().setRules(mainWindowController.getTableRules());
    }

    public void deleteRuleBtn(String actualState){
            DeleteRuleLoader deleteRuleLoader = new DeleteRuleLoader();
            AnchorPane pane = deleteRuleLoader.getPane();
            Stage stage = new Stage();
            stage.setTitle("Turing Machine - add/edit rule");

            deleteRuleController = deleteRuleLoader.getDeleteRuleController();
            deleteRuleController.setTuring(this);
            deleteRuleController.setStage(stage);
            deleteRuleController.setlState(actualState);
            deleteRuleController.getCbReadSymbol().setItems(FXCollections.observableArrayList(getActualProject().getAlph()));
            deleteRuleController.getCbReadSymbol().getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    deleteRuleController.setReadSymbol(getActualProject().getAlph().get(newValue.intValue()));
                }
            });
            stage.setScene(new Scene(pane, 250, 220));
            stage.show();
    }

    public void deleteRule(String actState, char readSymbol){
        for (int i = 0; i < getActualProject().getStates().size(); i++) {
            if (getActualProject().getStates().get(i).getName().equals(actState)) {
                State state = (State)mainWindowController.getTableRules().getItems().get(i);
                state.setValue(readSymbol, "null");
            }
        }
        GUIUtils.autoResizeColumns(mainWindowController.getTableRules());
        mainWindowController.getTableRules().refresh();
        getActualProject().setRules(mainWindowController.getTableRules());
    }

    //Run Stage---------------------------------------------------------------------------------------------------------
    public void startRunScene(){
        try {
            getActualProject().setName(mainWindowController.getValueName());
            setAlph(mainWindowController.getValueAlph());
            getActualProject().setTapeEx(mainWindowController.getValueTapeEx());
            if(Utils.StringWhitelist(getActualProject().getAlph(), getActualProject().getTapeEx())){
                RunScene runScene = new RunScene(this, pane, getActualProject().getName(), getActualProject().getInitState(), getActualProject().getTapeEx(), mainWindowController.getTableRules());
                primaryStage.getScene().setRoot(runScene.getPane());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //Getter & Setter---------------------------------------------------------------------------------------------------


    public Project getActualProject() {
        if(projects != null){
            for(int i = 0; i < projects.size(); i++){
                if(projects.get(i).isActive()){
                    mainWindowController.setlWarningVisibility(false);
                    return  projects.get(i);
                }
            }
            mainWindowController.setlWarningVisibility(true);
        }
        return null;
    }

    public void setSavManager(SAVManager savManager) {
        this.savManager = savManager;
    }

    public void setProjects(ObservableList<Project> projects) {
        this.projects = projects;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public MainController getMainWindowController() {
        return mainWindowController;
    }
}
