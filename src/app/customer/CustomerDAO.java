package app.customer;

import dao.GenericDAO;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.List;

/**
 * @author BOUGSID Ayoub
 */
public class CustomerDAO
        extends GenericDAO<Customer, Long>
        implements ICustomerDAO {

    @Override
    public Customer findByCode(String code) {
        List<Customer> customers = findByCriteria(Restrictions.eq("code", code));
        if (customers.size() == 1)
            return customers.get(0);
        return null;
    }
}
