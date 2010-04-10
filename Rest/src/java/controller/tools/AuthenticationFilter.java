package controller.tools;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.WebApplicationException;
import model.vo.User;

/**
 *
 * @author João Sávio Ceregatti Longo - joaosavio@gmail.com
 */
public class AuthenticationFilter implements Filter {

    private static final String LOGIN = "/resources/login/";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hreq = (HttpServletRequest) request;
        HttpServletResponse hresp = (HttpServletResponse) response;

        String requestUri = hreq.getRequestURI();

        if (requestUri.contains(LOGIN)) {
            chain.doFilter(request, response);
        }

        HttpSession session = hreq.getSession();

        //get session user
        User user = (User) session.getAttribute("user");
        if (user == null) {
            System.out.println("user é null");
        }
        if (user != null) {
            chain.doFilter(request, response);
        }       
        else {
            hresp.setStatus(HttpServletResponse.SC_FORBIDDEN);
         //   throw new WebApplicationException(403);
        }

    }

    @Override
    public void destroy() {

    }
}
