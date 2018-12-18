package utils;

import core.Project;

import java.io.*;
import java.util.ArrayList;

public class SAVLoader {

    String path = "./saves";
    ArrayList<Project> projects;

    public SAVLoader() {
        searchDirectory();
    }

    private void searchDirectory(){
        projects = new ArrayList<>();
        File f = new File(path);
        String[] fileArray = f.list();
        readData(fileArray);
        //System.out.println(fileArray[0]);
    }

    private void readData(String[] fileArray){
        try {

            FileReader fr = null;
            fr = new FileReader(path + "/" + fileArray[0]);
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
                    System.out.println(states.get(i).get(j));
                }
            }

            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createProject(String projectName, String initProject, String tapeEx, String alph, String active, ArrayList<ArrayList<String>> states){

    }
}
