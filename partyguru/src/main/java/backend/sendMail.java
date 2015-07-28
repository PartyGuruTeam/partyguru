package backend;



import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 * Klasse die dem Versenden von Emails über einen SMTP Server dient.
 * Übergeben werden müssen die String Felder: an email Adresse, Titel der Nachricht, Nachricht in HTML.
 * Die Funktion gibt bei Erfolg eine 1 zurück, bei einem Fehler eine 0
 * @author siegmunf
 *
 */

public class sendMail {

	static String password = "password";

	public static int emailSenden(String an, String titel, String nachricht){

		
		
		final String username = "derpartyguru@gmail.com";

		if (password == "password"){
			
			JFrame frame = new JFrame("PW Abfrage");
		    password = JOptionPane.showInputDialog(
		        frame, 
		        "Bitte Passwort eingeben: ", 
		        "Email Passwort wird benötigt!", 
		        JOptionPane.QUESTION_MESSAGE
		    );
		}
		
		
		
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

			message.setContent(nachricht, "text/html; charset=ISO-8859-1");
	         

			Transport.send(message);

			
			


		} catch (MessagingException e) {
			erfolg = 0;
			password = "password";

		} 
		return erfolg;
	}


	


}

