package Servlets;

import Database.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DisplayImageServlet extends HttpServlet {

    public static final String DEFAUL_IMAGE_LOCATION = "src/main/webapp/Images/user.png";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO UDAO = (UserDAO) getServletContext().getAttribute("UserDAO");
        InputStream input  = UDAO.getProfilePicture(request.getParameter("Username"));
        if(input == null) input = new FileInputStream(DEFAUL_IMAGE_LOCATION);
        OutputStream output = response.getOutputStream();
        byte[] buffer = new byte[10240];
        for (int length = 0; (length = input.read(buffer)) > 0;) {
            output.write(buffer, 0, length);
        }
        response.setContentType("image/gif");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}


