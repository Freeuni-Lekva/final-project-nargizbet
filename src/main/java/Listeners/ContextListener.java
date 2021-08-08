package Listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import Database.BalanceDAO;
import Database.FriendsDAO;
import Database.StatsDAO;
import Database.UserDAO;

import javax.servlet.ServletContext;

/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
public class ContextListener implements ServletContextListener {

	/**
	 * Not Used.
     */
    public void contextDestroyed(ServletContextEvent sce)  {}

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         ServletContext context = sce.getServletContext();
         
         context.setAttribute("StatsDAO", new StatsDAO());
         context.setAttribute("BalanceDAO", new BalanceDAO());
         context.setAttribute("UserDAO", new UserDAO());
         context.setAttribute("FriendsDAO", new FriendsDAO((UserDAO)context.getAttribute("UserDAO")));
    }
	
}
