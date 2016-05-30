package app.customer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import app.city.City;
import app.region.Region;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import org.controlsfx.validation.Validator;
import util.Checkable;
import util.GenericController;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import metier.IGenericMetier;
import view.screensframework.ControlledScreen;
import view.screensframework.ScreensController;

/**
 * FXML Controller class
 *
 * @author BOUGSID Ayoub
 */
public class CustomerController extends GenericController<Customer> implements Initializable, ControlledScreen {

    /**
     * Initializes the controller class.
     */

    @FXML
    private TableColumn<?, ?> codeColumn;

    @FXML
    private TableColumn<?, ?> firstNameColumn;

    @FXML
    private TableColumn<?, ?> lastNameColumn;

    @FXML
    private TableColumn<?, ?> genderColumn;

    @FXML
    private TableColumn<?, ?> birthdayDateColumn;

    @FXML
    private TableColumn<?, ?> adresseColumn;

    @FXML
    private TableColumn<?, ?> zipColumn;

    @FXML
    private TableColumn<?, ?> cityColumn;

    @FXML
    private TableColumn<?, ?> regionColumn;

    @FXML
    private TableColumn<?, ?> emailColumn;

    @FXML
    private TableColumn<?, ?> phoneColumn;

    @FXML
    private TableColumn<?, ?> faxColumn;

    @FXML
    private TableColumn<?, ?> noteColumn;

    @FXML
    private TableColumn<?, ?> loyalColumn;

    @FXML
    private TableColumn<?, ?> registrationDateColumn;

    @FXML
    private TableColumn<?, ?> stateColumn;

    @FXML
    private Button moinsBtn;

    @FXML
    private Button plusBtn;

    @FXML
    private ComboBox<?> type;

    @FXML
    @Checkable
    private TextField code;

    @FXML
    @Checkable
    private TextField firstName;

    @FXML
    @Checkable
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
    @Checkable(pattern = Checkable.Pattern.INTEGER)
    private TextField phone;

    @FXML
    private TextField fax;

    @FXML
    private TextField note;

    @FXML
    private ComboBox<?> loyal;

    @FXML
    private DatePicker registrationDate;

    @FXML
    private ComboBox<?> state;

    @FXML
    private Button add;
    @FXML
    private TableView<Customer> customerTable;
    private ScreensController myController;


    @Override
    public void initialize(URL url, ResourceBundle resources) {

        genericInitializer(customerTable, resources);
        //Set Regions
        region.setItems(FXCollections.observableList(((ICustomerMetier) metier).getRegions()));
        //regionComboBox.setValue(regions.get(0));
        region.valueProperty().addListener((obs, oldValue, newValue) -> {
            city.setItems(FXCollections.observableList(((ICustomerMetier) metier).getCities(newValue, null)));
        });
        //Set Cities
        city.setItems(FXCollections.observableList(((ICustomerMetier) metier).getCities(null, null)));

        //set States
        //state.setItems(FXCollections.observableList(states));
        //registreValidator();
    }

    private void registreValidator() {
        validationSupport.registerValidator(code, Validator.createEmptyValidator(resources.getString("validation.requiredField")));
        validationSupport.registerValidator(firstName, Validator.createEmptyValidator(resources.getString("validation.requiredField")));
        validationSupport.registerValidator(lastName, Validator.createEmptyValidator(resources.getString("validation.requiredField")));
        validationSupport.registerValidator(phone, Validator.createRegexValidator(resources.getString("validation.numbersonly"), "[0-9]+", null));
    }

    //    public void validate(){
//        if(code.getText().equals("")){
//            errors.add(resources.getString("validation.code"));
//        }
//        if(firstName.getText().equals("")){
//            errors.add(resources.getString("validation.firstName"));
//        }
//        if(lastName.getText().equals("")){
//            errors.add(resources.getString("validation.lastName"));
//        }
//        if(phone.getText().equals("")){
//            errors.add(resources.getString("validation.phone"));
//        }
//    }
    public Object add() {
        //validate();
            Customer customer = (Customer) super.add();
            return customer;

    }

    public void delete() {
        super.delete();
    }

    public void update() {
            super.update();
    }

    public void reset() {
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
