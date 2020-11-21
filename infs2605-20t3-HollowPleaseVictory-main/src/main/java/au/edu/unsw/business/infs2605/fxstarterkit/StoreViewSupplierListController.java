package au.edu.unsw.business.infs2605.fxstarterkit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author susannayao
 */
public class StoreViewSupplierListController {

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
    
    @FXML
    private Label name;
    
    //Buttons on top of table
    @FXML
    private Button addButton;
    
    @FXML
    private Button updateButton;
    
    @FXML
    private Button deleteButton;
    
    //Table
    @FXML
    TableView<Supplier> supplierTable;
    
    @FXML
    TableColumn<Supplier, String> supplier_name;
    
    @FXML
    TableColumn<Supplier, String> supplier_phone;
    
    @FXML
    TableColumn<Supplier, String> supplier_address;
    
    @FXML
    TableColumn<Supplier, String> supplier_email;
    
    ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
    
    @FXML
    public void initialize() throws SQLException {
        name.setText(LoginController.welcomeName);
        
        supplierTable.setItems(db.getSuppliers());
        
        supplier_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        supplier_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        supplier_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        supplier_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        // SEARCH Function
        
        FilteredList<Supplier> filteredData = new FilteredList<>(db.getSuppliers(), p -> true);
		
	search.textProperty().addListener((observable, oldValue, newValue) -> {
		filteredData.setPredicate(Supplier -> {
				
			if (newValue == null || newValue.isEmpty()) {
					return true;
			}

			String lowerCaseFilter = newValue.toLowerCase();
				
			if (Supplier.nameProperty().get().toLowerCase().contains(lowerCaseFilter)) {
				return true;
			} else if (Supplier.phoneProperty().get().toLowerCase().contains(lowerCaseFilter)) {
				return true;
			} else if (Supplier.addressProperty().get().toLowerCase().contains(lowerCaseFilter)) {
				return true;
			} else if (Supplier.emailProperty().get().toLowerCase().contains(lowerCaseFilter)) {
				return true;
			}
			return false; 
		});
	});
		
	SortedList<Supplier> sortedData = new SortedList<>(filteredData);	
	sortedData.comparatorProperty().bind(supplierTable.comparatorProperty());
	supplierTable.setItems(sortedData);
    }
    
    
    @FXML
    public void HandleLogoutButtonAction(ActionEvent Event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        
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
    public void HandleAddButtonAction(ActionEvent Event) throws IOException, SQLException {
        Supplier supplier = new Supplier();
        
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("supplierAdd.fxml"));
        DialogPane supplierDialogPane = fxmlLoader.load();
        
        SupplierAddController supplierController = fxmlLoader.getController();
        supplierController.setSupplier(supplier);
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(supplierDialogPane);
        dialog.setTitle("Add Supplier");
        
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        
        if(clickedButton.get() == ButtonType.OK){
            System.out.print("OK");
            db.createNewSupplier(supplier.nameProperty().get(),supplier.phoneProperty().get(),supplier.addressProperty().get(),supplier.emailProperty().get());
            supplierTable.setItems(db.getSuppliers());
            App.setRoot("StoreViewSupplierList");
        }
    }
    
    @FXML
    public void HandleUpdateButtonAction(ActionEvent event) throws IOException, SQLException {
        Supplier supplier = supplierTable.getSelectionModel().getSelectedItem();
 
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("supplierAdd.fxml"));
        DialogPane supplierDialogPane = fxmlLoader.load();
        
        SupplierAddController supplierController = fxmlLoader.getController();
        supplierController.setSupplier(supplier);
        
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(supplierDialogPane);
        dialog.setTitle("Edit Supplier");
        
        Optional<ButtonType> clickedButton = dialog.showAndWait();
        if(clickedButton.get() == ButtonType.OK){
            System.out.print("OK");
        }
    }
    
    @FXML
    public void HandleDeleteButtonAction(ActionEvent event) throws SQLException, IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete Confirmation");
        alert.setContentText("Are you sure you want to delete the selected entry?");
        
        Optional<ButtonType> isConfirmed = alert.showAndWait();
        if(isConfirmed.get() == ButtonType.OK) {
            Supplier supplier = supplierTable.getSelectionModel().getSelectedItem();
            db.removeSupplier(supplier.nameProperty().get(),supplier.phoneProperty().get(),supplier.addressProperty().get(),supplier.emailProperty().get());
            supplierTable.setItems(db.getSuppliers());
            App.setRoot("StoreViewSupplierList");
        }
    }
    
    @FXML
    public void HandleOrdersButtonAction(ActionEvent Event) throws IOException {
        App.setRoot("StoreViewOrders");
    }
    
    public void HandleDashboardButtonAction(ActionEvent Event) throws IOException {
        App.setRoot("StoreManagerDashboard");
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
        
        Alert alert = new Alert(AlertType.INFORMATION);
        
        alert.setTitle("About");
        alert.setHeaderText("Copyright Statement");
        alert.setContentText("Click to view statement");
        
        TextArea area = new TextArea(string);
        alert.getDialogPane().setExpandableContent(area);
        alert.showAndWait();
    }
}
