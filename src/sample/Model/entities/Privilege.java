package sample.Model.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Casa on 12/10/2014.
 */
public class Privilege {
    public static final List<Privilege> SystemPrivs = Arrays.asList(new Privilege("ADMIN"),
            new Privilege("ALTER ANY CACHE GROUP"),
            new Privilege("ALTER ANY INDEX"),
            new Privilege("ALTER ANY MATERIALIZED VIEW"),
            new Privilege("ALTER ANY PROCEDURE"),
            new Privilege("ALTER ANY SEQUENCE"),
            new Privilege("ALTER ANY TABLE"),
            new Privilege("ALTER ANY VIEW"),
            new Privilege("CACHE_MANAGER"),
            new Privilege("CREATE ANY CACHE GROUP"),
            new Privilege("CREATE ANY INDEX"),
            new Privilege("CREATE ANY MATERIALIZED VIEW"),
            new Privilege("CREATE ANY PROCEDURE"),
            new Privilege("CREATE ANY SEQUENCE"),
            new Privilege("CREATE ANY SYNONYM"),
            new Privilege("CREATE ANY TABLE"),
            new Privilege("CREATE ANY VIEW"),
            new Privilege("CREATE CACHE GROUP"),
            new Privilege("CREATE MATERIALIZED VIEW"),
            new Privilege("CREATE PROCEDURE"),
            new Privilege("CREATE PUBLIC SYNONYM"),
            new Privilege("CREATE SEQUENCE"),
            new Privilege("CREATE SESSION"),
            new Privilege("CREATE SYNONYM"),
            new Privilege("CREATE TABLE"),
            new Privilege("CREATE VIEW"),
            new Privilege("DELETE ANY TABLE"),
            new Privilege("DROP ANY CACHE GROUP"),
            new Privilege("DROP ANY INDEX"),
            new Privilege("DROP ANY MATERIALIZED VIEW"),
            new Privilege("DROP ANY PROCEDURE"),
            new Privilege("DROP ANY SEQUENCE"),
            new Privilege("DROP ANY SYNONYM"),
            new Privilege("DROP ANY TABLE"),
            new Privilege("DROP ANY VIEW"),
            new Privilege("DROP PUBLIC SYNONYM"),
            new Privilege("EXECUTE ANY PROCEDURE"),
            new Privilege("FLUSH ANY CACHE GROUP"),
            new Privilege("INSERT ANY TABLE"),
            new Privilege("LOAD ANY CACHE GROUP"),
            new Privilege("REFRESH ANY CACHE GROUP"),
            new Privilege("SELECT ANY SEQUENCE"),
            new Privilege("SELECT ANY TABLE"),
            new Privilege("UNLOAD ANY CACHE GROUP"),
            new Privilege("UPDATE ANY TABLE"),
            new Privilege("XLA"));
    StringProperty Privilege = new SimpleStringProperty();

    public Privilege(String privilege) {
        Privilege.setValue(privilege);
    }
    public Privilege(Privilege p){
        this.Privilege.set(p.getPrivilege());
    }

    public String getPrivilege() {
        return Privilege.get();
    }

    public StringProperty privilegeProperty() {
        return Privilege;
    }

    public void setPrivilege(String privilege) {
        this.Privilege.set(privilege);
    }
    @Override
    public String toString(){
        return this.getPrivilege();
    }

    public String ToUserQuery(User u){
        return new StringBuilder().append("GRANT ").append(this.getPrivilege()).append(" TO ").append(u.getUSERNAME()).toString();

    }
}
