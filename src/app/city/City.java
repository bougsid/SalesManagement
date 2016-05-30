package app.city;

import app.region.Region;
import app.customer.Customer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleLongProperty;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * City generated by hbm2java
 */
@Entity
@Table(name = "city", catalog = "sm"
)
public class City implements java.io.Serializable {

    private LongProperty idCity;
    private ObjectProperty<Region> region;
    private StringProperty name;
    private Set<Customer> customers = new HashSet<Customer>(0);

    public City() {
        this.idCity = new SimpleLongProperty();
        this.region = new SimpleObjectProperty();
        this.name = new SimpleStringProperty();
    }

    public LongProperty idCityProperty() {
        return idCity;
    }

    public StringProperty nameProperty() {
        return name;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id_city", unique = true, nullable = false)
    public Long getIdCity() {
        return this.idCity.get();
    }

    public void setIdCity(Long idCity) {
        this.idCity.set(idCity);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_region", nullable = false)
    public Region getRegion() {
        return this.region.get();
    }

    public void setRegion(Region region) {
        this.region.set(region);
    }

    @Column(name = "name")
    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "city")
    public Set<Customer> getCustomers() {
        return this.customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return getName();
    }
}
