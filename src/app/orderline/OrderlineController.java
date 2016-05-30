package app.orderline;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import app.bank.Bank;
import app.customer.Customer;
import app.item.Item;
import app.order.Order;
import app.payment.PaymentType;
import app.supplier.Supplier;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import util.Dialogs;
import util.GenericController;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import metier.IGenericMetier;
import view.screensframework.ControlledScreen;
import view.screensframework.ScreensController;
import view.screensframework.ScreensFramework;

/**
 * FXML Controller class
 *
 * @author BOUGSID Ayoub
 */
public class OrderlineController extends GenericController<Orderline> implements Initializable, ControlledScreen {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label nomClientLabel;

    @FXML
    private Label phoneClientLabel;

    @FXML
    private Label noVenteLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private TableView<Orderline> orderlineTable;

    @FXML
    private TableColumn<?, ?> barcodeColumn;

    @FXML
    private TableColumn<?, ?> labelColumn;

    @FXML
    private TableColumn<?, ?> supplierColumn;

    @FXML
    private TableColumn<?, ?> priceColumn;

    @FXML
    private TableColumn<?, ?> quantityColumn;

    @FXML
    private TableColumn<?, ?> totalPriceColumn;

    @FXML
    private TableColumn<?, ?> reductionColumn;

    @FXML
    private TableColumn<?, ?> subTotalColumn;

    @FXML
    private Button createBillBtn;

    @FXML
    private Label totalLabel;

    @FXML
    private Button delete;

    @FXML
    private Button reset;

    @FXML
    private TextField barcode;

    @FXML
    private ComboBox item;

    @FXML
    private ComboBox<Supplier> supplier;

    @FXML
    private TextField price;

    @FXML
    private TextField quantity;

    @FXML
    private Button add;

    //pament attributs
    @FXML
    private Label totalPayment;

    @FXML
    private ComboBox<PaymentType> payment;

    @FXML
    private ComboBox<Bank> bank;

    @FXML
    private TitledPane checkPane;

    @FXML
    private TextField avanceCash;

    @FXML
    private TextField avanceCheck;

    @FXML
    private Label totalAvanceLabel;

    @FXML
    private Label nbCheckLabel;

    @FXML
    private TextField nbCheck;

    @FXML
    private TextField checkName;
    @FXML
    private TextField reduction;

    @FXML
    private Label mensualityLabel;

    @FXML
    private Label dueDateLabel;

    @FXML
    private DatePicker dueDate;

    @FXML
    private Button finalValidate;

    private ScreensController myController;

    private Stage paymentStage;
    private Customer customer;
    private Order order;
    private List<Item> items;
    private List<Item> filtredItems;

    @Override
    public void initialize(URL url, ResourceBundle resources) {

        genericInitializer(orderlineTable, resources);
        this.order = ((IOrderlineMetier) metier).getOrder();
        this.customer = this.order.getCustomer();
        this.items = ((IOrderlineMetier) metier).getItems();
        this.filtredItems = this.items;

        nomClientLabel.setText(customer.getFirstName() + " " + customer.getLastName());
        phoneClientLabel.setText(customer.getPhone());
        noVenteLabel.setText(order.getIdOrder().toString());
        dateLabel.setText(order.getCreationDate().toString());

        item.setItems(FXCollections.observableList(filtredItems));
        item.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try {
                    price.setText(String.valueOf(((Item) newValue).getPrice()));
                    List suppliers = ((Item) newValue).getSuppliers();
                    supplier.setItems(FXCollections.observableList(suppliers));
                    supplier.getSelectionModel().selectFirst();
                } catch (NullPointerException e) {
                    System.out.println("To Change");
                }
            }
        });
        totalLabel.textProperty().bindBidirectional(((OrderlineMetier) metier).getOrder().totalAmountProperty(), new NumberStringConverter());
        orderlineTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Orderline>() {
            @Override
            public void changed(ObservableValue<? extends Orderline> observable, Orderline oldValue, Orderline newValue) {
                createBillBtn.setDisable(isEmpty());
            }
        });
        //new ComboBoxAutoComplete<Item>(item).setTextField(barcode);
//        item.getEditor().textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                item.show();
//                filtredItems = items.stream().filter(item1 -> item1.toString().contains(newValue)).collect(Collectors.toList());
//                item.setItems(FXCollections.observableList(filtredItems));
//            }
//        });
//        item.setConverter(new StringConverter() {
//            private Map<String, Object> map = new HashMap<>();
//
//            @Override
//            public String toString(Object t) {
//                if (t != null) {
//                    String str = t.toString();
//                    map.put(str, t);
//                    return str;
//                } else {
//                    return "";
//                }
//            }
//
//            @Override
//            public Object fromString(String string) {
//                if (!map.containsKey(string)) {
//                    item.setValue(null);
//                    item.getEditor().clear();
//                    return null;
//                }
//                return map.get(string);
//            }
//        });


        //this.add.setDefaultButton(true);        

//        observableObjects.addListener(new ListChangeListener<Orderline>() {
//
//            @Override
//            public void onChanged(ListChangeListener.Change<? extends Orderline> c) {
//                createBillBtn.setDisable(observableObjects.size() == 0);
//            }
//        });
    }

    public Object add() {
        Orderline tmpOrderline = (Orderline) context.getBean(Orderline.class);
        if (item.getValue() == null) {
            if(!barcode.getText().equals("")){
                boolean result = showItemNotExistDialog();
                if (result == true) {
                    Item item = ((IOrderlineMetier) metier).addNewItem(barcode.getText(), new Float(12));
                    updateItemComboBox();
                    this.item.setValue(item);
                } else {
                    return null;
                }
            }else{
                return null;
            }
        }
        setObjectWithFields(tmpOrderline);
        metier.makePersistent(tmpOrderline);
        Orderline orderline = new Orderline(tmpOrderline);
        Orderline found = null;
        for (Orderline ol : observableObjects) {
            if (ol.getItem().equals(orderline.getItem())) {
                ol.setQuantity(ol.getQuantity() + orderline.getQuantity());
                orderlineTable.getSelectionModel().select(ol);
                found = ol;
                break;
            }
        }
        if (found == null) {
            observableObjects.add(orderline);
        } else {
            orderline = found;
        }
        orderlineTable.getSelectionModel().clearSelection();
        orderlineTable.getSelectionModel().select(orderline);
        if (quantity.getText().equals("0")) {
            quantity.setText("");
            quantity.requestFocus();
        }else{
            reset();
        }
        return orderline;
    }

    public void delete() {
        super.delete();
    }

    public void update() {
//        supplier.show();
//        Orderline entity = getSelectedObject();
//        setObjectWithFields(entity);
//        ((IOrderlineMetier) metier).update(entity);
    }

    public void reset() {
        super.reset();
        this.barcode.requestFocus();
    }

    @FXML
    void handleEnterPressed(KeyEvent event) {
        System.out.println(event.getCode() + "-" + event.getText() + "-" + KeyCode.ENTER);
        if (event.getCode() == KeyCode.ENTER) {
            add();
        } else {
            if (barcode.isFocused()) {
                String barcodeOrLabel = this.barcode.getText() + event.getText();
                if (event.getCode() == KeyCode.BACK_SPACE && barcodeOrLabel.length() != 0)
                    barcodeOrLabel = barcodeOrLabel.substring(0, barcodeOrLabel.length() - 1);
                //System.out.println("barcodeLabel =" + barcodeOrLabel);
                if (!barcodeOrLabel.equals("")) {
                    String finalBarcodeOrLabel = barcodeOrLabel;
                    this.filtredItems = this.items.stream()
                            .filter(item -> item.getLabel().toLowerCase().contains(finalBarcodeOrLabel.toLowerCase()))
                            .collect(Collectors.toList());
                } else {
                    this.filtredItems = this.items;
                }
                this.item.setItems(FXCollections.observableList(this.filtredItems));
                this.item.show();
            } else {

            }
            //System.out.println("barcode = " + barcode.getText());
        }
    }

    @FXML
    void applyItemFilter(KeyEvent event) {
        //TODO
        String str = item.getAccessibleText();
        System.out.println(str);
    }


    @FXML
    void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case ADD: {
                reset();
                break;
            }
            case F8: {
                createBill();
                break;
            }
            case SUBTRACT:
            case DELETE: {
                delete();
                break;
            }
        }
    }

    public void createBill() {
        if (!isEmpty()) {
            myController.loadScreen(ScreensFramework.payment, true);
        }
    }

    public void setMetier(IGenericMetier metier) {
        this.metier = metier;
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    //    public class ComboBoxAutoComplete<T> {
//
//        private ComboBox<T> cmb;
//        String filter = "";
//        private ObservableList<T> originalItems;
//        private TextField textField;
//
//        public ComboBoxAutoComplete(ComboBox<T> cmb) {
//            this.cmb = cmb;
//            originalItems = FXCollections.observableArrayList(cmb.getItems());
//            //cmb.setTooltip(new Tooltip());
//            //cmb.setOnHidden(this::handleOnHiding);
//        }
//
//        public void setTextField(TextField textField) {
//            this.textField = textField;
//            this.textField.setOnKeyPressed(this::handleOnKeyPressed);
//        }
//
//        public void handleOnKeyPressed(KeyEvent e) {
//            ObservableList<T> filteredList = FXCollections.observableArrayList();
//            KeyCode code = e.getCode();
//
//            if (e.getCode() == KeyCode.ENTER) {
//                add();
//            }
//            if (code.isLetterKey() || code.isDigitKey()) {
//                filter += e.getText();
//            }
//            if (code == KeyCode.BACK_SPACE && filter.length() > 0) {
//                filter = filter.substring(0, filter.length() - 1);
//                cmb.getItems().setAll(originalItems);
//            }
//            if (code == KeyCode.ESCAPE) {
//                filter = "";
//            }
//            if (filter.length() == 0) {
//                filteredList = originalItems;
//                //cmb.getTooltip().hide();
//            } else {
//                Stream<T> items = cmb.getItems().stream();
//                String txtUsr = filter.toString().toLowerCase();
//                items.filter(el -> el.toString().toLowerCase().contains(txtUsr)).forEach(filteredList::add);
//                System.out.println("size" + filteredList.size() + "text" + txtUsr);
////            cmb.getTooltip().setText(txtUsr);
////            Window stage = cmb.getScene().getWindow();
////            double posX = stage.getX() + cmb.getBoundsInParent().getMinX();
////            double posY = stage.getY() + cmb.getBoundsInParent().getMinY();
////            cmb.getTooltip().show(stage, cmb.getLayoutBounds().getMinX(), cmb.getLayoutBounds().getMinY());
//                cmb.show();
//            }
//            cmb.getItems().setAll(filteredList);
//        }
//
////        public void handleOnHiding(Event e) {
////            filter = "";
////            cmb.getTooltip().hide();
////            T s = cmb.getSelectionModel().getSelectedItem();
////            cmb.getItems().setAll(originalItems);
////            cmb.getSelectionModel().select(s);
////        }
//
//    }
    private void updateItemComboBox() {
        this.items = ((IOrderlineMetier) metier).getItems();
        //this.filtredItems = this.items;
        this.item.setItems(FXCollections.observableList(this.items));
    }

    private boolean showItemNotExistDialog() {
        String item = barcode.getText();
        Optional<ButtonType> result = Dialogs.createConfirmationDialog(
                resources.getString("validation.itemnotexist.title"),
                resources.getString("validation.itemnotexist.content"), "");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }
}
