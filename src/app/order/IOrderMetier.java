/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.order;

import app.bank.Bank;
import app.bill.Bill;
import app.check.Check;
import app.customer.Customer;
import app.payment.Payment;
import app.user.User;
import java.sql.Date;
import java.util.List;
import metier.IGenericMetier;

/**
 *
 * @author BOUGSID Ayoub
 */
public interface IOrderMetier extends IGenericMetier<Order, Long> {

    public void makePayment();

    public void makePayment(float avanceEspece, float avanceCheque, Date dueDate, Bank bank, String checkName, int nbrCheque);

    public List<User> getUsers();

    public List<Customer> getCustomers();
    void getOrdersOfCustomer(Customer customer);
    public void newOrder(Customer customer);
    public void createBill();
    public void validateCheck(Order order,Check check);
    public void deleteCheck(Order order,Check check);
    public void addCheck(Order order,Check check);
    void updateOrder(Order order);
    void validateCash(Order order);
    List<Bank> getBanks();
    void initializeOrders();
    void updateCheck(Check check);
    void getOrdersOfCheck(Check check);
}
