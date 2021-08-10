package Listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import Database.BalanceDAO;
import Database.FriendsDAO;
import Database.StatsDAO;
import Database.UserDAO;
import Gameplay.Games.Blackjack.Blackjack;
import Gameplay.Room.Table;

import javax.servlet.ServletContext;
import java.util.Arrays;
import java.util.List;

/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
public class ContextListener implements ServletContextListener {
    public static final int INITIAL_TABLE_COUNT = 4;

	/**
	 * Not Used.
     */
    public void contextDestroyed(ServletContextEvent sce)  {}

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         ServletContext context = sce.getServletContext();
         Table[] tables = new Table[INITIAL_TABLE_COUNT];
         Table defaultTable = new Table(new Blackjack());
         Arrays.fill(tables, defaultTable);
         List<Table> blackjackTables = Arrays.asList(tables);
         context.setAttribute("BlackjackTables", blackjackTables);
         context.setAttribute("StatsDAO", new StatsDAO());
         context.setAttribute("BalanceDAO", new BalanceDAO());
         context.setAttribute("UserDAO", new UserDAO());
         context.setAttribute("FriendsDAO", new FriendsDAO((UserDAO)context.getAttribute("UserDAO")));
    }
	
}
