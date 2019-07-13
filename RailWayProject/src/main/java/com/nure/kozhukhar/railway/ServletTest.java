package com.nure.kozhukhar.railway;

import com.nure.kozhukhar.railway.util.DBUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@WebServlet(name = "ServletTest", urlPatterns = "/test1")
public class ServletTest extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ServletTest.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.debug(ServletTest.class.getName() + " was started!");

        try (Connection conn = DBUtil.getInstance().getDataSource().getConnection();
             Statement stmt = conn.createStatement();
        ) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Countries");
            while (rs.next()) {
                LOG.trace(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
