package core;

import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.HashMap;
import java.util.Map;

public class State {

    private final SimpleStringProperty name;
    private final SimpleMapProperty<Character, String> mapProperty;
    private boolean active = false;

    public State(String name) {
        this.name = new SimpleStringProperty(name);
        mapProperty = new SimpleMapProperty<Character, String>(FXCollections.observableHashMap());
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String toString(){
        return name.get();
    }

    public void addAlph(char c){
        if(!mapProperty.containsKey(c)){
            mapProperty.put(c, "null");
        }
    }

    public void setValue(char c, String s){
        mapProperty.put(c, s);
    }

    public ObservableMap<Character, String> getMapProperty() {
        return mapProperty.get();
    }

    public SimpleMapProperty<Character, String> mapPropertyProperty() {
        return mapProperty;
    }

    public void setMapProperty(ObservableMap<Character, String> mapProperty) {
        this.mapProperty.set(mapProperty);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
