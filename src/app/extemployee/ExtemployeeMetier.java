/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.extemployee;

import java.util.List;
import metier.GenericMetier;

/**
 *
 * @author BOUGSID Ayoub
 */
public class ExtemployeeMetier
        extends GenericMetier<Extemployee, Long>
        implements IExtemployeeMetier {

    public void setExtemployeeDAO(IExtemployeeDAO extemployeeDAO) {
        this.dao = extemployeeDAO;
    }

    @Override
    public void makePersistent(Extemployee extemployee) {
        //TODO
        super.makePersistent(extemployee);
    }

    @Override
    public void makeTransient(Extemployee extemployee) {
        //TODO
        super.makeTransient(extemployee);
    }

    @Override
    public Extemployee findById(Long id) {
        //TODO
        return super.findById(id);
    }

    @Override
    public List findAll() {
        //TODO
        return super.findAll();
    }

}
