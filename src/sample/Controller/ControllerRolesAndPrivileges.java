package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.ControlledScreen;
import sample.Model.access.Query.Query;
import sample.Model.entities.*;
import sample.ScreensController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.System.err;
import static java.lang.System.exit;

/**
 * Created by JoséPablo on 12/10/2014.
 */
public class ControllerRolesAndPrivileges implements Initializable, ControlledScreen {
    ScreensController myController;
    @FXML TextField role_Name;
    @FXML ComboBox<String> comboBox;
    @FXML TextField text_info;
    @FXML ListView roleListView;
    @FXML Label lb_Selected;
    @FXML ListView listViewObjects;
    @FXML ListView listViewHierarchy;
    @FXML ListView listViewSystem;
    @FXML Label errMsg;
    @FXML Label lbObjectPrivileges;
    @FXML TextField textObject;
    ObservableList<String> list= FXCollections.observableArrayList("NOT IDENTIFIED", "IDENTIFIED BY","IDENTIFIED USING",
            "IDENTIFIED EXTERNALLY","IDENTIFIED GLOBALLY");

    @FXML
    private void handleAssignPrivilege(){
        if(!role_Name.getText().isEmpty()){
            if(role_Name.getText().matches("^[a-zA-Z0-9]*$")){
                if(listViewObjects.getSelectionModel().getSelectedItems()!=null){
                    if(textObject.getText()!=null&&textObject.getText().matches("^[a-zA-Z0-9]*$")&&
                            !textObject.getText().isEmpty()){

                        ObservableList<String> privileges= listViewObjects.getSelectionModel().getSelectedItems();
                        StringBuilder stringPrivileges= new StringBuilder();

                        privileges.forEach(e->{

                            stringPrivileges.append(e.toString());
                            stringPrivileges.append(",");

                        });
                        stringPrivileges.deleteCharAt(stringPrivileges.length()-1);

                        errMsg.setText(Query.privilegeObjectToRole(role_Name.getText(),stringPrivileges.toString(),
                                textObject.getText()));
                    }else{
                        errMsg.setText("You must have to choose one table to assign the Object privileges.");
                    }


                }else{
                    errMsg.setText("You must have to choose your privileges.");
                }
            }else{
                errMsg.setText("Only use letters and digits for example: myRole1");
            }
        }else{
            errMsg.setText("You must have to fill the role name");
        }
    }
    @FXML
    private void handleAssignRole(){
        if(!role_Name.getText().isEmpty()){
            if(role_Name.getText().matches("^[a-zA-Z0-9]*$")){
                if(roleListView.getSelectionModel().getSelectedItem()!=null){

                    errMsg.setText(Query.grantRoletoRole(roleListView.getSelectionModel().getSelectedItem().toString()
                            ,role_Name.getText()));
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
        lbObjectPrivileges.setVisible(false);
        textObject.setVisible(false);
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
        /*
        ObservableList<String> objectList=FXCollections.observableArrayList("DELETE","EXECUTE","FLUSH","INDEX","INSERT"
                ,"LOAD","REFERENCES","REFRESH","SELECT","UNLOAD","UPDATE");
        */
        ObservableList<String> objectList=FXCollections.observableArrayList("DELETE","EXECUTE","INSERT"
                ,"SELECT","UPDATE");
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

        listViewObjects.setOnMouseClicked(e-> {
            lbObjectPrivileges.setVisible(true);
            textObject.setVisible(true);
        });


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

    @FXML public void handleExit(){
        frameClose();
        exit(0);
    }


}
