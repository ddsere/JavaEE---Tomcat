package lk.ijse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

@WebServlet(urlPatterns = "/data-formats")
@MultipartConfig/*(
        maxFileSize = 1024*1024*10,
        maxRequestSize = 1024*1024*15
)*/
public class DataFormatsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();
        System.out.println("contentType " + contentType);

        /*multipart/form-data*/
        System.out.println("id value " + req.getParameter("id"));
        System.out.println("name value " + req.getParameter("name"));
        Part filePart = req.getPart("image");
        System.out.println("filePart " + filePart.getSubmittedFileName());

        /*create Directory*/
        File uploadDir = new File("D:\\B74\\JavaEE\\JavaEE-Tomcat\\Day4-Sample-App2\\src\\main\\resources\\images");
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        /*save the file*/
        String fullPath = uploadDir.getAbsolutePath() + File.separator + filePart.getSubmittedFileName();
        filePart.write(fullPath);
    }
}
