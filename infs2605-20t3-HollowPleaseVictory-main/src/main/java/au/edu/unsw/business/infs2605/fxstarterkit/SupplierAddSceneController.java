/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.unsw.business.infs2605.fxstarterkit;

import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author kevinsurjadi
 */
public class SupplierAddSceneController { 
   Database db = new Database();
    
    @FXML
    ComboBox status;
    
    Orders order;
    
    @FXML
    public void intialize() throws SQLException {
        System.out.print("going to create an String list");
        ObservableList<String> statusList = FXCollections.observableArrayList("DELIVERED", "ORDER PLACED", "IN PROGRESS");
        //ComboBox status = new ComboBox(statusList);
        status.setItems(statusList);
        System.out.print("set the combobox to have the string list");
    }
    
    public void setOrder(Orders order) {
        this.order = order;
        
        status.valueProperty().bindBidirectional(order.statusProperty());
    }
}
