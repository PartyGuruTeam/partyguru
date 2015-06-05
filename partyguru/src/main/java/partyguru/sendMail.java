package partyguru;



	import java.util.Properties;

	import javax.mail.Message;
	import javax.mail.MessagingException;
	import javax.mail.PasswordAuthentication;
	import javax.mail.Session;
	import javax.mail.Transport;
	import javax.mail.internet.InternetAddress;
	import javax.mail.internet.MimeMessage;
	 
	public class sendMail {
	 
		

	public static int emailSenden(String an, String titel, String nachricht){
		
		final String username = "derpartyguru@gmail.com";
		final String password = "reichwald";
		int erfolg = 1;

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("derpartyguru@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(an));
			message.setSubject(titel);
			message.setText(nachricht);

			Transport.send(message);

			

		} catch (MessagingException e) {
			erfolg = 0;
						
		} 
		return erfolg;
	}

	//wichtig javax.mail.jar importieren
	//Aufruf an EmailMethode 
	//String an = "testmail@gmail.com";
	//String titel = "Test Titel";
	//String nachricht = "Test Nachricht";
	//
	//sendMail.emailSenden(an, titel, nachricht);



	}



