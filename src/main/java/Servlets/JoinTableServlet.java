package Servlets;

import Gameplay.Room.Table;
import User.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JoinTableServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currUser = (User)req.getSession().getAttribute("User");
        int tableId = Integer.parseInt(req.getParameter("tableId"));
        String gameName = req.getParameter("gameName");
        List<Table> tableList = (List)req.getServletContext().getAttribute(gameName + "Tables");
        Table currTable = tableList.get(tableId);
        synchronized (currTable){
            if(currTable.getUsers().size() < currTable.getMaxCapacity() && !currTable.getUsers().contains(currUser)) {
                currTable.addUser(currUser);
                req.getRequestDispatcher("/BlackjackTable.jsp").forward(req, resp);
                return;
            }
        }

        req.setAttribute("TableFullMessage", "The table was full");
        req.getRequestDispatcher("/ViewTables.jsp").forward(req, resp);
    }
}
