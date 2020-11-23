package au.edu.unsw.business.infs2605.fxstarterkit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        Database db = new Database();
        db.initialize();
        db.clearTables();
        
        db.createNewEmployee("Kevin","Surjadi","a","b");
        db.createNewEmployee("Sussana","Yao","lorikeet@gmail.com","rainbow123");
        db.createNewEmployee("William","Sexton","scareware@gmail.com","scary00");
        db.createNewEmployee("Jim","Ji","dog@outlook.com","smallBrain");
        
        db.createNewSEmployee("Kevin","Surjadi","b","The Big Dock","a");
        db.createNewSEmployee("Sussana","Yao","lorikeet@gmail.com","Jims Farm","rainbow123");
        db.createNewSEmployee("William","Sexton","scareware@gmail.com","Jims Farm","scary00");
        db.createNewSEmployee("Jim","Ji","dog@outlook.com","The Big Dock","miniBrain");

        db.createNewSupplier("BNY Trading", "0415163151", "Church Point NSW 2105", "monta@hotmail.com");
        db.createNewSupplier("Gilbert Wines", "0415163751", "Meadowbank NSW 2157", "ubansu12345678@gmail.com");
        db.createNewSupplier("Riccis Bikkies", "0435674151", "Kensington NSW 2999", "outbacksheppu@yahoo.com");
        db.createNewSupplier("Peloris Global Sourcing", "0415967421", "Haymarket NSW 2000", "oyo@gmail.com");
        db.createNewSupplier("Lemon Tree Dairy", "0424639151", "Cherrybrook NSW 2125", "Opps2@gmail.com");
        db.createNewSupplier("The Big Dock", "0495845759", "Westfield NSW 2125", "bigdoc@consulting.com");
        db.createNewSupplier("Jim's Farm", "0448952937", "Dog Kennel NSW 2125", "thebigbrainfarm.com");
        
        db.createNewOrder(690, "Lawn Mower","Jims Farm","10","50","ORDER PLACED");
        db.createNewOrder(456, "Tissues","The Big Dock","100","5","ORDER PLACED");
        db.createNewOrder(254, "Oranges","Jims Farm","200","3","ORDER PLACED");
        db.createNewOrder(563, "Carrots","Jims Farm","100","3","ORDER PLACED");
        db.createNewOrder(173, "Mango's","Jims Farm","100","5","ORDER PLACED");
        db.createNewOrder(534,"Wrench","Jims Farm","25","20","IN PROGRESS");
        db.createNewOrder(374,"Cups","Jims Farm","75","15","IN PROGRESS");
        db.createNewOrder(798, "Protein Powder","The Big Dock","10","90","IN PROGRESS");
        db.createNewOrder(342, "Milk","The Big Dock","100","5","DELIVERED");
        db.createNewOrder(763, "Apples","Jims Farm","100","5","DELIVERED");
        
        //db.createUserTable();
        //db.createSupplierTable();
        //db.createStoreTable();
        //db.fillOrdersTable(); 

        scene = new Scene(loadFXML("LoginScreenStore"), 1200, 800);

        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}