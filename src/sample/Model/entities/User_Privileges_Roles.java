package sample.Model.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.access.User.DBA_Roles_Access;
import sample.Model.access.User.User_Privileges_Roles_Access;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by JoséPablo on 11/10/2014.
 */
public class User_Privileges_Roles {

    StringProperty Username = new SimpleStringProperty();
    StringProperty Privilege=new SimpleStringProperty();
    StringProperty Role=new SimpleStringProperty();

    public User_Privileges_Roles() {
    }

    public User_Privileges_Roles(String username, String privilege, String role) {
        Username.set(username);
        Privilege.set(privilege);
        Role.set(role);
    }

    //----
    static boolean stop = false;
    public static List<User_Privileges_Roles> user_privileges = new ArrayList<>();
    static ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
    static Runnable tableSpacesRetriever;

    public static void begin(){
        user_privileges = User_Privileges_Roles_Access.retrieveTableSpaces();
        tableSpacesRetriever = ()->{
            try {
                if (stop) return;
                List<User_Privileges_Roles> l = User_Privileges_Roles_Access.retrieveTableSpaces();
                user_privileges.clear();
                user_privileges.addAll(l);
            }catch (Exception e){ e.printStackTrace();}

        };
        executor.scheduleAtFixedRate(tableSpacesRetriever,5,5, TimeUnit.MINUTES);

    }

    public static void end() throws InterruptedException {
        stop = true;
        executor.awaitTermination(100, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }

    public ObservableList getTables() {
        return tables;
    }

    public void setTables(ObservableList tables) {
        this.tables = tables;
    }

    ObservableList tables = FXCollections.observableList(new ArrayList<>());


    //---------------------

    @Override
    public String toString() {
        return "User_Privileges_Roles{" +
                "Username=" + Username +
                ", Privilege=" + Privilege +
                ", Role=" + Role +
                '}';
    }

    public String getUsername() {
        return Username.get();
    }

    public StringProperty usernameProperty() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username.set(username);
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

    public String getRole() {
        return Role.get();
    }

    public StringProperty roleProperty() {
        return Role;
    }

    public void setRole(String role) {
        this.Role.set(role);
    }
}
