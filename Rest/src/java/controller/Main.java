/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import java.io.IOException;
import java.net.MalformedURLException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import model.dao.UserDAO;
import model.vo.User;

/**
 *
 * @author joaosavio
 */
public class Main {

    private static final String BASE_URI = "http://localhost:8080/Rest";

    private static UserDAO udao = new UserDAO();

    public static void main(String[] args) throws MalformedURLException, IOException, ClassNotFoundException {
        long millis = System.currentTimeMillis();


        User user = new User();
        user.setLogin("teste");
        user.setPassword("teste");
        udao.add(user);

        Client client = Client.create();
        WebResource wr = client.resource(BASE_URI);
         
        ClientResponse response = wr.path("resources/login/" + user.getLogin() + "/" + user.getPassword()).post(ClientResponse.class);
        System.out.println(response.getEntity(String.class));
      

        
        User result = udao.getUser(user.getLogin());
        System.out.println(result);
        udao.delete(result);

        millis = System.currentTimeMillis() - millis;
        System.out.println("Time: " + millis + " ms");
    }
}
