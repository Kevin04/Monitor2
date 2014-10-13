package sample.Controller;

import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.controlsfx.dialog.Dialogs;
import sample.ControlledScreen;
import sample.Model.access.Query.Query;
import sample.Model.access.User.PlainDBARole;
import sample.Model.access.User.UserAccess;
import sample.Model.access.tablespace.TableSpaceAccess;
import sample.Model.entities.*;
import sample.ScreensController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by Casa on 12/10/2014.
 */

public class CreateUser implements Initializable, ControlledScreen {
    ScreensController ParentController;
    User u = null;
    @FXML    Label LBL_on;
    @FXML    TextField TXT_on;
    @FXML    ComboBox<String> CMB_permisos;
    @FXML    ComboBox<TableSpace> CMB_TableSpace;
    @FXML    ComboBox<TableSpace> CMB_TempTBS;
    @FXML    ListView<Role> LV_roles;
    @FXML    ListView<Privilege> LV_Permisos;
    @FXML    TextField TXT_cuota;
    @FXML    PasswordField TXT_pass;
    @FXML    TextField TXT_name;


    @Override
    public void setScreenParent(ScreensController screenPage) {
        ParentController = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> cmb_items= FXCollections.observableArrayList("System", "Objects");
        CMB_TableSpace.setItems(FXCollections.observableArrayList(TableSpaceAccess.retrieveTableSpaces().stream().filter(t->!t.IsTemporary()).collect(Collectors.toList())));
        CMB_TempTBS.setItems(FXCollections.observableArrayList(TableSpaceAccess.tempList()));
        CMB_permisos.setItems(cmb_items);
        LV_roles.getItems().addAll(PlainDBARole.retrieveRoles());


    }
    @FXML void onCMBChange(){
        System.out.println("YEY");
        if(CMB_permisos.getSelectionModel().getSelectedIndex() == 0){
            LV_Permisos.getItems().clear();
            LV_Permisos.getItems().addAll(Privilege.SystemPrivs);
            LBL_on.setVisible(false);
            TXT_on.setVisible(false);
        }
        else{
            LBL_on.setVisible(true);
            TXT_on.setVisible(true);
            LV_Permisos.getItems().clear();
            LV_Permisos.getItems().addAll(ObjectPrivilege.objectsPrivilege);
        }
    }
    @FXML
    void Crear(){
        try {
            Query.InitializeQueryExecutor();
        } catch (SQLException e) {
            Dialogs.create().message("No se Pudo Inicilizar EL QueryExecutor").title("Error").showError();
            return;
        }
        if(TXT_name.getText().trim().equals("") || TXT_cuota.getText().trim().equals("") || TXT_pass.getText().trim().equals(" ")) {
            Dialogs.create().message("Error No debe Dejar Campos Vacios ni sin Seleccionar").title("Error").showError();
            return;
        }
       if(!isNumeric(TXT_cuota.getText()) && !TXT_cuota.getText().toUpperCase().equals("UNLIMITED")){
           Dialogs.create().message("Error No Cuota debe ser un Numero mas la Unidad [M|K|G] o Unlimited").title("Error").showError();
           return;
       }
        TableSpace tbs = CMB_TableSpace.getSelectionModel().getSelectedItem();
        TableSpace tmptbs = CMB_TempTBS.getSelectionModel().getSelectedItem();
        if(tbs == null || tmptbs == null){
            Dialogs.create().message("Debe Seleccionar los TableSpaces").title("Error").showError();
            return;
        }
        if(!Query.crearUsuario(TXT_name.getText().trim(),TXT_pass.getText().trim(),tbs.getName(),tmptbs.getName(),TXT_cuota.getText())){
            Dialogs.create().message("Error No Se ha creado el Usuario").title("Error").showError();
            return;
        }
        else{
            Dialogs.create().message("Se ha creado el Usuario").title("Exito").showInformation();
        }
        this.u = UserAccess.getByName(TXT_name.getText());
    }
    @FXML
    void asignarPrivilegio(){
        if(u==null){
            Dialogs.create().message("No se Ha creado un Usuario al cual Asignarle los Privilegios").title("Error").showError();
            return;
        }
        Privilege p = LV_Permisos.getSelectionModel().getSelectedItem();
        if(CMB_permisos.getSelectionModel().getSelectedIndex() == 1){
            if(p instanceof ObjectPrivilege){
                ObjectPrivilege op = new ObjectPrivilege((ObjectPrivilege)p);
                if(TXT_on.getText().trim().equals("")){
                    Dialogs.create().message("No se ha indicado el Objecto sobre el cual recaen los permisos").title("Error").showError();
                    return;
                }
                op.setOn(TXT_on.getText());
                p = op;
            }
        }
        if(!Query.priviletoUser(p,u)) Dialogs.create().message("Error Al agregar Permisos").title("Error").showError();
        else Dialogs.create().message("Privilegios Agregados Correctamente").title("Exito").showConfirm();
    }
    private static boolean isNumeric(String str)
    {
        return str.matches("-?\\d+(\\.\\d+)? ?(M|K|G)");  //match a number with optional '-' and decimal.
    }
}