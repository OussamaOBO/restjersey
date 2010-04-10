package model.dao;

import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
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
public class HibernateUtilTest {

    public HibernateUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getSession method, of class HibernateUtil.
     */
    @Test
    public void testGetSession() {
        System.out.println("getSession");        
        Session result = HibernateUtil.getSession();
        assertNotNull(result);
    }

    /**
     * Test of geraTabelas method, of class HibernateUtil.
     */
    @Test
    public void testGeraTabelas() {
        System.out.println("geraTabelas");
        Configuration conf = new AnnotationConfiguration();
        conf.configure("hibernate.cfg.xml");
        HibernateUtil.geraTabelas(conf);        
    }

}