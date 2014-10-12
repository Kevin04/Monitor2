package sample.Model.access.User;

import javafx.collections.ObservableList;
import sample.Model.entities.DBA_Roles;
import sample.Model.entities.User;
import sample.Model.entities.User_Privileges_Roles;
import sample.cr.una.pesistence.access.ORCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoséPablo on 11/10/2014.
 */
public class User_Privileges_Roles_Access {

    private static Connection connection;
    private static PreparedStatement pps;

    static {
        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "select username,privilege,role from role_sys_privs,user_role_privs where granted_role=role order by username";
            pps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*select username,privilege,role from role_sys_privs,user_role_privs where granted_role=role;*/

    public static List<User_Privileges_Roles> retrieveUserPrivilegesRoles() {
        List<User_Privileges_Roles> table_User_Privileges_Roles = new ArrayList<>();
        try {
            if (ORCConnection.Instance().isInitialized()) {

                ResultSet rs = pps.executeQuery();
                while (rs.next()) {
                    String username = rs.getString(1);
                    String privilege = rs.getString(2);
                    String role = rs.getString(3);
                    User_Privileges_Roles tbs = new User_Privileges_Roles(username,privilege,role);
                    table_User_Privileges_Roles.add(tbs);
                }

                return table_User_Privileges_Roles;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
