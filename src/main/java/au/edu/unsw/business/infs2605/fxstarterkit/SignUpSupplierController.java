/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.unsw.business.infs2605.fxstarterkit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author kevinsurjadi
 */
public class SignUpSupplierController {
    public static Connection ction;
    
    Database database = new Database();
    
    @FXML
    private TextField fnameText;
    
    @FXML
    private TextField lnameText;
    
    @FXML 
    private TextField emailText;
    
    @FXML 
    private ComboBox supplierText;
    
    @FXML
    private PasswordField pwordText;
    
    @FXML 
    private PasswordField cpwordText;
    
    @FXML
    private Label successfulAccountCreated;
    
    @FXML
    private Button createAccount;
    
    @FXML
    private Button back;
        
    /**
     * Initializes the controller class.
     */
    public void initialize() throws SQLException {
        successfulAccountCreated.setVisible(false);
        
        ObservableList<String> supplierList = FXCollections.observableArrayList(database.getSupplierNames());
        
        supplierText.setItems(supplierList);
        
    }    
    
    public void handleBackButton (ActionEvent event) throws IOException {
        App.setRoot("LoginScreenStore");
    }
    
    public void handleCreateAccountButtonAction (ActionEvent event) throws IOException, SQLException{
        String firstName = fnameText.getText();
        String lastName = lnameText.getText();
        String signupEmail = emailText.getText();
        String supplier = supplierText.getSelectionModel().getSelectedItem().toString();
        String password = pwordText.getText();
        String confirmPassword = cpwordText.getText();
        
        if(firstName.isEmpty() || lastName.isEmpty() || signupEmail.isEmpty() || password.isEmpty()){
            successfulAccountCreated.setVisible(true);
            successfulAccountCreated.setText("*Please ensure you have filled in everything");
        }
        else if(!password.equals(confirmPassword)) {
            successfulAccountCreated.setVisible(true);
            successfulAccountCreated.setText("*Passwords don't match! Try again");
        }
        //else if(database.checkExists(signupEmail).equals(signupEmail)) {
            //successfulAccountCreated.setVisible(true);
            //successfulAccountCreated.setText("*Email already exists! Try again");
        //} 
        else {
            database.createNewSEmployee(firstName,lastName,signupEmail,supplier,password);
            App.setRoot("LoginScreenStore");

        }
    }
}
