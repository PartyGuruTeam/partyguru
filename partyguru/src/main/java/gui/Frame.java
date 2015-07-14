package gui;

import java.awt.Color;

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
		super("Party Guru");
		mMenuBar = new JMenuBar();
		this.setJMenuBar(mMenuBar);

		mMenuDatei = new JMenu("Datei");
		mMenuBar.add(mMenuDatei);

		mItemNeuesProj = new JMenuItem("Neue Party...");
		mMenuDatei.add(mItemNeuesProj);

		mItemOeffnen = new JMenuItem("Party �ffnen...");
		mMenuDatei.add(mItemOeffnen);

		mMenuBearbeiten = new JMenu("Bearbeiten");
		mMenuBar.add(mMenuBearbeiten);

		mMutter = new MutterLayout();
		this.add(mMutter);
		
		
		this.setSize(700, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	/**
	 * Fenster aufrufen
	 * @param args
	 */
	public static void main(String[] args) {
		new Frame();
	}

}
