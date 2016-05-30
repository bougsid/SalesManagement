package app.extemployee;

import app.customer.Customer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleLongProperty;
import java.time.LocalDate;

import java.sql.Date;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Extemployee generated by hbm2java
 */
@Entity
@Table(name = "extemployee", catalog = "sm"
)
public class Extemployee implements java.io.Serializable {

    private LongProperty idCustomer;
    private ObjectProperty<Customer> customer;
    private LongProperty idCity;
    private StringProperty code;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty gender;
    private ObjectProperty<LocalDate> birthdayDate;
    private StringProperty adresse;
    private StringProperty zip;
    private StringProperty email;
    private StringProperty phone;
    private StringProperty fax;
    private StringProperty note;
    private BooleanProperty loyal;
    private ObjectProperty<LocalDate> registrationDate;
    private StringProperty state;
    private StringProperty sector;

    public Extemployee() {
        this.idCustomer = new SimpleLongProperty();
        this.customer = new SimpleObjectProperty();
        this.idCity = new SimpleLongProperty();
        this.code = new SimpleStringProperty();
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.gender = new SimpleStringProperty();
        this.birthdayDate = new SimpleObjectProperty();
        this.adresse = new SimpleStringProperty();
        this.zip = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.phone = new SimpleStringProperty();
        this.fax = new SimpleStringProperty();
        this.note = new SimpleStringProperty();
        this.loyal = new SimpleBooleanProperty();
        this.registrationDate = new SimpleObjectProperty();
        this.state = new SimpleStringProperty();
        this.sector = new SimpleStringProperty();
    }

    public LongProperty idCustomerProperty() {
        return idCustomer;
    }

    public LongProperty idCityProperty() {
        return idCity;
    }

    public StringProperty codeProperty() {
        return code;
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public StringProperty adresseProperty() {
        return adresse;
    }

    public StringProperty zipProperty() {
        return zip;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public StringProperty faxProperty() {
        return fax;
    }

    public StringProperty noteProperty() {
        return note;
    }

    public StringProperty stateProperty() {
        return state;
    }

    public StringProperty sectorProperty() {
        return sector;
    }

    @GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "customer"))
    @Id
    @GeneratedValue(generator = "generator")

    @Column(name = "id_customer", unique = true, nullable = false)
    public long getIdCustomer() {
        return this.idCustomer.get();
    }

    public void setIdCustomer(long idCustomer) {
        this.idCustomer.set(idCustomer);
    }

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    public Customer getCustomer() {
        return this.customer.get();
    }

    public void setCustomer(Customer customer) {
        this.customer.set(customer);
    }

    @Column(name = "id_city")
    public Long getIdCity() {
        return this.idCity.get();
    }

    public void setIdCity(Long idCity) {
        this.idCity.set(idCity);
    }

    @Column(name = "code")
    public String getCode() {
        return this.code.get();
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return this.firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    @Column(name = "last_name")
    public String getLastName() {
        return this.lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    @Column(name = "gender", length = 10)
    public String getGender() {
        return this.gender.get();
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    //@Temporal(TemporalType.DATE)
    @Column(name = "birthday_date", length = 10)
    public Date getBirthdayDate() {
        return Date.valueOf(this.birthdayDate.get());
    }

    public void setBirthdayDate(Date birthdayDate) {
        this.birthdayDate.set(birthdayDate.toLocalDate());
    }

    @Column(name = "adresse")
    public String getAdresse() {
        return this.adresse.get();
    }

    public void setAdresse(String adresse) {
        this.adresse.set(adresse);
    }

    @Column(name = "zip")
    public String getZip() {
        return this.zip.get();
    }

    public void setZip(String zip) {
        this.zip.set(zip);
    }

    @Column(name = "email")
    public String getEmail() {
        return this.email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    @Column(name = "phone")
    public String getPhone() {
        return this.phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    @Column(name = "fax")
    public String getFax() {
        return this.fax.get();
    }

    public void setFax(String fax) {
        this.fax.set(fax);
    }

    @Column(name = "note")
    public String getNote() {
        return this.note.get();
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    @Column(name = "loyal")
    public Boolean getLoyal() {
        return this.loyal.get();
    }

    public void setLoyal(Boolean loyal) {
        this.loyal.set(loyal);
    }

    //@Temporal(TemporalType.DATE)
    @Column(name = "registration_date", length = 10)
    public Date getRegistrationDate() {
        return Date.valueOf(this.registrationDate.get());
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate.set(registrationDate.toLocalDate());
    }

    @Column(name = "state")
    public String getState() {
        return this.state.get();
    }

    public void setState(String state) {
        this.state.set(state);
    }

    @Column(name = "sector")
    public String getSector() {
        return this.sector.get();
    }

    public void setSector(String sector) {
        this.sector.set(sector);
    }

}
