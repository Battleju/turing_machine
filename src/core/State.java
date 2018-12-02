package core;

import javafx.beans.property.SimpleStringProperty;

public class State {

    private final SimpleStringProperty name;

    public State(String name) {
        this.name = new SimpleStringProperty(name);
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

}
