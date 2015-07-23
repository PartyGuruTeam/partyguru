package gui;

import java.awt.BorderLayout;
import java.io.File;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import db.Database;
import gui.gelegenheiten.GelegenheitenTabelle;
import gui.kontakte.Gaesteliste;
import gui.kontakte.PersonenTabelle;
import gui.material.MaterialTabelle;
import gui.material.Mitbringliste;
import gui.putz.PutzListe;
import gui.putz.PutzTemplate;

/**
 * 
 * @author Bastian
 *
 */
public class MutterLayout extends JPanel 
{
	private static final long serialVersionUID = 1L;

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
	private PutzListe mPutzliste;

	/**
	 * Konstruktor von MutterLayout. Initialisiert die verschiedenen Views des Programms.
	 */
	public MutterLayout()
	{		
		this.setLayout(new BorderLayout());

		String path = JOptionPane.showInputDialog("Bitte Pfad zur Datenbank eingeben", System.getProperty("user.home")+"/party");
		try {
			db = new Database(path);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Auf die Datenbank kann nicht zugegriffen werden!");
			System.exit(1);
		}

		selectDB();

		mTabs = new JTabbedPane(JTabbedPane.TOP);
		this.add(mTabs, BorderLayout.CENTER);

		try {
			mPersonen = new PersonenTabelle(db, this);
			mTabs.add(mPersonen, "Personen");
			mMaterial = new MaterialTabelle(db, this);
			mTabs.add(mMaterial, "Material");
			mGaesteliste = new Gaesteliste(db, this);
			mTabs.add(mGaesteliste, "G�steliste");
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

	private void selectDB()
	{
		SelectParty selection=null;
		try {
			selection = new SelectParty(db, this);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		mPID = -1;
		while(mPID==-1)
		{
			if(!selection.isVisible())
				System.exit(1); //TODO anders l�sen
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
