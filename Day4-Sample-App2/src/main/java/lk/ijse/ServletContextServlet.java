package lk.ijse;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.commons.dbcp2.BasicDataSource;

@WebListener
public class ServletContextServlet implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/javaeeapp");
        ds.setUsername("root");
        ds.setPassword("Daham2002");
        ds.setInitialSize(20);
        ds.setMaxTotal(180);
        servletContext.setAttribute("datasource", ds);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Stopping ServletContext Server");
    }
}
