/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.user;

import java.util.List;
import metier.GenericMetier;

/**
 *
 * @author BOUGSID Ayoub
 */
public class UserMetier
        extends GenericMetier<User, Long>
        implements IUserMetier {

    public void setUserDAO(IUserDAO userDAO) {
        this.dao = userDAO;
    }

    @Override
    public void makePersistent(User user) {
        //TODO
        super.makePersistent(user);
    }

    @Override
    public void makeTransient(User user) {
        //TODO
        super.makeTransient(user);
    }

    @Override
    public User findById(Long id) {
        //TODO
        return super.findById(id);
    }

    @Override
    public List findAll() {
        //TODO
        return super.findAll();
    }

}
