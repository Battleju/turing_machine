package core;

import GUI.run.RunScene;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Machine extends Thread{

    private Tape tape;
    private ArrayList<Character> alph;
    private ObservableList<core.State> states;
    private String initState;
    private int pointer;
    private RunScene runScene;
    private core.State actState;
    private RunScene.Direction dir;

    public Machine(RunScene runScene, ArrayList<Character> alph, String initState, String tapeEx, ObservableList<core.State> states) {
        this.runScene = runScene;
        pointer = 0;
        this.alph = alph;
        this.initState = initState;
        this.states = states;
        tape = new Tape(tapeEx);
        runScene.render(tape, pointer, dir);
        for (int i = 0; i < states.size(); i++){
            if(states.get(i).getName().equals(initState)){
                actState = states.get(i);
            }
        }
    }

    public void run() {
        while (true){
            try {
                sleep(Math.round(1000 * (1 - runScene.getSpeed())));
                String rule = actState.getMapProperty().get(tape.getValue(pointer));
                System.out.println("running");
                if(check4End(rule)){
                    System.out.println("finsihed");
                    runScene.finished();
                }
                List<String> ruleSplitted = Arrays.asList(rule.split(","));
                    for (int i = 0; i < states.size(); i++){
                        if(!actState.getName().equals(ruleSplitted.get(0).trim()) && runScene.isJustUntilNextState()){
                            suspend();
                            runScene.setJustUntilNextState(false);
                        }
                        if(states.get(i).getName().equals(ruleSplitted.get(0).trim())){
                            actState = states.get(i);
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

    public void runOneTime(){
        try {
            sleep(Math.round(1000 * (1 - runScene.getSpeed())));
            String rule = actState.getMapProperty().get(tape.getValue(pointer));
            System.out.println("running");
            if(check4End(rule)){
                System.out.println("finsihed");
                runScene.finished();
            }
            List<String> ruleSplitted = Arrays.asList(rule.split(","));
            for (int i = 0; i < states.size(); i++){
                if(!actState.getName().equals(ruleSplitted.get(0).trim()) && runScene.isJustUntilNextState()){
                    suspend();
                    runScene.setJustUntilNextState(false);
                }
                if(states.get(i).getName().equals(ruleSplitted.get(0).trim())){
                    actState = states.get(i);
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

    private boolean check4End(String  rule){
        if(rule.trim().equals("null")){
            return true;
        }
        return false;
    }
}
