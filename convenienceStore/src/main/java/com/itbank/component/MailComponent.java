package com.itbank.component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Component
public class MailComponent {

	
	 private final String host = "smtp.gmail.com";
	   private final int port = 587;
	   private String serverId = "kgitbankprojectgogo";		//	이 계정으로 로그인해서 메일을 보낼것임 
	   private String serverPw = "lhje mkpb sdwb fjfe";		//	2단계 인증 비번
	   
	   private Properties props;
	   
	   //	@Autowired 가 자동으로 스프링 빈 연결하듯이
	   //	@Value는 자동으로 자원(파일)을 연결한다
	   //	org.springframework.core.io.Resource
	   //	classpath : "src/main/java"		or		"src/main/resources
	   @Value("classpath:mailForm.html")
	   private Resource mailForm;
	   
	   @PostConstruct
	   private void init() {
	      props = new Properties();
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.prot", port);
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.ssl.enable", "true");
	      props.put("mail.smtp.true", host);
	   }

	   public int sendMimeMessage(HashMap<String, String> param) {

		      // 1) 메일 서버 인증 (접속에 필요하다)
		      Session mailSession = Session.getDefaultInstance(props, new Authenticator() {
		         String un = serverId;
		         String pw = serverPw;
		         
		         @Override
		         protected PasswordAuthentication getPasswordAuthentication() {
		            return new PasswordAuthentication(un, pw);
		         }
		      });
		      
		      mailSession.setDebug(true);      // 메일 전송 과정을 콘솔창에 출력한다
		      
		      
		      // 2) 보낼 메세지 작성
		      Message message = new MimeMessage(mailSession);
		      String address = param.get("address");
		      String subject = param.get("subject");
		      String content = param.get("content");
		      
		      try {
		         message.setFrom(new InternetAddress(serverId + "@gmail.com"));
		         message.setRecipient(Message.RecipientType.TO, new InternetAddress(address));   // 받는사람
		         message.setSubject(subject);   // 제목
		         
		         
		         String tag = "";
		         Scanner sc = new Scanner(mailForm.getFile());
		         
		         while(sc.hasNextLine()) {
		        	 tag += sc.nextLine();
		         }
		         sc.close();
		         
		         content = String.format(tag, content);
		         
		         message.setContent(content, "text/html; charset=utf-8");	//	태그 포함 내용
		         
		         Transport.send(message);   
		         return 1;
		         
		      } catch (MessagingException | IOException e) {
		         e.printStackTrace();
		         return 0;
		      }

	   }
	   
	   
}
