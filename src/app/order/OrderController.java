package app.order;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import app.bank.Bank;
import app.bill.Bill;
import app.cash.Cash;
import app.check.Check;
import app.check.ICheckMetier;
import app.payment.Payment;
import app.payment.PaymentState;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import metier.IGenericMetier;
import org.springframework.transaction.annotation.Transactional;
import util.GenericController;
import view.screensframework.ControlledScreen;
import view.screensframework.ScreensController;
import view.screensframework.ScreensFramework;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * FXML Controller class
 *
 * @author BOUGSID Ayoub
 */
public class OrderController extends GenericController<Order> implements Initializable, ControlledScreen {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<?, ?> customerColumn;

    @FXML
    private TableColumn<?, ?> creationDateColumn;

    @FXML
    private TableColumn<?, ?> shippingDateColumn;

    @FXML
    private TableColumn<?, ?> totalAmountColumn;

    @FXML
    private TableColumn<?, ?> paidAmountColumn;

    @FXML
    private TableColumn<?, ?> stateColumn;

    @FXML
    private Button moinsBtn;

    @FXML
    private Button plusBtn;

    @FXML
    private Button add;

    @FXML
    private DatePicker creationDate;

    @FXML
    private Label totalAmount;

    @FXML
    private DatePicker shippingDate;

    @FXML
    private Label paidAmount;

    @FXML
    private Label state;

    @FXML
    private TextField note;

    @FXML
    private Label bill;

    @FXML
    private ComboBox customer;
    //Check Controls
    @FXML
    private TableView<Check> checkTable;

    @FXML
    private TableColumn<Check, String> numColumn;

    @FXML
    private TableColumn<Check, String> nameColumn;

    @FXML
    private TableColumn<Check, Number> amountColumn;

    @FXML
    private TableColumn<Check, Bank> bankColumn;

    @FXML
    private TableColumn<Check, String> typeColumn;

    @FXML
    private TableColumn<Check, PaymentState> checkStateColumn;
    @FXML
    private TableColumn<Check, LocalDate> dueDateColumn;
    //Check Controls
    @FXML
    private TextField checkName;

    @FXML
    private TextField checkAmount;

    @FXML
    private ComboBox<Bank> checkBank;

    @FXML
    private DatePicker checkDueDate;

    @FXML
    private TextField cash;

    @FXML
    private TitledPane checkPane;
    @FXML
    private Button validateButton;
    private ObservableList<Check> checks;

    private ScreensController myController;

    @Override
    public void initialize(URL url, ResourceBundle resources) {

        genericInitializer(orderTable, resources);
        orderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                //updateCheckTable(newValue);
                refreshPayments(newValue);

            }
        });
        checkTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                validateButton.setDisable(newValue.getPaymentState() == PaymentState.PAYE);
                setFieldsWithCheck(newValue);
                if (!checkPane.isExpanded()) {
                    checkPane.setExpanded(true);
                }
            }
        });
        setCheckRowColor();


        customer.setItems(FXCollections.observableList(((IOrderMetier) metier).getCustomers()));
        checks = FXCollections.observableArrayList();

        if (!isEmpty()) {
            Order order = observableObjects.get(0);
            orderTable.getSelectionModel().select(order);
            refreshPayments(order);
        }
        setCellFactoryForCheck();
        checkTable.setItems(checks);
        checkBank.setItems(FXCollections.observableList(((IOrderMetier) metier).getBanks()));
    }

    public void setCheckRowColor() {
        //Change Color of Check Table
        PseudoClass nonpaye = PseudoClass.getPseudoClass("nonpaye");
        PseudoClass paye = PseudoClass.getPseudoClass("paye");
        PseudoClass impaye = PseudoClass.getPseudoClass("impaye");
        //PseudoClass down = PseudoClass.getPseudoClass("down");
        checkTable.setRowFactory(tableView -> {
            TableRow<Check> row = new TableRow<>();
            ChangeListener<PaymentState> changeListener = (obs, oldState, newState) -> {
//                row.pseudoClassStateChanged(nonpaye, oldState.equals(CheckState.NONPAYE.toString()));
//                row.pseudoClassStateChanged(paye, oldState.equals(CheckState.PAYE.toString()));
//                row.pseudoClassStateChanged(impaye, oldState.equals(CheckState.IMPAYE.toString()));
                row.pseudoClassStateChanged(nonpaye, oldState == PaymentState.NONPAYE);
                row.pseudoClassStateChanged(paye, oldState == PaymentState.PAYE);
                row.pseudoClassStateChanged(impaye, oldState == PaymentState.IMPAYE);
            };

            row.itemProperty().addListener((obs, oldCheck, newCheck) -> {
                if (oldCheck != null) {
                    oldCheck.paymentStateProperty().removeListener(changeListener);
                }
                if (newCheck != null) {
                    newCheck.paymentStateProperty().addListener(changeListener);
//                    row.pseudoClassStateChanged(nonpaye, newCheck.getState().equals(CheckState.NONPAYE.toString()));
//                    row.pseudoClassStateChanged(paye, newCheck.getState().equals(CheckState.PAYE.toString()));
//                    row.pseudoClassStateChanged(impaye, newCheck.getState().equals(CheckState.IMPAYE.toString()));
                    row.pseudoClassStateChanged(nonpaye, newCheck.getPaymentState() == PaymentState.NONPAYE);
                    row.pseudoClassStateChanged(paye, newCheck.getPaymentState() == PaymentState.PAYE);
                    row.pseudoClassStateChanged(impaye, newCheck.getPaymentState() == PaymentState.IMPAYE);
                } else {
                    row.pseudoClassStateChanged(nonpaye, false);
                    row.pseudoClassStateChanged(paye, false);
                    row.pseudoClassStateChanged(impaye, false);
                }
            });
            return row;
        });
    }

    public void refreshPayments(Order order) {
        Bill bill = order.getBill();
        Cash cashPayment = null;
        System.out.println("ssss"+bill.getPayments());
        Optional<Payment> optionalPayment = bill.getPayments().stream().filter(p -> p instanceof Cash).findFirst();
        if (optionalPayment.isPresent()){
            cashPayment = (Cash) optionalPayment.get();
            cash.setText(String.valueOf(cashPayment.getAmount()));
            cash.setDisable(cashPayment.getPaymentState() == PaymentState.PAYE);
        }
//        for (Payment payment : bill.getPayments()) {
//            if (payment instanceof Cash) {
//                cashPayment = (Cash) payment;
//                break;
//            }
//        }
        if (bill.getPayments().size() != 1 || bill.getChecks().size() != 0) {
            updateCheckTable(order);
        } else {
            checkTable.setItems(null);
            resetCheck();
        }
    }

    public void setCellFactoryForCheck() {
        numColumn.setCellValueFactory(cellData -> cellData.getValue().numProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
        bankColumn.setCellValueFactory(cellData -> cellData.getValue().bankProperty());
        dueDateColumn.setCellValueFactory(cellData -> cellData.getValue().dueDateProperty());
        checkStateColumn.setCellValueFactory(cellData -> cellData.getValue().paymentStateProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().avanceProperty());
    }

    public void updateCheckTable(Order order) {
        checks = FXCollections.observableList(order.getBill().getChecks());
        checkTable.setItems(checks);
    }

    @FXML
    void validateCash(KeyEvent event) {
        Order order = getSelectedObject();
        if (event.getCode() == KeyCode.ENTER) {
            ((IOrderMetier) metier).validateCash(order);
            cash.setDisable(true);
        }
    }

    public void validateCheck() {
        Check check = getSelectedCheck();
        Order order = getSelectedObject();
        if (check != null) {
            ((IOrderMetier) metier).validateCheck(order, check);
            setCheckRowColor();
            validateButton.setDisable(true);
        }
    }

    public void addCheck() {
        Order order = getSelectedObject();
        Check check = new Check();
        setCheckWithFields(check);
        checks.add(check);
        checkTable.getSelectionModel().select(check);
        ((IOrderMetier) metier).addCheck(order, check);
    }

    public void deleteCheck() {
        Check check = getSelectedCheck();
        Order order = getSelectedObject();
        if (check != null && order != null) {
            checks.remove(check);
            ((IOrderMetier) metier).deleteCheck(order, check);
        }
    }

    public void updateCheck() {
        Check check = getSelectedCheck();
        Order order = getSelectedObject();
        if (check != null && order != null) {
            setCheckWithFields(check);
            ((IOrderMetier) metier).updateCheck(check);
        }
    }

    public void resetCheck() {
        checkName.setText("");
        checkAmount.setText("0");
        checkBank.setValue(null);
        checkDueDate.setValue(null);
        checkName.requestFocus();
        checkTable.getSelectionModel().clearSelection();
    }

    public void setCheckWithFields(Check check) {
        check.setName(checkName.getText());
        check.setAmount(Float.valueOf(checkAmount.getText()));
        check.setBank(checkBank.getValue());
        check.setDueDate(Date.valueOf(checkDueDate.getValue()));
        if (check.getPaymentState() == null)
            check.setPaymentState(PaymentState.NONPAYE);
    }

    private void setFieldsWithCheck(Check check) {
        checkName.setText(check.getName());
        checkAmount.setText(String.valueOf(check.getAmount()));
        checkBank.setValue(check.getBank());
        checkDueDate.setValue(check.getDueDate().toLocalDate());
    }

    private Check getSelectedCheck() {
        return checkTable.getSelectionModel().getSelectedItem();
    }

    public Object add() {
        Order object = (Order) super.add();
        observableObjects.add(object);
        myController.loadScreen(ScreensFramework.orderLineFile, true);
        return object;
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
