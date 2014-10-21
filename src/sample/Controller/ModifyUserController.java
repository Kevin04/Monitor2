package sample.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import sample.Model.access.User.PlainDBARole;
import sample.Model.access.User.UserAccess;
import sample.Model.access.tablespace.TableSpaceAccess;
import sample.Model.entities.*;
import sample.ScreensController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
    @FXML
    ListView<Role> LV_rolesAsignados;
    @FXML
    Button BTN_backToUserSelect;
    @FXML
    ComboBox<TableSpace> CMB_tbs;
    @FXML
    TextField TF_size;
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
    public void clearData() {
        this.CMB_permisos.getSelectionModel().clearSelection();
        this.TXT_on.setText("");
        this.restoreHidden();
        this.TF_nameFilter.setText("");
        TF_size.setText("");
        CMB_tbs.getSelectionModel().clearSelection();
    }

    @Override
    public void reloadMainData() {
        List<User> users = UserAccess.retrieveUsers();
        CMB_tbs.getItems().clear();
        CMB_tbs.getItems().addAll(TableSpaceAccess.retrieveTableSpaces().stream().filter(tbs -> !tbs.IsTemporary()).collect(Collectors.toList()));
        userList = FXCollections.observableArrayList(users);
        LV_users.getItems().clear();
        LV_users.getItems().addAll(users);
        LV_roles.getItems().clear();
        LV_roles.getItems().addAll(PlainDBARole.retrieveRoles());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /************************************************************************************/
        Tooltip TP_expandButton = new Tooltip("Regresar a la lista de Usuarios");
        Tooltip TP_userList = new Tooltip("Doble Click para modificar el usuario Seleccionado");
        Tooltip TP_roleList = new Tooltip("Seleccione Uno o varios Roles para Asginar");
        BTN_backToUserSelect.setTooltip(TP_expandButton);
        LV_users.setTooltip(TP_userList);
        LV_roles.setTooltip(TP_roleList);
        /************************************************************************************/
        List<User> users = UserAccess.retrieveUsers();
        userList = FXCollections.observableArrayList(users);
        //Configs
        try {
            Query.InitializeQueryExecutor();
        } catch (SQLException e) {
            Dialogs.create().showException(e);
        }
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
        CMB_tbs.getItems().addAll(TableSpaceAccess.retrieveTableSpaces().stream().filter(tbs -> !tbs.IsTemporary()).collect(Collectors.toList()));
        LV_users.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        LV_roles.getItems().addAll(PlainDBARole.retrieveRoles());
        LV_roles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ObservableList<String> cmb_items = FXCollections.observableArrayList("System", "Objects");
        CMB_permisos.setItems(cmb_items);
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
                populateLV_rolesAsignados();
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
        List<Role> r = LV_roles.getSelectionModel().getSelectedItems();
        if (r == null) {
            Dialogs.create().message("Debe Seleccionar un Role").title("Error").showError();
            return;
        }
        //Query.grantRoleToUser(r, selectedUser))
        // Dialogs.create().message("Rol Asignado").title("Asignado").showInformation();
        List<Role> success = new ArrayList<>();
        r.stream().filter(rl -> Query.grantRoleToUser(rl, selectedUser)).forEach(rl -> {
            LV_rolesAsignados.getItems().add(rl);
            success.add(rl);
        });
        ArrayList<Role> failed = new ArrayList<>(r);
        failed.removeAll(success);
        if (!failed.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Los Siguientes Roles No pudieron Ser Asignados").append(System.lineSeparator());
            failed.stream().forEach(rl -> sb.append(rl.getRole()).append(System.lineSeparator()));
            Dialogs.create().message(sb.toString()).title("Error").showError();
        }
        //else Dialogs.create().message("No se a podido asignar el role").title("Error").showError();
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

    @FXML
    void quotatouser() {
        if (!TF_size.getText().toUpperCase().matches("([0-9]+(K|M|G|k|m|g))|UNLIMITED")) {
            Dialogs.create().message("Error El formato del tamaño no Es correcto").title("Error").showError();
            return;
        }
        TableSpace tbs = CMB_tbs.getSelectionModel().getSelectedItem();
        if (tbs == null) {
            Dialogs.create().message("Debe seleccionar un Tablespace").title("Error").showError();
            return;
        }
        if (Query.quotatoUser(selectedUser, TF_size.getText(), tbs))
            Dialogs.create().message("Se ha agregado la quota").title("Exito").showInformation();
        else Dialogs.create().message("Error AL agregar qouta").title("Error").showError();
    }

    void populateLV_rolesAsignados() {
        Runnable r = () -> {
            try {
                final List<Role> roles = PlainDBARole.retrieveUserRole(this.selectedUser);
                Platform.runLater(
                        () -> {
                            LV_rolesAsignados.getItems().clear();
                            LV_rolesAsignados.getItems().addAll(roles);
                        }
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
        new Thread(r).start();
    }
}