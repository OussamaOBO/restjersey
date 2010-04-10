/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller.resources;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MediaType;
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
public class UserResourceTest {

    private static final String BASE_URI = "http://localhost:8080/Rest/";
    private UserDAO userDAO = new UserDAO();


    public UserResourceTest() {
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
     * Test of getUser method, of class UserResource.
     */
    @Test
    public void testGetUser() {
        System.out.println("testGetUser");

        User user = new User();
        user.setLogin("teste");
        user.setPassword("teste");
        userDAO.add(user);

        Client client = Client.create();
        WebResource wr = client.resource(BASE_URI);
        String result = wr.path("resources/user/getUser/" + user.getLogin()).type(MediaType.APPLICATION_JSON).get(String.class);

        assertEquals(new Gson().fromJson(result, User.class), user);

        userDAO.delete(user);
    }

    @Test
    public void testGetUsers() {
        System.out.println("testGetUsers");

        User user1 = new User();
        user1.setLogin("teste");
        user1.setPassword("teste");
        userDAO.add(user1);

        User user2 = new User();
        user2.setLogin("teste1");
        user2.setPassword("teste1");
        userDAO.add(user2);

        User user3 = new User();
        user3.setLogin("teste2");
        user3.setPassword("teste2");
        userDAO.add(user3);

        List<User> list = new ArrayList<User>();
        list.add(user1);
        list.add(user2);
        list.add(user3);

        Client client = Client.create();
        WebResource wr = client.resource(BASE_URI);
        String result = wr.path("resources/user/getUsers").type(MediaType.APPLICATION_JSON).get(String.class);

        assertEquals(new Gson().fromJson(result, List.class), list);

        userDAO.delete(user1);
        userDAO.delete(user2);
        userDAO.delete(user3);
    }

    /**
     * Test of addUser method, of class UserResource.
     */
    @Test
    public void testAddUser() {
        System.out.println("testAddUser");        

        User user = new User();
        user.setLogin("teste");
        user.setPassword("teste");

        Client client = Client.create();
        WebResource wr = client.resource(BASE_URI);
        wr.path("resources/user/addUser/" + user.getLogin() + "/" + user.getPassword()).post();

        User result = userDAO.getUser(user.getLogin());

        assertEquals(user, result);

        userDAO.delete(user);        
    }

    @Test
    public void testDeleteUser() {
        System.out.println("testDeleteUser");

        User user = new User();
        user.setLogin("teste");
        user.setPassword("teste");

        userDAO.add(user);

        Client client = Client.create();
        WebResource wr = client.resource(BASE_URI);
        wr.path("resources/user/deleteUser/" + user.getLogin()).delete();

        User result = userDAO.getUser(user.getLogin());

        //assertEquals(result, null);
    }

}