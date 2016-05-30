package app.customer;

import app.city.City;
import app.region.Region;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import metier.IGenericMetier;
import util.GenericController;
import view.screensframework.ControlledScreen;
import view.screensframework.ScreensController;
import view.screensframework.ScreensFramework;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by AYOUB on 3/25/2016.
 */
public class AddCustomerController extends GenericController<Customer> implements Initializable, ControlledScreen {
    private ScreensController myController;

    @FXML
    private ComboBox<?> type;

    @FXML
    private TextField code;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private ComboBox<?> gender;

    @FXML
    private DatePicker birthdayDate;

    @FXML
    private TextField adresse;

    @FXML
    private TextField zip;

    @FXML
    private ComboBox<City> city;

    @FXML
    private ComboBox<Region> region;

    @FXML
    private TextField email;

    @FXML
    private TextField phone;

    @FXML
    private TextField fax;

    @FXML
    private TextField note;

    @FXML
    private ComboBox<Boolean> loyal;

    @FXML
    private DatePicker registrationDate;

    @FXML
    private ComboBox<?> state;

    @FXML
    private TitledPane moreInfoPane;

    @FXML
    private Button updateButton;
    @FXML
    private Button newOrderButton;
    @FXML
    private Button showChecksButton;
    @FXML
    private Button showOrdersButton;
    private Customer customer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genericInitializer(resources);
        //Set Regions
        region.setItems(FXCollections.observableList(((ICustomerMetier) metier).getRegions()));
        //regionComboBox.setValue(regions.get(0));
        region.valueProperty().addListener((obs, oldValue, newValue) -> {
            city.setItems(FXCollections.observableList(((ICustomerMetier) metier).getCities(newValue, null)));
        });
        //Set Cities
        city.setItems(FXCollections.observableList(((ICustomerMetier) metier).getCities(null, null)));
        loyal.getItems().add(new Boolean(true));
    }

    @FXML
    public Object add() {
        return super.add();
    }

    @FXML
    public void search(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String code = this.code.getText();
            customer = ((ICustomerMetier) metier).findByCode(code);
            if (customer != null) {
                super.setFiledsWithObject(customer);
                updateButton.setVisible(true);
                newOrderButton.setVisible(true);
                showOrdersButton.setVisible(true);
                showChecksButton.setVisible(true);
            } else {
                this.errors.add(resources.getString("validation.customernotfound"));
                showErrors();
                this.errors.clear();
                reset();
            }
        }
    }

    @FXML
    public void update() {
        if (customer != null) {
            setObjectWithFields(customer);
            metier.makePersistent(customer);
        }
    }

    @FXML
    public void reset() {
        this.code.requestFocus();
        updateButton.setVisible(false);
        newOrderButton.setVisible(false);
        showOrdersButton.setVisible(false);
        showChecksButton.setVisible(false);
        super.reset();
    }

    @FXML
    public void newOrder() {
        ((ICustomerMetier) metier).newOrder(customer);
        myController.loadScreen(ScreensFramework.orderLineFile, true);
    }

    @FXML
    public void showOrders() {
        ((ICustomerMetier) metier).showOrders(customer);
        myController.loadScreen(ScreensFramework.order, true);
    }

    @FXML
    public void showChecks() {
        ((ICustomerMetier) metier).showChecks(customer);
        myController.loadScreen(ScreensFramework.check, true);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

    @Override
    public void setMetier(IGenericMetier metier) {
        this.metier = metier;
    }
}
