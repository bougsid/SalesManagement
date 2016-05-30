package dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Repository
@Service
public abstract class GenericDAO<T, ID extends Serializable>
        implements IGenericDAO<T, ID> {

    private Class<T> persistentClass;
    private Session session;

    public GenericDAO() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected Session getSession() {
//        if (session == null || !session.isOpen()) {
//            session = HibernateUtil.getSessionFactory().openSession();
//        }
        return HibernateUtil.getSession();
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findById(ID id) {
        T entity;
        getSession().beginTransaction();

        entity = (T) getSession().load(getPersistentClass(), id);

        getSession().getTransaction().commit();
        return entity;
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return findByCriteria();
    }

    @SuppressWarnings("unchecked")
    public List<T> findByExample(T exampleInstance, String[] excludeProperty) {
        Criteria crit = getSession().createCriteria(getPersistentClass());
        Example example = Example.create(exampleInstance);
        for (String exclude : excludeProperty) {
            example.excludeProperty(exclude);
        }
        crit.add(example);
        return crit.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T makePersistent(T entity) {
        HibernateUtil.getSession().saveOrUpdate(entity);
        //commit();
        return entity;
    }

    @Override
    public void makeTransient(T entity) {
        HibernateUtil.getSession().delete(entity);
        //commit();
    }

    protected Session getCurrentSession() {
        if(session == null)
        return HibernateUtil.getSessionFactory().openSession();
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public void flush() {
        getSession().flush();
    }

    public void clear() {
        getSession().clear();
    }

    public void commit() {
        getSession().beginTransaction().commit();
    }

    /**
     * Use this inside subclasses as a convenience method.
     */
    @SuppressWarnings("unchecked")
    //@Transactional
    protected List<T> findByCriteria(Criterion... criterion) {
        //getSession().beginTransaction();
        Criteria crit = getSession().createCriteria(getPersistentClass());
        for (Criterion c : criterion) {
            crit.add(c);
        }
        return crit.list();
    }

}
