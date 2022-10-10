package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.AccountService;
import models.User;

/**
 *
 * @author godim
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String logout = request.getParameter("logout");

        if (logout != null) {
            session.invalidate();
            session = request.getSession();
        }

        String username = (String) session.getAttribute("username");

        if (username == null) {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } else {
            response.sendRedirect("home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // send user back to login if invalid entry
        if (username == null || username.equals("") || password == null || password.equals("")) {
            request.setAttribute("message", "Invalid username and password combination.");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp")
                    .forward(request, response);
        }

        AccountService as = new AccountService();
        User validUser = as.login(username, password);

        // send user back if invalid username/password
        if (validUser == null) {
            request.setAttribute("message", "Invalid username and password combination.");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } else {
            // log them in
            session.setAttribute("user", validUser);
            response.sendRedirect("home");
        }
    }

}
