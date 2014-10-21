package sample.Model.access.User;

import sample.Model.entities.Role;
import sample.Model.entities.User;
import sample.cr.una.pesistence.access.ORCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Casa on 12/10/2014.
 */
public class PlainDBARole {
    private static Connection connection;
    private static PreparedStatement pps;
    private static PreparedStatement rUserRole;

    static {
        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "SELECT ROLE , PASSWORD_REQUIRED , AUTHENTICATION_TYPE FROM DBA_ROLES";
            String retrieveUserRoleQuery = "select * from DBA_ROLE_PRIVS where GRANTEE = ?";
            pps = connection.prepareStatement(sql);
            rUserRole = connection.prepareStatement(retrieveUserRoleQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Role> retrieveRoles() {
        List<Role> roles = new ArrayList<>();
        try {
            if (ORCConnection.Instance().isInitialized()) {
                ResultSet rs = pps.executeQuery();
                while (rs.next()) {
                    String name = rs.getString(1);
                    String passreq = rs.getString(2);
                    String auth = rs.getString(3);
                    Role r = new Role(name, passreq, auth);
                    roles.add(r);
                }
                return roles;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Role> retrieveUserRole(User u) throws SQLException {
        ArrayList<Role> roles = new ArrayList<>();
        rUserRole.setString(1, u.getUSERNAME().toUpperCase());
        ResultSet rsRoles = rUserRole.executeQuery();
        while (rsRoles.next()) {
            String roleName = rsRoles.getString("GRANTED_ROLE");
            Role r = new Role(roleName, "true", "none");
            roles.add(r);
        }
        return roles;
    }
}
