package app.customer;
import dao.IGenericDAO;
/**
 *
 * @author BOUGSID Ayoub
 */
public interface ICustomerDAO extends IGenericDAO<Customer, Long>{
    Customer findByCode(String code);
}
