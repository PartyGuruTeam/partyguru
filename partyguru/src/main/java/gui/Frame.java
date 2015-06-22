package gui;

import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Frame extends JFrame 
{
	private static final long serialVersionUID = 1L;
	
	private JMenuBar mMenuBar;
	JMenu mDatei;
	
	private MutterLayout mMutter;
	
	/**
	 * Initialisiere Fenster
	 */
	public Frame()
	{
		mMenuBar = new JMenuBar();
		this.setJMenuBar(mMenuBar);
		
		mDatei = new JMenu("Datei");
		mMenuBar.add(mDatei);
		/*TODO 
		JMenuItem mntmNeuesProjekt = new JMenuItem("Neues Projekt");
		mnDatei.add(mntmNeuesProjekt);
		
		JMenuItem mntmffnen = new JMenuItem("\u00D6ffnen");
		mnDatei.add(mntmffnen);
		
		JMenuItem mntmSpeichern = new JMenuItem("Speichern");
		mnDatei.add(mntmSpeichern);
		
		JMenuItem mntmSpeichernUnter = new JMenuItem("Speichern Unter");
		mnDatei.add(mntmSpeichernUnter);
		
		JMenuItem mntmSchlieen = new JMenuItem("Schlie\u00DFen");
		mnDatei.add(mntmSchlieen);
		
		JMenu mnBearbeiten = new JMenu("Bearbeiten");
		menuBar.add(mnBearbeiten);
		*/
		long time = System.currentTimeMillis();
		try {
			mMutter = new MutterLayout();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.add(mMutter);
		this.setSize(500, 400);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		System.out.println("Start-up Time: "+(double)(System.currentTimeMillis()-time)/1000);
	}
	

	public static void main(String[] args) {
		new Frame();

	}

}
