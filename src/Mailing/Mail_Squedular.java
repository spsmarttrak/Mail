package Mailing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.smarttrak.constants.Constants;

public class Mail_Squedular implements Runnable {

	/*public static void main(String[] args) {
		System.out.println("main in mail squedular");
Thread t = new Thread(new Mail_Squedular());
			t.start();
	}*/

	@Override
	public void run() {
		System.out.println("child class run method");
		String mail_flg="";
		Connection con=null;
    	ResultSet rs=null;
    	PreparedStatement ps=null;
    	try{
    		
    		con=new DBUtil().getConnection();
    		mail_flg =Mail_Thread.p.getProperty("mail_flg");
    		String consumption_date=Mail_Thread.p.getProperty("date");
    		System.out.println("child class run method"+consumption_date);
    		Data d = new Data();
    		d.getData();
    		ReadPropertiesFile.readConfig();
    		GMailServer sender = new GMailServer(Constants.setFrom, Constants.setPassword);
    		sender.sendMail("Smarttrak Solar System Daily Update", "daily upadated data", Constants.setFrom,Constants.emailTO);
    		
    		 Mail_Thread.p.put("mail_status", "1");
    		 
    	}catch(Exception e){
  //  		SMS_Thread.logger.error("Error"+e.getMessage());
    		e.printStackTrace();
    	}
    	finally{
    		try{
    		if(con!=null)
    		  con.close();
    		}catch(Exception e){}
    	}
	}
	
	/*public static void main(String[] args) {
		
	}*/

}
