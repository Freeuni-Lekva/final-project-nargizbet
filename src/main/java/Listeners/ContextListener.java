package Listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import Database.BalanceDAO;
import Database.FriendsDAO;
import Database.StatsDAO;
import Database.UserDAO;
import Gameplay.Games.Blackjack.BlackJackTable;
import Gameplay.Games.Blackjack.BlackjackGame;
import Gameplay.Games.Slots.Slots;
import Gameplay.Room.Table;

import javax.servlet.ServletContext;
import java.util.ArrayList;
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
         List<Table> blackjackTables = new ArrayList<>();
        BalanceDAO balanceDAO = new BalanceDAO();
         for(int i = 0; i < INITIAL_TABLE_COUNT; i++){
            blackjackTables.add(new BlackJackTable(new BlackjackGame(), balanceDAO));
         }
         context.setAttribute("Blackjack", new BlackjackGame());
         context.setAttribute("Slots", new Slots());
         context.setAttribute("BlackjackTables", blackjackTables);
         context.setAttribute("StatsDAO", new StatsDAO());
         context.setAttribute("BalanceDAO", balanceDAO);
         context.setAttribute("UserDAO", new UserDAO());
         context.setAttribute("FriendsDAO", new FriendsDAO((UserDAO)context.getAttribute("UserDAO")));
    }
	
}
