/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.unsw.business.infs2605.fxstarterkit;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author kevinsurjadi
 */
public class SupplierLog {
    private final SimpleStringProperty orderId;

    private final SimpleStringProperty email;
    
    public SupplierLog(String orderId, String email){
        this.orderId = new SimpleStringProperty(orderId);
        this.email = new SimpleStringProperty(email);
    }
    
    public SupplierLog() {
        this("","");
    }
    
    public SimpleStringProperty emailProperty() {
        return email;
    }
    
    public SimpleStringProperty orderIdProperty() {
        return orderId;
    }
}
