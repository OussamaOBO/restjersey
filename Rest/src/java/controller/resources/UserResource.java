package controller.resources;

import com.google.gson.Gson;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

    private UserDAO userDAO = new UserDAO();

    @GET
    @Path("/getUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
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
        User user = userDAO.getUser(login);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }      
        return Response.ok(new Gson().toJson(user), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/addUser/{login}/{password}")
    public Response addUser(@PathParam("login") String login, @PathParam("password") String password) {
        if (login == null || password == null || login.equals("") || password.equals("")) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);        
        userDAO.add(user);
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/deleteUser/{login}")
    public Response deleteUser(@PathParam("login") String login) {
        if (login == null || login.equals("")) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        User user = new User();
        user.setLogin(login);
        userDAO.delete(user);
        return Response.status(Response.Status.OK).build();
    }
}
