package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/si/*")
public class StudentFilter implements Filter {
    private static final String LOGIN_URL       = "/login";
    private static final String STUDENT_SESSION = "studentSession";

    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest  request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession         session  = request.getSession();
        String              path     = request.getRequestURI().substring(request.getContextPath().length());

        if ((path.startsWith("/resources")) || (path.startsWith("/javax.faces.resource")) || (path.startsWith(LOGIN_URL))) {
            chain.doFilter(request, response);
            return;
        }

        if (session.getAttribute(STUDENT_SESSION) == null) {
            request.getRequestDispatcher(LOGIN_URL).forward(request, response);
        }
        else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
    }
}