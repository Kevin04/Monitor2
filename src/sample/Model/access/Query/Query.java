package sample.Model.access.Query;

import sample.Model.entities.Privilege;
import sample.Model.entities.Role;
import sample.Model.entities.TableSpace;
import sample.Model.entities.User;
import sample.cr.una.pesistence.access.ORCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Kevin on 11/10/2014.
 */
public class Query {
    private static boolean initialzed = false;
    private static Connection connection;
    private static PreparedStatement pps;

    public static void InitializeQueryExecutor() throws SQLException {
        initialzed = true;
        connection = ORCConnection.Instance().getOrcConnection();
    }

    public static boolean profileUnlimited(){
        try {
            String sql = "CREATE PROFILE 'SinExp' LIMIT" +
                    "SESSIONS_PER_USER UNLIMITED" +
                    "CPU_PER_SESSION UNLIMITED" +
                    "CPU_PER_CALL UNLIMITED" +
                    "CONNECT_TIME UNLIMITED" +
                    "IDLE_TIME UNLIMITED" +
                    "LOGICAL_READS_PER_SESSION UNLIMITED" +
                    "LOGICAL_READS_PER_CALL UNLIMITED" +
                    "COMPOSITE_LIMIT UNLIMITED" +
                    "PRIVATE_SGA UNLIMITED" +
                    "FAILED_LOGIN_ATTEMPTS 10" +
                    "PASSWORD_LIFE_TIME UNLIMITED" +
                    "PASSWORD_REUSE_TIME UNLIMITED" +
                    "PASSWORD_REUSE_MAX UNLIMITED" +
                    "PASSWORD_LOCK_TIME 1" +
                    "PASSWORD_GRACE_TIME 7" +
                    "PASSWORD_VERIFY_FUNCTION NULL";
            pps = connection.prepareStatement(sql);
            int i = pps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean profile30days(){
        try {
            String sql = "CREATE PROFILE 'days30' LIMIT" +
                    "SESSIONS_PER_USER UNLIMITED" +
                    "CPU_PER_SESSION UNLIMITED" +
                    "CPU_PER_CALL UNLIMITED" +
                    "CONNECT_TIME UNLIMITED" +
                    "IDLE_TIME UNLIMITED" +
                    "LOGICAL_READS_PER_SESSION UNLIMITED" +
                    "LOGICAL_READS_PER_CALL UNLIMITED" +
                    "COMPOSITE_LIMIT UNLIMITED" +
                    "PRIVATE_SGA UNLIMITED" +
                    "FAILED_LOGIN_ATTEMPTS 10" +
                    "PASSWORD_LIFE_TIME 30" +
                    "PASSWORD_REUSE_TIME UNLIMITED" +
                    "PASSWORD_REUSE_MAX UNLIMITED" +
                    "PASSWORD_LOCK_TIME 1" +
                    "PASSWORD_GRACE_TIME 7" +
                    "PASSWORD_VERIFY_FUNCTION NULL";
            pps = connection.prepareStatement(sql);
            int i = pps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean profile90days(){
        try {
            String sql = "CREATE PROFILE 'days90' LIMIT" +
                    "SESSIONS_PER_USER UNLIMITED" +
                    "CPU_PER_SESSION UNLIMITED" +
                    "CPU_PER_CALL UNLIMITED" +
                    "CONNECT_TIME UNLIMITED" +
                    "IDLE_TIME UNLIMITED" +
                    "LOGICAL_READS_PER_SESSION UNLIMITED" +
                    "LOGICAL_READS_PER_CALL UNLIMITED" +
                    "COMPOSITE_LIMIT UNLIMITED" +
                    "PRIVATE_SGA UNLIMITED" +
                    "FAILED_LOGIN_ATTEMPTS 10" +
                    "PASSWORD_LIFE_TIME 90" +
                    "PASSWORD_REUSE_TIME UNLIMITED" +
                    "PASSWORD_REUSE_MAX UNLIMITED" +
                    "PASSWORD_LOCK_TIME 1" +
                    "PASSWORD_GRACE_TIME 7" +
                    "PASSWORD_VERIFY_FUNCTION NULL";
            pps = connection.prepareStatement(sql);
            int i = pps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean modiPerfil(String user, String perfil){
        try{
            String sql = "ALTER USER"+user+"PROFILE"+perfil;
            pps = connection.prepareStatement(sql);
            int i = pps.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean crearUsuario(String user, String pass, String tspace,String tmptbs, String quota) {
        try {
            String sql = "create user " + user + " identified by " + pass + " default tablespace " + tspace + " quota " + quota +" ON "+ tspace+ " TEMPORARY TABLESPACE "+ tmptbs;
            pps = connection.prepareStatement(sql);
            int i = pps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean grantRoleToUser(Role r, User u){
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("GRANT ").append(r.getRole()).append(" TO ").append(u.getUSERNAME());
            pps = connection.prepareStatement(sb.toString());
            pps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    public static String crearRole(String rol) {
        try {
            StringBuilder sb= new StringBuilder();
            sb.append("create role ");
            sb.append(rol);
            pps = connection.prepareStatement(sb.toString());
            pps.executeUpdate();
            return "Role created";
        } catch (SQLException e) {
            return e.toString().substring(44,e.toString().length());
        }
    }

    public static String alterRole(String rol) {
        try {
            StringBuilder sb= new StringBuilder();
            sb.append("alter role ");
            sb.append(rol);
            pps = connection.prepareStatement(sb.toString());
            pps.executeUpdate();
            return "alter complete";

        } catch (SQLException e) {
            return e.toString().substring(44,e.toString().length());
        }
    }

    public static boolean priviletoUser(Privilege p, User u){
        String sql = p.ToUserQuery(u);
       try{
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
           return true;
        } catch (SQLException e) {
            e.printStackTrace();
           return false;
       }
    }

    public static boolean quotatoUser(User u, String quota, TableSpace tbs) {
        String sql = "ALTER USER " + u.getUSERNAME() + " QUOTA " + quota + " ON " + tbs.getName();
        try {
            connection.createStatement().executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String privilegeObjectToRole(String role, String privileges,String table){
        StringBuilder sb= new StringBuilder();
        sb.append("GRANT ");
        sb.append(privileges);
        sb.append(" ON ");
        sb.append(table);
        sb.append(" TO ");
        sb.append(role);
        try{

            pps = connection.prepareStatement(sb.toString());
            pps.executeUpdate();
            return "privilege(s) assigned";

        } catch (SQLException e) {
            return e.toString().substring(44,e.toString().length());
        }
    }

    public static String privilegeObjectToRole(String role, String privileges){
        StringBuilder sb= new StringBuilder();
        sb.append("GRANT ");
        sb.append(privileges);
        sb.append(" TO ");
        sb.append(role);
        try{

            pps = connection.prepareStatement(sb.toString());
            pps.executeUpdate();
            return "privilege(s) assigned";

        } catch (SQLException e) {
            return e.toString().substring(44,e.toString().length());
        }
    }

    public static String crearRole(String rol, String action) {
        try {
            StringBuilder sb= new StringBuilder();
            sb.append("create role ");
            sb.append(rol);
            sb.append(" ");
            sb.append(action);
            pps = connection.prepareStatement(sb.toString());
            pps.executeUpdate();
            return "role created";
        } catch (SQLException e) {
            return e.toString().substring(44,e.toString().length());
        }
    }

    public static String alterRole(String rol, String action) {
        try {
            StringBuilder sb= new StringBuilder();
            sb.append("alter role ");
            sb.append(rol);
            sb.append(" ");
            sb.append(action);
            System.out.println(sb.toString());
            pps = connection.prepareStatement(sb.toString());
            pps.executeUpdate();
            return "alter role";
        } catch (SQLException e) {
            return e.toString().substring(44,e.toString().length());
        }
    }

    public static void grantAll(String user) {
        try {
            String sql = "grant ALL PRIVILEGES to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantCREATESESSION(String user) {
        try {
            String sql = "grant CREATE SESSION to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantConnect(String user) {
        try {
            String sql = "grant CONNECT to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantSelect(String user) {
        try {
            String sql = "grant SELECT to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantInsert(String user) {
        try {
            String sql = "grant INSERT to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantDrop(String user) {
        try {
            String sql = "grant Drop to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantCreateUser(String user) {
        try {
            String sql = "grant CREATE USER to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantCreateRole(String user) {
        try {
            String sql = "grant CREATE ROLE to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantCreateAnyTable(String user) {
        try {
            String sql = "grant CREATE ANY TABLE to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantDelete(String user) {
        try {
            String sql = "grant DELETE to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantUpdate(String user) {
        try {
            String sql = "grant UPDATE to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantResource(String user) {
        try {
            String sql = "grant RESOURCE to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantDropUser(String user) {
        try {
            String sql = "grant DROP USER to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantDropRole(String rol) {
        try {
            String sql = "grant DROP ROLE to" + "$rol";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantRole(String user, String rol) {
        try {
            String sql = "grant" + "$rol" + "to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantCREATEVIEW(String user, String rol) {
        try {
            String sql = "grant CREATE VIEW to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantEXECUTE(String user, String rol) {
        try {
            String sql = "grant EXECUTE to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantINDEX(String user, String rol) {
        try {
            String sql = "grant INDEX to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantSHOWDATABASES(String user, String rol) {
        try {
            String sql = "grant SHOW DATABASES to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantSHOVIEW(String user, String rol) {
        try {
            String sql = "grant SHOW VIEW to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantTRIGGER(String user, String rol) {
        try {
            String sql = "grant TRIGGER to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantALTER(String user, String rol) {
        try {
            String sql = "grant ALTER to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantCREATETEMPORARYTABLES(String user, String rol) {
        try {
            String sql = "grant CREATE TEMPORARY TABLES to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantALTERROUTINE(String user, String rol) {
        try {
            String sql = "grant ALTER ROUTINE to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantCREATEROUTINE(String user, String rol) {
        try {
            String sql = "grant CREATE ROUTINE to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void grantFILE(String user, String rol) {
        try {
            String sql = "grant FILE to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //REVOKES
    public static void revokeRole(String user, String rol) {
        try {
            String sql = "revoke" + "$rol" + "from" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeConnect(String user) {
        try {
            String sql = "revoke CONNECT from" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeSelect(String user) {
        try {
            String sql = "revoke SELECT from" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeInsert(String user) {
        try {
            String sql = "revoke INSERT from" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeDrop(String user) {
        try {
            String sql = "revoke Drop from" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeCreateUser(String user) {
        try {
            String sql = "revoke CREATE USER from" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeCreateRole(String user) {
        try {
            String sql = "revoke CREATE ROLE from" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeCreateAnyTable(String user) {
        try {
            String sql = "revoke CREATE ANY TABLE from" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeDelete(String user) {
        try {
            String sql = "revoke DELETE from" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeUpdate(String user) {
        try {
            String sql = "revoke UPDATE from" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeResource(String user) {
        try {
            String sql = "revoke RESOURCE from" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeDropUser(String user) {
        try {
            String sql = "revoke DROP USER from" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeDropRole(String rol) {
        try {
            String sql = "revoke DROP ROLE from" + "$rol";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeCREATESESSION(String rol) {
        try {
            String sql = "revoke CREATE SESSION from" + "$rol";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeAll(String rol) {
        try {
            String sql = "revoke ALL PRIVILEGES from" + "$rol";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeCREATEVIEW(String user, String rol) {
        try {
            String sql = "revoke CREATE VIEW to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeEXECUTE(String user, String rol) {
        try {
            String sql = "revoke EXECUTE to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeINDEX(String user, String rol) {
        try {
            String sql = "revoke INDEX to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeSHOWDATABASES(String user, String rol) {
        try {
            String sql = "revoke SHOW DATABASES to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeSHOVIEW(String user, String rol) {
        try {
            String sql = "revoke SHOW VIEW to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeTRIGGER(String user, String rol) {
        try {
            String sql = "revoke TRIGGER to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeALTER(String user, String rol) {
        try {
            String sql = "revoke ALTER to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeCREATETEMPORARYTABLES(String user, String rol) {
        try {
            String sql = "revoke CREATE TEMPORARY TABLES to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeALTERROUTINE(String user, String rol) {
        try {
            String sql = "revoke ALTER ROUTINE to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void revokeCREATEROUTINE(String user, String rol) {
        try {
            String sql = "revoke CREATE ROUTINE to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void REVOKEFILE(String user, String rol) {
        try {
            String sql = "revoke FILE to" + "$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isInitialzed() {
        return initialzed;
    }


    public static String grantRoletoRole(String parent,String son) {
        try {
            StringBuilder sb= new StringBuilder();
            sb.append("grant ");
            sb.append(parent);
            sb.append(" to ");
            sb.append(son);
            pps = connection.prepareStatement(sb.toString());
            pps.executeUpdate();
            return "Role assigned\n";
        } catch (SQLException e) {
            return e.toString().substring(44,e.toString().length());
        }
    }
    public static String dropRole(String role){
        try {
            StringBuilder sb= new StringBuilder();
            sb.append("drop ");
            sb.append("role ");
            sb.append(role);
            pps = connection.prepareStatement(sb.toString());
            pps.executeUpdate();
            return "Role drop.\n";
        } catch (SQLException e) {
            return e.toString().substring(44,e.toString().length());
        }
    }
}
