package app.item;

import app.category.Category;
import app.city.City;
import app.customer.Customer;
import app.customer.ICustomerMetier;
import app.region.Region;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import metier.IGenericMetier;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import util.Dialogs;
import util.GenericController;
import view.screensframework.ControlledScreen;
import view.screensframework.ScreensController;
import view.screensframework.ScreensFramework;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by AYOUB on 3/25/2016.
 */
public class AddItemController extends GenericController<Item> implements Initializable, ControlledScreen {
    private ScreensController myController;
    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private DatePicker expirationDate;

    @FXML
    private TextField additionalInfo;

    @FXML
    private TextField barcode;

    @FXML
    private TextField label;

    @FXML
    private TextField description;

    @FXML
    private TextField quantity;

    @FXML
    private TextField price;

    @FXML
    private ComboBox<Category> category;
    private Item item;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        genericInitializer(resources);
        category.setItems(FXCollections.observableList(((IItemMetier) metier).getCategories()));
        registrValidator();
    }

    private void registrValidator() {
        validationSupport.registerValidator(barcode, Validator.createEmptyValidator(resources.getString("validation.requiredField")));
        validationSupport.registerValidator(label, Validator.createEmptyValidator(resources.getString("validation.requiredField")));
        validationSupport.registerValidator(quantity, Validator.createEmptyValidator(resources.getString("validation.requiredField")));
        validationSupport.registerValidator(price, Validator.createEmptyValidator(resources.getString("validation.requiredField")));
        validationSupport.registerValidator(category, Validator.createEmptyValidator(resources.getString("validation.requiredField")));
    }

    @FXML
    public Object add() {
        if (!validateFields()) {
            Dialogs.createErrorDialog(resources.getString("validation.error"), resources.getString("validation.fillAll"), resources.getString("validation.addFailed"));
            return null;
        }
        return super.add();
    }

    private boolean validateFields() {
        if (barcode.getText() == "" ||
                label.getText() == "" ||
                quantity.getText() == "" ||
                price.getText() == "" ||
                category.getSelectionModel().getSelectedItem() == null
                )
            return false;
        return true;
    }

    @FXML
    public void search(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String barCode = this.barcode.getText();
            item = ((IItemMetier) metier).findByCode(barCode);
            if (item != null) {
                super.setFiledsWithObject(item);
                updateButton.setVisible(true);
            } else {
                reset();
            }
        }
    }

    @FXML
    public void update() {
        if (item != null) {
            setObjectWithFields(item);
            metier.makePersistent(item);
        }
    }

    @FXML
    public void reset() {
        this.barcode.requestFocus();
        updateButton.setVisible(false);
        super.reset();
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
