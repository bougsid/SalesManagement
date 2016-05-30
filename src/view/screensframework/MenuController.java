package view.screensframework;

import app.order.IOrderMetier;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by AYOUB on 3/25/2016.
 */
public class MenuController implements Initializable,ControlledScreen {
    private ScreensController myController;
    private IOrderMetier orderMetier;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void item(){
        myController.loadScreen(ScreensFramework.item,true);
    }
    public void supplier(){
        myController.loadScreen(ScreensFramework.supplier,true);
    }
    public void addItem(){
        myController.loadScreen(ScreensFramework.addItem,true);
    }
    public void addCustomer(){
        myController.loadScreen(ScreensFramework.addCustomer,true);
    }
    public void customer(){
        myController.loadScreen(ScreensFramework.customer,true);
    }
    public void order(){
        orderMetier.initializeOrders();
        myController.loadScreen(ScreensFramework.order,true);
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    public void setOrderMetier(IOrderMetier orderMetier) {
        this.orderMetier = orderMetier;
    }
}
