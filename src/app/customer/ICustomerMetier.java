/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.customer;

import app.city.City;
import app.region.Region;
import metier.IGenericMetier;

import java.util.List;
import java.util.Set;

/**
 *
 * @author BOUGSID Ayoub
 */
public interface ICustomerMetier extends IGenericMetier<Customer,Long>{
    List<City> getCities(Region region, City city);
    List<Region> getRegions();
    Customer findByCode(String code);
    void newOrder(Customer customer);
    void showOrders(Customer customer);
    void showChecks(Customer customer);
}
