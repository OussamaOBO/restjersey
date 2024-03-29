package model.dao;

import java.util.ArrayList;
import java.util.List;
import model.vo.User;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
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

    private User user1 = new User();
    private User user2 = new User();
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

    @Test
    public void testAddUser() throws Exception {
        System.out.println("testAddUser");
        userDAO.add(user1);     
        User result = userDAO.getByUnique(user1);
        userDAO.delete(user1);

        assertEquals(user1, result);
    }

    @Test
    public void testDeleteUser() throws Exception {
        System.out.println("testDeleteUser");
        userDAO.add(user1);
        userDAO.delete(user1);
        assertEquals(null, userDAO.getByUnique(user1));
    }

    @Test
    public void testUpdateUser() throws Exception {
        System.out.println("testUpdateUser");
        userDAO.add(user1);
        user1.setLogin("newLogin");
        user1.setPassword("newPassword");
        userDAO.update(user1);
        User result = userDAO.getByUnique(user1);
        userDAO.delete(user1);
        assertEquals(user1, result);
    }

    /**
     * Test of getUser method, of class UserDAO.
     */
    @Test
    public void testGetUser_String_String() throws Exception {
        System.out.println("testGetUser_String_String");
        userDAO.add(user1);
        userDAO.add(user2);
        List<Criterion> list = new ArrayList<Criterion>();
        Criterion c = Restrictions.eq("login", user1.getLogin());
        list.add(c);
        c = Restrictions.eq("password", user1.getPassword());
        list.add(c);
        User result = userDAO.findByCriteria(list).get(0);
        assertEquals(user1, result);
        userDAO.delete(user1);
        userDAO.delete(user2);
    }

    /**
     * Test of getUser method, of class UserDAO.
     */
    @Test
    public void testGetUser_String() throws Exception {
        System.out.println("testGetUser_String");
        userDAO.add(user1);       
        User result = userDAO.getByUnique(user1);
        userDAO.delete(user1);
        assertEquals(user1, result);
    }
    
    @Test
    public void testListAllUsers() throws Exception {
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
    public void testDeleteAllUsers() throws Exception {
        System.out.println("testDeleteAllUsers");

        userDAO.add(user1);
        userDAO.add(user2);

        userDAO.deleteAll();

        User result = userDAO.getByUnique(user1);
        assertEquals(result, null);
    }



}