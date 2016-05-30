/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import metier.IGenericMetier;

/**
 *
 * @author BOUGSID Ayoub
 */
public interface IGenericController<T> {
    public Object add();
    public void delete();
    public void update();
    public void reset();
    public void setMetier(IGenericMetier metier);
}
