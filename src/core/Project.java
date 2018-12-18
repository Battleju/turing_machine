package core;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import utils.Utils;

import java.io.*;
import java.util.ArrayList;

public class Project {

    private ArrayList<Character> alph;
    private String name;
    private String initState;
    private String tapeEx;
    private final ObservableList<State> states = FXCollections.observableArrayList(new State("state1"));
    private boolean active;
    private TableView rules;

    public Project() {
        active = false;
        alph = new ArrayList<>();
        alph.add('#');
        name = "new project";
        initState = "";
        tapeEx = "#";
    }

    public void save(){
        String fileName = name + ".tmsSAV";
        String path = "./../saves/";
        PrintWriter pWriter = null;
        try
        {
            try {
                pWriter = new PrintWriter(new BufferedWriter(new FileWriter(path + fileName)));
            }catch (FileNotFoundException ex){
                pWriter = new PrintWriter(new BufferedWriter(new FileWriter("saves/" + fileName)));
            }

            pWriter.println(name);
            pWriter.println(initState);
            pWriter.println(tapeEx);
            String bufferAlph = "";
            for (int i = 0; i < alph.size(); i++){
                bufferAlph += "" + alph.get(i);
            }
            pWriter.println(bufferAlph);
            pWriter.println(Utils.booleanToString(active));

            for (int i = 0; i < states.size(); i++){
                pWriter.println(states.get(i).getSaveString());
            }
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        finally
        {
            if (pWriter != null)
            {
                pWriter.flush();
                pWriter.close();
            }
        }
    }

    public ArrayList<Character> getAlph() {
        return alph;
    }

    public void setAlph(ArrayList<Character> alph) {
        this.alph = alph;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitState() {
        return initState;
    }

    public void setInitState(String initState) {
        this.initState = initState;
    }

    public String getTapeEx() {
        return tapeEx;
    }

    public void setTapeEx(String tapeEx) {
        this.tapeEx = tapeEx;
    }

    public ObservableList<State> getStates() {
        return states;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public TableView getRules() {
        return rules;
    }

    public void setRules(TableView rules) {
        this.rules = rules;
    }
}
