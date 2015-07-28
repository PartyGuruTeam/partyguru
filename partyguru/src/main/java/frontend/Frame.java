package frontend;

import java.awt.Color;

import javax.swing.JFrame;


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
		this.setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setBackground(MutterLayout.grau);
	}

	/**
	 * Fenster aufrufen
	 * @param args
	 */
	public static void main(String[] args) {
		new Frame();
	}

}
