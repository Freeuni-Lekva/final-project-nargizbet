package Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Database.BalanceDAO;
import User.User;

/**
 * Servlet implementation class BalanceServlet
 */
public class BalanceServlet extends HttpServlet {
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("User");
		
		request.setAttribute("firstName", user.getFirstName());
		request.setAttribute("lastName", user.getLastName());
		request.setAttribute("balance", user.getBalance());
		
		request.getRequestDispatcher("/Balance.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User)request.getSession().getAttribute("User");
		BalanceDAO balanceDao = (BalanceDAO)getServletContext().getAttribute("BalanceDAO");
		double amount = Double.parseDouble(request.getParameter("amount"));
		
		user.deposit(amount);
		balanceDao.setBalance(user);
		
		doGet(request, response);
	}

}
