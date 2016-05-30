package app.supplier;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import util.Checkable;
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
public class SupplierController extends GenericController<Supplier> implements Initializable, ControlledScreen {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Supplier> supplierTable;

    @FXML
    private TableColumn<Supplier, String> nameColumn;

    @FXML
    private TableColumn<Supplier, String> itemTypeColumn;

    @FXML
    private TableColumn<Supplier, String> adresseColumn;

    @FXML
    private TableColumn<Supplier, String> emailColumn;

    @FXML
    private TableColumn<Supplier, String> phoneColumn;

    @FXML
    private TableColumn<Supplier, String> faxColumn;

    @FXML
    @Checkable
    private TextField name;

    @FXML
    private TextField itemType;

    @FXML
    private TextField fax;

    @FXML
    private TextField adresse;

    @FXML
    private TextField email;

    @FXML
    @Checkable(pattern = Checkable.Pattern.INTEGER)
    private TextField phone;

    private ScreensController myController;

    @Override
    public void initialize(URL url, ResourceBundle resources) {

        genericInitializer( supplierTable, resources);
    }

    public Object add() {
        Supplier supplier = (Supplier) super.add();
        return supplier;
    }

    public void delete() {
        super.delete();
    }
    public void update(){
        super.update();
    }
    public void reset(){
        super.reset();
        this.name.requestFocus();
    }
    
    public void setMetier(IGenericMetier metier) {
        this.metier = metier;
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
}
