/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.unsw.business.infs2605.fxstarterkit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author kevinsurjadi
 */
public class StoreManagerDashboardController {
    Database db = new Database();
    
    @FXML 
    private TextField search;
    
    @FXML
    private PieChart statusPieChart;
    
    @FXML
    private PieChart supplierPieChart;
    
    //Navigation Buttons
    @FXML 
    private Button logoutButton;
    
    @FXML 
    private Button supplierButton;
    
    @FXML 
    private Button about;
    
    //Buttons above table
    @FXML
    private Button addButton;
    
    @FXML
    private Button updateButton;
    
    @FXML
    private Button deleteButton;
    
    @FXML
    private TableView<Orders> orderTable;
    
    @FXML
    private TableColumn<Orders, String> orderId;
    
    @FXML
    private TableColumn<Orders, String> supplier;
    
    @FXML
    private TableColumn<Orders, String> status;
    
    @FXML
    private TableColumn<Orders, String> timeStamp;    
    
    @FXML
    private Label percentage;
    
    @FXML
    private Label supplierDistribution;
    
    @FXML
    private Label name;
    
    ObservableList<Orders> orderObservableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws SQLException {
        name.setText(LoginController.welcomeName);
        
        statusPieChart();
        supplierPieChart();
            
        orderTable.setItems(db.getOrders());
        orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        supplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        timeStamp.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));
            
        FilteredList<Orders> filteredData = new FilteredList<>(db.getOrders(), p -> true);
		
	search.textProperty().addListener((observable, oldValue, newValue) -> {
		filteredData.setPredicate(Orders -> {
				
			if (newValue == null || newValue.isEmpty()) {
					return true;
			}

			String lowerCaseFilter = newValue.toLowerCase();
				
			if (Orders.orderIdProperty().get().toLowerCase().contains(lowerCaseFilter)) {
				return true;
			} else if (Orders.supplierProperty().get().toLowerCase().contains(lowerCaseFilter)) {
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
    /*
    public ObservableList<Orders> getOrders() throws SQLException{
        Connection ction = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        Statement stment = ction.createStatement();  
        String query = "SELECT ORDER_ID, SUPPLIER_NAME, STORE_EMAIL, STATUS, TSTAMP FROM ORDERS WHERE STORE_EMAIL = '" + loggedInUser + "';";
        ResultSet rs = stment.executeQuery(query);
        ObservableList<Orders> ordersList = FXCollections.observableArrayList();
        while (rs.next()){
            ordersList.add(new Orders(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        stment.close();
        ction.close();
        return ordersList;
    }*/
    
    // piechart Order Status left hand side of Dashboard 
    public void statusPieChart() throws SQLException {
        ObservableList<PieChart.Data> orderStatus = FXCollections.observableArrayList();
        
        Connection ction = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        Statement stment = ction.createStatement();
        
        try{
            String query = "SELECT COUNT(ORDER_ID), STATUS FROM ORDERLIST GROUP BY STATUS;";
            ResultSet rs = stment.executeQuery(query);
            while(rs.next()){
                orderStatus.add(new PieChart.Data(rs.getString(2), rs.getDouble(1)));
            }
            statusPieChart.setData(orderStatus);
            statusPieChart.setTitle("Order Status"); 
            String totalOrders = "SELECT COUNT(*) FROM ORDERLIST;";
            ResultSet calculation = stment.executeQuery(totalOrders);
            double total = calculation.getDouble(1);
            for (final PieChart.Data data : statusPieChart.getData()){
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
                    
                    @Override
                    public void handle(MouseEvent event){
                        percentage.setText(String.valueOf(data.getPieValue()/(total/100)) + "%");
                    }
                });
            }
        } catch(Exception e){
            System.out.println("Error on DB connection");
        }
    }
    
    public void supplierPieChart() throws SQLException {
        ObservableList<PieChart.Data> orderStatus = FXCollections.observableArrayList();
        
        Connection ction = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        Statement stment = ction.createStatement();
        
        try{
            String query = "SELECT COUNT(ORDER_ID), SUPPLIER FROM ORDERLIST GROUP BY SUPPLIER;";
            ResultSet rs = stment.executeQuery(query);
            while(rs.next()){
                orderStatus.add(new PieChart.Data(rs.getString(2), rs.getDouble(1)));
            }
            supplierPieChart.setData(orderStatus);
            supplierPieChart.setTitle("Supplier Distribution");
            String supplierCalculation = "SELECT COUNT(SUPPLIER) FROM ORDERLIST;";
            ResultSet calculation = stment.executeQuery(supplierCalculation);
            double totalSuppliers = calculation.getDouble(1);
            for (final PieChart.Data data : supplierPieChart.getData()){
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
                    
                    @Override
                    public void handle(MouseEvent event){
                        supplierDistribution.setText(String.valueOf(data.getPieValue()/(totalSuppliers/100)) + "%");
                    }
                });
            }
        } catch(Exception e){
            System.out.println("Error on DB connection");
        }
    }
    
    //::::NAVIGATION BAR::::
    @FXML
    public void HandleDashboardButtonAction(ActionEvent Event) throws IOException {
        App.setRoot("StoreManagerDashboard");
    }
    
    @FXML
    public void HandleSupplierButtonAction(ActionEvent Event) throws IOException {
        App.setRoot("StoreViewSupplierList");
    }
    
    @FXML
    public void HandleOrdersButtonAction(ActionEvent Event) throws IOException {
        App.setRoot("StoreViewOrders");
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
}
