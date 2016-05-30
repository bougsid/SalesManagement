/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.city;

import java.util.List;
import metier.GenericMetier;

/**
 *
 * @author BOUGSID Ayoub
 */
public class CityMetier
        extends GenericMetier<City, Long>
        implements ICityMetier {

    public void setCityDAO(ICityDAO cityDAO) {
        this.dao = cityDAO;
    }

    @Override
    public void makePersistent(City city) {
        //TODO
        super.makePersistent(city);
    }

    @Override
    public void makeTransient(City city) {
        //TODO
        super.makeTransient(city);
    }

    @Override
    public City findById(Long id) {
        //TODO
        return super.findById(id);
    }

    @Override
    public List findAll() {
        //TODO
        return super.findAll();
    }

}
