package sample.Model.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Casa on 12/10/2014.
 */
public class ObjectPrivilege extends Privilege {
    public static final List<Privilege> objectsPrivilege = Arrays.asList(
            new ObjectPrivilege("DELETE",""),
            new ObjectPrivilege("EXECUTE",""),
            new ObjectPrivilege("FLUSH",""),
            new ObjectPrivilege("INDEX",""),
            new ObjectPrivilege("INSERT",""),
            new ObjectPrivilege("LOAD",""),
            new ObjectPrivilege("REFERENCES",""),
            new ObjectPrivilege("REFRESH",""),
            new ObjectPrivilege("SELECT",""),
            new ObjectPrivilege("UNLOAD",""),
            new ObjectPrivilege("UPDATE","")
    );
    StringProperty on = new SimpleStringProperty();

    public ObjectPrivilege(String priv, String on) {
        super(priv);
        this.on.set(on);
    }
    public ObjectPrivilege(ObjectPrivilege p) {
        super(p);
        this.on.set(p.getOn());
    }
    public String getOn() {
        return on.get();
    }

    public StringProperty onProperty() {
        return on;
    }

    public void setOn(String on) {
        this.on.set(on);
    }
    @Override
    public String toString(){
        return this.getPrivilege().toString();
    }
    @Override
    public String ToUserQuery(User u){
        return new StringBuilder().append("GRANT ").append(toString()).append(" ON ").append(this.getOn()).append(" TO ").append(u.getUSERNAME()).toString();
    }
}
