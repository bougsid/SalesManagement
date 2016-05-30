/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.payment;

import java.util.List;
import metier.IGenericMetier;

/**
 *
 * @author BOUGSID Ayoub
 */
public interface IPaymentMetier extends IGenericMetier<Payment,Long>{
    public List<PaymentType> getPaymentTypes();
}
