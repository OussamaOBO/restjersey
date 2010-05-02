/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller.services;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.WebApplicationException;
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
public class UserServiceTest {
    private User user1 = new User();
    private User user2 = new User();
    private UserDAO userDAO = new UserDAO();


    public UserServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        userDAO.deleteAll();
        
        user1.setLogin("teste1");
        user1.setPassword("teste1");
        user2.setLogin("teste2");
        user2.setPassword("teste2");
    }

    @After
    public void tearDown() throws Exception {
        userDAO.deleteAll();
    }

    /**
     * Test of add method, of class UserService.
     */
    @Test
    public void testAdd_User() throws Exception {
        System.out.println("add");        
        UserService instance = new UserService();
        int expResult = Response.Status.CREATED.getStatusCode();
        Response result = instance.add(user1);
        assertEquals(expResult, result.getStatus());

        userDAO.delete(user1);
    }

    @Test
    public void testAdd_UserAlreadyExists() throws Exception {
        System.out.println("addAlreadyExists");
        userDAO.add(user1);

        UserService instance = new UserService();
        int expResult = Response.Status.CONFLICT.getStatusCode();
        Response result = instance.add(user1);
        assertEquals(expResult, result.getStatus());

        userDAO.delete(user1);
    }

    /**
     * Test of update method, of class UserService.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        userDAO.add(user1);
        UserService instance = new UserService();
        int expResult = Response.Status.OK.getStatusCode();
        user1.setPassword("newPassword");
        Response result = instance.update(user1);
        assertEquals(expResult, result.getStatus());

        userDAO.delete(user1);
    }

    /**
     * Test of delete method, of class UserService.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        userDAO.add(user1);

        UserService instance = new UserService();
        int expResult = Response.Status.OK.getStatusCode();
        Response result = instance.delete(user1);
        assertEquals(expResult, result.getStatus());
    }

    /**
     * Test of listAll method, of class UserService.
     */
    @Test
    public void testListAll() throws Exception {
        System.out.println("listAll");
  
        List<User> list = new ArrayList<User>();
        list.add(user1);
        list.add(user2);
        UserService instance = new UserService();
        int expResult = Response.Status.OK.getStatusCode();
        Response result = instance.listAll();
        assertEquals(expResult, result.getStatus());

        userDAO.delete(user1);
        userDAO.delete(user2);
    }

    /**
     * Test of list method, of class UserService.
     */
    @Test
    public void testList() throws Exception {
        System.out.println("list");
        int begin = 0;
        int length = 2;
        List<User> list = new ArrayList<User>();
        list.add(user1);
        list.add(user2);
        UserService instance = new UserService();
        int expResult = Response.Status.OK.getStatusCode();
        Response result = instance.list(begin, length);
        assertEquals(expResult, result.getStatus());

        userDAO.delete(user1);
        userDAO.delete(user2);
    }

    /**
     * Test of add method, of class UserService.
     */
    @Test
    public void testAdd_List() throws Exception {
        System.out.println("add");
        List<User> list = new ArrayList<User>();
        list.add(user1);
        list.add(user2);
        UserService instance = new UserService();
        int expResult = Response.Status.CREATED.getStatusCode();
        Response result = instance.add(list);
        assertEquals(expResult, result.getStatus());

        userDAO.delete(user1);
        userDAO.delete(user2);
    }

    /**
     * Test of get method, of class UserService.
     */
    @Test
    public void testGet_long() throws Exception {
        System.out.println("get");

        userDAO.add(user1);
        long id = userDAO.getByUnique(user1).getId();
        UserService instance = new UserService();
        int expResult = Response.Status.OK.getStatusCode();
        Response result = instance.get(id);
        assertEquals(expResult, result.getStatus());

        userDAO.delete(user1);
    }

    /**
     * Test of get method, of class UserService.
     */
    @Test
    public void testGet_String() throws Exception {
        System.out.println("get");
       
        userDAO.add(user1);
        UserService instance = new UserService();
        int expResult = Response.Status.OK.getStatusCode();
        Response result = instance.get(user1.getLogin());
        assertEquals(expResult, result.getStatus());

        assertEquals(((User)result.getEntity()).getLogin(), user1.getLogin());

        userDAO.delete(user1);
    }

    /**
     * Test of get method, of class UserService.
     */
    @Test
    public void testGet_String_String() throws Exception {
        System.out.println("get");

        userDAO.add(user1);
        UserService instance = new UserService();
        int expResult = Response.Status.OK.getStatusCode();
        Response result = instance.get(user1.getLogin(), user1.getPassword());
        assertEquals(expResult, result.getStatus());

        //assertEquals(((User)result.getEntity()).getLogin(), user1.getLogin());

        userDAO.delete(user1);
    }

    @Test
    public void testGet_String_StringWrong() throws Exception {
        System.out.println("get");

        userDAO.add(user1);
        UserService instance = new UserService();
        int expResult = Response.Status.NOT_FOUND.getStatusCode();
        Response result = instance.get(user1.getLogin(), "wrong");
        assertEquals(expResult, result.getStatus());

        assertEquals(((User)result.getEntity()), null);

        userDAO.delete(user1);
    }

    /**
     * Test of validateRequest method, of class UserService.
     */
    @Test
    public void testValidateRequest_User() {
        System.out.println("validateRequest");

        UserService instance = new UserService();
        instance.validateRequest(user1);
    }

    @Test(expected = WebApplicationException.class)
    public void testValidateRequest_UserWrong() {
        System.out.println("validateRequest");

        UserService instance = new UserService();
        user1.setLogin("");
        instance.validateRequest(user1);
        user1.setLogin("teste1");
    }

    /**
     * Test of validateRequest method, of class UserService.
     */
    @Test
    public void testValidateRequest_ObjectArr() {
        System.out.println("validateRequest");
        Object[] object = new Object[] {"teste", 1, 1.0, 12L};
        UserService.validateRequest(object);
        // TODO review the generated test code and remove the default call to fail.        
    }

    @Test(expected = WebApplicationException.class)
    public void testValidateRequest_ObjectArrWrong() {
        System.out.println("validateRequestWrong");

        Object[] object = new Object[] {"teste", 1, 1.0, 0L};
        UserService.validateRequest(object);
        // TODO review the generated test code and remove the default call to fail.
    }



}