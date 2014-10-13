package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static String screen1ID = "main";
    public static String screen1File = "./View/Login.fxml";
    public static String screen2ID = "screen2";
    public static String screen2File = "./View/VistaPrincipal.fxml";
    public static String autoLogin="autoLogin";
    public static String autoLoginFile="./View/AutoLogin.fxml";
    public static String RoleNPrivileges="rolesNprivileges";
    public static String RoleNPrivilegesFile="./View/VentanaPrivilegios.fxml";
    public static ScreensController mainContainer;
    public static String ModifyRoleNPrivileges="ControllerModifyRolesAndPrivileges";
    public static String ModifyRoleNPrivilegesFile="./View/ViewModifyPrivileges.fxml";

    public static final String keyEncrypth="Mary has one ca1";

    public static String appWindowFile = "./View/Main.fxml";
    public static String appWindow = "appWindow";
    public static String creteUserWindow = "createUserWindow";
    public static String getCreteUserWindowFile = "./View/CreateUser.fxml";



    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource(screen1File));
        Parent loadScreen = (Parent) myLoader.load();
        //ScreensController screens = myLoader.getController();
        //screens.loadScreen(screen2ID,screen2File);
        //screens.setScreen(screen2ID);
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
