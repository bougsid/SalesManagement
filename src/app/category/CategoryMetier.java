/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.category;

import java.util.List;
import metier.GenericMetier;

/**
 *
 * @author BOUGSID Ayoub
 */
public class CategoryMetier
        extends GenericMetier<Category, Long>
        implements ICategoryMetier {

    public void setCategoryDAO(ICategoryDAO categoryDAO) {
        this.dao = categoryDAO;
    }

    @Override
    public void makePersistent(Category category) {
        //TODO
        super.makePersistent(category);
    }

    @Override
    public void makeTransient(Category category) {
        //TODO
        super.makeTransient(category);
    }

    @Override
    public Category findById(Long id) {
        //TODO
        return super.findById(id);
    }

    @Override
    public List findAll() {
        //TODO
        return super.findAll();
    }

}
