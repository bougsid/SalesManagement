/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.intemployee;

import java.util.List;
import metier.GenericMetier;

/**
 *
 * @author BOUGSID Ayoub
 */
public class IntemployeeMetier
        extends GenericMetier<Intemployee, Long>
        implements IIntemployeeMetier {

    public void setIntemployeeDAO(IIntemployeeDAO intemployeeDAO) {
        this.dao = intemployeeDAO;
    }

    @Override
    public void makePersistent(Intemployee intemployee) {
        //TODO
        super.makePersistent(intemployee);
    }

    @Override
    public void makeTransient(Intemployee intemployee) {
        //TODO
        super.makeTransient(intemployee);
    }

    @Override
    public Intemployee findById(Long id) {
        //TODO
        return super.findById(id);
    }

    @Override
    public List findAll() {
        //TODO
        return super.findAll();
    }

}
