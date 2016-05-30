package app.payment;

/**
 *
 * @author BOUGSID Ayoub
 */
public enum PaymentType {

    CHECK("Cheque"), CASH("Espece");

    private final String fieldDescription;

    private PaymentType(String value) {
        fieldDescription = value;
    }

    public String toString() {
        return fieldDescription;
    }

}
