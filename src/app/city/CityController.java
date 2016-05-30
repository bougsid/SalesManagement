package app.city;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import util.GenericController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import metier.IGenericMetier;
import view.screensframework.ControlledScreen;
import view.screensframework.ScreensController;

/**
 * FXML Controller class
 *
 * @author BOUGSID Ayoub
 */
public class CityController extends GenericController<City> implements Initializable, ControlledScreen {

    /**
     * Initializes the controller class.
     */
        @FXML
    private TextField barcode;

    @FXML
    private TextField label;

    @FXML
    private TextField price;

    @FXML
    private TextField quantity;

    @FXML
    private TextField description;

    @FXML
    private DatePicker expirationDate;

    @FXML
    private ComboBox<?> category;

    @FXML
    private ComboBox<?> supplier;
    @FXML
    private TableColumn<?, ?> labelColumn;

    @FXML
    private TableColumn<?, ?> priceColumn;

    @FXML
    private TableColumn<?, ?> quantityColumn;

    @FXML
    private TableColumn<?, ?> expirationDateColumn;

    @FXML
    private TableColumn<?, ?> categoryColumn;

    @FXML
    private TableColumn<?, ?> supplierColumn;

    @FXML
    private TableColumn<?, ?> barcodeColumn;
    @FXML
    private TableView<City> cityTable;

    private ScreensController myController;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        genericInitializer(cityTable, resources);
    }

    public Object add() {
        return super.add();
    }

    public void delete() {
        super.delete();
    }
    public void update(){
        super.update();
    }
    public void reset(){
        super.reset();
    }
    
    public void setMetier(IGenericMetier metier) {
        this.metier = metier;
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
}
