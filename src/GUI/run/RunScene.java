package GUI.run;

import core.Machine;
import GUI.main.MainScene;
import core.State;
import core.Tape;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class RunScene {

    private AnchorPane pane;
    private RunController runController;
    private double speed;
    private AnchorPane origin;
    private MainScene turing;
    private int running = 0;
    private Machine machine;
    private String tapeEx;
    private String initState;
    private boolean justUntilNextState;
    private int movedDelta;
    public enum Direction{LEFT, RIGHT}
    private TableView rules;

    public RunScene(MainScene turing, AnchorPane origin, String name, String initState, String tapeEx, TableView rules)throws Exception{
        this.origin = origin;
        this.turing = turing;
        this.tapeEx = tapeEx;
        this.initState = initState;
        this.rules = rules;
        justUntilNextState = false;
        speed = 0.5;
        movedDelta = 0;
        FXMLLoader loader = new FXMLLoader(RunScene.class.getResource("RunWindow.fxml"));
        pane = loader.load();
        runController = loader.getController();
        runController.setlName(name);
        runController.setRunScene(this);
        for(int i = 0; i < rules.getColumns().size(); i++){
            runController.setTableRulesCol((TableColumn) rules.getColumns().get(i));
        }
        runController.setTableRulesRow(rules.getItems());
        machine = new Machine(this, initState, tapeEx, turing.getActualProject().getStates());

        runController.getTableRules().setRowFactory(tv -> new TableRow<State>() {
            @Override
            public void updateItem(State item, boolean empty) {
                super.updateItem(item, empty) ;
                if (item == null) {
                    setStyle("");
                } else if (item.isActive()) {
                    setStyle("-fx-background-color: #039ed3;");
                } else {
                    setStyle("");
                }
            }
        });
    }

    public void setFocusOnTable(State actState){
        for (int i = 0; i < rules.getItems().size(); i++){
            State state = (State)rules.getItems().get(i);
            state.setActive(false);
        }
        for (int i = 0; i < rules.getItems().size(); i++){
            State state = (State)rules.getItems().get(i);
            if(state.getName().trim().equals(actState.getName().trim())){
                state.setActive(true);
            }
        }
        runController.getTableRules().refresh();
    }

    void actionStartPause(){
        switch (running){
            case 0:
                machine.start();
                running = 2;
                break;
            case 1:
                machine.resume();
                running = 2;
                break;
            case 2:
                machine.suspend();
                running = 1;
        }
    }

    public void render(Tape tape, int pointer, Direction dir){
        int count = 0;
        if(dir == Direction.LEFT){
            if(movedDelta >= -4){
                movedDelta--;
            }
        }else if(dir == Direction.RIGHT){
            if(movedDelta <= 4){
                movedDelta++;
            }
        }
        for(int i = pointer - (10 + movedDelta); i <= pointer + (10 + movedDelta); i++){
            if(i == pointer){
                runController.setTaPointer(count);
            }
            runController.setTa(count, tape.getValue(i));
            count++;
        }
    }

    void goToOrigin(){
        machine.stop();
        turing.getPrimaryStage().getScene().setRoot(origin);
    }

    public AnchorPane getPane() {
        return pane;
    }

    void reset(){
        machine.stop();
        running = 0;
        speed = 0.5;
        movedDelta = 0;
        runController.setlFinished("");
        machine = new Machine(this, initState, tapeEx, turing.getActualProject().getStates());
    }

    public void finished(){
        runController.setlFinished("finished");
        machine.stop();
    }

    void setSpeed(double slValue){
            speed = slValue/100;
    }

    public double getSpeed() {
        return speed;
    }

    Machine getMachine() {
        return machine;
    }

    public boolean isJustUntilNextState() {
        return justUntilNextState;
    }

    public void setJustUntilNextState(boolean justUntilNextState) {
        this.justUntilNextState = justUntilNextState;
    }
}
