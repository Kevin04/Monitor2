package sample.Model.entities;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Casa on 14/09/2014.
 */
public class TableSpace {
    private StringProperty name = new SimpleStringProperty();
    private StringProperty status = new SimpleStringProperty();
    private BooleanProperty isTemporary = new SimpleBooleanProperty();

    public boolean IsTemporary() {
        return isTemporary.get();
    }

    public BooleanProperty isTemporaryProperty() {
        return isTemporary;
    }

   /* public void setIsTemporary(boolean isTemporary) {
        this.isTemporary.set(isTemporary);
    }*/

    public TableSpace(String name,String status,boolean isTmp) {
        this.name.set(name);
        this.status.set(status);
        this.isTemporary.set(isTmp);
    }
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
