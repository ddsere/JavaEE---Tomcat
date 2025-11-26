import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    List<Customer> customers = new ArrayList<Customer>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaeeapp","root","Daham2002");
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
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaeeapp","root","Daham2002");
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
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaeeapp", "root", "Daham2002")) {
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

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaeeapp", "root", "Daham2002")) {
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