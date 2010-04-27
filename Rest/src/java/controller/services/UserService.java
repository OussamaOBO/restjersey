package controller.services;

import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import model.dao.UserDAO;
import model.vo.User;


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
        } catch (Exception e) {
            throw new WebApplicationException(e, 500);
        }
    }

    public Response update(User user) {
        validateRequest(user);
        try {
            if (userDAO.exists(user)) {
                userDAO.update(user);
                return Response.status(Response.Status.OK).build();
            }
            else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    public Response delete(User user) {
        validateRequest(user);
        try {
            if (userDAO.exists(user)) {
                userDAO.delete(user);
                return Response.status(Response.Status.OK).build();
            }
            else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    public Response get(String login) {
        validateRequest(login);
        try {            
            User u = userDAO.getUser(login);
            if (u != null) {
                return Response.ok(u).build();
            }
            else {
                return Response.status(Response.Status.CONFLICT).build();
            }
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    public Response listAll() {
        try {
            return Response.ok(userDAO.listAll()).build();
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    public Response list(int begin, int length) {
        try {
            return Response.ok(userDAO.list(begin, length)).build();
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    public Response deleteAll(User t) {
        throw new UnsupportedOperationException("Not supported yet.");
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
        } catch(WebApplicationException we) {
            throw we;
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }        
    }

    public static void validateRequest(User user) {
        if (user.getLogin() == null || user.getLogin().equals("")) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    public static void validateRequest(String login) {
        if (login == null || login.equals("")) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    public Response get(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
