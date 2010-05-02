package controller.services;

import java.util.List;
import javax.ws.rs.core.Response;

/**
 *
 * @author João Sávio C. Longo - joaosavio@gmail.com
 */

public interface IService<T> {    
    Response add(T t);
    Response update(T t);
    Response delete(T t);
    Response get(long id);
    Response listAll();
    Response list(int begin, int length);
    void validateRequest(T t);
}
