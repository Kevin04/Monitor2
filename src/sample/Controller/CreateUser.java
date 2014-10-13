package sample.Controller;

import javafx.fxml.Initializable;
import sample.ControlledScreen;
import sample.ScreensController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Casa on 12/10/2014.
 */
public class CreateUser implements Initializable, ControlledScreen {
    ScreensController ParentController;
    @Override
    public void setScreenParent(ScreensController screenPage) {
        ParentController = screenPage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
