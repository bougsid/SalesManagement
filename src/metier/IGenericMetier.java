/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author BOUGSID Ayoub
 */
public interface IGenericMetier<T, ID extends Serializable> {

    public void makePersistent(T object);

    public List<T> findAll();

    T findById(ID id);
    //List<T> findByExample(T exampleInstance);

    void makeTransient(T entity);

}
