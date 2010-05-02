package controller.resources;

import com.google.gson.Gson;
import controller.services.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.dao.UserDAO;
import model.vo.User;

/**
 *
 * @author joaosavio
 */
@Path("/login")
public class LoginResource {
    @Context HttpServletRequest r;

    @POST
    @Path("/{login}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@PathParam("login") String login, @PathParam("password") String password) throws Exception {
        UserService userService = new UserService();
        Response response = userService.get(login, password);
        if (response.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            return response;
        }

        /*
        HttpSession hreq = r.getSession(true);
        hreq.setAttribute("user", user);

        System.out.println("aeae= "+hreq.getAttribute("user"));
        */
        String token = "TOKEN";
        return Response.ok(new Gson().toJson(token), MediaType.APPLICATION_JSON).build();
    }

}
