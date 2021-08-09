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
        User user = (User)request.getSession().getAttribute("User");


        if (filePart != null && filePart.getSize() != 0) {
            inputStream = filePart.getInputStream();
            UserDAO UDAO = (UserDAO)getServletContext().getAttribute("UserDAO");
            UDAO.setProfilePicture(user.getUsername(), inputStream);
            request.getSession().setAttribute("User", user);
        }

        request.getRequestDispatcher("/profile?Username=" + user.getUsername()).forward(request, response);
    }
}