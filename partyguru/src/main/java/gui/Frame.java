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

	private MutterLayout mMutter;

	/**
	 * Initialisiere Fenster
	 */
	public Frame()
	{
		super("Party Guru");

		mMutter = new MutterLayout();
		this.add(mMutter);
		
		
		this.setSize(1024, 700);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Fenster aufrufen
	 * @param args
	 */
	public static void main(String[] args) {
		new Frame();
	}

}
