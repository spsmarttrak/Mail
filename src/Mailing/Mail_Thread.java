package Mailing;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Mail_Thread {
	
	public static  Properties p=new Properties();
	
	public static void main(String[] args) {
		System.out.println("4");
		int delay=1;
		
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        p.load(Mail_Thread.class.getResourceAsStream("database.properties"));
	        delay=Integer.parseInt(p.getProperty("delay").trim());
	        String current_date=sdf.format(new java.util.Date());
	        String report_date=new Mail_Thread().getPreviousDay(current_date);
			p.setProperty("date",report_date);
	        p.setProperty("mail_status","0");
		
	        System.out.println("in mail thread"+report_date+"&&&&"+current_date);
	        
	        while(true){
	        	
				if(current_date.equals(new Mail_Thread().getPreviousDay(sdf.format(new java.util.Date())))){ //block for interchanging the dates at midnight
					current_date=sdf.format(new java.util.Date());
					report_date=new Mail_Thread().getPreviousDay(current_date);
					p.setProperty("date",report_date);
					p.setProperty("mail_status","0");
					
		//			logger.info("Report Date :"+report_date);
				}
				 if(Integer.parseInt(p.getProperty("mail_status"))==0){   
					 //start thread only if status is 0
				    	// send sms if time is greater than 08:30:00
				    	if(new java.util.Date().getTime()>sdf1.parse(sdf.format(new java.util.Date())+" 13:00:00").getTime()){
					        Thread t=new Thread(new Mail_Squedular());
					        t.start();
				    	}
					  
				     }
				    System.out.println("Method executed at every "+delay+" seconds.time is :: "+ new Date());
					Thread.sleep(delay*1000); 
					}//whiles
				}catch(Exception e){
	//				logger.info("error :"+e.getMessage());
					e.printStackTrace();
				}
			}

	public String getPreviousDay(String date){ //date should be in yyyy-MM-dd formate
		String result=date;
		try 
        {
        	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");        	
        	result=sdf.format(sdf.parse(date).getTime()-24*60*60*1000);
        	System.out.println("$$"+result);
        }
        catch(Exception e) 
        {
 //       	logger.info("Error :"+e.getMessage());
            e.printStackTrace();
        }
		return result;
	}

	}