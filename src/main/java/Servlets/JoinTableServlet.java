package Servlets;

import Database.BalanceDAO;
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

        if (req.getSession().getAttribute("User") == null) {
            resp.sendRedirect("/homepage");
            return;
        }

        User currUser = (User)req.getSession().getAttribute("User");
        BalanceDAO BDAO = (BalanceDAO)getServletContext().getAttribute("BalancDAO");
        int tableId = Integer.parseInt(req.getParameter("tableId"));
        String gameName = req.getParameter("gameName");
        List<Table> tableList = (List)req.getServletContext().getAttribute(gameName + "Tables");
        Table currTable = tableList.get(tableId);

        int amount = Integer.parseInt(req.getParameter("amount"));

        if (amount > currUser.getBalance()) {
            req.setAttribute("message", "Not enough balance");
            req.getRequestDispatcher("/JSP/ViewTables.jsp").forward(req, resp);
            return;
        }

        synchronized (currTable){

            if (currTable.getUsers().contains(currUser)) {
                req.setAttribute("message", "You have already joined the selected table from another session");
                req.getRequestDispatcher("/JSP/ViewTables.jsp").forward(req, resp);
                return;
            }

            if(currTable.getUsers().size() < currTable.getMaxCapacity()) { ;
                resp.sendRedirect("/JSP/BlackjackTable.jsp?amount=" + amount + "&tableId=" + tableId);
                return;
            }

            req.setAttribute("message", "Selected table is full");
            req.getRequestDispatcher("/JSP/ViewTables.jsp").forward(req, resp);
        }

    }
}
