/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.cash;

import java.util.List;
import metier.GenericMetier;

/**
 *
 * @author BOUGSID Ayoub
 */
public class CashMetier
        extends GenericMetier<Cash, Long>
        implements ICashMetier {

    public void setCashDAO(ICashDAO cashDAO) {
        this.dao = cashDAO;
    }

    @Override
    public void makePersistent(Cash cash) {
        //TODO
        super.makePersistent(cash);
    }

    @Override
    public void makeTransient(Cash cash) {
        //TODO
        super.makeTransient(cash);
    }

    @Override
    public Cash findById(Long id) {
        //TODO
        return super.findById(id);
    }

    @Override
    public List findAll() {
        //TODO
        return super.findAll();
    }

}
