package controller.resources;

import com.google.gson.Gson;
import controller.services.IService;
import controller.services.UserService;
import java.util.List;
import javax.ws.rs.Consumes;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
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
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        IService service = new UserService();
        List<User> list = (List<User>) service.listAll().getEntity();
        return Response.ok(new Gson().toJson(list), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{login}")
    @Produces(MediaType.APPLICATION_JSON)    
    public Response getUser(@PathParam("login") String login) {
        UserService userService = new UserService();
        Response response = userService.get(login);
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return Response.ok(new Gson().toJson(response.getEntity()), MediaType.APPLICATION_JSON).build();
        }
        else {
            return response;
        }
    }

    @POST    
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(String userJSON) {
        System.out.println("JSONNNN:" +userJSON);
        User user = new Gson().fromJson(userJSON, User.class);
        if (user == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }  
        if (user.getId() == null) {
            user.setId(0L);
        }
        UserService service = new UserService();
        return service.add(user);
    }
   
    @PUT    
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(String userJSON) {
        System.out.println("JSONNNN:" +userJSON);
        User user = new Gson().fromJson(userJSON, User.class);
        if (user == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        if (user.getId() == null) {
            user.setId(0L);
        }
        UserService service = new UserService();
        return service.update(user);
    }

    @DELETE
    @Path("/{login}")
    public Response deleteUser(@PathParam("login") String login) {       
        User user = new User();
        user.setLogin("teste");
        UserService service = new UserService();
        return service.delete(user);
    }
}
