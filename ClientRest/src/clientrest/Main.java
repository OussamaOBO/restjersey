package clientrest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author joaosavio
 */
public class Main {
    private static final String BASE_URI = "http://localhost:8080/Rest";
   
    private static String addUser = "/resources/user/addUser/%s/%s";
    private static String getUser = "/resources/user/getUser/%s";
    private static String deleteUser = "/resources/user/deleteUser/%s";



    public static void main(String[] args) {
        String param = String.format(addUser, "teste", "teste");  
        time(post(param));   

        param = String.format(getUser, "teste");
        time(get(param));

        param = String.format(deleteUser, "teste");
        time(get(param));
    }

    public static void time(long time) {
        System.out.println("Time: " + time + " ms");
    }

    public static long post(String path) {        
        Client client = Client.create();
        WebResource wr = client.resource(BASE_URI);
        long millis = System.currentTimeMillis();
        ClientResponse response = wr.path(path).post(ClientResponse.class);
        long result = System.currentTimeMillis() - millis;
        System.out.println(response.getEntity(String.class));

        return result;
    }

    public static long delete(String path) {
        Client client = Client.create();
        WebResource wr = client.resource(BASE_URI);
        long millis = System.currentTimeMillis();
        ClientResponse response = wr.path(path).delete(ClientResponse.class);
        long result = System.currentTimeMillis() - millis;
        System.out.println(response.getEntity(String.class));

        return result;
    }

    public static long get(String path) {
        Client client = Client.create();
        WebResource wr = client.resource(BASE_URI);
        long millis = System.currentTimeMillis();
        String teste = wr.path(path).type(MediaType.APPLICATION_JSON).get(String.class);
        long result = System.currentTimeMillis() - millis;
        System.out.println(teste);

        return result;
    }

}
