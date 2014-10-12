package sample.Controller;
//connection.initializeConnection("Johan", "sysdba", "root", "192.168.1.111", "XE", 1521, true);

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.ControlledScreen;
import sample.Model.entities.DBA_Roles;
import sample.Model.entities.User;
import sample.Model.entities.User_Privileges_Roles;
import sample.ScreensController;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.exit;


public class ControllerVistaPrincipal implements Initializable, ControlledScreen {

    ScreensController myController;
    ObservableList<BarChart.Series<String,Number>> barCharData;

    @FXML   BorderPane mainPane;
   //Tables
    @FXML   TableView<User> tableUsersView;
    @FXML   TableView<DBA_Roles> tableDBA_Roles;
    @FXML   TableView<User_Privileges_Roles> tableUser_Privileges_Roles;
    //User --Columns
    @FXML TableColumn<User,String> TBC_USERNAME;
    @FXML TableColumn<User,String> TBC_USERID;
    @FXML TableColumn<User,String> TBC_PASSWORD;
    @FXML TableColumn<User,String> TBC_ACCOUNTSTATUS;
    @FXML TableColumn<User,String> TBC_LOCKDATE;
    @FXML TableColumn<User,String> TBC_EXPIRYDATE;
    @FXML TableColumn<User,String> TBC_DEFAULTTABLESPACE;
    @FXML TableColumn<User,String> TBC_TEMPORARYTABLESPACE;
    @FXML TableColumn<User,String> TBC_CREATED;
    @FXML TableColumn<User,String> TBC_PROFILE;
    @FXML TableColumn<User,String> TBC_INICIAL;
    @FXML TableColumn<User,String> TBC_EXTERNAL;
    //--DBA_Roles Columns
    @FXML TableColumn<DBA_Roles,String> Grantee;
    @FXML TableColumn<DBA_Roles,String> GrantedRole;
    @FXML TableColumn<DBA_Roles,String> Admin_Option;
    @FXML TableColumn<DBA_Roles,String> Default_Role;
    //---
    //--- User_Privileges_Roles
    @FXML TableColumn<User_Privileges_Roles,String> TBC_username;
    @FXML TableColumn<User_Privileges_Roles,String> TBC_privilege;
    @FXML TableColumn<User_Privileges_Roles,String> TBC_role;


     @FXML
     public void handleExit(){
         frameClose();
         exit(0);
     }
    //private ScheduledExecutorService ex;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

         TBC_USERNAME.setCellValueFactory(new PropertyValueFactory("USERNAME"));
         TBC_USERID.setCellValueFactory(new PropertyValueFactory("USER_ID"));
         TBC_PASSWORD.setCellValueFactory(new PropertyValueFactory("PASSWORD"));
         TBC_ACCOUNTSTATUS.setCellValueFactory(new PropertyValueFactory("ACCOUNT_STATUS"));
         TBC_LOCKDATE.setCellValueFactory(new PropertyValueFactory("LOCK_DATE"));
         TBC_EXPIRYDATE.setCellValueFactory(new PropertyValueFactory("EXPIRY_DATE"));
         TBC_DEFAULTTABLESPACE.setCellValueFactory(new PropertyValueFactory("DEFAULT_TABLESPACE"));
         TBC_TEMPORARYTABLESPACE.setCellValueFactory(new PropertyValueFactory("TEMPORARY_TABLESPACE"));
         TBC_CREATED.setCellValueFactory(new PropertyValueFactory("CREATED"));
         TBC_PROFILE.setCellValueFactory(new PropertyValueFactory("PROFILE"));
         TBC_INICIAL.setCellValueFactory(new PropertyValueFactory("INITIAL_RSRC_CONSUMER_GROUP"));
         TBC_EXTERNAL.setCellValueFactory(new PropertyValueFactory("EXTERNAL_NAME"));
//---
        TBC_USERNAME.prefWidthProperty().bind(tableUsersView.prefWidthProperty().multiply(0.5));
        TBC_USERID.prefWidthProperty().bind(tableUsersView.prefWidthProperty().multiply(0.5));
        TBC_PASSWORD.prefWidthProperty().bind(tableUsersView.prefWidthProperty().multiply(0.5));
        TBC_ACCOUNTSTATUS.prefWidthProperty().bind(tableUsersView.prefWidthProperty().multiply(0.5));
        TBC_LOCKDATE.prefWidthProperty().bind(tableUsersView.prefWidthProperty().multiply(0.5));
        TBC_EXPIRYDATE.prefWidthProperty().bind(tableUsersView.prefWidthProperty().multiply(0.5));
        TBC_DEFAULTTABLESPACE.prefWidthProperty().bind(tableUsersView.prefWidthProperty().multiply(0.5));
        TBC_TEMPORARYTABLESPACE.prefWidthProperty().bind(tableUsersView.prefWidthProperty().multiply(0.5));
        TBC_CREATED.prefWidthProperty().bind(tableUsersView.prefWidthProperty().multiply(0.5));
        TBC_PROFILE.prefWidthProperty().bind(tableUsersView.prefWidthProperty().multiply(0.5));
        TBC_INICIAL.prefWidthProperty().bind(tableUsersView.prefWidthProperty().multiply(0.5));
        TBC_EXTERNAL.prefWidthProperty().bind(tableUsersView.prefWidthProperty().multiply(0.5));
        User.begin();
        tableUsersView.setItems(FXCollections.observableList(User.userList));

//--- DBA_Roles

        Grantee.setCellValueFactory(new PropertyValueFactory("Grantee"));
        GrantedRole.setCellValueFactory(new PropertyValueFactory("GrantedRole"));
        Admin_Option.setCellValueFactory(new PropertyValueFactory("Admin_Option"));
        Default_Role.setCellValueFactory(new PropertyValueFactory("Default_Role"));


        Grantee.prefWidthProperty().bind(tableDBA_Roles.prefWidthProperty().multiply(0.5));
        GrantedRole.prefWidthProperty().bind(tableDBA_Roles.prefWidthProperty().multiply(0.5));
        Admin_Option.prefWidthProperty().bind(tableDBA_Roles.prefWidthProperty().multiply(0.5));
        Default_Role.prefWidthProperty().bind(tableDBA_Roles.prefWidthProperty().multiply(0.5));

        DBA_Roles.begin();
        tableDBA_Roles.setItems(FXCollections.observableList(DBA_Roles.DBA_Roles));
//---
        //USER_PRIVLIEGES_ROLES
        TBC_username.setCellValueFactory(new PropertyValueFactory("Username"));
        TBC_privilege.setCellValueFactory(new PropertyValueFactory("Privilege"));
        TBC_role.setCellValueFactory(new PropertyValueFactory("Privilege"));
        User_Privileges_Roles.begin();
        tableUser_Privileges_Roles.setItems(FXCollections.observableList(User_Privileges_Roles.user_privileges));


    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
        Stage stage = (Stage) myController.getScene().getWindow();
        stage.setWidth(1000);
        stage.setHeight(600);
        stage.setOnCloseRequest(e->frameClose());
        //ORCConnection.Instance().close();
    }
    @FXML void frameClose(){
        try {
            User.end();
            DBA_Roles.end();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       // ex.shutdown();
    }

}
