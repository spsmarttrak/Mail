package Mailing;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Data {
	Connection con=null;
	ResultSet rs=null;
	PreparedStatement ps=null;
	String sql="",mail_flg="";

	public void getData() throws SQLException {
		System.out.println("5");

		try {
			con=new DBUtil().getConnection();
			mail_flg =Mail_Thread.p.getProperty("mail_flg");
			 String consumption_date=Mail_Thread.p.getProperty("date");
			Statement stmt = con.createStatement();
			Statement stmt1 = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from totalenergy");
			ResultSet rs1 = stmt1.executeQuery("select * from insttable");
			ResultSetMetaData rsmd = rs.getMetaData();
			ResultSetMetaData rsmd1 = rs1.getMetaData();
			Document document = new Document(PageSize.A4.rotate());

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String cdate = sdf.format(new Date());
			//File file = new File("C:/Users/admin/Desktop/Reports");
			File file = new File("/home/smart/SERVER/MailReport");
			if (!file.exists()) {
				if (file.mkdir()) {
					System.out.println("Directory is created!");
				} else {
					System.out.println("Failed to create directory!");
				}
			}

		//	File f = new File("C:/Users/admin/Desktop/Reports/" + cdate + ".pdf");
			File f = new File("/home/smart/SERVER/MailReport/" + cdate + ".pdf");

			PdfWriter.getInstance(document, new FileOutputStream(f));

			document.open();

			Image image = Image.getInstance("Img/logo.png");
			image.scaleAbsolute(150, 150);
			document.add(image);

			Paragraph paragraph = new Paragraph("Today Energy Generation Details",
					new Font(FontFamily.TIMES_ROMAN, 20, Font.BOLD));
			paragraph.setIndentationLeft(250);



			document.add(paragraph);
			
			document.add(Chunk.SPACETABBING);
			
			PdfPTable table = new PdfPTable(4);

			PdfPCell tablecell = new PdfPCell();
			tablecell.setHorizontalAlignment(table.ALIGN_CENTER);
			tablecell.setBorderColorTop(BaseColor.BLACK);

			// tablecell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablecell.setBorder(60);

			table.addCell(rsmd.getColumnName(1));
			table.addCell(rsmd.getColumnName(2));
			table.addCell(rsmd.getColumnName(3));
			table.addCell(rsmd.getColumnName(4));

			table.setHeaderRows(1);
			int flag=0;
			while (rs.next()) {
				flag=1;
				String Sid = rs.getString(1);
				tablecell = new PdfPCell(new Phrase(Sid));
				table.addCell(tablecell);

				String TodayEnergy = rs.getString(2);
				tablecell = new PdfPCell(new Phrase(TodayEnergy));
				table.addCell(tablecell);

				String InverterId = rs.getString(3);
				tablecell = new PdfPCell(new Phrase(InverterId));
				table.addCell(tablecell);

				String CurrentDate = rs.getString(4);
				tablecell = new PdfPCell(new Phrase(CurrentDate));
				table.addCell(tablecell);

				System.out.println("*=========>>>>" + rs.getString(1) + "$" + rs.getString(2) + "#" + rs.getString(3));
			}
			if(flag==0){
				System.out.println("NO DATA AVILABLE IN DB");
			}
//	        	SMS_Thread.logger.warn("Error : No Data found in DB for this date");
	        
			document.add(table);
			
			Paragraph paragraph1 = new Paragraph("Instant WheatherStation Data",
					new Font(FontFamily.TIMES_ROMAN, 20, Font.BOLD));
			paragraph1.setIndentationLeft(250);
			document.add(paragraph1);
			
			PdfPTable table1 = new PdfPTable(4);
			document.add(Chunk.SPACETABBING);
			PdfPCell tablecell1 = new PdfPCell();
			tablecell1.setHorizontalAlignment(table1.ALIGN_CENTER);
			tablecell1.setBorderColorTop(BaseColor.BLACK);

			// tablecell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tablecell1.setBorder(60);
			
			table1.addCell(rsmd1.getColumnName(1));
			table1.addCell(rsmd1.getColumnName(2));
			table1.addCell(rsmd1.getColumnName(3));
			table1.addCell(rsmd1.getColumnName(4));
		//	table1.addCell(rsmd1.getColumnName(4));

			table.setHeaderRows(1);
			int flag1=0;
			while (rs1.next()) {
				flag1=1;
				
				String Sid = rs1.getString(1);
				tablecell1 = new PdfPCell(new Phrase(Sid));
				table1.addCell(tablecell1);
				
				String TodayData = rs1.getString(2);
				tablecell1 = new PdfPCell(new Phrase(TodayData));
				table1.addCell(tablecell1);

				String InvData = rs1.getString(3);
				tablecell1 = new PdfPCell(new Phrase(InvData));
				table1.addCell(tablecell1);

				String Date = rs1.getString(4);
				tablecell1 = new PdfPCell(new Phrase(Date));
				table1.addCell(tablecell1);

				/*String CurrentDate = rs.getString(4);
				tablecell = new PdfPCell(new Phrase(CurrentDate));
				table.addCell(tablecell);*/

				System.out.println("*=========>>>>" + rs1.getString(1) + "$" + rs1.getString(2) + "#" + rs1.getString(3));
			}
			
			document.add(table1);
			rs1.close();
			
			document.close();
			stmt.close();
			System.out.println("Completed writing into text file");

		} catch (Exception e) {
			
			e.printStackTrace();
			
		}finally {
			try{
				rs.close();
	    		if(con!=null)
	    		  con.close();
	    		}catch(Exception e){}
		}

	}
	/*public static void main(String[] args) throws SQLException {
		Data d = new Data();
		d.getData();
		
	}*/
	

}
