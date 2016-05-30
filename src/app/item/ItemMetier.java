/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.item;

import java.util.List;

import app.category.Category;
import app.category.ICategoryMetier;
import app.supplier.ISupplierMetier;
import app.supplier.Supplier;
import metier.GenericMetier;

/**
 *
 * @author BOUGSID Ayoub
 */
public class ItemMetier
        extends GenericMetier<Item, Long>
        implements IItemMetier {
    private ICategoryMetier categoryMetier;
    private ISupplierMetier supplierMetier;
    public void setItemDAO(IItemDAO itemDAO) {
        this.dao = itemDAO;
    }

    @Override
    public void makePersistent(Item item) {
        //TODO
        super.makePersistent(item);
    }

    @Override
    public void makeTransient(Item item) {
        //TODO
        super.makeTransient(item);
    }

    @Override
    public Item findById(Long id) {
        //TODO
        return super.findById(id);
    }
    public Item findByCode(String barCode) {
        return ((IItemDAO)this.dao).findByCode(barCode);
    }

    @Override
    public List findAll() {
        //TODO
        return super.findAll();
    }

    @Override
    public List<Category> getCategories() {
        return categoryMetier.findAll();
    }

    @Override
    public List<Supplier> getSuppliers() {
        return supplierMetier.findAll();
    }

    public void setCategoryMetier(ICategoryMetier categoryMetier) {
        this.categoryMetier = categoryMetier;
    }

    public void setSupplierMetier(ISupplierMetier supplierMetier) {
        this.supplierMetier = supplierMetier;
    }
}
