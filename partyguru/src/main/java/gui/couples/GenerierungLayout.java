package gui.couples;

import java.util.ArrayList;

import javax.swing.JTabbedPane;

public class GenerierungLayout extends JTabbedPane {

	//TODO Einfügen Verfügbarkeit 4 Stati
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Arrays für die "verfügbaren" personen mit Personen ID gelistet
	ArrayList suchtMann;
	ArrayList suchtFrau;
	ArrayList suchtBeides;			//Oder Zuordnung der "Beide"-Kandidaten in beide Arrays
	
	
	public GenerierungLayout(){
		
		suchtMann = new ArrayList<>();
		suchtFrau = new ArrayList<>();
		suchtBeides = new ArrayList<>();
		
		
		
		
	}
	
	
	
}
