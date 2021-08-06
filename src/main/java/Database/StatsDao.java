package java.Database;

import java.util.List;
import javax.sql.DataSource;

public class StatsDao {

	private DataSource dataSource;
	
	public StatsDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public synchronized int getWins() {
		return 0;	
	}
	
	public synchronized void addWins() {
		
	}
	
//	public synchronized List<Pair<User, Integer> getLeaderboard() {
//		return null;
//	}
	
	public synchronized int getUserPlace() {
		return 0;
	}
	
}
