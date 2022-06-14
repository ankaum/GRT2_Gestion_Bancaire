package banking;

import java.sql.Connection;
import java.sql.DriverManager;

public class connection{
    static Connection con;
    public static Connection getConnection()
    {
        try {
            String mysqlJDBCDriver = "com.mysql.cj.jdbc.Driver"; //moteur jdbc
            String url = "jdbc:mysql://localhost:3306/bank"; //localhost num de port mysql
            String user = "root";	 //login mysql
            String pass = "anass"; //mdp mysql
            Class.forName(mysqlJDBCDriver);
            con = DriverManager.getConnection(url, user, pass);
        }
        catch (Exception e){
            System.out.println("Erreur de connexion");
        }
        return con;
    }
}
