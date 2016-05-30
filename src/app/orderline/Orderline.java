package app.orderline;

import app.item.Item;
import app.order.Order;
import app.supplier.Supplier;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleLongProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Orderline generated by hbm2java
 */
@Entity
@Table(name = "orderline", catalog = "sm"
)
public class Orderline implements java.io.Serializable {

    private LongProperty idOrderline;
    private ObjectProperty<Item> item;
    private ObjectProperty<Order> order;
    private ObjectProperty<Supplier> supplier;
    private IntegerProperty quantity;
    private FloatProperty reduction;
    private FloatProperty totalPrice;
    private FloatProperty subTotal;

    public Orderline(Orderline orderline) {
        this.idOrderline = orderline.idOrderline;
        this.item = orderline.item;
        this.order = orderline.order;
        this.supplier = orderline.supplier;
        this.quantity = new SimpleIntegerProperty(orderline.quantity.get());
        this.reduction = new SimpleFloatProperty(orderline.reduction.get());
        this.totalPrice = new SimpleFloatProperty(orderline.totalPrice.get());
        this.subTotal = new SimpleFloatProperty(orderline.subTotal.get());
    }

    public Orderline() {
        this.idOrderline = new SimpleLongProperty();
        this.item = new SimpleObjectProperty();
        this.order = new SimpleObjectProperty();
        this.supplier = new SimpleObjectProperty();
        this.quantity = new SimpleIntegerProperty();
        this.reduction = new SimpleFloatProperty(0);
        this.totalPrice = new SimpleFloatProperty(0);
        this.subTotal = new SimpleFloatProperty(0);
    }

    public LongProperty idOrderlineProperty() {
        return idOrderline;
    }

    public IntegerProperty quantityProperty() {
        this.totalPrice.set(getTotalPrice());
        this.subTotal.set(getSubTotal());
        return quantity;
    }

    public FloatProperty reductionProperty() {
        return reduction;
    }

    public FloatProperty totalPriceProperty() {
        return totalPrice;
    }

    public FloatProperty subTotalProperty() {
        return subTotal;
    }

    public ObjectProperty<Item> itemProperty() {
        return item;
    }

    public ObjectProperty<Supplier> supplierProperty() {
        return supplier;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id_orderline", unique = true, nullable = false)
    public Long getIdOrderline() {
        return this.idOrderline.get();
    }

    public void setIdOrderline(Long idOrderline) {
        this.idOrderline.set(idOrderline);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_item", nullable = false)
    public Item getItem() {
        return this.item.get();
    }

    public void setItem(Item item) {
        this.item.set(item);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order", nullable = false)
    public Order getOrder() {
        return this.order.get();
    }

    public void setOrder(Order order) {
        this.order.set(order);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_supplier", nullable = true)
    public Supplier getSupplier() {
        return this.supplier.get();
    }

    public void setSupplier(Supplier supplier) {
        this.supplier.set(supplier);
    }

    @Column(name = "quantity")
    public Integer getQuantity() {
        return this.quantity.get();
    }

    public void setQuantity(Integer quantity) {
        this.quantity.set(quantity);
    }

    @Column(name = "reduction", precision = 12, scale = 0)
    public Float getReduction() {
        return this.reduction.get();
    }

    public void setReduction(Float reduction) {
        this.reduction.set(reduction);
    }

    @Transient
    public Float getTotalPrice() {
        return getItem().getPrice() * getQuantity();
    }

    @Transient
    public Float getSubTotal() {
        return getTotalPrice() - getReduction() / 100;
    }

}
