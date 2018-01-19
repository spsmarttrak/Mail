package Mailing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtil {

	public Connection getConnection(){
		Connection con=null;
		try{
			Properties p=new Properties();
			p.load(this.getClass().getResourceAsStream("database.properties"));
			String driver=p.getProperty("database.driver");
			String url=p.getProperty("database.url");
			String user=p.getProperty("database.user");
			String password=p.getProperty("database.password");
			
			 Class.forName(driver).newInstance();
			 con=DriverManager.getConnection(url, user, password);
		}catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
	
	/*public static void main(String args[]){
		try{
			System.out.println("1");
			System.out.println("---->"+new DBUtil().getConnection());
		}catch(Exception e){}
	}*/
}
