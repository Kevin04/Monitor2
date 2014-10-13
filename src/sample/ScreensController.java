package sample;
/**
 * Created by Jose on 13/09/2014.
 */

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import sample.Model.entities.DBA_Roles;
import sample.Model.entities.User;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ScreensController implements Initializable, ControlledScreen {
    //Holds the screens to be displayed
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

    private HashMap<String, Node> screens = new HashMap<>();

    public ScreensController() {
        super();
    }

    //Add the screen to the collection
    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    //Returns the Node with the appropriate name
    public Node getScreen(String name) {
        return screens.get(name);
    }

    //Loads the fxml file, add the screen to the screens collection and
    //finally injects the screenPane to the controller.
    public boolean loadScreen(String name, String resource) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) myLoader.load();
            ControlledScreen myScreenControler = ((ControlledScreen) myLoader.getController());
            myScreenControler.setScreenParent(this);
            addScreen(name, loadScreen);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //This method tries to displayed the screen with a predefined name.
    //First it makes sure the screen has been already loaded.  Then if there is more than
    //one screen the new screen is been added second, and then the current screen is removed.
    // If there isn't any screen being displayed, the new screen is just added to the root.
    public boolean setScreen(final String name) {
        content.getScene().getWindow().setOnCloseRequest((e) -> {
            this.close();
        });
        if (screens.get(name) != null) {   //screen loaded
            final DoubleProperty opacity = content.opacityProperty();
            if (!content.getChildren().isEmpty()) {    //if there is more than one screen
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent t) {
                                content.getChildren().remove(0);                    //remove the displayed screen
                                content.getChildren().add(0, screens.get(name));     //add the screen
                                content.setTopAnchor(content.getChildren().get(0), 0.0);
                                content.setLeftAnchor(content.getChildren().get(0), 0.0);
                                content.setBottomAnchor(content.getChildren().get(0), 0.0);
                                content.setRightAnchor(content.getChildren().get(0), 0.0);
                                Timeline fadeIn = new Timeline(
                                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                        new KeyFrame(new Duration(800), new KeyValue(opacity, 1.0)));
                                fadeIn.play();
                            }
                        }, new KeyValue(opacity, 0.0)));
                fade.play();
            } else {
                content.setOpacity(0.0);
                content.getChildren().add(screens.get(name));       //no one else been displayed, then just show
                content.setTopAnchor(content.getChildren().get(0), 0.0);
                content.setLeftAnchor(content.getChildren().get(0), 0.0);
                content.setBottomAnchor(content.getChildren().get(0), 0.0);
                content.setRightAnchor(content.getChildren().get(0), 0.0);
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(2500), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        } else {
            System.out.println("screen hasn't been loaded!!! \n");
            return false;
        }
    }

    //This method will remove the screen with the given name from the collection of screens
    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }

    public Scene getScene() {
        return this.content.getScene();
    }

    @FXML void handleChangeToModifyRole(){
        this.loadScreen(Main.ModifyRoleNPrivileges, Main.ModifyRoleNPrivilegesFile);
        this.setScreen(Main.ModifyRoleNPrivileges);
    }

    @FXML
    void changeTOInfoScreen() {
        this.loadScreen(Main.screen2ID, Main.screen2File);
        this.setScreen(Main.screen2ID);
    }

    @FXML
    void createUserWindow() {
        this.loadScreen(Main.creteUserWindow, Main.getCreteUserWindowFile);
        this.setScreen(Main.creteUserWindow);
    }

    @FXML
    void changeTORoleNPrivilege() {
        this.loadScreen(Main.RoleNPrivileges, Main.RoleNPrivilegesFile);
        this.setScreen(Main.RoleNPrivileges);
    }

    @FXML
    void close() {
        try {
            User.end();
            DBA_Roles.end();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.exit();
        System.exit(0);
    }
}