/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.orderline;

import app.bank.Bank;
import app.item.Item;
import app.order.Order;
import app.payment.PaymentType;
import java.sql.Date;
import java.util.List;
import metier.IGenericMetier;

/**
 *
 * @author BOUGSID Ayoub
 */
public interface IOrderlineMetier extends IGenericMetier<Orderline, Long> {

    public void setOrder(Order order);
    public Order getOrder();

    public List getItems();

    public List<PaymentType> getPaymentTypes();

    public List<Bank> getBanks();

    public void makePayment();

    public void makePayment(float avanceEspece, float avanceCheque, Date dueDate, Bank bank, String checkName, int nbrCheque);

    public void createBill();

    public void update(Orderline orderline);

    Item addNewItem(String label, Float price);
}
