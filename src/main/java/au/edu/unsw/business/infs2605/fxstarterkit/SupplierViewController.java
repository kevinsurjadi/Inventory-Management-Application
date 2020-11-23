/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.unsw.business.infs2605.fxstarterkit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author kevinsurjadi
 */
public class SupplierViewController {
    Database db = new Database();
    
    @FXML 
    private TextField search;
    
    //Navigation Buttons
    @FXML
    private Button orders;
    
    @FXML
    private Button about;
    
    @FXML 
    private Button logoutButton;
    
    //Buttons on top of table
    @FXML
    private Button updateButton;
    
    @FXML
    private Button updateButton1;

    @FXML
    TableView<Orders> orderTable;
    
    @FXML
    TableColumn<Orders, String> orderId;
    
    @FXML
    TableColumn<Orders, String> product;
    
    @FXML
    TableColumn<Orders, Integer> qty;
    
    @FXML
    TableColumn<Orders, Integer> price;
    
    @FXML
    TableColumn<Orders, String> status;
    
    @FXML
    TableColumn<Orders, String> timeStamp;
    
    @FXML
    private Label test;
    
    @FXML
    private Label name;
    
    @FXML
    Label changedBy;
    
    String changeByText;
    
    ObservableList<Orders> orderList = FXCollections.observableArrayList();
    
    @FXML
    public void initialize() throws SQLException {
        test.setText(LoginController.userSupplier);
        System.out.println("set supplier text");
        
        
        //setChangedByText();
        
        System.out.println("set supplierLog text");
        
        name.setText(LoginController.welcomeName);
        
        orderTable.setItems(db.getOrders(LoginController.userSupplier));
        
        orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        product.setCellValueFactory(new PropertyValueFactory<>("product"));
        qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        timeStamp.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));
        
        FilteredList<Orders> filteredData = new FilteredList<>(db.getOrders(LoginController.userSupplier), p -> true);
		
	search.textProperty().addListener((observable, oldValue, newValue) -> {
		filteredData.setPredicate(Orders -> {
				
			if (newValue == null || newValue.isEmpty()) {
					return true;
			}

			String lowerCaseFilter = newValue.toLowerCase();
				
			if (Orders.orderIdProperty().get().toLowerCase().contains(lowerCaseFilter)) {
				return true;
			} else if (Orders.productProperty().get().toLowerCase().contains(lowerCaseFilter)) {
				return true;
			} else if (Orders.qtyProperty().get().toLowerCase().contains(lowerCaseFilter)) {
				return true;
			} else if (Orders.priceProperty().get().toLowerCase().contains(lowerCaseFilter)) {
				return true;
			} else if (Orders.statusProperty().get().toLowerCase().contains(lowerCaseFilter)) {
				return true;
			} else if (Orders.timeStampProperty().get().toLowerCase().contains(lowerCaseFilter)) {
				return true;
			}
			return false; 
		});
	});
		
	SortedList<Orders> sortedData = new SortedList<>(filteredData);	
	sortedData.comparatorProperty().bind(orderTable.comparatorProperty());
	orderTable.setItems(sortedData);
    }

    @FXML
    public void HandleLogoutButtonAction(ActionEvent Event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.setContentText("This will take you back to the main page");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
           App.setRoot("LoginScreenStore");
           System.out.print("Logged out");
        } else {
            System.out.print("cancelled");
        }
        
    }
    
    @FXML
    public void HandleUpdateButtonAction(ActionEvent Event) throws IOException, SQLException {
        Orders order = orderTable.getSelectionModel().getSelectedItem();
        
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("supplierSceneAdd.fxml"));
        DialogPane orderDialogPane = fxmlLoader.load();
        
        SupplierAddSceneController orderController = fxmlLoader.getController();
        orderController.setOrder(order);
        orderController.intialize();
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(orderDialogPane);
        dialog.setTitle("Edit Order");
        
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if(clickedButton.get() == ButtonType.OK){
            System.out.print("OK");
            db.updateOrderTableS(order.statusProperty().get(), order.orderIdProperty().get());
            //db.setSupplierLog(order.orderIdProperty().get(), LoginController.userEmail);
            
            orderTable.setItems(db.getOrders());
            App.setRoot("SupplierView");
        }
    }
    
    @FXML
    public void HandleAboutButtonAction(ActionEvent Event) {
        String string = String.format("Copyright © 2020. We, the developers of this software application, declare that it %n"
                                    + "is our work towards an assessment item submitted to fulfil the requirements of %n"
                                    + "INFS2605 (UNSW Sydney). We declare that it is our own work, except where acknowledged, %n"
                                    + "and has not been submitted for academic credit elsewhere. We acknowledge that the %n"
                                    + "assessor of this item may, for the purpose of assessing this item: Reproduce this %n"
                                    + "assessment item and provide a copy to another member of the University; and/or, %n"
                                    + "Communicate a copy of this assessment item to a plagiarism checking service (which may %n"
                                    + "then retain a copy of the assessment item on its database for the purpose of future %n"
                                    + "plagiarism checking). We certify that we have read and understood the UNSW University %n"
                                    + "Rules in respect of Student Academic Misconduct.%n");
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        
        alert.setTitle("About");
        alert.setHeaderText("Copyright Statement");
        alert.setContentText("Click to view statement");
        
        TextArea area = new TextArea(string);
        alert.getDialogPane().setExpandableContent(area);
        alert.showAndWait();
    }
}
