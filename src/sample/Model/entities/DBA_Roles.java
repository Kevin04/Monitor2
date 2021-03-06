package sample.Model.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.access.User.DBA_Roles_Access;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by JoséPablo on 11/10/2014.
 */
public class DBA_Roles {
    //TODO CAMBIAR TODO ESTO DONDE VA
    static boolean stop = false;
    public static List<DBA_Roles> DBA_Roles = new ArrayList<>();
    static ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
    static Runnable rolesRetriever;


    ObservableList Roles = FXCollections.observableList(new ArrayList<>());
    StringProperty Grantee = new SimpleStringProperty();
    StringProperty GrantedRole = new SimpleStringProperty();
    StringProperty Admin_Option = new SimpleStringProperty();
    StringProperty Default_Role = new SimpleStringProperty();

    public DBA_Roles() {
    }

    public DBA_Roles(String grantee, String grantedRole, String admin_Option, String default_Role) {
        Grantee.set(grantee);
        GrantedRole.set(grantedRole);
        Admin_Option.set(admin_Option);
        Default_Role.set(default_Role);
    }

    //..


    public static void begin() {
        DBA_Roles = DBA_Roles_Access.retrieveRoles();
        rolesRetriever = () -> {
            try {
                if (stop) return;
                List<DBA_Roles> l = DBA_Roles_Access.retrieveRoles();
                DBA_Roles.clear();
                DBA_Roles.addAll(l);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        executor.scheduleAtFixedRate(rolesRetriever, 5, 5, TimeUnit.MINUTES);
    }

    public static void end() throws InterruptedException {
        stop = true;
        executor.awaitTermination(100, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }

    public ObservableList getRoles() {
        return Roles;
    }

    public void setRoles(ObservableList roles) {
        this.Roles = roles;
    }
    //---------------------

    public String getGrantee() {
        return Grantee.get();
    }

    public StringProperty granteeProperty() {
        return Grantee;
    }

    public void setGrantee(String grantee) {
        this.Grantee.set(grantee);
    }

    public String getGrantedRole() {
        return GrantedRole.get();
    }

    public StringProperty grantedRoleProperty() {
        return GrantedRole;
    }

    public void setGrantedRole(String grantedRole) {
        this.GrantedRole.set(grantedRole);
    }

    public String getAdmin_Option() {
        return Admin_Option.get();
    }

    public StringProperty admin_OptionProperty() {
        return Admin_Option;
    }

    public void setAdmin_Option(String admin_Option) {
        this.Admin_Option.set(admin_Option);
    }

    public String getDefault_Role() {
        return Default_Role.get();
    }

    public StringProperty default_RoleProperty() {
        return Default_Role;
    }

    public void setDefault_Role(String default_Role) {
        this.Default_Role.set(default_Role);
    }

    @Override
    public String toString() {
        return "DBA_Roles{" +
                "Grantee=" + Grantee +
                ", GrantedRole=" + GrantedRole +
                ", Admin_Option=" + Admin_Option +
                ", Default_Role=" + Default_Role +
                '}';
    }
}
