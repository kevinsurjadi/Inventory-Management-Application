package au.edu.unsw.business.infs2605.fxstarterkit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    Database db = new Database();
    
    @FXML
    private TextField storeUnameField;

    @FXML
    private PasswordField storePwordField;
    
    @FXML
    private Button storeManagerLoginButton;

    @FXML
    private Button loginAsSupplier;
    
    @FXML
    private TextField supplierUnameField;

    @FXML
    private PasswordField supplierPwordField;
    
    @FXML
    private Button supplierLoginButton;

    @FXML
    private Button loginAsStoreManager;

    @FXML
    private Hyperlink signUp;
    
    @FXML
    private Label loginStatus;
    
    public static String userSupplier;
    
    public static String userEmail;
    
    public static String welcomeName;
    
    PageChanger pc = new PageChanger();
    
    public void initialize() {
            loginStatus.setVisible(false);
    }
    
    //::::LOGIN BUTTON RED::::
    public void handleSupplierLoginButtonAction(ActionEvent event) throws IOException, SQLException {
        String user = supplierUnameField.getText();
        String pword = supplierPwordField.getText();
        
        if (db.checkLoginSEmployee(user, pword)) {
            userSupplier = db.getSupplier(user, pword);
            welcomeName = db.getSName(user,pword);
            userEmail = supplierUnameField.getText();
            
            App.setRoot("SupplierView");
        } else {
            loginStatus.setText("Incorrect email or password");
            loginStatus.setVisible(true);
        }
    }
    
    public void handleStoreManagerLoginButtonAction(ActionEvent event) throws IOException, SQLException {
        String user = storeUnameField.getText();
        String pword = storePwordField.getText();
        
        if (db.checkLoginEmployee(user, pword)) {
            welcomeName = db.getName(user,pword);
            App.setRoot("StoreManagerDashboard");
        } else {
            loginStatus.setText("*Incorrect email or password");
            loginStatus.setVisible(true);
        }
    }
    
    //::::SIGN UP AN ACCOUNT HYPERLINK::::
    public void handleSignUpButtonAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Selection");
        alert.setHeaderText("What type of account would you like to sign up for?");
        alert.setContentText("Choose your option below:");
        
        ButtonType buttonTypeOne = new ButtonType("Employee");
        ButtonType buttonTypeTwo = new ButtonType("Supplier");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == buttonTypeOne){
            System.out.print("Employee selected");
            App.setRoot("SignUp");
        } else if(result.get() == buttonTypeTwo){
            System.out.print("Supplier selected");
            App.setRoot("SupplierSignUp");
        } else if(result.get() == buttonTypeCancel){
            System.out.print("Cancelled");
        } 
    }
    
    //::::SWITCH LOGIN SCREEN::::
    public void handleLoginAsStoreManagerAction(ActionEvent event) throws IOException {
        App.setRoot("LoginScreenStore");
    }
    
    public void handleLoginAsSupplierAction(ActionEvent event) throws IOException{
        App.setRoot("LoginScreenSupplier");
    }
}