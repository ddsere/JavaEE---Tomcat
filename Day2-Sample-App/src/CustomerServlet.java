import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
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

        System.out.println(id+name+address);

        Customer customer = new Customer(id, name, address);
        customers.add(customer);

        resp.getWriter().println("Customer added successfully!");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        for(Customer c:customers){
            resp.getWriter().println("<tr>" +
                    "<td>"+c.getId()+"</td>" +
                    "<td>"+c.getName()+"</td>" +
                    "<td>"+c.getAddress()+"</td>" +
                    "</tr>");
        }
    }
}