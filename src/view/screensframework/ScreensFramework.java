package view.screensframework;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Locale;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.TAB;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * @author BOUGSID Ayoub
 */
public class ScreensFramework extends Application {

    public static String loginScreen = "login";
    public static String loginFile = "/view/login.fxml";
    public static String itemScreen = "Item";
    public static String itemFile = "/app/item/item.fxml";
    public static String mainScreen = "main";
    public static String mainFile = "/view/main/main.fxml";
    public static String supplierScreen = "supplier";
    public static String supplierFile = "/app/supplier/supplier.fxml";
    public static String orderLineScreen = "orderline";
    public static String orderLineFile = "/app/orderline/orderline.fxml";
    public static String payment = "/app/orderline/payment.fxml";
    public static String orderScreen = "order";
    public static String order = "/app/order/order.fxml";
    public static String item = "/app/item/item.fxml";
    public static String supplier = "/app/supplier/supplier.fxml";
    public static String addItem = "/app/item/addItem.fxml";
    public static String check = "/app/check/customerPayment.fxml";
    public static String customer = "/app/customer/customer.fxml";
    public static String addCustomer = "/app/customer/addCostumer.fxml";
    public static String menu = "/view/screensframework/menu.fxml";

    public static Stage mainStage;

    @Override
    public void start(Stage primaryStage) {

        Locale.setDefault(new Locale("fr", "FR"));

        mainStage = primaryStage;
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(ScreensFramework.menu,true);
        //Group root = new Group();
        
//        root.getChildren().addAll(mainContainer);
//         get the user's screen size
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//         get width and height of screen and divide them by two
//        double width = screenSize.getWidth();
//        double height = screenSize.getHeight();
        //primaryStage.setMaximized(true);
          /*     scene.getStylesheets().add(getClass().getResource("/resources/css/controls.css").toExternalForm());
          scene.getStylesheets().add(getClass().getResource("/resources/css/style.css").toExternalForm());
         scene.getStylesheets().add(getClass().getResource("/resources/css/jfoenix-design.css").toExternalForm());
         scene.getStylesheets().add(getClass().getResource("/resources/css/jfoenix-fonts.css").toExternalForm());
         scene.getStylesheets().add(getClass().getResource("/resources/css/jfoenix-components.css").toExternalForm());
         scene.getStylesheets().add(getClass().getResource("/resources/css/jfoenix-main-demo.css").toExternalForm());*/
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
