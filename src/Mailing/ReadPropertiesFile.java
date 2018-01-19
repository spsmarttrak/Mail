package Mailing;

import java.io.FileInputStream;
import java.util.Properties;

import com.smarttrak.constants.Constants;
import com.sun.mail.imap.protocol.MailboxInfo;
 
public class ReadPropertiesFile {
	public static void readConfig() throws Exception {
		try {
			Properties pro = new Properties();
			String path = System.getProperty("user.dir") + "/src/Mailing/database.properties";
			pro.load(new FileInputStream(path));
			
			Constants.setFrom = pro.getProperty("setFrom");
			Constants.setPassword = pro.getProperty("setPassword");
			Constants.emailTO = pro.getProperty("emailTO");
			System.out.println("**"+Constants.emailTO);
	//		Constants.delay = pro.getProperty("delay");
	//		Constants.timetoquery = pro.getProperty("timetoquery");

		} catch (Exception e) {
			throw new Exception(e);
		}
	}
		/*public static void main(String[] args) {
			System.out.println("proprty");
			ReadPropertiesFile p = new ReadPropertiesFile();
			try {
				p.readConfig();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}*/

}