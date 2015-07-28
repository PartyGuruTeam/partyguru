package frontend.forms;

import frontend.MutterLayout;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import backend.Database;

public class Generierung extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static final String Color = null;

	/**
	 *  @author Franzi
					 	mVerf übersetzt die Verfügbarkeit in eine Zahl für die DB
						1 --> nicht verfügbar
						2 --> verfügbar für w
						3 --> verfügbar für m
						0 --> wurde nicht eingetragen
	 */


	//Arrays für die "verfügbaren" personen mit Personen ID gelistet
	ArrayList<String> mMannWillMann = new ArrayList<String>();
	ArrayList<String> mFrauWillMann = new ArrayList<String>();
	ArrayList<String> mMannWillFrau = new ArrayList<String>();
	ArrayList<String> mFrauWillFrau = new ArrayList<String>();

	ArrayList<ArrayList<String>> mPaare;


	Database mDB;
	MutterLayout mParent;
	int mPID;

	ResultSet result;

	//Erstellung der Buttons für unten
	private JButton mMixButton;

	Vector<JPanel> JPanelArray = new Vector<JPanel>();


	public Generierung(Database db, MutterLayout parent) throws SQLException {


		//Erstelle die GUI
		//super();
		mMixButton = new JButton("Neu mischen");
		mMixButton.setBackground(MutterLayout.mittelrosa);
		mMixButton.setForeground(MutterLayout.schriftrosa);
		mMixButton.setFont(MutterLayout.knoepfe);
		mPaare = new ArrayList<ArrayList<String>>();

		Border border = this.getBorder();
		Border margin = new EmptyBorder(10, 10, 10, 10);
		this.setBorder(new CompoundBorder(border, margin));

		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[] {86, 86, 0};
		gbl.rowHeights = new int[] {50, 20, 20, 20, 20, 20, 20, 30, Double.BYTES };
		//gbl.columnWeights = new double[] { 0.0, 1.0,0.0, 0.0, Double.MIN_VALUE };
		//gbl.rowWeights = new double[] { 0.0, 0.0,  0.0, 0.0, 0.0, 0.0, 0.0, 0.0,Double.MIN_VALUE };
		this.setLayout(gbl);
		this.setBackground(MutterLayout.hellrosa);
		
		mDB = db;

		//mPID = 3;
		//mPID = parent.getPID();

		print();
	}

	public void print()
	{
		mMannWillMann = new ArrayList<String>();
		mFrauWillMann = new ArrayList<String>();
		mMannWillFrau = new ArrayList<String>();
		mFrauWillFrau = new ArrayList<String>();
		mPaare = new ArrayList<ArrayList<String>>();
		

		try {
			result = mDB.executeQuery("SELECT GESCHLECHT, NAME, VERFUEGBARKEIT FROM GAESTELISTE LEFT JOIN PERSONEN ON GAESTELISTE.PERSID = PERSONEN.PERSID");


			while (result.next())
			{

				String mVerf = result.getString("VERFUEGBARKEIT");
				String mGes = result.getString("GESCHLECHT");
				String mName = result.getString("NAME");


				if( mGes.contains("m"))

				{

					if (mVerf.contains("2"))
					{
						mMannWillFrau.add(mName);	
					}

					else if (mVerf.contains("3"))
					{
						mMannWillMann.add(result.getString("NAME"));
					}
				}
				else if (mGes.contains("w"))
				{

					if (mVerf.contains("2"))
					{
						mFrauWillFrau.add(result.getString("NAME"));
					}
					else if (mVerf.contains("3"))
					{
						mFrauWillMann.add(result.getString("NAME"));
					}
				}
				/*else
				JOptionPane.showMessageDialog(parent, "FEHLER IM sYSTEM");**/

			}

			//		System.out.println("Mann will Frau:");
			//		System.out.println(mMannWillFrau);
			//		System.out.println("schwul:");
			//		System.out.println(mMannWillMann);
			//		System.out.println("lesbisch:");
			//		System.out.println(mFrauWillFrau);
			//		System.out.println("Frau will Mann:");
			//		System.out.println(mFrauWillMann);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/**
		 * @author Franzi
		 * 
		 * Mapping der Personen für die Pärchengenerierung: 
		 * 
		 * TODO Mehrere Personen zusammen mappen
		 * 
		 */


		//Matching von Mann will Mann
		Collections.shuffle(mMannWillMann);
		int k = mMannWillMann.size();
		int aRestMann = k;

		for (int i = 0; i < mMannWillMann.size(); i = i+2)
		{
			int j = i + 1;
			ArrayList<String> aPaar = new ArrayList<String>();

			if(aRestMann > 1){
				aPaar.add(mMannWillMann.get(i));
				aPaar.add(mMannWillMann.get(j));
				aRestMann = aRestMann-2;
			}

			else if(aRestMann == 1)
			{
				aPaar.add(mMannWillMann.get(i));
				aPaar.add("Traummann");
				aRestMann = aRestMann-1;
			}

			mPaare.add(aPaar);
			//			System.out.println(aPaar + " an der Stelle " + i);
		}


		//Matching von Frau will Frau
		Collections.shuffle(mFrauWillFrau);
		k = mFrauWillFrau.size();
		int aRestFrauen = k;

		for (int i = 0; i < k; i = i+2)
		{
			int j = i + 1;
			ArrayList<String> aPaar = new ArrayList<String>();

			if(aRestFrauen > 1){
				aPaar.add(mFrauWillFrau.get(i));
				aPaar.add(mFrauWillFrau.get(j));
				aRestFrauen = aRestFrauen - 2;
			}
			else if (aRestFrauen == 1){
				aPaar.add(mFrauWillFrau.get(i));
				aPaar.add("Traumfrau");
				aRestFrauen = aRestFrauen-1;
			}
			mPaare.add(aPaar);
			//			System.out.println(aPaar + " an der Stelle " + i);

		}


		//Matching von normalen Pärchen
		Collections.shuffle(mMannWillFrau);
		k = mMannWillFrau.size();

		Collections.shuffle(mFrauWillMann);
		int kk = mFrauWillMann.size();
		int aRest = k - kk;			//z > 0: mehr Männer mit Anzahl, Zahl = 0: gleich viele, Zahl < 0: mehr Frauen
		int aUeberbleibsel = Math.abs(k - kk);
		int aAnzahlPaare = (((k + kk) - Math.abs(aRest)) / 2);
		int aRestPaare = aAnzahlPaare;

		for (int i = 0; i < (aAnzahlPaare + aUeberbleibsel); i++)
		{
			ArrayList<String> aPaar = new ArrayList<String>();

			if (aRestPaare > 0)
			{
				aPaar.add(mMannWillFrau.get(i));
				aPaar.add(mFrauWillMann.get(i));
				mPaare.add(aPaar);
			}
			else 
			{
				if(aRest < 0){ 			//mehr Frauen

					aPaar.add(mFrauWillMann.get(i));
					aPaar.add("Traummann");
					mPaare.add(aPaar);
				}
				else if(aRest > 0)		//mehr Männer
				{
					aPaar.add(mMannWillFrau.get(i));
					aPaar.add("Traumfrau");
					mPaare.add(aPaar);	
				}
			}
			//			System.out.println(aPaar + " an der Stelle " + i);
			aRestPaare = aAnzahlPaare - (i +1);
		}

		this.removeAll();
		
		//Erstelle Kopfzeile als Header
		addLabel("Diese Pärchen wurden für deine Party generiert. Wenn du noch einmal neu mischen möchtest,"
				+ " klicke den Button ganz unten", 0, this);


		//Befülle die JLabels mit den Pärchen und die JPanels mit den JLabels
		int yPosNEU = 1;
		for (int i = 0; i < mPaare.size();i++)
		{
			String aName1 = mPaare.get(i).get(0);
			//			System.out.println("NAME1:" + i + aName1);
			String aName2 = mPaare.get(i).get(1);
			//			System.out.println("NAME1:" + i + aName2);
			//String aName3 = mPaare.get(i).get(2);
			//			System.out.println("NAME1:" + i + aName3);


			addCouplePanel(aName1, aName2, yPosNEU, this, i+1);
			yPosNEU = yPosNEU + 1;
		}

		//Add JButton für das "Neu mischen"
		addButttons(yPosNEU, this);
		this.revalidate();
		this.repaint();
	}



	/**
	 * GUI-Methoden zur Erstellung von
	 * - JPanels für die kopfzeile
	 * - JPanel mit eingelagerten JLabels für die Pärchen
	 * - JButtons für die Action
	 * 
	 */
	private void addCouplePanel( String labelTextT1, String labelTextT2, int yPos, Container formular, int aZahl) {

		JLabel typ1 = new JLabel(labelTextT1 + " & ");
		JLabel typ2 = new JLabel(labelTextT2);
		//		JLabel typ3 = new JLabel(labelTextT3);
		JPanel box = new JPanel();
		
		LineBorder border = new LineBorder(MutterLayout.dunkelrosa, 1, true);
		TitledBorder tborder = new TitledBorder(border, "Pärchen " + aZahl, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, MutterLayout.titel, MutterLayout.dunkelrosa );
		box.setLayout(new BorderLayout());
		
		box.setBorder(tborder);
		typ1.setBackground(MutterLayout.hellrosa);
		typ2.setBackground(MutterLayout.hellrosa);
		box.setBackground(MutterLayout.hellrosa);


		GridBagConstraints gbcBox = new GridBagConstraints();
		gbcBox.fill = GridBagConstraints.BOTH;
		gbcBox.insets = new Insets(0, 0, 5, 5);
		gbcBox.gridx = 0;
		gbcBox.gridy = yPos;
		formular.add(box, gbcBox);
		box.add(typ1, BorderLayout.WEST);
		box.add(typ2, BorderLayout.CENTER);
		//		box.add(typ3);

		JPanelArray.add(box);

	}


	private void addLabel(String labelText, int yPos, Container formular ){

		JLabel kopfzeile = new JLabel(labelText);
		GridBagConstraints gbcLabel = new GridBagConstraints();
		gbcLabel.fill = GridBagConstraints.BOTH;
		gbcLabel.insets = new Insets(0, 0, 0, 0);
		gbcLabel.gridx = 0;
		gbcLabel.gridy = yPos;
		formular.add(kopfzeile, gbcLabel);

	}



	private void addButttons(int yPos, Container formular) {


		mMixButton.addActionListener(this);

		GridBagConstraints gbcButton = new GridBagConstraints();
		gbcButton.fill = GridBagConstraints.BOTH;
		gbcButton.insets = new Insets(0, 0, 0, 0);
		gbcButton.gridx = 0;
		gbcButton.gridy = yPos + 1;
		formular.add(mMixButton, gbcButton);

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(mMixButton))
		{
			//Methode, die den Datensatz in die Datenbank speichert
			print();
		}
		else 
		{

		}

	}
	
	
}