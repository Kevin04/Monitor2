package sample.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
import sample.ControlledScreen;
import sample.Main;
import sample.Model.FileManagement.CryptoException;
import sample.Model.FileManagement.Encrypth;
import sample.Model.FileManagement.LoginFile;
import sample.ScreensController;
import sample.cr.una.pesistence.access.ORCConnection;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Jose on 14/09/2014.
 */
public class ControllerLogin implements Initializable, ControlledScreen {
    private String ipRegex = "\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b";
    //Login things
    private final String pathArchiveLogin = "src\\FileMonitor\\login.ser";
    private LoginFile dataLogin;
    //------
    ScreensController myController;
    @FXML
    BorderPane bordePaneLogin;
    @FXML
    GridPane gridPane;
    @FXML
    ProgressIndicator PGI_loading;
    @FXML
    private TextField user;
    @FXML
    private TextField password;
    @FXML
    private TextField port;
    @FXML
    private TextField url;
    @FXML
    private TextField serviceName;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private CheckBox chkRememberMe;
    ObservableList<String> list = FXCollections.observableArrayList("sysdba", "sysoper");

    private boolean readLoginFile() {
        LoginFile lf = null;
        File encrypth = new File(pathArchiveLogin);
        File deEncrypth = new File(pathArchiveLogin);
        try {
            Encrypth.decrypt(Main.keyEncrypth, encrypth, deEncrypth);
            FileInputStream fileIn = new FileInputStream(pathArchiveLogin);
            System.out.println(fileIn.available());
            if (fileIn.available() > 1) {
                ObjectInputStream in = new ObjectInputStream(fileIn);
                lf = (LoginFile) in.readObject();
                in.close();
                fileIn.close();
                dataLogin = lf;
                Encrypth.encrypt(Main.keyEncrypth, deEncrypth, encrypth);
                return true;
            } else {
                Encrypth.encrypt(Main.keyEncrypth, deEncrypth, encrypth);
                return false;
            }
        } catch (CryptoException | IOException i) {
            i.printStackTrace();
            return false;
        } catch (ClassNotFoundException c) {
            System.out.println("class not found");
            c.printStackTrace();
            return false;
        }
    }

    private void writeLoginFile() {
        if (dataLogin == null) {
            dataLogin = new LoginFile();
            dataLogin.setAutoLogin(false);
        }
        dataLogin.setNameService(this.serviceName.getText());
        dataLogin.setPassword(this.password.getText());
        dataLogin.setPort(Integer.parseInt(this.getPort().getText()));
        dataLogin.setPrivilege(this.comboBox.getValue());
        dataLogin.setUrl(this.url.getText());
        dataLogin.setUser(this.user.getText());
        dataLogin.setRememberMe(this.chkRememberMe.isSelected());
        try {
            File input = new File(pathArchiveLogin);
            File output = new File(pathArchiveLogin);
            FileOutputStream fileOut = new FileOutputStream(input);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(dataLogin);
            out.close();
            fileOut.close();
            Encrypth.encrypt(Main.keyEncrypth, input, output);
            System.out.printf("Serialized data is saved in" + pathArchiveLogin);
        } catch (CryptoException | IOException i) {
            i.printStackTrace();
        }
    }

    private void clearLoginFile() {
        try {
            FileOutputStream writer = new FileOutputStream(pathArchiveLogin);
            writer.write(0);
            writer.close();
            System.out.printf("Serialized data is saved in" + pathArchiveLogin);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @Override
    public void clearData() {
    }

    @Override
    public void reloadMainData() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox.setItems(list);
        if (readLoginFile()) {
            this.user.setText(dataLogin.getUser());
            this.url.setText(dataLogin.getUrl());
            this.serviceName.setText(dataLogin.getNameService());
            this.password.setText(dataLogin.getPassword());
            this.comboBox.setValue(dataLogin.getPrivilege());
            this.chkRememberMe.setSelected(dataLogin.isRememberMe());
            this.port.setText(String.valueOf(dataLogin.getPort()));
            if (dataLogin.isAutoLogin()) {
                goToPrincipal();
            }
        }
    }

    @FXML
    private void goToPrincipal() {
        PGI_loading.setVisible(true);
        Runnable r = () -> {
            try {
                /*URL u1 = getClass().getResource("sample/View/Login.fxml");*/
                URL u2 = getClass().getResource("/sample/View/Main.fxml");//--
               /* URL u3 = getClass().getResource("./sample/View/Login.fxml");
                URL u4 = getClass().getResource("/View/Login.fxml");
                URL u5 = getClass().getResource("./View/Login.fxml");//---
                URL u6 = getClass().getResource("View/Login.fxml");//---
                URL u7 = getClass().getResource("/Login.fxml");
                if(u1!=null) System.out.println("u1");
                if(u2!=null) System.out.println("u2");
                if(u3!=null) System.out.println("u3");
                if(u4!=null) System.out.println("u4");
                if(u5!=null) System.out.println("u5");
                if(u6!=null) System.out.println("u6");
                if(u7!=null) System.out.println("u7");*/
                if (u2 != null) System.out.println("u2");
                FXMLLoader loader = new FXMLLoader(u2);
                final Parent root = loader.load();
                Platform.runLater(() -> {
                    Stage stage = new Stage();
                    stage.setTitle("Monitor 2");
                    stage.setScene(new Scene(root, 1024, 768));
                    stage.show();
                    this.bordePaneLogin.getScene().getWindow().hide();
                    // Stage stage2 = (Stage) bordePaneLogin.getScene().getWindow();
                    // stage.close();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            //hide this current window (if this is whant you want
            //((Node)(event.getSource())).getScene().getWindow().hide()
        };
        if (checkInitiation()) new Thread(r).start();
        else PGI_loading.setVisible(false);
    }

    public TextField getServiceName() {
        return serviceName;
    }

    public void setServiceName(TextField serviceName) {
        this.serviceName = serviceName;
    }

    public TextField getUrl() {
        return url;
    }

    public void setUrl(TextField url) {
        this.url = url;
    }

    public TextField getPort() {
        return port;
    }

    public void setPort(TextField port) {
        this.port = port;
    }

    public TextField getPassword() {
        return password;
    }

    public void setPassword(TextField password) {
        this.password = password;
    }

    public TextField getUser() {
        return user;
    }

    public void setUser(TextField user) {
        this.user = user;
    }

    private boolean checkInitiation() {
        String errors = "";
        if (!user.getText().isEmpty() && !password.getText().isEmpty() && !url.getText().isEmpty() && !port.getText().isEmpty()
                && !serviceName.getText().isEmpty()) {
            if (!url.getText().toLowerCase().matches("(localhost)|" + ipRegex))
                errors += "URL miss-matches Format" + System.lineSeparator();
            if (!port.getText().matches("[0-9]+")) errors += "Port Is A Number";
            if (errors != "") {
                Dialogs.create().message(errors).owner(myController).masthead("Error En Los Datos Ingresados").showError();
                return false;
            }
            ORCConnection connection = ORCConnection.Instance();
            boolean isS = false;
            if (comboBox.getSelectionModel().getSelectedIndex() >= 0) isS = true;
            if (user.getText().toLowerCase().equals("system")) isS = true;
            if (isS == false) {
                Dialogs.create().message("Se Necesita Conectarse Como SYSDBA").owner(myController).masthead("Error En Los Privilegios").showError();
                return false;
            }
            if (user.getText().toLowerCase().equals("system")) isS = false;
            String usertxt = user.getText();
            if (isS) usertxt += " as sysdba";
            try {
                connection.initializeConnection(usertxt, comboBox.getValue(), password.getText(), url.getText(), serviceName.getText(), Integer.parseInt(port.getText()), isS);
                if (connection.isInitialized()) {
                    if (chkRememberMe.isSelected()) {
                        writeLoginFile();
                    } else {
                        clearLoginFile();
                    }
                    return true;
                } else {
                    Dialogs.create().masthead("Error En log In").owner(this.myController).message("No se pudo Conectar Con el Servidor").showError();
                    return false;
                }
            } catch (ClassNotFoundException e) {
                Dialogs.create().showException(e);
                // connection.close();
                return false;
            } catch (SQLException e) {
                Dialogs.create().showException(e);
                // connection.close();
            }
        }
        return false;
    }
}
