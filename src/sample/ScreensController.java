package sample;
/**
 * Created by Jose on 13/09/2014.
 */

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    @FXML
    Label LBL_LEFT;
    @FXML
    Label LBL_RIGHT;
    ScreensController parentController;
    String lastLoaded = "";
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

    private HashMap<String, Node> screens = new HashMap<>();
    private HashMap<String, ControlledScreen> controlers = new HashMap<>();

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
        if (screens.containsKey(name)) return true;
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) myLoader.load();
            ControlledScreen myScreenControler = ((ControlledScreen) myLoader.getController());
            myScreenControler.setScreenParent(this);
            addScreen(name, loadScreen);
            addControler(name, myScreenControler);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.err.print("Error");
            e.printStackTrace();
            return false;
        }
    }

    private void addControler(String name, ControlledScreen myScreenControler) {
        this.controlers.put(name, myScreenControler);
    }

    private boolean removeControler(String name) {
        return this.controlers.remove(name) != null;
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
                        new KeyFrame(new Duration(1000), t -> {
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
        return screens.remove(name) != null;

    }

    public Scene getScene() {
        return this.content.getScene();
    }

    void setLeftStatus(String status) {
        this.LBL_LEFT.setText(status);
    }

    void setRightStatus(String status) {
        this.LBL_RIGHT.setText(status);
    }

    void clearLastScreenData() {
        if (lastLoaded.equals("")) return;
        ControlledScreen ctr = controlers.get(lastLoaded);
        ctr.clearData();
    }

    void reloadMainData(String name) {
        ControlledScreen ctr = controlers.get(name);
        ctr.reloadMainData();
    }

    @FXML void handleChangeToModifyRole(){
        clearLastScreenData();
        lastLoaded = Main.ModifyRoleNPrivileges;
        this.loadScreen(Main.ModifyRoleNPrivileges, Main.ModifyRoleNPrivilegesFile);
        this.setScreen(Main.ModifyRoleNPrivileges);
        setLeftStatus("Modificar Roll");
    }

    @FXML
    void changeTOInfoScreen() {
        clearLastScreenData();
        lastLoaded = Main.screen2ID;
        this.loadScreen(Main.screen2ID, Main.screen2File);
        reloadMainData(Main.screen2ID);
        this.setScreen(Main.screen2ID);
        this.setLeftStatus("Información");
    }

    @FXML
    void createUserWindow() {
        clearLastScreenData();
        lastLoaded = Main.creteUserWindow;
        this.loadScreen(Main.creteUserWindow, Main.getCreteUserWindowFile);
        reloadMainData(Main.creteUserWindow);
        this.setScreen(Main.creteUserWindow);
        this.setLeftStatus("Crear Usuario");
    }

    @FXML
    void changeTORoleNPrivilege() {
        clearLastScreenData();
        lastLoaded = Main.RoleNPrivileges;
        this.loadScreen(Main.RoleNPrivileges, Main.RoleNPrivilegesFile);
        reloadMainData(Main.RoleNPrivileges);
        this.setScreen(Main.RoleNPrivileges);
        this.setLeftStatus("Crear Role");
    }

    @FXML
    void changeTOModifyUser() {
        clearLastScreenData();
        lastLoaded = Main.modifyUserWindow;
        this.loadScreen(Main.modifyUserWindow, Main.modifyUserFile);
        reloadMainData(Main.modifyUserWindow);
        this.setScreen(Main.modifyUserWindow);
        this.setLeftStatus("Modificar Usuario");
    }

    @FXML
    void close() {
        this.setLeftStatus("Cerrando");
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