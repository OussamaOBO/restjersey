package clientrest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import model.vo.User;

/**
 *
 * @author joaosavio
 */
public class Main {
    private static final String BASE_URI = "http://localhost:8080/Rest";
   
    private static String addUser = "/resources/user/";
    private static String putUser = "/resources/user/";
    private static String getUser = "/resources/user/%s";
    private static String deleteUser = "/resources/user/%s";



    public static void main(String[] args) {
        teste();
    }

    public static void teste() {
        User user = new User();
        user.setLogin("teste");
        user.setPassword("teste");
        String param = addUser;
        time(post(param, user));

        User user2 = new User();
        user2.setLogin("teste");
        user2.setPassword("teste3");
        param = putUser;
        time(put(param, user2));

        param = String.format(getUser, "teste");
        time(get(param));

        param = String.format(deleteUser, "teste");
        time(delete(param));
    }


    public static void time(long time) {
        System.out.println("Time: " + time + " ms");
    }

    public static long post(String path, User user) {
        String json = new Gson().toJson(user, User.class);
        Client client = Client.create();
        WebResource wr = client.resource(BASE_URI);
        long millis = System.currentTimeMillis();

        ClientResponse response = wr.path(path).type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);
        long result = System.currentTimeMillis() - millis;
    //    System.out.println(response.getEntity(String.class));

        return result;
    }

    public static long put(String path, User user) {
        String json = new Gson().toJson(user, User.class);
        Client client = Client.create();
        WebResource wr = client.resource(BASE_URI);
        long millis = System.currentTimeMillis();

        ClientResponse response = wr.path(path).type(MediaType.APPLICATION_JSON).put(ClientResponse.class, json);
        long result = System.currentTimeMillis() - millis;
        System.out.println(response);

        return result;
    }

    public static long delete(String path) {
        Client client = Client.create();
        WebResource wr = client.resource(BASE_URI);
        long millis = System.currentTimeMillis();
        ClientResponse response = wr.path(path).delete(ClientResponse.class);
        long result = System.currentTimeMillis() - millis;
    //    System.out.println(response.getEntity(String.class));

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
