package dao;

/**
 *
 * @author AYOUB
 */
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;


public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        HibernateUtil.sessionFactory = sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {
            return sessionFactory.getCurrentSession();
    }

}
