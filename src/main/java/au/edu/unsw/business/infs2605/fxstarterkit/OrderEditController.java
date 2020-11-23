/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.unsw.business.infs2605.fxstarterkit;

import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 *
 * @author kevinsurjadi
 */
public class OrderEditController {
    Database db = new Database();
    
    @FXML
    TextField product;    

    @FXML
    TextField qty;
    
    @FXML
    TextField price;
    
    @FXML
    ComboBox supplier;
    
    private Orders order;
    
    private productOrder pOrder;
    
    //ObservableList<productOrder> orderList = FXCollections.observableArrayList();
    
    public void intialize() throws SQLException {
    
    ObservableList<String> newsupplierList = FXCollections.observableArrayList(db.getSupplierNames());
        
    supplier.setItems(newsupplierList);
        /*ObservableList<String> supplierList = FXCollections.observableArrayList(db.getSupplierNames());
        
        supplier.setItems(supplierList);
        
        newOrderTable.setItems(db.getOrdersTable());
        
        orderProductNameCol.setCellValueFactory(new PropertyValueFactory<>("product"));
        orderSupplierCol.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        orderQtyCol.setCellValueFactory(new PropertyValueFactory<>("qty"));
        orderPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));*/
       
    }
    
    public void setOrder(Orders order) {
        this.order = order;
        product.textProperty().bindBidirectional(order.productProperty());
        supplier.valueProperty().bindBidirectional(order.supplierProperty());
        qty.textProperty().bindBidirectional(order.qtyProperty());
        price.textProperty().bindBidirectional(order.priceProperty());
    }
    
    public void setPOrder(productOrder pOrder) {
        this.pOrder = pOrder;
        
        product.textProperty().bindBidirectional(pOrder.productProperty());
        supplier.valueProperty().bindBidirectional(pOrder.supplierProperty());
        qty.textProperty().bindBidirectional(pOrder.qtyProperty());
        price.textProperty().bindBidirectional(pOrder.priceProperty());
    }
    
    
}
