/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.orderline;

import app.bank.Bank;
import app.bank.IBankMetier;
import app.item.IItemMetier;
import app.item.Item;
import app.order.IOrderMetier;
import app.order.Order;
import app.payment.IPaymentMetier;
import app.payment.PaymentType;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import metier.GenericMetier;
import metier.IGenericMetier;
import util.GenericController;

/**
 *
 * @author BOUGSID Ayoub
 */
public class OrderlineMetier
        extends GenericMetier<Orderline, Long>
        implements IOrderlineMetier {

    private IItemMetier itemMetier;
    //private IGenericMetier supplierMetier;
    private Order order;
    private IPaymentMetier paymentMetier;
    private IOrderMetier orderMetier;
    private IBankMetier bankMetier;

    public void setOrderlineDAO(IOrderlineDAO orderlineDAO) {
        this.dao = orderlineDAO;
    }

    @Override
    public void makePersistent(Orderline orderline) {
        //TODO
        orderline.setOrder(order);
        this.addOrderline(orderline);
    }

    @Override
    public void update(Orderline orderline) {
        //TODO
        //super.makePersistent(orderline);
    }

    @Override
    public void makeTransient(Orderline orderline) {
        //TODO
        Set<Orderline> orderlines = this.order.getOrderlines();
        Iterator<Orderline> olIterator = orderlines.iterator();
        while (olIterator.hasNext()) {
            Orderline ol = olIterator.next();
            if (ol.getItem().equals(orderline.getItem())) {
                olIterator.remove();
            }
        }
    }

    @Override
    public Orderline findById(Long id) {
        //TODO
        return super.findById(id);
    }

    @Override
    public List findAll() {
        //TODO
//        if (this.orderlineList == null) {
//            this.orderlineList = new ArrayList<>();
//        }

        /*if (this.order != null) {
         this.orderlineList.addAll(this.order.getOrderlines());
         }*/
        //return super.findAll();
//        return this.orderlineList;
        return null;
    }

    public List getItems() {
        return this.itemMetier.findAll();
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setItemMetier(IItemMetier itemMetier) {
        this.itemMetier = itemMetier;
    }

    private void addOrderline(Orderline orderline) {
        //Add new orderline to list if not exist else increment quantity
        for (Orderline ol : this.order.getOrderlines()) {
            if (ol.getItem().equals(orderline.getItem())
                    /*&& ol.getSupplier().equals(orderline.getSupplier())*/) {
                ol.setQuantity(ol.getQuantity() + orderline.getQuantity());
                refreshAmount();
                return;
            }
        }
        this.order.addOrderline(orderline);
        //Refresh Amount
        refreshAmount();
    }

    private void refreshAmount() {
        float totalAmount = 0;
        //float paidAmount = 0;
        for (Orderline orderline : this.order.getOrderlines()) {
            totalAmount += orderline.getTotalPrice();
            //paidAmount += orderline.getSubTotal();
        }
        this.order.setTotalAmount(totalAmount);
        //this.order.setPaidAmount(paidAmount);
    }
    public Item addNewItem(String label, Float price){
        Item item = (Item) GenericController.context.getBean(Item.class);
        item.setLabel(label);
        item.setPrice(price);
        itemMetier.makePersistent(item);
        return item;
    }
    @Override
    public void makePayment() {
        this.orderMetier.makePayment();
    }

    @Override
    public void makePayment(float avanceEspece, float avanceCheque, Date dueDate, Bank bank, String checkName, int nbrCheque) {
        this.orderMetier.makePayment(avanceEspece, avanceCheque, dueDate, bank, checkName, nbrCheque);
    }

    @Override
    public void createBill() {
        this.orderMetier.createBill();
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public List<PaymentType> getPaymentTypes() {
        return paymentMetier.getPaymentTypes();
    }

    @Override
    public List<Bank> getBanks() {
        return bankMetier.findAll();
    }

    public void setPaymentMetier(IPaymentMetier paymentMetier) {
        this.paymentMetier = paymentMetier;
    }

    public void setOrderMetier(IOrderMetier orderMetier) {
        this.orderMetier = orderMetier;
    }

    public void setBankMetier(IBankMetier bankMetier) {
        this.bankMetier = bankMetier;
    }

}
