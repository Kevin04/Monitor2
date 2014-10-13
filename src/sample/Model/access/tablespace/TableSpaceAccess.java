package sample.Model.access.tablespace;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import sample.Model.entities.TableSpace;
import sample.cr.una.pesistence.access.ORCConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Casa on 14/09/2014.
 */
public class TableSpaceAccess {
    public static ObservableList<TableSpace> tableSpaces;

    private static Connection connection;
    private static PreparedStatement pps;

    static {
        try {
            connection = ORCConnection.Instance().getOrcConnection();
            String sql = "SELECT TABLESPACE_NAME,STATUS,CONTENTS FROM DBA_TABLESPACES";
            pps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<TableSpace> retrieveTableSpaces(){
        List<TableSpace> tableSpaces = new ArrayList<>();
        try{
        if(ORCConnection.Instance().isInitialized()) {

            ResultSet rs = pps.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                String status = rs.getString(2);
                Boolean isTmp = rs.getString(3).toUpperCase().equals("TEMPORARY");
                TableSpace tbs = new TableSpace(name,status,isTmp);
                tableSpaces.add(tbs);
            }
            return tableSpaces;
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<TableSpace> tempList(){
        return retrieveTableSpaces().stream().filter((t)->t.IsTemporary()).collect(Collectors.toList());
    }
}
