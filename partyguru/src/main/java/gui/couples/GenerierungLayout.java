package gui.couples;

import java.util.ArrayList;

import javax.swing.JTabbedPane;

public class GenerierungLayout extends JTabbedPane {

	//TODO Einf�gen Verf�gbarkeit 4 Stati
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Arrays f�r die "verf�gbaren" personen mit Personen ID gelistet
	ArrayList suchtMann;
	ArrayList suchtFrau;
	ArrayList suchtBeides;			//Oder Zuordnung der "Beide"-Kandidaten in beide Arrays
	
	
	public GenerierungLayout(){
		
		suchtMann = new ArrayList<>();
		suchtFrau = new ArrayList<>();
		suchtBeides = new ArrayList<>();
		
		
		
		
	}
	
	
	
}
