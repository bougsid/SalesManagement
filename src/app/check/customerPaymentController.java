package app.check;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import app.payment.PaymentState;
import util.GenericController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import metier.IGenericMetier;
import view.screensframework.ControlledScreen;
import view.screensframework.ScreensController;
import view.screensframework.ScreensFramework;

/**
 * FXML Controller class
 *
 * @author BOUGSID Ayoub
 */
public class customerPaymentController extends GenericController<Check> implements Initializable, ControlledScreen {

    /**
     * Initializes the controller class.
     */



    @FXML
    private TableColumn<?, ?> numColumn;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> amountColumn;

    @FXML
    private TableColumn<?, ?> bankColumn;

    @FXML
    private TableColumn<?, ?> checkStateColumn;

    @FXML
    private TableColumn<?, ?> typeColumn;

    @FXML
    private TableColumn<?, ?> dueDateColumn;

    @FXML
    private TextField checkNum;


    @FXML
    private Button plusBtn;


    @FXML
    private TextField checkName;

    @FXML
    private TableView<Check> checkTable;

    @FXML
    private DatePicker checkDueDate;


    @FXML
    private Button moinsBtn;

    @FXML
    private TextField checkAmount;

    @FXML
    private ComboBox<?> checkBank;


    private ScreensController myController;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        genericInitializer(checkTable,resources);
        setCheckRowColor();

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
//    public Object add() {
//        return super.add();
//    }

   @FXML
    public void showOrders() {
        ((ICheckMetier) metier).showOrders(getSelectedObject());
        myController.loadScreen(ScreensFramework.order, true);
    }
  @FXML
   public void validateCheck() {
        Check check = getSelectedObject();
        if (check != null) {
            ((ICheckMetier) metier).validateCheck(check);
            setCheckRowColor();
        }
    }
    @FXML
    public void delete() {
        super.delete();
    }
    @FXML
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
