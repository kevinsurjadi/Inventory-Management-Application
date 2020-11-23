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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author kevinsurjadi
 */
public class OrderAddController {
    Database db = new Database();
    
    @FXML
    private TableView<productOrder> newOrderTable;
    
    @FXML
    private TableColumn<productOrder, String> orderProductNameCol;

    @FXML
    private TableColumn<productOrder, String> orderSupplierCol;

    @FXML
    private TableColumn<productOrder, String> orderQtyCol;

    @FXML
    private TableColumn<productOrder, String> orderPriceCol;
    
    @FXML
    private TableColumn<productOrder, String> orderIDCol;

    
    @FXML
    TextField product;
    
    @FXML
    ComboBox supplier;

    @FXML
    TextField qty;
    
    @FXML
    TextField price;
    
    @FXML
    TextField orderIDField;
    
    @FXML
    private Button editOrderButton;

    @FXML
    private Button removeOrderButton;

    @FXML
    private Button addOrderButton;
            
    private Orders order;
    
    private productOrder pOrder;
    
    ObservableList<productOrder> orderList = FXCollections.observableArrayList();
    
    public void intialize() throws SQLException {
        ObservableList<String> supplierList = FXCollections.observableArrayList(db.getSupplierNames());
        
        supplier.setItems(supplierList);
        
        newOrderTable.setItems(db.getOrdersTable());
        
        orderProductNameCol.setCellValueFactory(new PropertyValueFactory<>("product"));
        orderSupplierCol.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        orderQtyCol.setCellValueFactory(new PropertyValueFactory<>("qty"));
        orderPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        orderIDCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));

       
    }
    
    public void setOrder(Orders order) {
        this.order = order;
        product.textProperty().bindBidirectional(order.productProperty());
        supplier.valueProperty().bindBidirectional(order.supplierProperty());
        qty.textProperty().bindBidirectional(order.qtyProperty());
        price.textProperty().bindBidirectional(order.priceProperty());
        
    }
    
    public void setpOrder(productOrder pOrder) {
        this.pOrder = pOrder;
        product.textProperty().bindBidirectional(pOrder.productProperty());
        supplier.valueProperty().bindBidirectional(pOrder.supplierProperty());
        qty.textProperty().bindBidirectional(pOrder.qtyProperty());
        price.textProperty().bindBidirectional(pOrder.priceProperty());

    }

    @FXML
    public void HandleAddToOrderButtonAction(ActionEvent Event) throws IOException, SQLException {
        db.createNewProductOrder(product.getText(), 
                                 supplier.getSelectionModel().getSelectedItem().toString(), 
                                 price.getText(), 
                                 qty.getText());
        
        newOrderTable.setItems(db.getOrdersTable());
        
        product.setText("");
        supplier.setValue("");
        price.setText("");
        qty.setText("");

    }
    
    @FXML
    public void HandleEditOrderButtonAction (ActionEvent Event) throws IOException, SQLException {
        productOrder pOrder = newOrderTable.getSelectionModel().getSelectedItem();
        
        db.editProductTable(product.getText(),
                            supplier.getSelectionModel().getSelectedItem().toString(),
                            price.getText(),
                            qty.getText(),pOrder.orderIdProperty().get());
        
        newOrderTable.setItems(db.getOrdersTable());
        
        System.out.println(pOrder.orderIdProperty().get());
        System.out.println(pOrder.productProperty().get());
        System.out.println(supplier.getSelectionModel().getSelectedItem().toString());
        newOrderTable.setItems(db.getOrdersTable());
        System.out.println("order edited");

    }
    
    @FXML
    public void HandleDeleteOrderButtonAction (ActionEvent Event) throws IOException, SQLException {
        productOrder pOrder = newOrderTable.getSelectionModel().getSelectedItem();
        
            db.removeProductOrder(pOrder.productProperty().get(),
                                  pOrder.supplierProperty().get(),
                                  pOrder.qtyProperty().get(),
                                  pOrder.priceProperty().get());
           
        newOrderTable.setItems(db.getOrdersTable());

        System.out.println("order deleted");
        
        
    }
    
}
