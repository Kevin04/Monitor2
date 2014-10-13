package sample.Model.access.User;

import sample.Model.entities.DBA_Roles;
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
public class DBA_Roles_Access {
    private static Connection connection;
    private static PreparedStatement pps;

    static {
        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "select * from dba_role_privs order by grantee";
            pps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*select username,privilege,role from role_sys_privs,user_role_privs where granted_role=role;*/

    public static List<DBA_Roles> retrieveRoles() {
        List<DBA_Roles> table_DBA_Roles = new ArrayList<>();
        try {
            if (ORCConnection.Instance().isInitialized()) {
                ResultSet rs = pps.executeQuery();
                while (rs.next()) {
                    String grantee = rs.getString(1);
                    String granted_Role = rs.getString(2);
                    String account_Option = rs.getString(3);
                    String default_Role = rs.getString(4);
                    DBA_Roles tbs = new DBA_Roles(grantee, granted_Role, account_Option, default_Role);
                    table_DBA_Roles.add(tbs);
                }
                return table_DBA_Roles;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
