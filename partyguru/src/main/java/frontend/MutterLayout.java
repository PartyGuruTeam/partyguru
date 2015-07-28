package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import backend.Database;
import frontend.forms.Generierung;
import frontend.forms.PStammdaten;
import frontend.tables.Gaesteliste;
import frontend.tables.GelegenheitenTabelle;
import frontend.tables.MaterialTabelle;
import frontend.tables.Mitbringliste;
import frontend.tables.PersonenTabelle;
import frontend.tables.PutzListe;
import frontend.tables.PutzTemplate;


/**
 * Hauptpanel, in dem alle Tabs eingebunden werden.
 * @author Bastian
 *
 */
public class MutterLayout extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	public static final Color dunkelrosa = new Color(205, 16, 118);
	public static final Color schriftrosa = new Color(139, 10, 80);
	public static final Color mittelrosa = new Color(255, 105, 180);
	public static final Color hellrosa = new Color(255, 228, 225);
	public static final Color schriftweiﬂ = new Color(255,255,255);
	public static final Color grau = Color.LIGHT_GRAY;
	
	public static final Font titel = new Font ("Comic Sans MS", Font.BOLD, 12);
	public static final Font knoepfe = new Font ("Comic Sans MS", Font.PLAIN, 14);
	
	private Database db;
	int mPID;
	JTabbedPane mTabs;
	
	private PersonenTabelle mPersonen;
	private Gaesteliste mGaesteliste;
	private MaterialTabelle mMaterial;
	private Mitbringliste mMitbringliste;
	private GelegenheitenTabelle mGelegenheiten;
	private PutzTemplate mPutzen;
	private PStammdaten mStammdaten;
	private Generierung mGenerierung;	
	private PutzListe mPutzliste;
	private Tasks mTasks;

	/**
	 * Konstruktor von MutterLayout. Initialisiert die verschiedenen Views des Programms.
	 */
	public MutterLayout()
	{		
		this.setLayout(new BorderLayout());
		this.setBackground(hellrosa);

		String path = JOptionPane.showInputDialog("Bitte Pfad zur Datenbank eingeben", System.getProperty("user.home")+"/party");
		try {
			db = new Database(path);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Auf die Datenbank kann nicht zugegriffen werden!");
			System.exit(1);
		}
		if(path==null)
		{
			System.exit(0);
		}
		selectParty();

		mTabs = new JTabbedPane(JTabbedPane.TOP);
		mTabs.setBackground(mittelrosa);
		mTabs.setForeground(schriftrosa);
		mTabs.setFont(MutterLayout.knoepfe);
		this.add(mTabs, BorderLayout.CENTER);

		try {
			mPersonen = new PersonenTabelle(db, this);
			mTabs.add(mPersonen, "Personen");
			mMaterial = new MaterialTabelle(db, this);
			mTabs.add(mMaterial, "Material");
			mGaesteliste = new Gaesteliste(db, this);
			mTabs.add(mGaesteliste, "G‰steliste");
			mPutzen = new PutzTemplate(db, this);
			mTabs.add(mPutzen, "Putzliste");
			mPutzliste = new PutzListe(db, this);
			mTabs.add(mPutzliste, "Putzplan");
			mMitbringliste = new Mitbringliste(db, this);
			mTabs.add(mMitbringliste, "Mitbringliste");
			mGelegenheiten = new GelegenheitenTabelle(db, this);
			mTabs.add(mGelegenheiten, "Gelegenheiten");
			mStammdaten = new PStammdaten(db, this);
			mTabs.add(mStammdaten, "Stammdaten");
			mGenerierung = new Generierung(db, this);
			mTabs.add(mGenerierung, "P‰rchengenerierung");
			
			mTasks = new Tasks(db, this);
			this.add(mTasks, BorderLayout.EAST);

		} catch (SQLException e) {
			e.printStackTrace();
		}			
		mTabs.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				mPersonen.printTable();
				mMaterial.printTable();
				mGaesteliste.printTable();
				mPutzen.printTable();
				mPutzliste.printTable();
				mMitbringliste.printTable();
				mGelegenheiten.printTable();
			}
		});
	}
	
	/**
	 * Dialog wird aufgerufen, um Party auszuw‰hlen.
	 */
	private void selectParty()
	{
		SelectParty selection=null;
		try {
			selection = new SelectParty(db, this);
		} catch (SQLException e1) {

		}
		mPID = -1;
		while(mPID==-1)
		{
			if(!selection.isVisible())
				System.exit(1);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public int getPID(){
		return mPID;
		
	}
	
}
