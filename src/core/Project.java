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
    private boolean saved;

    private ArrayList<Character> alphOld;
    private String nameOld;
    private String initStateOld;
    private String tapeExOld;
    private final ObservableList<State> statesOld;

    public Project() {
        active = false;
        alph = new ArrayList<>();
        alph.add('#');
        name = "new project";
        initState = "";
        tapeEx = "#";
        saved = false;

        alphOld = alph;
        nameOld = name;
        initStateOld = initState;
        tapeExOld = tapeEx;
        statesOld = states;
    }

    public void save(){
        saved = true;
        System.out.println(saved);
        String fileName = name + ".tmsSAV";
        String path = "./../saves/";
        PrintWriter pWriter = null;
        try
        {
            try {
                pWriter = new PrintWriter(new BufferedWriter(new FileWriter(path + fileName)));
            }catch (FileNotFoundException ex1){
                try {
                    pWriter = new PrintWriter(new BufferedWriter(new FileWriter("saves/" + fileName)));
                }catch (FileNotFoundException ex2){
                    File f = new File("saves/");
                    f.mkdir();
                    save();
                }

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

    public void saveAt(File file){
        String fileName = name + ".tmsSAV";
        String path = "./../saves/";
        PrintWriter pWriter = null;
        try
        {
            try {
                pWriter = new PrintWriter(file);
            }catch (FileNotFoundException ex){
                pWriter = new PrintWriter(file);
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
        alphOld = this.alph;
        this.alph = alph;
        System.out.println(alphOld.size() + " " + alph.size());
        if(alphOld.size() - 1 != (alph.size())){
            saved = false;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        nameOld = this.name;
        this.name = name;
        if(!name.equals(nameOld)){
            saved = false;
        }
    }

    public String getInitState() {
        return initState;
    }

    public void setInitState(String initState) {
        initStateOld = this.initState;
        this.initState = initState;
        if(!initState.equals(initStateOld)){
            saved = false;
        }
    }

    public String getTapeEx() {
        return tapeEx;
    }

    public void setTapeEx(String tapeEx) {
        tapeExOld = this.tapeEx;
        this.tapeEx = tapeEx;
        if(!tapeEx.equals(tapeExOld)){
            saved = false;
        }
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

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
