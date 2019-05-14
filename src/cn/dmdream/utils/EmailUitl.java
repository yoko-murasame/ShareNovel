package cn.dmdream.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class EmailUitl {
	private static Properties prop = new Properties();
	private static String senderEmail;
	private static String senderAuthCode;
	static {
		prop = new Properties();
		try {
			String path = EmailUitl.class.getClassLoader().getResource("").toURI().getPath();
			System.out.println(path);
			prop.load(new FileReader(path+"email.properties"));
			senderEmail=prop.getProperty("senderEmail");
			senderAuthCode=prop.getProperty("senderAuthCode");
			prop.remove("senderAuthCode");
			prop.remove("sendEmail");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static int sendEmial(String to,String msg) {
		Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail,senderAuthCode);
            }
        });
        Message message = new MimeMessage(session);
        //设置发件人
        try {
	            message.setFrom(new InternetAddress(senderEmail));
	
	        //设置收件人
	        message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));//收件人
	        //设置主题
	        message.setSubject("验证码");
	        //设置邮件正文  第二个参数是邮件发送的类型
	        message.setContent(msg,"text/html;charset=UTF-8");
	        //发送一封邮件
	        Transport transport = session.getTransport();
	        transport.connect(senderEmail,senderAuthCode);
	        Transport.send(message);
	        transport.close();
	    }catch (MessagingException e) {
	    	e.printStackTrace();
	    	return -1;
		}
		return 1;	
	}
	public static int AuthUserEmail(String to,String url) {
		Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail,senderAuthCode);
            }
        });
        Message message = new MimeMessage(session);
        //设置发件人
        try {
	            message.setFrom(new InternetAddress(senderEmail));
	
	        //设置收件人
	        message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));//收件人
	        //设置主题
	        message.setSubject("欢迎使用xxx小说网,请继续以下步骤");
	        //设置邮件正文  第二个参数是邮件发送的类型
	        String txt="<p>点击下面链接,激活邮箱.</p><a href='"+url+"'>"+url+"</a>";
	        message.setContent(txt,"text/html;charset=UTF-8");
	        //发送一封邮件
	        Transport transport = session.getTransport();
	        transport.connect(senderEmail,senderAuthCode);
	        Transport.send(message);
	        transport.close();
	    }catch (MessagingException e) {
	    	e.printStackTrace();
	    	return -1;
		}
		return 1;	
	}
}
