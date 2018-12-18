package utils;

import core.Project;
import core.State;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SAVLoader {

    String path = "./../saves";
    ArrayList<Project> projects;

    public SAVLoader() {
        searchDirectory();
    }

    private void searchDirectory(){
        projects = new ArrayList<>();
        File f = new File(path);
        String[] fileArray = f.list();
        if(fileArray == null){
            f = new File("./saves");
            fileArray = f.list();
        }
        try{
            for(int i = 0; i < fileArray.length; i++){
                readData(fileArray[i]);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void readData(String file){
        try {

            FileReader fr = null;
            try {
                fr = new FileReader(path + "/" + file);
            } catch (FileNotFoundException ex){
                fr = new FileReader("./saves" + "/" + file);
            }

            BufferedReader br = new BufferedReader(fr);

            String projectName = br.readLine();
            String initProject = br.readLine();
            String tapeEx = br.readLine();
            String alph = br.readLine();
            String active = br.readLine();

            ArrayList<ArrayList<String>> states = new ArrayList<>();
            boolean reading = true;
            while (reading){
                String readLine = br.readLine();
                if(readLine == null){
                    break;
                }else if(readLine.trim().equals("-")){
                    states.add(new ArrayList<>());
                }else {
                    states.get(states.size() - 1).add(readLine);
                }
            }
            System.out.println(projectName);
            System.out.println(initProject);
            System.out.println(tapeEx);
            System.out.println(alph);
            System.out.println(active);
            for (int i = 0; i < states.size(); i++){
                for (int j = 0; j < states.get(i).size(); j++){
                    if(states.get(i).get(j).trim().equals("")){
                        states.get(i).remove(j);
                    }
                }
            }
            for (int i = 0; i < states.size(); i++){
                for (int j = 0; j < states.get(i).size(); j++){
                    System.out.println(states.get(i).get(j));
                }
            }
            br.close();

            createProject(projectName, initProject, tapeEx, alph, active, states);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createProject(String projectName, String initProject, String tapeEx, String alph, String active, ArrayList<ArrayList<String>> statesString){
        Project project = new Project();
        project.setName(projectName);
        project.setInitState(initProject);
        project.setTapeEx(tapeEx);

        ArrayList<Character> alphArray = new ArrayList<Character>();
        for (char c : alph.toCharArray()) {
            alphArray.add(c);
        }
        project.setAlph(alphArray);

        ArrayList<State> states = new ArrayList<>();

        project.setActive(Utils.stringToBoolean(active));
        for (int i = 0; i < statesString.size(); i++){
            states.add(new State(statesString.get(i).get(0)));
            for (int j = 1; j < statesString.get(i).size(); j++){
                char key;
                String line = statesString.get(i).get(j);
                List<String> lineSpliited = Arrays.asList(line.split("-"));
                key = lineSpliited.get(0).trim().charAt(0);
                states.get(i).getMapProperty().put(key, lineSpliited.get(1));
            }
        }

        project.getStates().clear();
        project.getStates().setAll(states);

        TableView rules = new TableView();
        rules.getColumns().clear();
        TableColumn statesCol = new TableColumn("state");
        statesCol.setCellValueFactory(new PropertyValueFactory<State, String>("name"));
        rules.getColumns().addAll(statesCol);
        for(int i = 0; i < alphArray.size(); i++){
            final int j = i;
            TableColumn tc = new TableColumn("" + alphArray.get(i));
            tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<State,String>, ObservableValue<String>>(){
                public ObservableValue<String> call(TableColumn.CellDataFeatures<State, String> param) {
                    return new SimpleStringProperty(param.getValue().mapPropertyProperty().get(alphArray.get(j)));
                }
            });
            rules.getColumns().addAll(tc);
        }

        ObservableList<State> observableList = FXCollections.observableArrayList(states);
        rules.setItems(observableList);
        project.setRules(rules);

        projects.add(project);
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }
}
