package au.edu.unsw.business.infs2605.fxstarterkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author susannayao
 */
public class Database {
    
    // ::::CREATING THE TABLES::::
    public static void initialize() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        Statement stment = conn.createStatement();
          
        String employee = "CREATE TABLE IF NOT EXISTS EMPLOYEE(" 
                        + "EMP_ID INTEGER PRIMARY KEY AUTOINCREMENT"
                        + ",FIRST_NAME VARCHAR(20) NOT NULL"
                        + ",LAST_NAME VARCHAR(20) NOT NULL"
                        + ",EMAIL VARCHAR(20) NOT NULL" 
                        + ",PASSWORD VARCHAR(30) NOT NULL"
                        + ");";
        
        String s_employee = "CREATE TABLE IF NOT EXISTS S_EMPLOYEE(" 
                          + "S_EMP_ID INTEGER PRIMARY KEY AUTOINCREMENT"
                          + ",FIRST_NAME VARCHAR(20) NOT NULL"
                          + ",LAST_NAME VARCHAR(20) NOT NULL"
                          + ",EMAIL VARCHAR(20) NOT NULL" 
                          + ",SUPPLIER VARCHAR(40) NOT NULL" 
                          + ",PASSWORD VARCHAR(30) NOT NULL"
                          + ");";
        
        String supplier = "CREATE TABLE IF NOT EXISTS SUPPLIER(" 
			+ "SUPPLIER_ID INTEGER PRIMARY KEY AUTOINCREMENT"
			+ ",S_NAME VARCHAR(40) NOT NULL" 
			+ ",S_PHONE VARCHAR(20) NOT NULL"
                        + ",S_ADDRESS VARCHAR(50) NOT NULL"
                        + ",EMAIL VARCHAR(20) NOT NULL"
                        + ",FOREIGN KEY (EMAIL) REFERENCES USER(EMAIL)"
			+ ");";

        String order = "CREATE TABLE IF NOT EXISTS ORDERLIST(" 
                      + "ORDER_ID INTEGER PRIMARY KEY AUTOINCREMENT"
                      + ",PRODUCT_NAME VARCHAR(40) NOT NULL"
                      + ",SUPPLIER VARCHAR(40) NOT NULL"
                      + ",QTY VARCHAR(3) NOT NULL"
                      + ",STATUS VARCHAR(20) DEFAULT 'ORDER PLACED'"
                      + ",PRICE VARCHAR(6) NOT NULL"
                      + ",ORDER_PLACED TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                      + ",FOREIGN KEY (SUPPLIER) REFERENCES SUPPLIER(S_NAME)"
                      + ");";
        
        String productOrder = "CREATE TABLE IF NOT EXISTS PRODUCTORDER("
                            + "PRODUCT_ID INTEGER PRIMARY KEY AUTOINCREMENT"
                            + ",PRODUCT_NAME VARCHAR(40) NOT NULL"
                            + ",SUPPLIER VARCHAR(40) NOT NULL"
                            + ",QTY VARCHAR(3) NOT NULL"
                            + ",PRICE VARCHAR(6) NOT NULL"
                            + ");";
        
        String supplierLog = "CREATE TABLE IF NOT EXISTS SUPPLIERLOG("
                           + "ORDER_ID INTEGER PRIMARY KEY "
                           + ",EMAIL VARCHAR(20) NOT NULL"
                           + ");";
        stment.execute(employee);
        stment.execute(s_employee);
        stment.execute(supplier);
        stment.execute(order);
        stment.execute(productOrder);
        stment.execute(supplierLog);
        
        stment.close();
        conn.close();	
    }
    
    // ::::CLEARING THE TABLES::::
    public static void clearTables() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        Statement stment = conn.createStatement();

        String supplier = "DELETE FROM SUPPLIER";
        String order = "DELETE FROM ORDERLIST";
        String productOrder = "DELETE FROM PRODUCTORDER";
        String s_employee = "DELETE FROM S_EMPLOYEE";
        String employee = "DELETE FROM EMPLOYEE";
        
        stment.execute(supplier);
        stment.execute(order);
        stment.execute(productOrder);
        stment.execute(s_employee);
        stment.execute(employee);
        
        stment.close();
        conn.close();
    }
    
    public static void clearProductOrderTable() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        Statement stment = conn.createStatement();

        String productOrder = "DELETE FROM PRODUCTORDER";

        stment.execute(productOrder);
        
        stment.close();
        conn.close();  
    }

    // ::::POPULATING THE TABLES::::
    public static void createNewEmployee(String fName, String lName, String email, String password) throws SQLException {
        Connection ction = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = ction.prepareStatement("INSERT INTO EMPLOYEE(FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES(?,?,?,?)");
        
        stment.setString(1, fName);
        stment.setString(2, lName);
        stment.setString(3, email);
        stment.setString(4, password);
        stment.executeUpdate();
        
        stment.close();
        ction.close();
    }
    
    public static void createNewSEmployee(String fName, String lName, String email, String supplier, String password) throws SQLException {
        Connection ction = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = ction.prepareStatement("INSERT INTO S_EMPLOYEE(FIRST_NAME, LAST_NAME, EMAIL, SUPPLIER, PASSWORD) VALUES(?,?,?,?,?)");
        
        stment.setString(1, fName);
        stment.setString(2, lName);
        stment.setString(3, email);
        stment.setString(4, supplier);
        stment.setString(5, password);
        stment.executeUpdate();
        
        stment.close();
        ction.close();
    }
    
    public static void createNewSupplier(String name, String phone, String address, String email) throws SQLException {
        Connection ction = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = ction.prepareStatement("INSERT INTO SUPPLIER(S_NAME, S_PHONE, S_ADDRESS, EMAIL) VALUES(?,?,?,?)");
        
        stment.setString(1, name);
        stment.setString(2, phone);
        stment.setString(3, address);
        stment.setString(4, email);
        
        stment.executeUpdate();
        stment.close();
        ction.close();
    }
    
    public static void createNewOrder(int orderId, String pName, String supplier, String qty, String price, String status) throws SQLException {
        Connection ction = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = ction.prepareStatement("INSERT INTO ORDERLIST(ORDER_ID, PRODUCT_NAME, SUPPLIER, QTY, PRICE, STATUS) VALUES(?,?,?,?,?,?)");
        
        stment.setInt(1, orderId);
        stment.setString(2, pName);
        stment.setString(3, supplier);
        stment.setString(4, qty);
        stment.setString(5, price);
        stment.setString(6, status);
        
        stment.executeUpdate();
        stment.close();
        ction.close();
    }
    
    public static void createNewOrder(String pName, String supplier, String qty, String price) throws SQLException {
        Connection ction = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = ction.prepareStatement("INSERT INTO ORDERLIST(PRODUCT_NAME, SUPPLIER, QTY, PRICE) VALUES(?,?,?,?)");

        stment.setString(1, pName);
        stment.setString(2, supplier);
        stment.setString(3, qty);
        stment.setString(4, price);
        
        stment.executeUpdate();
        stment.close();
        ction.close();
    }
    
    public static void createNewProductOrder(int productID, String pName, String supplier, String qty, String price) throws SQLException {
        Connection ction = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = ction.prepareStatement("INSERT INTO PRODUCTORDER(PRODUCT_ID, PRODUCT_NAME, SUPPLIER, QTY, PRICE) VALUES(?,?,?,?,?)");

        stment.setInt(1, productID);
        stment.setString(2, pName);
        stment.setString(3, supplier);
        stment.setString(4, qty);
        stment.setString(5, price);
        
        stment.executeUpdate();
        stment.close();
        ction.close();
    }
    
    public static void createNewProductOrder(String pName, String supplier, String qty, String price) throws SQLException {
        Connection ction = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = ction.prepareStatement("INSERT INTO PRODUCTORDER(PRODUCT_NAME, SUPPLIER, QTY, PRICE) VALUES(?,?,?,?)");

        stment.setString(1, pName);
        stment.setString(2, supplier);
        stment.setString(3, qty);
        stment.setString(4, price);
        
        stment.executeUpdate();
        stment.close();
        ction.close();
    }
    
    public static void createSupplierLog(int orderID) throws SQLException {
        Connection ction = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = ction.prepareStatement("INSERT INTO USERLOG(EMAIL) WHERE ORDER_ID = ?");
        
        stment.setInt(1, orderID);
        
        stment.executeUpdate();
        stment.close();
        ction.close();
        
    }

    //::::UPDATING TABLES::::
    public static void updateSupplier(String name, String phone, String address, String email, String supplierId) throws SQLException {
        Connection ction = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = ction.prepareStatement("UPDATE SUPPLIER SET S_NAME = ?, S_PHONE = ?, S_ADDRESS = ?, EMAIL = ? WHERE SUPPLIER_ID = ?");
        
        stment.setString(1, name);
        stment.setString(2, phone);
        stment.setString(3, address);
        stment.setString(4, email);
        stment.setString(5, supplierId);
        
        stment.executeUpdate();
        stment.close();
        ction.close();
    }
    
    public static void updateTable(String pName, String supplier, String qty, String price, String orderID) throws SQLException {
        Connection ction = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = ction.prepareStatement("UPDATE ORDERLIST SET PRODUCT_NAME = ?, SUPPLIER = ?, QTY = ?, PRICE = ? WHERE ORDER_ID = ?");

        stment.setString(1, pName);
        stment.setString(2, supplier);
        stment.setString(3, qty);
        stment.setString(4, price);
        stment.setString(5, orderID);
        
        stment.executeUpdate();
        stment.close();
        ction.close();
    }
    
    public static void updateOrderTableS(String status, String orderId) throws SQLException {
        Connection ction = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = ction.prepareStatement("UPDATE ORDERLIST SET STATUS = ? WHERE ORDER_ID = ?");

        stment.setString(1, status);
        stment.setString(2, orderId);
        
        stment.executeUpdate();
        stment.close();
        ction.close();
    }
    
    public static void editProductTable(String product, String supplier, String qty, String price, String orderId) throws SQLException {
        Connection ction = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = ction.prepareStatement("UPDATE PRODUCTORDER SET PRODUCT_NAME = ?, SUPPLIER =?, QTY = ?, PRICE = ? WHERE PRODUCT_ID = ?");
    
        stment.setString(1, product);
        stment.setString(2, supplier);
        stment.setString(3, qty);
        stment.setString(4, price);
        stment.setString(5, orderId);
        
        stment.executeUpdate();
        stment.close();
        ction.close();
    }
    
    //::::REMOVING FROM TABLES::::
    // related to buttons of the screens
    public static void removeSupplier(String name, String phone, String address, String email) throws SQLException {
        Connection ction = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = ction.prepareStatement("DELETE FROM SUPPLIER WHERE S_NAME = ? AND S_PHONE = ? AND S_ADDRESS = ? AND EMAIL = ?");
        
        stment.setString(1, name);
        stment.setString(2, phone);
        stment.setString(3, address);
        stment.setString(4, email);
        
        stment.executeUpdate();
        stment.close();
        ction.close();
    }
    
    public static void removeOrder(String orderId, String product, String supplier) throws SQLException {
        Connection ction = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = ction.prepareStatement("DELETE FROM ORDERLIST WHERE ORDER_ID = ? AND PRODUCT_NAME = ? AND SUPPLIER = ?");
        
        stment.setString(1, orderId);
        stment.setString(2, product);
        stment.setString(3, supplier);
        
        stment.executeUpdate();
        stment.close();
        ction.close();
    }
    
    public static void removeProductOrder(String product, String supplier, String qty, String price) throws SQLException {
        Connection ction = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = ction.prepareStatement("DELETE FROM PRODUCTORDER WHERE PRODUCT_NAME = ? AND SUPPLIER =? AND QTY = ? AND PRICE = ?");
    
        stment.setString(1, product);
        stment.setString(2, supplier);
        stment.setString(3, qty);
        stment.setString(4, price);
        
        stment.executeUpdate();
        stment.close();
        ction.close();
    }

    
    //::::JOINING TABLES::::
    public static void joinOrders() throws SQLException {        
        Connection conn = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = conn.prepareStatement("INSERT INTO ORDERLIST (PRODUCT_NAME, SUPPLIER, QTY, PRICE) SELECT PRODUCT_NAME, SUPPLIER, QTY, PRICE FROM PRODUCTORDER");
        System.out.println("Successful join");
        stment.executeUpdate();
        stment.close();
        conn.close();
    }
    
    // ::::POPULATING THE TABLEVIEW WITH LISTS::::
    public ObservableList<Supplier> getSuppliers() throws SQLException {
        ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
        
        Connection conn = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        Statement stment = conn.createStatement();
        
        String query = "SELECT SUPPLIER_ID, S_NAME, S_PHONE, S_ADDRESS, EMAIL FROM SUPPLIER;";
        ResultSet rs = stment.executeQuery(query);
        
        while (rs.next()){
                supplierList.add(new Supplier(rs.getString(1), rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5)));
        }
        
        stment.close();
        conn.close();
        return supplierList;
    }
    
    public ObservableList<Orders> getOrders() throws SQLException {
        ObservableList<Orders> orderList = FXCollections.observableArrayList();
        
        Connection conn = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        Statement stment = conn.createStatement();
 
        String query = "SELECT ORDER_ID, PRODUCT_NAME, SUPPLIER, QTY, STATUS, PRICE, ORDER_PLACED FROM ORDERLIST;";
        ResultSet rs = stment.executeQuery(query);
        
        while (rs.next()){
                orderList.add(new Orders(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
        }
        
        stment.close();
        conn.close();
        return orderList;
    }

    public ObservableList<productOrder> getOrdersTable() throws SQLException {
        ObservableList<productOrder> newOrderList = FXCollections.observableArrayList();
        
        Connection conn = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        Statement stment = conn.createStatement();
        
        String query = "SELECT PRODUCT_ID, PRODUCT_NAME, SUPPLIER, QTY, PRICE FROM PRODUCTORDER;";
        ResultSet rs = stment.executeQuery(query);
       
        //stment.setString(1, supplier);
        
        while (rs.next()){
                newOrderList.add(new productOrder(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        
        stment.close();
        conn.close();
        return newOrderList;
    }
    
    public ObservableList<Orders> getOrders(String supplier) throws SQLException {
        ObservableList<Orders> orderList = FXCollections.observableArrayList();
        
        Connection conn = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = conn.prepareStatement("SELECT ORDER_ID, PRODUCT_NAME, SUPPLIER, QTY, STATUS, PRICE, ORDER_PLACED FROM ORDERLIST WHERE SUPPLIER = ?");
        
        stment.setString(1, supplier);
        ResultSet rs = stment.executeQuery();
        
        while (rs.next()){
                orderList.add(new Orders(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
        }
        
        stment.close();
        conn.close();
        return orderList;
    }

    public String[] getSupplierNames() throws SQLException {
        ArrayList<String> supplierList = new ArrayList<String>();
        
        try{
            Connection conn = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
            Statement stment = conn.createStatement();
 
            String query = "SELECT S_NAME FROM SUPPLIER;";
            ResultSet rs = stment.executeQuery(query);
        
            while (rs.next()){
                supplierList.add(rs.getString(1));
            }
        
            stment.close();
            conn.close();
        
            return supplierList.toArray(new String[0]);   
        }catch(SQLException e){
            System.out.print(e);
        }
        return supplierList.toArray(new String[0]);  
    }
    
    public void setSupplierLog(String orderId, String email) throws SQLException {
        Connection ction = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = ction.prepareStatement("INSERT INTO SUPPLIERLOG(ORDER_ID, EMAIL) VALUES(?,?)");
        
        stment.setString(1, orderId);
        stment.setString(2, email);

        stment.executeUpdate();
        
        stment.close();
        ction.close();
        
    }
    
    public String getSupplierLog(String orderID) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = conn.prepareStatement("SELECT EMAIL FROM SUPPLIERLOG WHERE ORDER_ID = ?");
        
        stment.setString(1, orderID);

        ResultSet rs = stment.executeQuery();
        
        stment.close();
        conn.close();
        return rs.getString(1);
    }
    
    //::::CHECKING LOGIN::::
    public boolean checkLoginEmployee(String email, String password) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = conn.prepareStatement("SELECT EMAIL, PASSWORD FROM EMPLOYEE WHERE EMAIL = ? AND PASSWORD = ?");
        
        stment.setString(1, email);
        stment.setString(2, password);
        
        ResultSet results = stment.executeQuery();
        
        if (results.next()) {
            return true;
        } else {
            return false;
        }
    }
    
    // Check login for Employee from Supplier company
    public boolean checkLoginSEmployee(String email, String password) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = conn.prepareStatement("SELECT EMAIL, PASSWORD FROM S_EMPLOYEE WHERE EMAIL = ? AND PASSWORD = ?");
        
        stment.setString(1, email);
        stment.setString(2, password);
        
        ResultSet results = stment.executeQuery();
        
        if (results.next()) {
            return true;
        } else {
            return false;
        }
    }
    
    //::::ADDITIONAL QUERIES::::
    public String getSupplier(String email, String password) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = conn.prepareStatement("SELECT SUPPLIER FROM S_EMPLOYEE WHERE EMAIL = ? AND PASSWORD = ?");
        
        stment.setString(1, email);
        stment.setString(2, password);
        
        ResultSet rs = stment.executeQuery();
        
        if(rs.next()) {
            return rs.getString(1);
        } else {
            return "";
        }
    }
    
    public String getSName(String email, String password) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = conn.prepareStatement("SELECT FIRST_NAME FROM S_EMPLOYEE WHERE EMAIL = ? AND PASSWORD = ?");
        
        stment.setString(1, email);
        stment.setString(2, password);
        
        ResultSet rs = stment.executeQuery();
        
        if(rs.next()) {
            return rs.getString(1);
        } else {
            return "";
        }
    }
    
    public String getName(String email, String password) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:InventoryDatabase.db");
        PreparedStatement stment = conn.prepareStatement("SELECT FIRST_NAME FROM EMPLOYEE WHERE EMAIL = ? AND PASSWORD = ?");
        
        stment.setString(1, email);
        stment.setString(2, password);
        
        ResultSet rs = stment.executeQuery();
        
        if(rs.next()) {
            return rs.getString(1);
        } else {
            return "";
        }
    }
}    
