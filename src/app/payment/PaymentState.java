package app.payment;

/**
 *
 * @author BOUGSID Ayoub
 */
public enum PaymentState {

    PAYE("Payé"), NONPAYE("Non Payé"), IMPAYE("Impayé");

    private final String fieldDescription;

    private PaymentState(String value) {
        fieldDescription = value;
    }

    public String toString() {
        return fieldDescription;
    }

}
