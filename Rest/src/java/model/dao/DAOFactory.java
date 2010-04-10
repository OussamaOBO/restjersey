package model.dao;

import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author João Sávio C. Longo - joaosavio@gmail.com
 */
public class DAOFactory implements Serializable {

    private Session session;
    private Transaction transaction;

    public DAOFactory() {
        session = HibernateUtil.getSession();
    }

    public void close() {
        if (session.isOpen()) {
            session.close();
        }
    }

    public Session getSession() {
        if (!session.isOpen()) {
            session = HibernateUtil.getSession();
        }
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void beginTransaction() {
        transaction = session.beginTransaction();
    }

    public void commit() {
        transaction.commit();
        transaction = null;
    }

    public boolean hasTransaction() {
        return transaction != null;
    }

    public void rollback() {
        transaction.rollback();
        transaction = null;
    }

    public void clear() {
        session.clear();
    }
}