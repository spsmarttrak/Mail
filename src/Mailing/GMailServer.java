package Mailing;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class GMailServer extends javax.mail.Authenticator {
	private String mailhost = "smtp.gmail.com"; // "smtp.mail.yahoo.com" //"smtp.gmail.com";
	private String user;
	private String password;
	private Session session;

	public GMailServer(String user, String password) {
		System.out.println("3");
		this.user = user;
		this.password = password;

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", mailhost);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.quitwait", "false");

		session = Session.getDefaultInstance(props, this);
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, password);
	}

	public synchronized void sendMail(String subject, String body, String sender, String recipients) throws Exception {
		System.out.println("6");
		System.out.println("sender=====>>" + sender + "reciepent=====>" + recipients);
		MimeMessage message = new MimeMessage(session);
		DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
		message.setSender(new InternetAddress(sender));
		message.setSubject(subject);

		BodyPart messegebodypart1 = new MimeBodyPart();

		messegebodypart1.setText(subject);

		// java.util.Date d = new java.util.Date();
		// int cdate = d.getDate();

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String cdate = sdf.format(new Date());

		String filename = "C:/Users/admin/Desktop/Reports/" + cdate + ".pdf";

		BodyPart messegebodyPart2 = new MimeBodyPart();

		DataSource source = new FileDataSource(filename);

		messegebodyPart2.setDataHandler(new DataHandler(source));

		messegebodyPart2.setFileName(filename);

		// 5) create Multipart object and add MimeBodyPart objects to this
		// object


		Multipart multipart = new MimeMultipart();

		multipart.addBodyPart(messegebodypart1);
		multipart.addBodyPart(messegebodyPart2);

		// 6) set the multiplart object to the message object

		message.setContent(multipart);

		message.saveChanges();

		if (recipients.indexOf(',') > 0)
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
		else
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
		Transport.send(message);
	}

	public class ByteArrayDataSource implements DataSource {
		private byte[] data;
		private String type;

		public ByteArrayDataSource(byte[] data, String type) {
			super();
			this.data = data;
			this.type = type;
		}

		public ByteArrayDataSource(byte[] data) {
			super();
			this.data = data;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getContentType() {
			if (type == null)
				return "application/octet-stream";
			else
				return type;
		}

		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(data);
		}

		public String getName() {
			return "ByteArrayDataSource";
		}

		public OutputStream getOutputStream() throws IOException {
			throw new IOException("Not Supported");
		}
	}
	/*public static void main(String[] args) {
		System.out.println("main");
		
	}*/
}