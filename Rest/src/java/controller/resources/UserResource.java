package controller.resources;

import com.google.gson.Gson;
import java.util.List;
import javax.ws.rs.Consumes;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.dao.UserDAO;
import model.vo.User;

/**
 *
 * @author joaosavio
 */
@Path("/user")
public class UserResource {


    @GET
    @Path("/getUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        UserDAO userDAO = new UserDAO();
        List<User> list = userDAO.listAll();
        if (list.size() == 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new Gson().toJson(list), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/getUser/{login}")
    @Produces(MediaType.APPLICATION_JSON)    
    public Response getUser(@PathParam("login") String login) {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUser(login);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }      
        return Response.ok(new Gson().toJson(user), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/addUser")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(String userJSON) {
        User user = new Gson().fromJson(userJSON, User.class);
        if (user.getLogin() == null || user.getPassword() == null || user.getLogin().equals("") || user.getPassword().equals("")) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        UserDAO userDAO = new UserDAO();
        if (userDAO.getUser(user.getLogin()) != null) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        userDAO.add(user);
        return Response.status(Response.Status.CREATED).build();
    }
   
    @PUT
    @Path("/updateUser")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(String userJSON) {
        User user = new Gson().fromJson(userJSON, User.class);
        if (user.getLogin() == null || user.getPassword() == null || user.getLogin().equals("") || user.getPassword().equals("")) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        UserDAO userDAO = new UserDAO();
        if (userDAO.getUser(user.getLogin()) == null) {
            return Response.status(Response.Status.CONFLICT).build();
        }
        System.out.println("Session state -> "+userDAO.getSession().isOpen());
        userDAO.update(user);        
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @DELETE
    @Path("/deleteUser/{login}")
    public Response deleteUser(@PathParam("login") String login) {       
        if (login == null || login.equals("")) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUser(login);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        userDAO.delete(user);
        return Response.status(Response.Status.OK).build();
    }
}
