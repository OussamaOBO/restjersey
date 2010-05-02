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
        return getByUnique(user) != null;
    }
    
    /*
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
     */

    @Override
    public User getByUnique(User user) throws Exception {
        try {            
            String hql = "select u from User as u where u.login = :login";
            Query q = getSession().createQuery(hql);
            q.setParameter("login", user.getLogin());
            return (User) q.uniqueResult();
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }
}

/*
 * <select> -> SELECT [DISTINCT] <selectExpression>
 * <selectExpression> -> * | <expression>
 * <expression> -> <andCondition> [ OR <andCondition> ]
 * <andCondition> -> AND <condition>
 * <condition> -> <operand> | NOT <condition> | EXISTS ( select )
 *
 */