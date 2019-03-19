package com.bridgelabz.fundoonotes.utility;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

	@Autowired
	private MailSender mailSender;

	public void sendEmail(String toEmail, String subject, String emailId) {
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setFrom("captain.india2112@gmail.com");
			msg.setTo("captain.india2112@gmail.com");
			String message = "Note has been shared to you.\n Check it out by logging in by clicking on the below url\n"
					+ "http://localhost:4200/login";
			msg.setText(message);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			mailSender.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
