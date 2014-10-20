package sample.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.dialog.Dialogs;
import sample.ControlledScreen;
import sample.Model.access.Query.Query;
import sample.Model.access.User.UserAccess;
import sample.Model.entities.ObjectPrivilege;
import sample.Model.entities.Privilege;
import sample.Model.entities.Role;
import sample.Model.entities.User;
import sample.ScreensController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by Casa on 18/10/2014.
 */
public class ModifyUserController implements Initializable, ControlledScreen {
    //cPOY PASTEADOS
    @FXML
    Label LBL_on;
    @FXML
    TextField TXT_on;
    @FXML
    ComboBox<String> CMB_permisos;
    @FXML
    ListView<Role> LV_roles;
    @FXML
    ListView<Privilege> LV_Permisos;
    //--------------
    //View Components
    @FXML
    TextField TF_nameFilter;
    @FXML
    ListView<User> LV_users;
    @FXML
    AnchorPane selectPane;
    //Logic Components
    ObservableList<User> userList;
    private User selectedUser;
    //Animation Components
    Timeline hidePanelTimeLine = null;
    Timeline showPanelTimeLine = null;
    private ScreensController parent;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.parent = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<User> users = UserAccess.retrieveUsers();
        userList = FXCollections.observableArrayList(users);
        //Configs
        //TIMELINE
        DoubleProperty tx = selectPane.translateXProperty();
        double restorex = selectPane.getTranslateX();
        hidePanelTimeLine = new Timeline(new KeyFrame(Duration.millis(600), t -> {
            selectPane.resize(500, selectPane.getHeight());
        }, new KeyValue(tx, selectPane.getTranslateX()), new KeyValue(tx, -2110)));
        showPanelTimeLine = new Timeline(new KeyFrame(Duration.millis(600), new KeyValue(tx, -2110), new KeyValue(tx, -restorex)));
        //hidePanelTimeLine.setCycleCount(Timeline.INDEFINITE);
        //Properties
        LV_users.getItems().addAll(users);
        LV_users.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //Events
        TF_nameFilter.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            String query = TF_nameFilter.getText();
            LV_users.getItems().clear();
            LV_users.getItems().addAll(users.stream().filter(u -> u.getUSERNAME().contains(query.toUpperCase())).collect(Collectors.toList()));
            //Dialogs.create().message(query).showInformation();
        });
        LV_users.setOnMouseClicked(event -> {
            if (event.getClickCount() > 1) {
                selectedUser = LV_users.getSelectionModel().getSelectedItem();
                hidePanelTimeLine.play();
            }
        });
        //pruebas de cambio
        //selectPane.prefHeightProperty().addListener((observable, oldValue, newValue) -> System.out.println(oldValue+ "->"+newValue));
        //selectPane.widthProperty().addListener((observable, oldValue, newValue) -> System.out.println(oldValue + "->" + newValue));
    }

    @FXML
    void asignarPrivilegio() {
        if (selectedUser == null) {
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
        if (!Query.priviletoUser(p, selectedUser))
            Dialogs.create().message("Error Al agregar Permisos").title("Error").showError();
        else Dialogs.create().message("Privilegios Agregados Correctamente").title("Exito").showInformation();
    }

    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)? ?(M|m|K|k|G|g)");
    }

    @FXML
    void granRoleToUser() {
        if (selectedUser == null) {
            Dialogs.create().message("No se Ha creado un Usuario al cual Asignarle los Privilegios").title("Error").showError();
            return;
        }
        Role r = LV_roles.getSelectionModel().getSelectedItem();
        if (r == null) {
            Dialogs.create().message("Debe Seleccionar un Role").title("Error").showError();
            return;
        }
        if (Query.grantRoleToUser(r, selectedUser))
            Dialogs.create().message("Rol Asignado").title("Asignado").showInformation();
        else Dialogs.create().message("No se a podido asignar el role").title("Error").showError();
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
    void restoreHidden() {
        showPanelTimeLine.play();
    }
}