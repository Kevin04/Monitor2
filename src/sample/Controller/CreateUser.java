package sample.Controller;

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

import javax.management.Query;
import java.net.URL;
import java.sql.SQLException;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by Casa on 12/10/2014.
 */
public class CreateUser implements Initializable, ControlledScreen {
    ScreensController ParentController;
    User u = null;
    @FXML
    Label LBL_on;
    @FXML
    TextField TXT_on;
    @FXML
    ComboBox<String> CMB_permisos;
    @FXML
    ComboBox<String> ex_pass;
    @FXML
    ComboBox<TableSpace> CMB_TableSpace;
    @FXML
    ComboBox<TableSpace> CMB_TempTBS;
    @FXML
    ListView<Role> LV_roles;
    @FXML
    ListView<Privilege> LV_Permisos;
    @FXML
    TextField TXT_cuota;
    @FXML
    PasswordField TXT_pass;
    @FXML
    TextField TXT_name;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        ParentController = screenPage;
    }

    @Override
    public void clearData() {
        this.CMB_permisos.getSelectionModel().clearSelection();
        this.ex_pass.getSelectionModel().clearSelection();
        this.CMB_TableSpace.getSelectionModel().clearSelection();
        this.CMB_TempTBS.getSelectionModel().clearSelection();
        this.TXT_cuota.setText("");
        this.TXT_name.setText("");
        this.TXT_on.setText("");
        this.TXT_pass.setText("");
    }

    @Override
    public void reloadMainData() {
        CMB_TableSpace.setItems(FXCollections.observableArrayList(TableSpaceAccess.retrieveTableSpaces().stream().filter(t -> !t.IsTemporary()).collect(Collectors.toList())));
        CMB_TempTBS.setItems(FXCollections.observableArrayList(TableSpaceAccess.tempList()));
        LV_roles.getItems().clear();
        LV_roles.getItems().addAll(PlainDBARole.retrieveRoles());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> cmb_items = FXCollections.observableArrayList("System", "Objects");
        ObservableList<String> exp_items = FXCollections.observableArrayList("Unlimited", "30 days","90 days");
        CMB_TableSpace.setItems(FXCollections.observableArrayList(TableSpaceAccess.retrieveTableSpaces().stream().filter(t -> !t.IsTemporary()).collect(Collectors.toList())));
        CMB_TempTBS.setItems(FXCollections.observableArrayList(TableSpaceAccess.tempList()));
        CMB_permisos.setItems(cmb_items);
        ex_pass.setItems(exp_items);
        LV_roles.getItems().addAll(PlainDBARole.retrieveRoles());
    }

    @FXML
    void onCMBChange() {
        if (CMB_permisos.getSelectionModel().getSelectedIndex() == 0) {
            LV_Permisos.getItems().clear();
            LV_Permisos.getItems().addAll(Privilege.SystemPrivs);
            LBL_on.setVisible(false);
            TXT_on.setVisible(false);
        } else {
            LBL_on.setVisible(true);
            TXT_on.setVisible(true);
            LV_Permisos.getItems().clear();
            LV_Permisos.getItems().addAll(ObjectPrivilege.objectsPrivilege);
        }
    }

    @FXML
    void Crear() {
        try {
            Query.InitializeQueryExecutor();
        } catch (SQLException e) {
            Dialogs.create().message("No se Pudo Inicilizar EL QueryExecutor").title("Error").showError();
            return;
        }
        if (TXT_name.getText().trim().equals("") || TXT_cuota.getText().trim().equals("") || TXT_pass.getText().trim().equals(" ")) {
            Dialogs.create().message("Error No debe Dejar Campos Vacios ni sin Seleccionar").title("Error").showError();
            return;
        }
        if (!isNumeric(TXT_cuota.getText()) && !TXT_cuota.getText().toUpperCase().equals("UNLIMITED")) {
            Dialogs.create().message("Error No Cuota debe ser un Numero mas la Unidad [M|K|G] o Unlimited").title("Error").showError();
            return;
        }
        TableSpace tbs = CMB_TableSpace.getSelectionModel().getSelectedItem();
        TableSpace tmptbs = CMB_TempTBS.getSelectionModel().getSelectedItem();
        if (tbs == null || tmptbs == null) {
            Dialogs.create().message("Debe Seleccionar los TableSpaces").title("Error").showError();
            return;
        }
        if (!Query.crearUsuario(TXT_name.getText().trim(), TXT_pass.getText().trim(), tbs.getName(), tmptbs.getName(), TXT_cuota.getText())) {
            Dialogs.create().message("Error No Se ha creado el Usuario").title("Error").showError();
            return;
        } else {
            String sinex= "SinExp";
            String day30= "days30";
            String day90= "days90";
            if (ex_pass.getSelectionModel().getSelectedIndex() == 0) {
                Query.modiPerfil(TXT_name.getText().trim(),sinex);
            }
            else  if (ex_pass.getSelectionModel().getSelectedIndex() == 1) {
                Query.modiPerfil(TXT_name.getText().trim(),day30);
            }
            else{
                Query.modiPerfil(TXT_name.getText().trim(),day90);
            }
            Dialogs.create().message("Se ha creado el Usuario").title("Exito").showInformation();
            u = UserAccess.getByName(TXT_name.getText());
            if (u == null)
                u = new User(TXT_name.getText(), 10, "", "", "", "", "", "", "", "", "", "");
            Privilege connect = new Privilege("CONNECT");
            Privilege session = new Privilege("CREATE SESSION");
            Query.priviletoUser(connect, u);
            Query.priviletoUser(session, u);
        }
    }

    @FXML
    void asignarPrivilegio() {
        if (u == null) {
            Dialogs.create().message("No se Ha creado un Usuario al cual Asignarle los Privilegios").title("Error").showError();
            return;
        }
        Privilege p = LV_Permisos.getSelectionModel().getSelectedItem();
        if (CMB_permisos.getSelectionModel().getSelectedIndex() == 1) {
            if (p instanceof ObjectPrivilege) {
                ObjectPrivilege op = new ObjectPrivilege((ObjectPrivilege) p);
                if (TXT_on.getText().trim().equals("")) {
                    Dialogs.create().message("No se ha indicado el Objecto sobre el cual recaen los permisos").title("Error").showError();
                    return;
                }
                op.setOn(TXT_on.getText());
                p = op;
            }
        }
        if (!Query.priviletoUser(p, u))
            Dialogs.create().message("Error Al agregar Permisos").title("Error").showError();
        else Dialogs.create().message("Privilegios Agregados Correctamente").title("Exito").showInformation();
    }

    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)? ?(M|m|K|k|G|g)");
    }

    @FXML
    void granRoleToUser() {
        if (u == null) {
            Dialogs.create().message("No se Ha creado un Usuario al cual Asignarle los Privilegios").title("Error").showError();
            return;
        }
        Role r = LV_roles.getSelectionModel().getSelectedItem();
        if (r == null) {
            Dialogs.create().message("Debe Seleccionar un Role").title("Error").showError();
            return;
        }
        if (Query.grantRoleToUser(r, u)) Dialogs.create().message("Rol Asignado").title("Asignado").showInformation();
        else Dialogs.create().message("No se a podido asignar el role").title("Error").showError();
    }
}
