package core;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Project {

    private ArrayList<Character> alph;
    private String name;
    private String initState;
    private String tapeEx;
    private final ObservableList<State> states = FXCollections.observableArrayList(new State("state1"));

    public Project(ArrayList<Character> alph, String name, String initState, String tapeEx) {
        this.alph = alph;
        this.name = name;
        this.initState = initState;
        this.tapeEx = tapeEx;
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
}
