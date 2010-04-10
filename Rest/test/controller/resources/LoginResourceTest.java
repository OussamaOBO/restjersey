/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller.resources;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.dao.UserDAO;
import model.vo.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joaosavio
 */
public class LoginResourceTest {

    private static final String BASE_URI = "http://localhost:8080/Rest";
    private UserDAO userDAO = new UserDAO();

    public LoginResourceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        userDAO.deleteAll();
    }

    @After
    public void tearDown() {
        userDAO.deleteAll();
    }

    /**
     * Test of login method, of class LoginResource.
     */
    @Test
    public void testLogin() {
        System.out.println("testLogin");

        User user = new User();
        user.setLogin("teste");
        user.setPassword("teste");
        userDAO.add(user);

        Client client = Client.create();
        WebResource wr = client.resource(BASE_URI);

        ClientResponse response = wr.path("/resources/login/" + user.getLogin() + "/" + user.getPassword()).post(ClientResponse.class);
        assertEquals(new Gson().fromJson(response.getEntity(String.class), String.class), "TOKEN");

        userDAO.delete(user);
    }

    @Test(expected = NotFoundException.class)
    public void testBadLogin() {
        System.out.println("testBadLogin");

        Client client = Client.create();
        WebResource wr = client.resource(BASE_URI);
        ClientResponse response = wr.path("/resources/login/" + "notExist/erro").post(ClientResponse.class);
        //assertEquals(response.getStatus(), 404);
    }

}