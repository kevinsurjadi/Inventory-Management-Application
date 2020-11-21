/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.unsw.business.infs2605.fxstarterkit;

/**
 *
 * @author susannayao
 */
public class StoreManager {
    private String name;
    private String phone;
    private String address;
    private String email;
    
    public StoreManager(String name, String phone, String address, String email){
        this.name=name;
        this.phone=phone;
        this.address=address;
        this.email=email;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(){
        this.name=name;
    }
    
    public String getPhone(){
        return phone;
    }
    
    public void setPhone(){
        this.phone=phone;
    }
    
    public String getAddress(){
        return address;
    }
    
    public void setAddress(){
        this.address=address;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(){
        this.email=email;
    }
}
