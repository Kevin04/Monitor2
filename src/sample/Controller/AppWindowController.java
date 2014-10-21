package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import sample.ControlledScreen;
import sample.Model.entities.DBA_Roles;
import sample.Model.entities.User;
import sample.Model.entities.User_Privileges_Roles;
import sample.ScreensController;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.exit;

/**
 * Created by Casa on 12/10/2014.
 */
public class AppWindowController implements Initializable, ControlledScreen {
    @FXML
    AnchorPane content;
    ScreensController parentController;

    @Override
    public void setScreenParent(ScreensController screenPage) {
        parentController = screenPage;
    }

    @Override
    public void clearData() {
    }

    @Override
    public void reloadMainData() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void frameClose() {
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
    public void close() {
        frameClose();
        exit(0);
    }
}
