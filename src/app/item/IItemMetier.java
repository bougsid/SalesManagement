/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.item;

import app.category.Category;
import app.supplier.Supplier;
import metier.IGenericMetier;

import java.util.List;

/**
 *
 * @author BOUGSID Ayoub
 */
public interface IItemMetier extends IGenericMetier<Item,Long>{
    Item findByCode(String barCode);
    List<Category> getCategories();
    List<Supplier> getSuppliers();
}
