package partyguru;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Component;
import java.awt.Dimension;

public class Gui extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 664, 315);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);
		
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
		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(600, 700));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(600, 700));
		contentPane.add(panel, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(600, 700));
		panel_1.setMinimumSize(new Dimension(100, 20));
		panel.add(panel_1);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setPreferredSize(new Dimension(600, 500));
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		panel_1.add(tabbedPane);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setPreferredSize(new Dimension(500, 500));
		tabbedPane_1.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane_1.setAutoscrolls(true);
		tabbedPane.addTab("Party-Stammdaten", null, tabbedPane_1, null);
		
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_2.setPreferredSize(new Dimension(500, 500));
		tabbedPane_2.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane_1.addTab("Ort/Datum/Uhrzeit/Motto", null, tabbedPane_2, null);
		
		JTabbedPane tabbedPane_3 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_3.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane_1.addTab("Schlaf-/Mitfahrgelegenheiten", null, tabbedPane_3, null);
		
		JTabbedPane tabbedPane_12 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Materialmanagement", null, tabbedPane_12, null);
		
		JTabbedPane tabbedPane_13 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_12.addTab("Speisen", null, tabbedPane_13, null);
		
		JTabbedPane tabbedPane_14 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_12.addTab("Getr\u00E4nke", null, tabbedPane_14, null);
		
		JTabbedPane tabbedPane_15 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_12.addTab("Partyutensilien", null, tabbedPane_15, null);
		
		JTabbedPane tabbedPane_4 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_4.setAlignmentX(Component.LEFT_ALIGNMENT);
		tabbedPane.addTab("Kontaktmanagement", null, tabbedPane_4, null);
		
		JTabbedPane tabbedPane_6 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_4.addTab("G\u00E4steliste", null, tabbedPane_6, null);
		
		JTabbedPane tabbedPane_5 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_4.addTab("Mitbringliste", null, tabbedPane_5, null);
		
		JTabbedPane tabbedPane_7 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_4.addTab("P\u00E4rchengenerierung", null, tabbedPane_7, null);
		
		JTabbedPane tabbedPane_8 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_4.addTab("Partyfeedback", null, tabbedPane_8, null);
		
		JTabbedPane tabbedPane_9 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Planer-Support", null, tabbedPane_9, null);
		
		JTabbedPane tabbedPane_10 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_9.addTab("Putzplan", null, tabbedPane_10, null);
		
		JTabbedPane tabbedPane_11 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_9.addTab("Taskliste", null, tabbedPane_11, null);
		
		JTabbedPane tabbedPane_16 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Zusammenfassung", null, tabbedPane_16, null);
	}

}
