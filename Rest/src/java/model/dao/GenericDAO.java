package model.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;


/**
 *
 * @author João Sávio C. Longo - joaosavio@gmail.com
 */
public abstract class GenericDAO<T> extends DAOFactory {

    private final Class classe;

    public GenericDAO(Class classe) {
        this.classe = classe;
    }

    public abstract boolean exists(T t) throws Exception;
    public abstract T getByUnique(T t) throws Exception;
   

    public void add(T t) throws Exception {
        try {
            getSession().save(t);
            beginTransaction();
            commit();
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }        
    }

    public void delete(T t) throws Exception {
        try {
            getSession().delete(t);
            beginTransaction();
            commit();
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }        
    }

    public void update(T t) throws Exception {
        try {
            getSession().update(t);
            beginTransaction();
            commit();
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }       
    }

    public T getById(Long id) throws Exception {
        try {
            return (T) getSession().get(classe, id);
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }

    public List<T> findByCriteria(CriterionConfiguration criterionConfiguration) throws Exception {
        return findByCriteria(criterionConfiguration.getListCriterion());
    }

    public List<T> findByCriteria(List<Criterion> list) throws Exception {
        try {
            Criteria criteria = getSession().createCriteria(classe);
            for (int i = 0; i < list.size(); i++) {
                criteria.add(list.get(i));
            }
            return criteria.list();
        } catch (Exception e) {
            throw e;
        }
        finally {
            close();
        }
    }

    public List<T> listAll() throws Exception {
        try {
            return getSession().createCriteria(classe).list();
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }

    public List<T> list(int begin, int length) throws Exception {
        try {
            return getSession().createCriteria(classe).setMaxResults(length).setFirstResult(begin).list();
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }

    public void add(List<T> list) throws Exception {
        try {
            for (int i = 0; i < list.size(); i++) {
                getSession().save(list.get(i));
                beginTransaction();
                commit();
            }
        } catch (Exception e) {
            throw e;
        }
        finally {
            close();
        }

    }

    public void deleteAll() throws Exception {
        try {
            String hql = "delete from " + classe.getSimpleName();
            Query q = getSession().createQuery(hql);
            q.executeUpdate();            
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            close();
        }
    }

    @Override
    public void close() {
        try {
            super.close();
        } catch (HibernateException he) {
            throw he;
        }
    }

    @Override
    public void rollback() {
        if (hasTransaction()) {
            try {
                super.rollback();
            } catch (HibernateException he) {
                throw he;
            }
        }
    }
}
