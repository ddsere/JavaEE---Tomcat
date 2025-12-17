package lk.ijse;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.commons.dbcp2.BasicDataSource;

@WebListener
public class DBConnection implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/pos74");
        ds.setUsername("root");
        ds.setPassword("Daham2002");
        ds.setInitialSize(50);
        ds.setMaxTotal(100);
        context.setAttribute("datasource", ds);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Stopping ServletContextListener");
    }
}
