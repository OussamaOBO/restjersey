package model.dao;

import com.sun.jersey.api.NotFoundException;
import javax.ws.rs.WebApplicationException;
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

    public User getUser(String login, String password) {
        try {
            String hql = "select u from User as u where u.login = :login and u.password = :password";
            Query q = getSession().createQuery(hql);
            q.setParameter("login", login);
            q.setParameter("password", password);
            User user = new User();
            user = (User) q.uniqueResult();            
            return user;
        } catch (Exception e) {
            throw new WebApplicationException(e, 500);
        } finally {
            close();
        }
    }

    public User getUser(String login) {
        return getById(login);
    }    
}
