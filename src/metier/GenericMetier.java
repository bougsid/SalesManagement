/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import dao.IGenericDAO;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author BOUGSID Ayoub
 */
@Transactional
public abstract class GenericMetier<T,ID extends Serializable> implements IGenericMetier<T,ID>{  
    
    protected IGenericDAO<T,ID> dao;

    @Override
    public void makePersistent(T object) {
        this.dao.makePersistent(object);
    }

    @Override
    public List findAll() {
        return dao.findAll();
    }

    @Override
    public void makeTransient(T entity) {
        dao.makeTransient(entity);
    }

    @Override
    public T findById(ID id) {
        return dao.findById(id);
    }
    
    
}
