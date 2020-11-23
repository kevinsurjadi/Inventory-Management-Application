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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author susannayao
 */
public class SignUpScreenController {
    public static Connection ction;
    
    Database db = new Database();
    
    @FXML
    private TextField fnameText;
    
    @FXML
    private TextField lnameText;
    
    @FXML 
    private TextField emailText;
    
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
        
    public void initialize() {
        successfulAccountCreated.setVisible(false);
    }    
    
    public void handleBackButton (ActionEvent event) throws IOException {
        App.setRoot("LoginScreenStore");
    }
    
    public void handleCreateAccountButtonAction (ActionEvent event) throws IOException, SQLException{
        String fName = fnameText.getText();
        String lName = lnameText.getText();
        String email = emailText.getText();
        String password = pwordText.getText();
        String confirmPassword = cpwordText.getText();
        
        if(fName.isEmpty() || lName.isEmpty() || email.isEmpty() || password.isEmpty()){
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
            db.createNewEmployee(fName,lName,email,password);
            
            App.setRoot("LoginScreenStore");
        }
    }
}
