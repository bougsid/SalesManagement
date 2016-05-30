package app.orderline;

import app.bank.Bank;
import app.payment.PaymentType;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import view.screensframework.ControlledScreen;
import view.screensframework.ScreensController;
import view.screensframework.ScreensFramework;

/**
 *
 * @author BOUGSID Ayoub
 */
public class PaymentController implements Initializable, ControlledScreen {

    @FXML
    private Label paymentLabel;

    @FXML
    private ComboBox<PaymentType> payment;

    @FXML
    private Label totalPayment;

    @FXML
    private TitledPane checkPane;

    @FXML
    private Label avanceCashLabel;

    @FXML
    private TextField avanceCash;

    @FXML
    private Label avanceCheckLabel;

    @FXML
    private TextField avanceCheck;

    @FXML
    private DatePicker dueDate;

    @FXML
    private ComboBox<Bank> bank;

    @FXML
    private TextField checkName;

    @FXML
    private TextField nbCheck;

    @FXML
    private Label nbCheckLabel;

    @FXML
    private Label dueDateLabel;

    @FXML
    private Label nbCheckLabel1;

    @FXML
    private Label nbCheckLabel11;

    @FXML
    private Label totalAvanceLabel;

    @FXML
    private Label mensualityLabel;

    @FXML
    private Button finalValidate;

    private IOrderlineMetier orderlineMetier;
    private ScreensController myController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //To change
        this.avanceCash.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    float value = Float.parseFloat(newValue);
                    avanceCash.setText(newValue);
                    totalAvanceLabel.setText(String.valueOf(Float.parseFloat(avanceCash.getText()) + Float.parseFloat(avanceCheck.getText())));

                } catch (NumberFormatException e) {
                    if (!newValue.equals("")) {
                        avanceCash.setText(oldValue);
                    }
                }
            }
        });
        this.avanceCheck.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    float value = Float.parseFloat(newValue);
                    avanceCheck.setText(newValue);
                    totalAvanceLabel.setText(String.valueOf(Float.parseFloat(avanceCash.getText()) + Float.parseFloat(avanceCheck.getText())));
                } catch (NumberFormatException e) {
                    if (!newValue.equals("")) {
                        avanceCheck.setText(oldValue);
                    }
                }
            }
        });
        payment.requestFocus();
        totalPayment.textProperty().bindBidirectional(((OrderlineMetier) orderlineMetier).getOrder().totalAmountProperty(), new NumberStringConverter());
        payment.setItems(FXCollections.observableList(((IOrderlineMetier) orderlineMetier).getPaymentTypes()));
        bank.setItems(FXCollections.observableList(((IOrderlineMetier) orderlineMetier).getBanks()));
        payment.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (((PaymentType) newValue) == PaymentType.CHECK) {
                    //((Stage)checkPane.getScene().getWindow()).setMinHeight(400);
                    ((Stage)checkPane.getScene().getWindow()).setHeight(500);
                    checkPane.setVisible(true);
                    checkPane.setExpanded(true);
                    avanceCash.requestFocus();
                } else {
                    checkPane.setVisible(false);
                    checkPane.setExpanded(false);
                    ((Stage)checkPane.getScene().getWindow()).setHeight(180);
                }
            }
        });
        //create Bill
        //((IOrderlineMetier) this.orderlineMetier).createBill();
    }

    public void validate() {
        myController.closeStage(ScreensFramework.payment);
        if (payment.getValue() == PaymentType.CASH) {
            ((IOrderlineMetier) orderlineMetier).makePayment();
        } else {
            float avanceespece = Float.parseFloat(this.avanceCash.getText());
            float avancecheck = Float.parseFloat(this.avanceCheck.getText());
            Date due = Date.valueOf(this.dueDate.getValue());
            Bank selectedBank = this.bank.getValue();
            String checkname = this.checkName.getText();
            int nbrCheque = Integer.parseInt(nbCheck.getText());
            ((IOrderlineMetier) orderlineMetier).makePayment(avanceespece, avancecheck, due, selectedBank, checkname, nbrCheque);
        }
        myController.closeStage(ScreensFramework.orderLineFile);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

    public void setOrderlineMetier(IOrderlineMetier orderlineMetier) {
        this.orderlineMetier = orderlineMetier;
    }

}
