package sample.Model.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Casa on 12/10/2014.
 */
public class Role {
    StringProperty Role = new SimpleStringProperty();
    StringProperty PassRequired = new SimpleStringProperty();
    StringProperty Authentication_type = new SimpleStringProperty();

    public Role(String role, String passRequired, String authentication_type) {
        Role.setValue(role);
        PassRequired.set(passRequired);
        Authentication_type.setValue(authentication_type);
    }

    public String getRole() {
        return Role.get();
    }

    public StringProperty roleProperty() {
        return Role;
    }

    public void setRole(String role) {
        this.Role.set(role);
    }

    public String getPassRequired() {
        return PassRequired.get();
    }

    public StringProperty passRequiredProperty() {
        return PassRequired;
    }

    public void setPassRequired(String passRequired) {
        this.PassRequired.set(passRequired);
    }

    public String getAuthentication_type() {
        return Authentication_type.get();
    }

    public StringProperty authentication_typeProperty() {
        return Authentication_type;
    }

    public void setAuthentication_type(String authentication_type) {
        this.Authentication_type.set(authentication_type);
    }
    @Override
    public String toString(){
        return this.getRole();
    }
}
