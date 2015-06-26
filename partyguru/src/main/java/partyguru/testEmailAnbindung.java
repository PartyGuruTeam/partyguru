package partyguru;

public class testEmailAnbindung {

	public testEmailAnbindung() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String an = "felix.siegmund@gmail.com";
		String titel = "Test Titel";
		String nachricht = "Test Nachricht";
		
		
		System.out.println(sendMail.emailSenden(an, titel, nachricht));
	}

}
