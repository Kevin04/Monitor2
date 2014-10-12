package sample.Model.entities;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Casa on 15/09/2014.
 */
public class Table {
    private StringProperty name = new SimpleStringProperty();
    private StringProperty tableSpace = new SimpleStringProperty();
    private StringProperty owner = new SimpleStringProperty();
    public Table(String name, String owner, String tableSpace) {
        this.name.set(name);
        this.owner.set(owner);
        this.tableSpace.set(tableSpace);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getTableSpace() {
        return tableSpace.get();
    }

    public StringProperty tableSpaceProperty() {
        return tableSpace;
    }

    public void setTableSpace(String tableSpace) {
        this.tableSpace.set(tableSpace);
    }

    public StringProperty OwnerProperty() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner.set(owner);
    }

    public String getOwner(){
        return owner.get();
    }
}
