package app.item;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import app.category.Category;
import app.supplier.Supplier;
import javafx.collections.FXCollections;
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
public class ItemController extends GenericController<Item> implements Initializable, ControlledScreen {

    /**
     * Initializes the controller class.
     */
    @FXML
    @Checkable
    private TextField barcode;

    @FXML
    @Checkable
    private TextField label;

    @FXML
    @Checkable(pattern = Checkable.Pattern.FLOAT)
    private TextField price;

    @FXML
    @Checkable(pattern = Checkable.Pattern.INTEGER)
    private TextField quantity;

    @FXML
    @Checkable
    private ComboBox<Category> category;

    @FXML
    private TextField description;

    @FXML
    private DatePicker expirationDate;


    @FXML
    private ComboBox<Supplier> supplier;
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
    private TableView<Item> itemTable;

    private ScreensController myController;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        //
        genericInitializer(itemTable, resources);
        category.setItems(FXCollections.observableList(((IItemMetier) metier).getCategories()));
        supplier.setItems(FXCollections.observableList(((IItemMetier) metier).getSuppliers()));
    }

    public Object add() {
        Item item = (Item) super.add();
        return item;
    }

    public void delete() {
        super.delete();
    }

    public void update() {
        super.update();
    }

    public void reset() {
        super.reset();
        barcode.requestFocus();
    }

    public void setMetier(IGenericMetier metier) {
        this.metier = metier;
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }
}
