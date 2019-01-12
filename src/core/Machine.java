package core;

import GUI.run.RunScene;
import javafx.collections.ObservableList;
import java.util.Arrays;
import java.util.List;

public class Machine extends Thread{

    private Tape tape;
    private ObservableList<core.State> states;
    private int pointer;
    private RunScene runScene;
    private core.State actState;
    private RunScene.Direction dir;
    private boolean running;

    public Machine(RunScene runScene, String initState, String tapeEx, ObservableList<core.State> states) {
        this.runScene = runScene;
        pointer = 0;
        running = true;
        this.states = states;
        tape = new Tape(tapeEx);
        runScene.render(tape, pointer, dir);
        for (core.State state : states) {
            if (state.getName().equals(initState)) {
                actState = state;
            }
        }
    }

    public void run() {
        while (running) {
            runAlgorithm();
        }
    }

    public void runAlgorithm(){
        try {
            sleep(Math.round(1000 * (1 - runScene.getSpeed())));
            String rule = actState.getMapProperty().get(tape.getValue(pointer));
            if(rule.trim().equals("null")){
                runScene.finished();
            }
            List<String> ruleSplitted = Arrays.asList(rule.split(","));
            for (core.State state : states) {
                if (!actState.getName().equals(ruleSplitted.get(0).trim()) && runScene.isJustUntilNextState()) {
                    checkAccess();
                    runScene.setJustUntilNextState(false);
                }
                if (state.getName().equals(ruleSplitted.get(0).trim())) {
                    actState = state;
                    runScene.setFocusOnTable(actState);
                }
            }

            tape.setValue(pointer, ruleSplitted.get(1).charAt(1));
            if(ruleSplitted.get(2).equals(" L")){
                pointer--;
                dir = RunScene.Direction.LEFT;
            }else if(ruleSplitted.get(2).equals(" R")){
                pointer++;
                dir = RunScene.Direction.RIGHT;
            }

        }
        catch(InterruptedException e) {
            System.out.println("Interrupted");
        }
        runScene.render(tape, pointer, dir);
    }
}
