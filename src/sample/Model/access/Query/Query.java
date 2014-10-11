package sample.Model.access.Query;

import sample.cr.una.pesistence.access.ORCConnection;
import java.sql.*;

/**
 * Created by Kevin on 11/10/2014.
 */
public class Query {

    private static Connection connection;
    private static PreparedStatement pps;

    public static void crearUsuario (String user,String pass,String tspace){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "create user"+"$user"+"identified by"+"$pass"+"default tablespace"+"$tspace"+"quota on"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void crearRole (String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "create role"+"$rol";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void crearRole (String rol,String pass){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "create role"+"$rol"+"identified by"+"$pass";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void grantAll(String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant ALL PRIVILEGES to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void grantCREATESESSION (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant CREATE SESSION to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void grantConnect (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant CONNECT to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void grantSelect (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant SELECT to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void grantInsert (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant INSERT to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void grantDrop (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant Drop to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void grantCreateUser (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant CREATE USER to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void grantCreateRole (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant CREATE ROLE to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void grantCreateAnyTable (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant CREATE ANY TABLE to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void grantDelete (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant DELETE to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void grantUpdate (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant UPDATE to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void grantResource (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant RESOURCE to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void grantDropUser (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant DROP USER to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void grantDropRole (String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant DROP ROLE to"+"$rol";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void grantRole (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant"+"$rol"+"to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void grantCREATEVIEW (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant CREATE VIEW to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void grantEXECUTE (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant EXECUTE to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void grantINDEX (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant INDEX to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void grantSHOWDATABASES (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant SHOW DATABASES to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void grantSHOVIEW (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant SHOW VIEW to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void grantTRIGGER (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant TRIGGER to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void grantALTER (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant ALTER to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void grantCREATETEMPORARYTABLES (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant CREATE TEMPORARY TABLES to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void grantALTERROUTINE (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant ALTER ROUTINE to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void grantCREATEROUTINE (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant CREATE ROUTINE to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void grantFILE (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "grant FILE to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //REVOKES
    public static void revokeRole (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke"+"$rol"+"from"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void revokeConnect (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke CONNECT from"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void revokeSelect (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke SELECT from"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void revokeInsert (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke INSERT from"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void revokeDrop (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke Drop from"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void revokeCreateUser (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke CREATE USER from"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void revokeCreateRole (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke CREATE ROLE from"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void revokeCreateAnyTable (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke CREATE ANY TABLE from"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void revokeDelete (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke DELETE from"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void revokeUpdate (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke UPDATE from"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void revokeResource (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke RESOURCE from"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void revokeDropUser (String user){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke DROP USER from"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void revokeDropRole (String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke DROP ROLE from"+"$rol";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void revokeCREATESESSION (String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke CREATE SESSION from"+"$rol";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void revokeAll (String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke ALL PRIVILEGES from"+"$rol";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void revokeCREATEVIEW (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke CREATE VIEW to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void revokeEXECUTE (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke EXECUTE to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void revokeINDEX (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke INDEX to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void revokeSHOWDATABASES (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke SHOW DATABASES to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void revokeSHOVIEW (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke SHOW VIEW to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void revokeTRIGGER (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke TRIGGER to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void revokeALTER (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke ALTER to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void revokeCREATETEMPORARYTABLES (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke CREATE TEMPORARY TABLES to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void revokeALTERROUTINE (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke ALTER ROUTINE to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void revokeCREATEROUTINE (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke CREATE ROUTINE to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void REVOKEFILE (String user,String rol){

        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "revoke FILE to"+"$user";
            pps = connection.prepareStatement(sql);
            pps.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
