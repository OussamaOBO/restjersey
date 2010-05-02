package controller.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import model.dao.GenericDAO;
import model.dao.UserDAO;
import model.vo.User;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;


/**
 *
 * @author João Sávio C. Longo - joaosavio@gmail.com
 */
public class UserService implements IService<User> {
    
    private UserDAO userDAO = new UserDAO();

    public Response add(User user) {
        validateRequest(user);
        try {
            if (!userDAO.exists(user)) {
                userDAO.add(user);
                return Response.status(Response.Status.CREATED).build();
            }
            else {
                return Response.status(Response.Status.CONFLICT).build();
            }
        } catch (WebApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new WebApplicationException(e, 500);
        }
    }

    public Response update(User user) {
        validateRequest(user);
        try {
            user = putId(user);
            userDAO.update(user);
            return Response.status(Response.Status.OK).build();            
        } catch (WebApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    public Response delete(User user) {
        validateRequest(user);
        try {
            user = putId(user);
            userDAO.delete(user);
            return Response.status(Response.Status.OK).build();           
        } catch (WebApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    public Response get(String login) {
        validateRequest(login);
        try {            
            User u = userDAO.getByUnique(login);
            if (u != null) {
                return Response.ok(u).build();
            }
            else {
                return Response.status(Response.Status.CONFLICT).build();
            }
        } catch (WebApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }*/
    
    public Response listAll() {
        try {
            return Response.ok(userDAO.listAll()).build();
        } catch (WebApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    public Response list(int begin, int length) {
        try {
            return Response.ok(userDAO.list(begin, length)).build();
        } catch (WebApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

     public Response add(List<User> list) {
         try {
            for (int i = 0; i < list.size(); i++) {
                validateRequest(list.get(i));
                if (userDAO.exists(list.get(i))) {
                    return Response.status(Response.Status.CONFLICT).build();
                }
            }
            userDAO.add(list);
            return Response.status(Response.Status.CREATED).build();
        } catch(WebApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }        
    }

    public Response get(long id) {
        validateRequest(id);
        try {
            return Response.ok(userDAO.getById(id)).build();
        } catch (WebApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    private User putId(User user) throws Exception {
        if (user.getId() == null || user.getId() == 0) {
            User u = userDAO.getByUnique(user);
            if (u == null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            return u;
        }
        return user;
    }

    public Response get(String login) {
        validateRequest(login);
        try {
            List<Criterion> list = new ArrayList<Criterion>();
            Criterion c = Restrictions.eq("login", login);
            list.add(c);
            User user = userDAO.findByCriteria(list).get(0);
            if (user != null) {
                return Response.ok(user).build();
            }
            else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (WebApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    public Response get(String login, String password) {
        validateRequest(login, password);

        List<Criterion> list = new ArrayList<Criterion>();
        Map map = new HashMap();
        map.put("login", login);
        map.put("password", password);
        Criterion c = Restrictions.allEq(map);
        list.add(c);
        
        try {
            List<User> result = userDAO.findByCriteria(list);
            if (result.size() == 0) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(list.get(0)).build();
        } catch (WebApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    public void validateRequest(User user) {
        if (user == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        if (user.getId() == null || user.getId() == 0) {
            if (user.getLogin() == null || user.getLogin().equals("")) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
        }
    }

    public static void validateRequest(Object... object) {
        for (int i = 0; i < object.length; i++) {                     
            if (object[i] == null) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
            else if (object[i].getClass().toString().equals(String.class.toString())) {
                String aux = (String)object[i];
                if (aux == null || aux.equals("")) {
                    throw new WebApplicationException(Response.Status.BAD_REQUEST);
                }
            }
            else if (object[i].getClass().toString().equals(Integer.class.toString())) {
                if ((Integer)object[i] == 0) {
                    throw new WebApplicationException(Response.Status.BAD_REQUEST);
                }
            }
            else if (object[i].getClass().toString().equals(Long.class.toString())) {
                if ((Long)object[i] == 0L) {
                    throw new WebApplicationException(Response.Status.BAD_REQUEST);
                }
            }
            else if (object[i].getClass().toString().equals(Double.class.toString())) {
                if ((Double)object[i] == 0.0) {
                    throw new WebApplicationException(Response.Status.BAD_REQUEST);
                }
            }           
            else {                
                throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
