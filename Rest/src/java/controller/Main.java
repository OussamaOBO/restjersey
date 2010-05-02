/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import controller.services.UserService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import model.dao.UserDAO;
import model.vo.User;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author joaosavio
 */
public class Main {

    private static final String BASE_URI = "http://localhost:8080/Rest";

    private static UserDAO userDAO = new UserDAO();

    public static void main(String[] args) throws MalformedURLException, IOException, ClassNotFoundException, Exception {
        long millis = System.currentTimeMillis();

        millis = System.currentTimeMillis() - millis;
        System.out.println("Time: " + millis + " ms");
    }
}
