package lk.ijse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(urlPatterns = "/json")
public class JSONProcessingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject customer = new JsonObject();
        customer.addProperty("name","Akila");
        customer.addProperty("email","example@gmail.com");
        customer.addProperty("contact","0775132538");
        customer.addProperty("age",25);

        JsonObject address1 = new JsonObject();
        address1.addProperty("no",7);
        address1.addProperty("street","San Francisco");
        address1.addProperty("city","San Francisco");

        JsonObject address2 = new JsonObject();
        address2.addProperty("no",7);
        address2.addProperty("street","San fjfjkfjkf");
        address2.addProperty("city","San jhbfdjbe");

        JsonArray address = new JsonArray();
        address.add(address1);
        address.add(address2);
        customer.add("address",address);

        resp.setContentType("application/json");
        resp.getWriter().write(customer.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        JsonObject customer = gson.fromJson(req.getReader(), JsonObject.class);
        System.out.println(customer);

    }
}

