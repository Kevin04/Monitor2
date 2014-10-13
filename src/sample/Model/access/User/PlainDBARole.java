package sample.Model.access.User;

import sample.Model.entities.Role;
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

    static {
        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "SELECT ROLE , PASSWORD_REQUIRED , AUTHENTICATION_TYPE FROM DBA_ROLES";
            pps = connection.prepareStatement(sql);
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
}
