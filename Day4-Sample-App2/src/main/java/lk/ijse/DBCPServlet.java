package lk.ijse;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/api/v1/customer")
public class DBCPServlet extends HttpServlet {

    BasicDataSource ds;
    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        ds = (BasicDataSource) context.getAttribute("datasource");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        try {
            Connection connection = ds.getConnection();
            String query = "INSERT INTO customer(id,name,address) VALUES(?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, address);
            int rows = ps.executeUpdate();
            if(rows > 0) {
                resp.getWriter().println("Customer Saved");
            }else {
                resp.getWriter().println("Customer Not Saved!");
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        try {
            Connection connection = ds.getConnection();
            if (id == null) {
                String query = "SELECT * FROM customer";
                PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String cusId = rs.getString("id");
                    String cusName = rs.getString("name");
                    String cusAddress = rs.getString("address");

                    resp.getWriter().println("<tr>" +
                            "<td>"+cusId+"</td>" +
                            "<td>"+cusName+"</td>" +
                            "<td>"+cusAddress+"</td>" +
                            "<td>\n" +
                            "          <button class=\"edit-customer\">Edit</button>\n" +
                            "          <button class=\"delete-customer\">Delete</button>\n" +
                            "        </td>" +
                            "</tr>");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        try (Connection connection = ds.getConnection()) {
            String query = "UPDATE customer SET name = ?, address = ? WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, name);
                ps.setString(2, address);
                ps.setString(3, id);

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    resp.getWriter().println("Customer updated successfully!");
                } else {
                    resp.getWriter().println("Customer not found!");
                }
            }
        } catch (SQLException e) {
            resp.getWriter().println("Error updating customer: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        if (id == null || id.trim().isEmpty()) {
            resp.getWriter().println("Customer ID is required!");
            return;
        }

        try (Connection connection = ds.getConnection()) {
            String query = "DELETE FROM customer WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, id);
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    resp.getWriter().println("Customer deleted successfully!");
                } else {
                    resp.getWriter().println("Customer not found!");
                }
            }
        } catch (SQLException e) {
            resp.getWriter().println("Error deleting customer: " + e.getMessage());
            e.printStackTrace();
        }
    }
}