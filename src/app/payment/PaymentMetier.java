/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.payment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import metier.GenericMetier;

/**
 *
 * @author BOUGSID Ayoub
 */
public class PaymentMetier
        extends GenericMetier<Payment, Long>
        implements IPaymentMetier {

    public void setPaymentDAO(IPaymentDAO paymentDAO) {
        this.dao = paymentDAO;
    }

    @Override
    public void makePersistent(Payment payment) {
        //TODO
        super.makePersistent(payment);
    }

    @Override
    public void makeTransient(Payment payment) {
        //TODO
        super.makeTransient(payment);
    }

    @Override
    public Payment findById(Long id) {
        //TODO
        return super.findById(id);
    }

    @Override
    public List findAll() {
        //TODO
        return super.findAll();
    }

    public List<PaymentType> getPaymentTypes() {
        List<PaymentType> paymentsTypes = new ArrayList<>();
        paymentsTypes = Arrays.asList(PaymentType.values());
        return paymentsTypes;
    }
}
