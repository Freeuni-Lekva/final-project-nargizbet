package Servlets;

import Database.DataSource;
import Database.FriendsDAO;
import Database.UserDAO;
import User.User;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

@MultipartConfig(maxFileSize = 16177215)
public class AddImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputStream inputStream = null;
        Part filePart = request.getPart("image");
        if (filePart != null) {
            inputStream = filePart.getInputStream();
            UserDAO UDAO = (UserDAO)getServletContext().getAttribute("UserDAO");
            User user = (User)request.getSession().getAttribute("User");
            UDAO.setProfilePicture(user.getUsername(), inputStream);
            request.getSession().setAttribute("User", user);
        }

        request.getRequestDispatcher("AddImage.jsp").forward(request, response);
    }
}