/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import app.check.ICheckMetier;
import app.city.City;
import app.order.IOrderMetier;
import app.region.IRegionMetier;
import app.region.Region;
import metier.GenericMetier;

/**
 * @author BOUGSID Ayoub
 */
public class CustomerMetier
        extends GenericMetier<Customer, Long>
        implements ICustomerMetier {

    private List<Region> regions;
    //private List<State> regions;
    private Set<City> cities;
    private IRegionMetier regionMetier;
    private IOrderMetier orderMetier;
    private ICheckMetier checkMetier;
    public void setCustomerDAO(ICustomerDAO customerDAO) {
        this.dao = customerDAO;
    }

    @Override
    public void makePersistent(Customer customer) {
        //TODO
        super.makePersistent(customer);
    }

    @Override
    public void makeTransient(Customer customer) {
        //TODO
        super.makeTransient(customer);
    }

    @Override
    public Customer findById(Long id) {
        //TODO
        return super.findById(id);
    }

    @Override
    public List findAll() {
        //TODO
        return super.findAll();
    }

    public Customer findByCode(String code) {
        return ((ICustomerDAO)this.dao).findByCode(code);
    }
    @Override
    public void newOrder(Customer customer){
        orderMetier.newOrder(customer);
    }

    @Override
    public void showOrders(Customer customer) {
        orderMetier.getOrdersOfCustomer(customer);
    }

    @Override
    public List<City> getCities(Region region, City city) {
        ArrayList<City> cities;
        if (region == null) {
            if (city == null) {
                region = regions.get(0);
            } else {
                region = city.getRegion();
            }
        }
        cities = new ArrayList<>(region.getCities());
        if (city == null) {
            city = cities.get(0);
        }
        return cities;
    }

    @Override
    public List<Region> getRegions() {
        if (regions == null)
            regions = regionMetier.findAll();
        return regions;
    }
    @Override
    public void showChecks(Customer customer) {
        checkMetier.getChecksOfCustomer(customer);
    }

    public void setRegionMetier(IRegionMetier regionMetier) {
        this.regionMetier = regionMetier;
    }

    public void setOrderMetier(IOrderMetier orderMetier) {
        this.orderMetier = orderMetier;
    }

    public void setCheckMetier(ICheckMetier checkMetier) {
        this.checkMetier = checkMetier;
    }
}
