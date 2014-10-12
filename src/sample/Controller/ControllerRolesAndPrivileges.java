package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.ControlledScreen;
import sample.Model.entities.DBA_Roles;
import sample.ScreensController;

import java.net.URL;
import java.util.ResourceBundle;

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
    ObservableList<String> list= FXCollections.observableArrayList("NOT IDENTIFIED", "IDENTIFIED BY","IDENTIFIED USING",
            "IDENTIFIED EXTERNALLY","IDENTIFIED GLOBALLY");
    @FXML
    private void handleOk(){

    }
    @FXML
    private void handleCancel(){

    }

    @FXML
    private void handleAssignPrivilege(){

    }
    @FXML
    private void handleAssignRole(){

    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController=screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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


        //roleListView.setItems();
    }
}
