/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.check;

import app.customer.Customer;
import metier.IGenericMetier;

/**
 *
 * @author BOUGSID Ayoub
 */
public interface ICheckMetier extends IGenericMetier<Check,Long>{
    public void validateCheck(Check check);
    public void getChecksOfCustomer(Customer customer);
    public void showOrders(Check check);
}
