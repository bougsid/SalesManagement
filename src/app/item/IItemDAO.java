package app.item;
import dao.IGenericDAO;
/**
 *
 * @author BOUGSID Ayoub
 */
public interface IItemDAO extends IGenericDAO<Item, Long>{
    Item findByCode(String barCode);
}
