package Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataSource {
    private static Connection con;

    public static Connection getCon(){
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nargizbet", "root", "24112001");
            return con;
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
}