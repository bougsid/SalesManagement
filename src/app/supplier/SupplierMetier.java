/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.supplier;

import java.util.List;
import metier.GenericMetier;

/**
 *
 * @author BOUGSID Ayoub
 */
public class SupplierMetier
        extends GenericMetier<Supplier, Long>
        implements ISupplierMetier {

    public void setSupplierDAO(ISupplierDAO supplierDAO) {
        this.dao = supplierDAO;
    }

    @Override
    public void makePersistent(Supplier supplier) {
        //TODO
        super.makePersistent(supplier);
    }

    @Override
    public void makeTransient(Supplier supplier) {
        //TODO
        super.makeTransient(supplier);
    }

    @Override
    public Supplier findById(Long id) {
        //TODO
        return super.findById(id);
    }

    @Override
    public List findAll() {
        //TODO
        return super.findAll();
    }

}
