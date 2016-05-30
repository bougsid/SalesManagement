package app.item;

import dao.GenericDAO;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author BOUGSID Ayoub
 */
public class ItemDAO
        extends GenericDAO<Item, Long>
        implements IItemDAO {
    @Override
    public Item findByCode(String barCode) {
        List<Item> items = findByCriteria(Restrictions.eq("barcode", barCode));
        if (items.size() == 1)
            return items.get(0);
        return null;
    }
}
