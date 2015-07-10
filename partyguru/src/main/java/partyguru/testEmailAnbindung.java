package partyguru;

public class testEmailAnbindung {

	public testEmailAnbindung() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String an = "derpartyguru@gmail.com";
		String titel = "Test Titel";
		String nachricht = "This is a test <b>HOWTO<b>";
		
		
		System.out.println(sendMail.emailSenden(an, titel, nachricht));
		
	}

}