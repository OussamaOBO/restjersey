package model.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.HibernateException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author João Sávio C. Longo - joaosavio@gmail.com
 */
public abstract class GenericDAO<T> extends DAOFactory {

    private final Class classe;

    public GenericDAO(Class classe) {
        this.classe = classe;
    }

    public void add(T t) {
        try {
            getSession().save(t);
            beginTransaction();
            commit();
        } catch (Exception e) {
            rollback();
            throw new WebApplicationException(e, 500);
        } finally {
            close();
        }        
    }

    public void delete(T t) {
        try {
            getSession().delete(t);
            beginTransaction();
            commit();
        } catch (Exception e) {
            rollback();
            throw new WebApplicationException(e, 500);
        } finally {
            close();
        }        
    }

    public void update(T t) {
        try {
            getSession().update(t);
            beginTransaction();
            commit();
        } catch (Exception e) {
            rollback();
            throw new WebApplicationException(e, 500);
        } finally {
            close();
        }       
    }

    public List<T> listAll() {
        try {
            return getSession().createCriteria(classe).list();
        } catch (Exception e) {
            throw new WebApplicationException(e, 500);
        } finally {
            close();
        }
    }

    public List<T> list(int begin, int length) {
        try {
            return getSession().createCriteria(classe).setMaxResults(length).setFirstResult(begin).list();
        } catch (Exception e) {
            throw new WebApplicationException(e, 500);
        } finally {
            close();
        }
    }

    public Response deleteAll() {
        try {
            String hql = "delete from " + classe.getSimpleName();
            Query q = getSession().createQuery(hql);
            q.executeUpdate();
            return Response.ok().build();
        } catch (Exception e) {
            rollback();
            throw new WebApplicationException(e, 500);
        } finally {
            close();
        }
    }

    public T getById(String id) {        
        try {
            return (T) getSession().get(classe, id);
        } catch (Exception e) {
            throw new WebApplicationException(e, 500);
        } finally {
            close();
        }
    }

    public T getById(Long id) {        
        try {
            return (T) getSession().get(classe, id);
        } catch (Exception e) {
            throw new WebApplicationException(e, 500);
        } finally {
            close();
        }
    }

    @Override
    public void close() {
        try {
            super.close();
        } catch (HibernateException he) {
            throw new WebApplicationException(he, 500);
        }
    }

    @Override
    public void rollback() {
        if (hasTransaction()) {
            try {
                super.rollback();
            } catch (HibernateException he) {
                throw new WebApplicationException(he, 500);
            }
        }
    }
}
