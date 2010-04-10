package model.dao;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.WebApplicationException;
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
public class UserDAOTest {

    private User user1;
    private User user2;
    private UserDAO userDAO = new UserDAO();

    public UserDAOTest() {
        
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

        user1 = new User();
        user1.setLogin("teste1");
        user1.setPassword("teste1");
        user2 = new User();
        user2.setLogin("teste2");
        user2.setPassword("teste2");
    }

    @After
    public void tearDown() {
        userDAO.deleteAll();
    }

    @Test
    public void testAddUser() {       
        System.out.println("testAddUser");
        userDAO.add(user1);     
        User result = userDAO.getUser(user1.getLogin());
        userDAO.delete(user1);

        assertEquals(user1, result);
    }

    @Test
    public void testDeleteUser() {
        System.out.println("testDeleteUser");
        userDAO.add(user1);
        userDAO.delete(user1);
        assertEquals(null, userDAO.getUser(user1.getLogin()));
    }

    @Test
    public void testUpdateUserPassword() {
        System.out.println("testUpdateUserPassword");
        userDAO.add(user1);
        user1.setPassword("newPassword");
        userDAO.update(user1);
        User result = userDAO.getUser(user1.getLogin());
        userDAO.delete(user1);
        assertEquals(user1, result);
    }

    @Test(expected = Exception.class)
    public void testUpdateUserLogin() {
        System.out.println("testUpdateUserLogin");
        userDAO.add(user1);
        user1.setLogin("newLogin");
        userDAO.update(user1);
        User result = userDAO.getUser(user1.getLogin());
        userDAO.delete(user1);
        assertEquals(user1, result);
    }

    /**
     * Test of getUser method, of class UserDAO.
     */
    @Test
    public void testGetUser_String_String() {
        System.out.println("testGetUser_String_String");
        userDAO.add(user1);        
        User result = userDAO.getUser(user1.getLogin(), user1.getPassword());
        userDAO.delete(user1);
        assertEquals(user1, result);
    }

    /**
     * Test of getUser method, of class UserDAO.
     */
    @Test
    public void testGetUser_String() {
        System.out.println("testGetUser_String");
        userDAO.add(user1);       
        User result = userDAO.getUser(user1.getLogin());
        userDAO.delete(user1);
        assertEquals(user1, result);
    }
    
    @Test
    public void testListAllUsers() {
        System.out.println("testListAllUsers");
        List<User> list = new ArrayList<User>();
        list.add(user1);
        list.add(user2);
        
        for (int i = 0; i < list.size(); i++) {
            userDAO.add(list.get(i));
        }      
                
        List<User> result = userDAO.listAll();
        for (int i = 0; i < result.size(); i++) {
            userDAO.delete(result.get(i));
        }        
        assertEquals(list, result);
    }

    @Test
    public void testDeleteAllUsers() {
        System.out.println("testDeleteAllUsers");

        userDAO.add(user1);
        userDAO.add(user2);

        userDAO.deleteAll();

        User result = userDAO.getUser(user1.getLogin());
        assertEquals(result, null);
    }



}