/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.unsw.business.infs2605.fxstarterkit;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author susannayao
 */
public class productOrder {
    private final SimpleStringProperty orderId;
    private final SimpleStringProperty product;
    private final SimpleStringProperty supplier;
    private final SimpleStringProperty price;
    private final SimpleStringProperty qty;
    //private final SimpleStringProperty statusPO;
    
    public productOrder(String orderId, String product, String supplier, String qty, String price){
        this.orderId = new SimpleStringProperty(orderId);
        this.product = new SimpleStringProperty(product);
        this.supplier = new SimpleStringProperty(supplier);
        this.price = new SimpleStringProperty(price);
        this.qty = new SimpleStringProperty(qty);
        //this.statusPO = new SimpleStringProperty(status);
    }
    
    /*public productOrder(String product, String supplier, String qty, String price){
        this.product = new SimpleStringProperty(product);
        this.supplier = new SimpleStringProperty(supplier);
        this.price = new SimpleStringProperty(price);
        this.qty = new SimpleStringProperty(qty);
        this.orderId = new SimpleStringProperty();
        //this.statusPO = new SimpleStringProperty(status);
    }*/
    
    public productOrder(){
        this("","","","","");
    }
    /*
    public SimpleStringProperty productIDPOProperty(){
        return productID;
    */
    
    public SimpleStringProperty supplierProperty(){
        return supplier;
    }
    
    public SimpleStringProperty orderIdProperty(){
        return orderId;
    }
    
    public SimpleStringProperty productProperty(){
        return product;
    }
    
    public SimpleStringProperty priceProperty(){
        return price;
    }
    
    public SimpleStringProperty qtyProperty(){
        return qty;
    }

    /*public SimpleStringProperty statusProperty(){
        return statusPO;
    }
*/
}
