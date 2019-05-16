package cn.dmdream.utils;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;


public class MailUtils {

	public static void sendMail(String email, String emailMsg)
			throws AddressException, MessagingException, GeneralSecurityException {
		/*// 1.创建一个程序与邮件服务器会话对象 Session

		Properties props = new Properties();
		// 设置发送的协议
		// props.setProperty("mail.transport.protocol", "SMTP");

		// 设置发送邮件的服务器
		// props.setProperty("mail.host", "smtp.126.com");
		// props.setProperty("mail.smtp.auth", "true");// 指定验证为true

		// 创建验证器
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				// 设置发送人的帐号和密码
				return new PasswordAuthentication("kuluseky@qq.com", "ntjpvjfdnsxjbbib");
			}
		};

		Session session = Session.getInstance(props, auth);

		// 2.创建一个Message，它相当于是邮件内容
		Message message = new MimeMessage(session);

		// 设置发送者
		message.setFrom(new InternetAddress("kuluseky@qq.com"));

		// 设置发送方式与接收者
		message.setRecipient(RecipientType.TO, new InternetAddress(email));

		// 设置邮件主题
		message.setSubject("用户激活");
		// message.setText("这是一封激活邮件，请<a href='#'>点击</a>");

		String url = "http://localhost:8080/store_v5/UserServlet?method=active&code=" + emailMsg;
		String content = "<h1>来自购物天堂的激活邮件!激活请点击以下链接!</h1><h3><a href='" + url + "'>" + url + "</a></h3>";
		// 设置邮件内容
		message.setContent(content, "text/html;charset=utf-8");

		// 3.创建 Transport用于将邮件发送
		Transport.send(message);*/

		/*
		 * 使用QQ邮箱的设置
		 */
		Properties props = new Properties();
		// 开启debug调试
		props.setProperty("mail.debug", "true");
		// 发送服务器需要身份验证
		props.setProperty("mail.smtp.auth", "true");
		// 设置邮件服务器主机名
		props.setProperty("mail.host", "smtp.qq.com");
		// 发送邮件协议名称
		props.setProperty("mail.transport.protocol", "smtp");

		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.socketFactory", sf);
		
		Session session = Session.getInstance(props);
        
        Message msg = new MimeMessage(session);
        msg.setSubject("星象小说的注册验证邮件");
		String url = "http://localhost:8080/ShareNovel/UserServlet?method=active&code=" + emailMsg;
		String content = "<h1>来自星象小说的激活邮件!激活请点击以下链接!</h1><h3><a href='" + url + "'>" + url + "</a></h3>";
		// 设置邮件内容
		msg.setContent(content, "text/html;charset=utf-8");
        
        msg.setFrom(new InternetAddress("kuluseky@qq.com"));
     
        Transport transport = session.getTransport();
        transport.connect("smtp.qq.com", "kuluseky@qq.com", "ntjpvjfdnsxjbbib");
     
        transport.sendMessage(msg, new Address[] { new InternetAddress(email) });
        transport.close();

	}
	
	public static void sendConfirmMail(String email, String emailMsg)
			throws AddressException, MessagingException, GeneralSecurityException {
		/*
		 * 使用QQ邮箱的设置
		 */
		Properties props = new Properties();
		// 开启debug调试
		props.setProperty("mail.debug", "true");
		// 发送服务器需要身份验证
		props.setProperty("mail.smtp.auth", "true");
		// 设置邮件服务器主机名
		props.setProperty("mail.host", "smtp.qq.com");
		// 发送邮件协议名称
		props.setProperty("mail.transport.protocol", "smtp");

		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.socketFactory", sf);
		
		Session session = Session.getInstance(props);
        
        Message msg = new MimeMessage(session);
        msg.setSubject("星象小说的找回密码邮件");
		String url = "http://localhost:8080/ShareNovel/findPassword.do?method=confirm&code=" + emailMsg;
		String content = "<h1>来自星象小说的找回密码邮件!确认请点击以下链接!</h1><h3><a href='" + url + "'>" + url + "</a></h3>";
		// 设置邮件内容
		msg.setContent(content, "text/html;charset=utf-8");
        
        msg.setFrom(new InternetAddress("1289127381@qq.com"));
     
        Transport transport = session.getTransport();
        transport.connect("smtp.qq.com", "1289127381@qq.com", "ucrjifvwurpwgeic");
     
        transport.sendMessage(msg, new Address[] { new InternetAddress(email) });
        transport.close();
	}

	public static void main(String[] args) throws AddressException, MessagingException, GeneralSecurityException {
		MailUtils.sendMail("kuluseky@icloud.com", "123456789");
	}
}
