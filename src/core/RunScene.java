package core;

import controllers.RunController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class RunScene {

    private AnchorPane pane;
    private RunController runController;
    private double speed;
    private AnchorPane origin;
    private Turing turing;
    private int running = 0;
    private Machine machine;
    private ArrayList<Character> alph;
    private String tapeEx;
    private String initState;
    private boolean justUntilNextState;
    private int movedDelta;
    public enum Direction{LEFT, RIGHT};

    public RunScene(Turing turing, AnchorPane origin, String name, ArrayList<Character> alph, String initState, String tapeEx, TableView rules)throws Exception{
        this.origin = origin;
        this.turing = turing;
        this.alph = alph;
        this.tapeEx = tapeEx;
        this.initState = initState;
        justUntilNextState = false;
        speed = 0.5;
        movedDelta = 0;
        FXMLLoader loader = new FXMLLoader(Turing.class.getResource("../windows/RunWindow.fxml"));
        pane = loader.load();
        runController = loader.getController();
        runController.setlName(name);
        runController.setRunScene(this);
        for(int i = 0; i < rules.getColumns().size(); i++){
            runController.setTableRulesCol((TableColumn) rules.getColumns().get(i));
        }
        runController.setTableRulesRow(rules.getItems());
        machine = new Machine(this, alph, initState, tapeEx, turing.getStates());
    }

    public void actionStartPause(){
        switch (running){
            case 0:
                runStart();
                break;
            case 1:
                runResume();
                break;
            case 2:
                runPause();
        }
    }

    public void runStart(){
        machine.start();
        running = 2;
    }

    public void runResume(){
        machine.resume();
        running = 2;
    }

    public void runPause(){
        machine.suspend();
        running = 1;
    }

    public void render(Tape tape, int pointer, Direction dir){
        int count = 0;
        if(dir == Direction.LEFT){
            if(movedDelta >= -3){
                movedDelta--;
            }
        }else if(dir == Direction.RIGHT){
            if(movedDelta <= 3){
                movedDelta++;
            }
        }
        for(int i = pointer - (7 + movedDelta); i <= pointer + (8 + movedDelta); i++){
            if(i == pointer){
                runController.setTaPointer(count);
            }
            runController.setTa(count, tape.getValue(i));
            count++;
        }
    }

    public void goToOrigin(){
        machine.stop();
        turing.getPrimaryStage().getScene().setRoot(origin);
    }

    public AnchorPane getPane() {
        return pane;
    }

    public void reset(){
        machine.stop();
        running = 0;
        speed = 0.5;
        movedDelta = 0;
        runController.setlFinished("");
        machine = new Machine(this, alph, initState, tapeEx, turing.getStates());
    }

    public void finished(){
        runController.setlFinished("finished");
        System.out.println("finished");
        machine.stop();
    }

    public void setSpeed(double slValue){
            speed = slValue/100;
    }

    public double getSpeed() {
        return speed;
    }

    public Machine getMachine() {
        return machine;
    }

    public boolean isJustUntilNextState() {
        return justUntilNextState;
    }

    public void setJustUntilNextState(boolean justUntilNextState) {
        this.justUntilNextState = justUntilNextState;
    }
}
