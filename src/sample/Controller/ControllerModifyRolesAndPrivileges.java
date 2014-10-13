package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.ControlledScreen;
import sample.Model.access.Query.Query;
import sample.Model.entities.DBA_Roles;
import sample.Model.entities.User;
import sample.Model.entities.User_Privileges_Roles;
import sample.ScreensController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static java.lang.System.exit;

/**
 * Created by JoséPablo on 12/10/2014.
 */
public class ControllerModifyRolesAndPrivileges implements Initializable, ControlledScreen {
    ScreensController myController;
    @FXML
    TextField role_Name;
    @FXML
    ComboBox<String> comboBox;
    @FXML TextField text_info;
    @FXML
    ListView roleListView;
    @FXML
    Label lb_Selected;
    @FXML ListView listViewObjects;
    @FXML ListView listViewHierarchy;
    @FXML ListView listViewSystem;
    @FXML Label errMsg;
    @FXML Label lbObjectPrivileges;
    @FXML TextField textObject;

    ObservableList<String> list= FXCollections.observableArrayList("NOT IDENTIFIED", "IDENTIFIED BY", "IDENTIFIED USING",
            "IDENTIFIED EXTERNALLY", "IDENTIFIED GLOBALLY");

    @FXML
    private void handleAssignPrivilege(){

    }
    @FXML
    private void handleAssignRole(){
        if(!role_Name.getText().isEmpty()){
            if(role_Name.getText().matches("^[a-zA-Z0-9]*$")){
                if(roleListView.getSelectionModel().getSelectedItem()!=null){
                    Query.grantRoletoRole(roleListView.getSelectionModel().getSelectedItem().toString()
                            , role_Name.getText());
                    errMsg.setText("Role assigned.");
                }else{
                    errMsg.setText("You must have to choose one role to assign.");
                }
            }else{
                errMsg.setText("Only use letter and digits for example: myRole1");
            }
        }else{
            errMsg.setText("You must have to fill the role name");
        }
    }
    @FXML
    private void handleCreateRole(){

        if(role_Name.getText().matches("^[a-zA-Z0-9]*$")){
            if(comboBox.getSelectionModel().getSelectedItem()!=null){
                if(comboBox.getSelectionModel().getSelectedItem().toString().equals("IDENTIFIED BY")||
                        comboBox.getSelectionModel().getSelectedItem().toString().equals("IDENTIFIED USING")) {

                    if(text_info.getText().matches("^[a-zA-Z0-9]*$")){
                        StringBuilder sb = new StringBuilder();
                        sb.append(comboBox.getSelectionModel().getSelectedItem().toString());
                        sb.append(" ");
                        sb.append(text_info.getText());
                        Query.crearRole(role_Name.getText(), sb.toString());
                        errMsg.setText("Role created");
                    }else{
                        errMsg.setText("Only use letter and digits for example: mypass123");
                    }
                }
            }else{
                System.out.println(role_Name.getText());
                Query.crearRole(role_Name.getText());
                errMsg.setText("Role created");
            }
        }else{
            errMsg.setText("Only use letter and digits for example: myRole1");
        }

    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController=screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            DBA_Roles.begin();
            Query.InitializeQueryExecutor();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        text_info.setVisible(false);
        comboBox.setItems(list);
        comboBox.setOnAction(e->{
            if(comboBox.getSelectionModel().getSelectedItem().equals("IDENTIFIED BY")){
                text_info.setVisible(true);
                text_info.setPromptText("password");
            }else if(comboBox.getSelectionModel().getSelectedItem().equals("IDENTIFIED USING")){
                text_info.setVisible(true);
                text_info.setPromptText("[ schema. ] package");
            }
            else{text_info.setVisible(false);}

        });
        //setRoleList
        ObservableList<String> stringObservableList=FXCollections.observableArrayList();
        DBA_Roles.DBA_Roles.forEach( e->stringObservableList.add(e.getGrantedRole()) );
        roleListView.setItems(stringObservableList);
        roleListView.setOnMouseClicked(e->{
            if(e.getClickCount()==2){
                lb_Selected.setText(roleListView.getSelectionModel().getSelectedItem().toString());
            }

        });
        //--

        //setlistViewObjects
        ObservableList<String> objectList=FXCollections.observableArrayList("DELETE","EXECUTE","FLUSH","INDEX","INSERT"
                ,"LOAD","REFERENCES","REFRESH","SELECT","UNLOAD","UPDATE");
        listViewObjects.setItems(objectList);
        listViewObjects.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //--

        //--setPrivilegeHierachyList
        ObservableList<String> hierachylist=FXCollections.observableArrayList("ADMIN","CREATE ANY INDEX","CREATE ANY MATERIALIZED VIEW"
                ,"CREATE ANY PROCEDURE","CREATE ANY SEQUENCE","CREATE ANY SYNONYM","CREATE ANY TABLE","CREATE ANY VIEW",
                "DELETE ANY TABLE","EXECUTE ANY PROCEDURE","INSERT ANY TABLE","SELECT ANY SEQUENCE","SELECT ANY TABLE"
                ,"UPDATE ANY TABLE");
        listViewHierarchy.setItems(hierachylist);
        listViewHierarchy.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //--

        //setSystemList
        ObservableList<String> systemList=FXCollections.observableArrayList("ADMIN","ALTER ANY CACHE GROUP","ALTER ANY INDEX"
                ,"ALTER ANY MATERIALIZED VIEW","ALTER ANY PROCEDURE","ALTER ANY SEQUENCE","ALTER ANY TABLE"
                ,"ALTER ANY VIEW","CACHE_MANAGER","CREATE ANY CACHE GROUP","CREATE ANY INDEX","CREATE ANY MATERIALIZED VIEW",
                "CREATE ANY PROCEDURE","CREATE ANY SEQUENCE","CREATE ANY SYNONYM","CREATE ANY TABLE","CREATE ANY VIEW"
                ,"CREATE CACHE GROUP","CREATE MATERIALIZED VIEW","CREATE PROCEDURE","CREATE PUBLIC SYNONYM","CREATE SEQUENCE"
                ,"CREATE SESSION","CREATE SYNONYM","CREATE TABLE","CREATE VIEW","DELETE ANY TABLE","DROP ANY CACHE GROUP"
                ,"DROP ANY INDEX","DROP ANY MATERIALIZED VIEW","DROP ANY PROCEDURE","DROP ANY SEQUENCE","DROP ANY SYNONYM"
                ,"DROP ANY TABLE","DROP ANY VIEW","DROP PUBLIC SYNONYM","EXECUTE ANY PROCEDURE","FLUSH ANY CACHE GROUP"
                ,"INSERT ANY TABLE","LOAD ANY CACHE GROUP","REFRESH ANY CACHE GROUP","SELECT ANY SEQUENCE","SELECT ANY TABLE"
                ,"UNLOAD ANY CACHE GROUP","UPDATE ANY TABLE","XLA");
        listViewSystem.setItems(systemList);
        listViewSystem.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //--

    }
    @FXML void frameClose(){
        try {
            User.end();
            DBA_Roles.end();
            User_Privileges_Roles.end();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // ex.shutdown();
    }
    @FXML
    public void handleExit(){
        frameClose();
        exit(0);
    }


}
