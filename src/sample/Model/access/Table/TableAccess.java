package sample.Model.access.Table;

import sample.Model.access.tablespace.TableSpaceAccess;
import sample.Model.entities.Table;
import sample.Model.entities.TableSpace;
import sample.cr.una.pesistence.access.ORCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Casa on 15/09/2014.
 */
public class TableAccess {
    private static TableAccess instance=null;
    public static TableAccess Instance(){
        if(instance==null) instance=new TableAccess();
        return instance;
    }
    Connection connection = null;
    PreparedStatement pps = null;
    protected TableAccess(){
        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "SELECT TABLE_NAME,TABLESPACE_NAME,OWNER FROM DBA_TABLES WHERE TABLESPACE_NAME = ?";
            pps=connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Table> retrieveForTableSpace(String TBS_name){

        List<Table> tables = new ArrayList<>();
        try {
            if(!ORCConnection.Instance().isInitialized()) return tables;
            pps.setString(1,TBS_name);
            ResultSet rs = pps.executeQuery();
            while (rs.next()){
                String t_name = rs.getString(1);
                String t_owner = rs.getString(2);
                String tb_name = rs.getString(3);
                tables.add(new Table(t_name,t_owner,tb_name));
            }
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }
    public List<Table> retrieveForTableSpace(TableSpace TBS_name)
    {
        return retrieveForTableSpace(TBS_name.getName());
    }
    public Map<TableSpace,List<Table>> retriveAllTables(){
        Map<TableSpace,List<Table>> allTables = new HashMap<>();
        List<TableSpace> tableSpaces = TableSpaceAccess.retrieveTableSpaces();
        tableSpaces.stream().forEach(t->allTables.put(t,retrieveForTableSpace(t)));
        return allTables;
    }
}
