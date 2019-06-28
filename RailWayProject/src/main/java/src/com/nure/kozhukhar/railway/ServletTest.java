package src.com.nure.kozhukhar.railway;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "ServletTest", urlPatterns = "/test1")
public class ServletTest extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ServletTest.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /***
         * Your code, logic and etc.
         */
        LOG.debug(ServletTest.class.getName() + " was started!");
    }
}
