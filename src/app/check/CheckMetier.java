/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.check;

import app.bill.Bill;
import app.customer.Customer;
import app.order.IOrderMetier;
import app.order.Order;
import app.payment.Payment;
import java.util.List;

import app.payment.PaymentState;
import java.util.ArrayList;
import java.util.Set;
import metier.GenericMetier;

/**
 *
 * @author BOUGSID Ayoub
 */
public class CheckMetier
        extends GenericMetier<Check, Long>
        implements ICheckMetier {
    private List<Check> checks;
    private IOrderMetier orderMetier;

    public void setCheckDAO(ICheckDAO checkDAO) {
        this.dao = checkDAO;
    }
     public void setOrderMetier(IOrderMetier orderMetier) {
        this.orderMetier = orderMetier;
    }

    @Override
    public void makePersistent(Check check) {
        //TODO
        super.makePersistent(check);
    }

    @Override
    public void makeTransient(Check check) {
        //TODO
        super.makeTransient(check);
    }

    @Override
    public Check findById(Long id) {
        //TODO
        return super.findById(id);
    }

    @Override
    public List findAll() {
        //TODO
        if (checks == null)
            return super.findAll();
        return checks;
    }
    public void validateCheck(Check check){
        check.setPaymentState(PaymentState.PAYE);
    }
    
    @Override
    public void getChecksOfCustomer(Customer customer) {
        Set<Order> orderSet = customer.getOrders();
        checks =new ArrayList();
        for(Order order : orderSet){
            checks.addAll(order.getBill().getChecks());
        }   
    }
      @Override
    public void showOrders(Check check) {
       orderMetier.getOrdersOfCheck(check);
    }
}
