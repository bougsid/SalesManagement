/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.bank;

import java.util.List;
import metier.GenericMetier;

/**
 *
 * @author BOUGSID Ayoub
 */
public class BankMetier
        extends GenericMetier<Bank, Long>
        implements IBankMetier {

    public void setBankDAO(IBankDAO bankDAO) {
        this.dao = bankDAO;
    }

    @Override
    public void makePersistent(Bank bank) {
        //TODO
        super.makePersistent(bank);
    }

    @Override
    public void makeTransient(Bank bank) {
        //TODO
        super.makeTransient(bank);
    }

    @Override
    public Bank findById(Long id) {
        //TODO
        return super.findById(id);
    }

    @Override
    public List findAll() {
        //TODO
        return super.findAll();
    }

}
