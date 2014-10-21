package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application {

    public static String screen1ID = "main";
    public static String screen1File = "/sample/View/Login.fxml";
    public static String screen2ID = "screen2";
    public static String screen2File = "/sample/View/VistaPrincipal.fxml";
    public static String autoLogin="autoLogin";
    public static String autoLoginFile = "/sample/View/AutoLogin.fxml";
    public static String RoleNPrivileges="rolesNprivileges";
    public static String RoleNPrivilegesFile = "/sample/View/VentanaPrivilegios.fxml";
    public static ScreensController mainContainer;
    public static String ModifyRoleNPrivileges="ControllerModifyRolesAndPrivileges";
    public static String ModifyRoleNPrivilegesFile = "/sample/View/ViewModifyPrivileges.fxml";

    public static final String keyEncrypth="Mary has one ca1";
    public static String appWindowFile = "/sample/View//Main.fxml";
    public static String appWindow = "appWindow";
    public static String creteUserWindow = "createUserWindow";
    public static String getCreteUserWindowFile = "/sample/View/CreateUser.fxml";
    public static String modifyUserWindow = "modifiUserWindow";
    public static String modifyUserFile = "/sample/View/ModifyUser.fxml";



    @Override
    public void start(Stage primaryStage) throws Exception{
        URL u2 = getClass().getResource("/sample/View/Login.fxml");
        File theDir = new File("src");
        if (!theDir.exists()) {
            try {
                theDir.mkdir();
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }
        theDir = new File("src/FileMonitor");
        if (!theDir.exists()) {
            try {
                theDir.mkdir();
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }
        FXMLLoader myLoader = new FXMLLoader(u2);
        Parent loadScreen = (Parent) myLoader.load();
        Scene scene = new Scene(loadScreen);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(450);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}
