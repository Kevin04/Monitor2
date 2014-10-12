package sample.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import sample.ControlledScreen;
import sample.ScreensController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Casa on 12/10/2014.
 */
public class AppWindowController implements Initializable, ControlledScreen{
    @FXML
    AnchorPane content;
    ScreensController parentController;
    @Override
    public void setScreenParent(ScreensController screenPage) {
        parentController = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
