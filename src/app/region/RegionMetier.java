/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.region;

import java.util.List;
import metier.GenericMetier;

/**
 *
 * @author BOUGSID Ayoub
 */
public class RegionMetier
        extends GenericMetier<Region, Long>
        implements IRegionMetier {

    public void setRegionDAO(IRegionDAO regionDAO) {
        this.dao = regionDAO;
    }

    @Override
    public void makePersistent(Region region) {
        //TODO
        super.makePersistent(region);
    }

    @Override
    public void makeTransient(Region region) {
        //TODO
        super.makeTransient(region);
    }

    @Override
    public Region findById(Long id) {
        //TODO
        return super.findById(id);
    }

    @Override
    public List findAll() {
        //TODO
        return super.findAll();
    }

}
