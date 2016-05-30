/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.order;

import app.bank.Bank;
import app.bill.Bill;
import app.bill.IBillMetier;
import app.cash.Cash;
import app.check.Check;
import app.check.ICheckMetier;
import app.customer.Customer;
import app.orderline.IOrderlineMetier;
import app.payment.IPaymentMetier;
import app.payment.Payment;
import app.payment.PaymentState;
import app.user.User;
import metier.GenericMetier;
import metier.IGenericMetier;
import org.springframework.util.SocketUtils;
import util.ApplicationContextProvider;
import util.GenericController;

import java.sql.Date;
import java.util.*;

/**
 * @author BOUGSID Ayoub
 */

public class OrderMetier
        extends GenericMetier<Order, Long>
        implements IOrderMetier {

    private IGenericMetier customerMetier;
    private IGenericMetier userMetier;
    private IOrderlineMetier orderlineMetier;
    private IBillMetier billMetier;
    private IPaymentMetier paymentMetier;
    private ICheckMetier checkMetier;
    private Order order;
    private Bill bill;
    private Cash cash;
    private Check check;
    private User user;

    private List<Order> orders;

    public void setOrderDAO(IOrderDAO orderDAO) {
        this.dao = orderDAO;
    }

    @Override
    public void makePersistent(Order order) {
        //TODO
        this.order = order;

        if (this.user == null) getUsers();

        super.makePersistent(order);
    }

    @Override
    public void newOrder(Customer customer) {
        this.order = (Order) GenericController.context.getBean(Order.class);
        this.order.setCustomer(customer);
        this.order.setUser(this.user);//TODO
        this.order.setShippingDate(new Date(Calendar.getInstance().getTime().getTime()));
        this.order.setCreationDate(this.order.getShippingDate());//TODO

        orderlineMetier.setOrder(this.order);

        //makePersistent(order);
        customer.addOrder(order);
    }

    @Override
    public void getOrdersOfCustomer(Customer customer) {
        System.out.println("dddd" + customer.getOrders().size());
        Set<Order> orderSet = customer.getOrders();
        this.orders = new ArrayList<>();
        for (Order order : orderSet) {
            System.out.println("entity2 = "+order);
            this.orders.add(order);
        }
        //System.out.println("_________"+orders.get(0).getBill().getChecks().size());

    }

    @Override
    public void getOrdersOfCheck(Check check) {
        Payment p = check;
        p.getBills();
        this.orders = new ArrayList<>();
        for (Bill bill : p.getBills()) {
            this.orders.add(bill.getOrder());
        }
    }

    @Override
    public void makeTransient(Order order) {
        //TODO
        order.getCustomer().getOrders().remove(order);
        super.makeTransient(order);
    }

    @Override
    public Order findById(Long id) {
        //TODO
        return super.findById(id);
    }

    @Override
    public List findAll() {
        //TODO
        if (this.orders == null)
            return super.findAll();
        System.out.println("lll " + this.orders.size());

        return this.orders;
    }

    public void setOrderlineMetier(IOrderlineMetier orderlineMetier) {
        this.orderlineMetier = orderlineMetier;
    }

    @Override
    public List<Customer> getCustomers() {
        return customerMetier.findAll();
    }

    @Override
    public List<User> getUsers() {
        List<User> users = userMetier.findAll();
        this.user = users.get(0);
        return users;
    }

    //Cash Payment
    @Override
    public void makePayment() {
        createBill();
        this.cash = (Cash) ApplicationContextProvider.getContext()
                .getBean("cash");
        this.cash.setAvance(Boolean.FALSE);
        this.cash.setAmount(this.order.getTotalAmount());
        this.cash.setCashTendered(this.order.getTotalAmount());//To Change
        this.cash.addBill(this.bill);
        this.cash.setPaymentState(PaymentState.NONPAYE);
        this.paymentMetier.makePersistent(this.cash);

        this.bill.addPayment(this.cash);
        this.billMetier.makePersistent(this.bill);

        this.order.setPaidAmount(new Float(0));
        this.order.setBill(this.bill);
        super.makePersistent(this.order);
    }

    //Check Payment
    @Override
    public void makePayment(float avanceEspece, float avanceCheque, Date dueDate, Bank bank, String checkName, int nbrCheque) {
        createBill();
        this.order.setPaidAmount(new Float(0));

        if (avanceEspece != 0) {
            this.cash = (Cash) ApplicationContextProvider.getContext()
                    .getBean("cash");
            this.cash.setAvance(Boolean.TRUE);
            this.cash.setAmount(avanceEspece);
            this.cash.setCashTendered(this.order.getTotalAmount());//To Change
            this.cash.addBill(this.bill);
            this.cash.setPaymentState(PaymentState.NONPAYE);
            this.bill.addPayment(this.cash);
            paymentMetier.makePersistent(this.cash);
        }
        if (avanceCheque != 0) {
            this.check = (Check) ApplicationContextProvider.getContext()
                    .getBean("check");
            this.check.setAvance(true);
            this.check.setAmount(avanceCheque);
            this.check.setBank(bank);
            this.check.setDueDate(dueDate);
            this.check.setName(checkName);
            this.check.addBill(this.bill);
            //this.check.setState(CheckState.NONPAYE.toString());
            this.check.setPaymentState(PaymentState.NONPAYE);
            this.bill.addPayment(this.check);
            paymentMetier.makePersistent(this.check);
        }
        if (nbrCheque != 0) {
            float deferenceAmount = this.order.getTotalAmount() - avanceCheque - avanceEspece;
            createChecks(nbrCheque, bank, checkName, dueDate, deferenceAmount);
        }
        this.billMetier.makePersistent(this.bill);
        super.makePersistent(this.order);
    }

    @Override
    public void createBill() {
        makePersistent(order);
        //customer.addOrder(order);

        if (this.order.getBill() != null) return;
        this.bill = (Bill) ApplicationContextProvider.getContext()
                .getBean("bill");
        this.bill.setOrder(order);
        this.bill.setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()));
        this.order.setBill(this.bill);
        //
        // billMetier.makePersistent(this.bill);
        //super.makePersistent(this.order);
    }

    public void createChecks(int nbrCheque, Bank bank, String checkName, Date dueDate, float deferenceAmount) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(dueDate);
        //float deferenceAmount = this.order.getTotalAmount() - this.order.getPaidAmount();
        float checkAmount = deferenceAmount / nbrCheque; //TODO Formule
        for (int i = 0; i < nbrCheque; i++) {
            cal.add(Calendar.MONTH, 1);
            Check check = (Check) ApplicationContextProvider.getContext()
                    .getBean("check");
            check.setAvance(false);
            check.setAmount(checkAmount);
            check.setBank(bank);
            check.setDueDate(new Date(cal.getTime().getTime()));
            check.setName(checkName);
            //check.setState(CheckState.NONPAYE.toString());
            check.setPaymentState(PaymentState.NONPAYE);
            check.addBill(this.bill);
            this.bill.addPayment(check);
            paymentMetier.makePersistent(check);
        }
    }

    public void validateCheck(Order order, Check check) {
        checkMetier.validateCheck(check);
        order.setPaidAmount(order.getPaidAmount() + check.getAmount());
        super.makePersistent(order);
        paymentMetier.makePersistent(check);
    }

    public void validateCash(Order order) {
        Bill bill = order.getBill();
        Optional<Payment> optionalPayment = bill.getPayments().stream().filter(p -> p instanceof Cash).findFirst();
        Cash cash = (Cash) optionalPayment.get();
        cash.setPaymentState(PaymentState.PAYE);
        order.setPaidAmount(order.getPaidAmount() + cash.getAmount());
        updateOrder(order);
        paymentMetier.makePersistent(cash);
    }

    public void deleteCheck(Order order, Check check) {
        order.getBill().removePayment(check);
        paymentMetier.makeTransient(check);
    }

    public void addCheck(Order order, Check check) {
        check.addBill(order.getBill());
        order.getBill().addPayment(check);
        updateOrder(order);
    }

    public void updateCheck(Check check) {
        paymentMetier.makePersistent(check);
    }

    @Override
    public void initializeOrders() {
        if (this.orders != null) {
            this.orders.clear();
            this.orders = null;
        }
    }

    public void updateOrder(Order order) {
        super.makePersistent(order);
    }

    @Override
    public List<Bank> getBanks() {
        return orderlineMetier.getBanks();
    }

    public void setBillMetier(IBillMetier billMetier) {
        this.billMetier = billMetier;
    }

    public void setCustomerMetier(IGenericMetier customerMetier) {
        this.customerMetier = customerMetier;
    }

    public void setUserMetier(IGenericMetier userMetier) {
        this.userMetier = userMetier;
    }

    public void setPaymentMetier(IPaymentMetier paymentMetier) {
        this.paymentMetier = paymentMetier;
    }

    public void setCheckMetier(ICheckMetier checkMetier) {
        this.checkMetier = checkMetier;
    }
}
