package model.dao;

import model.vo.User;
import org.hibernate.Query;

/**
 *
 * @author João Sávio Ceregatti Longo - joaosavio@gmail.com
 */
public class UserDAO extends GenericDAO<User> {

    public UserDAO() {
        super(User.class);
    }

    @Override
    public boolean exists(User user) throws Exception {
        return getByUnique(user.getLogin()) != null;
    }

    public User getUser(String login, String password) throws Exception {
        try {
            String hql = "select u from User as u where u.login = :login and u.password = :password";
            Query q = getSession().createQuery(hql);
            q.setParameter("login", login);
            q.setParameter("password", password);
            return (User) q.uniqueResult();  
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }

    @Override
    public User getByUnique(Object o) throws Exception {        
        try {
            String login = (String) o;
            String hql = "select u from User as u where u.login = :login";
            Query q = getSession().createQuery(hql);
            q.setParameter("login", login);
            return (User) q.uniqueResult();
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }
}
