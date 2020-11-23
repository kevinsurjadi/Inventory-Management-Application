/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.unsw.business.infs2605.fxstarterkit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

/**
 *
 * @author kevinsurjadi
 */
public class SupplierAddController {
    
    @FXML
    TextField name;
    
    @FXML
    TextField phone;
    
    @FXML
    TextField address;
    
    @FXML
    TextField email;
            
    Supplier supplier;
    
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
        
        name.textProperty().bindBidirectional(supplier.nameProperty());
        phone.textProperty().bindBidirectional(supplier.phoneProperty());
        address.textProperty().bindBidirectional(supplier.addressProperty());
        email.textProperty().bindBidirectional(supplier.emailProperty());
    }
}
