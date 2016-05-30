/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.bill;

import app.payment.IPaymentMetier;
import java.util.List;
import metier.GenericMetier;

/**
 *
 * @author BOUGSID Ayoub
 */
public class BillMetier
        extends GenericMetier<Bill, Long>
        implements IBillMetier {
    public void setBillDAO(IBillDAO billDAO) {
        this.dao = billDAO;
    }

    @Override
    public void makePersistent(Bill bill) {
        //TODO
        super.makePersistent(bill);
    }

    @Override
    public void makeTransient(Bill bill) {
        //TODO
        super.makeTransient(bill);
    }

    @Override
    public Bill findById(Long id) {
        //TODO
        return super.findById(id);
    }

    @Override
    public List findAll() {
        //TODO
        return super.findAll();
    }



}
