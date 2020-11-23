/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.unsw.business.infs2605.fxstarterkit;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author susannayao
 */
public class Supplier {
    private final SimpleStringProperty supplierId;
    private final SimpleStringProperty name;
    private final SimpleStringProperty phone;
    private final SimpleStringProperty address;
    private final SimpleStringProperty email;
    
    public Supplier(String supplierId, String name, String phone, String address, String email){
        this.name = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
        this.address = new SimpleStringProperty(address);
        this.email = new SimpleStringProperty(email);
        this.supplierId = new SimpleStringProperty(supplierId);
    }
    
    public Supplier() {
        this("","","","", "");
    }
    
    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }
    
    public SimpleStringProperty emailProperty() {
        return email;
    }
    
    public SimpleStringProperty supplierIdProperty() {
        return supplierId;
    }
}
