package gui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Klasse, die das Hauptfenster beinhaltet
 * @author Bastian
 *
 */
public class Frame extends JFrame 
{
	private static final long serialVersionUID = 1L;
	
	private JMenuBar mMenuBar;
	
	private JMenu mMenuDatei;
	private JMenuItem mItemNeuesProj;
	private JMenuItem mItemOeffnen;
	
	private JMenu mMenuBearbeiten;
	
	private MutterLayout mMutter;
	
	/**
	 * Initialisiere Fenster
	 */
	public Frame()
	{
		long time = System.currentTimeMillis();
		
		mMenuBar = new JMenuBar();
		this.setJMenuBar(mMenuBar);
		
		mMenuDatei = new JMenu("Datei");
		mMenuBar.add(mMenuDatei);
		
		mItemNeuesProj = new JMenuItem("Neue Party...");
		mMenuDatei.add(mItemNeuesProj);
		
		mItemOeffnen = new JMenuItem("Party öffnen...");
		mMenuDatei.add(mItemOeffnen);
		
		mMenuBearbeiten = new JMenu("Bearbeiten");
		mMenuBar.add(mMenuBearbeiten);
		
		mMutter = new MutterLayout();
		this.add(mMutter);
		this.setSize(700, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		System.out.println("Start-up Time: "+(double)(System.currentTimeMillis()-time)/1000);
	}
	
	/**
	 * Fenster aufrufen
	 * @param args
	 */
	public static void main(String[] args) {
		new Frame();
	}

}
