package gui.couples;

import gui.MutterLayout;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import db.Database;

public class Generierung extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static final String Color = null;

	/**
	 *  @author Franzi
					 	mVerf �bersetzt die Verf�gbarkeit in eine Zahl f�r die DB
						1 --> nicht verf�gbar
						2 --> verf�gbar f�r w
						3 --> verf�gbar f�r m
						0 --> wurde nicht eingetragen
	 */


	//Arrays f�r die "verf�gbaren" personen mit Personen ID gelistet
	ArrayList<String> mMannWillMann = new ArrayList<String>();
	ArrayList<String> mFrauWillMann = new ArrayList<String>();
	ArrayList<String> mMannWillFrau = new ArrayList<String>();
	ArrayList<String> mFrauWillFrau = new ArrayList<String>();

	ArrayList<ArrayList<String>> mPaare;


	Database mDB;
	MutterLayout mParent;
	int mPID;

	ResultSet result;

	//Erstellung der Buttons f�r unten
	private JButton mMixButton;

	Vector<JPanel> JPanelArray = new Vector<JPanel>();


	public Generierung(Database db, MutterLayout parent) throws SQLException {


		//Erstelle die GUI
		//super();
		mMixButton = new JButton("Neu mischen");
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
		 * Mapping der Personen f�r die P�rchengenerierung: 
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


		//Matching von normalen P�rchen
		Collections.shuffle(mMannWillFrau);
		k = mMannWillFrau.size();

		Collections.shuffle(mFrauWillMann);
		int kk = mFrauWillMann.size();
		int aRest = k - kk;			//z > 0: mehr M�nner mit Anzahl, Zahl = 0: gleich viele, Zahl < 0: mehr Frauen
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
				else if(aRest > 0)		//mehr M�nner
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
		addLabel("Diese P�rchen wurden f�r deine Party generiert. Wenn du noch einmal neu mischen m�chtest,"
				+ " klicke den Button ganz unten", 0, this);


		//Bef�lle die JLabels mit den P�rchen und die JPanels mit den JLabels
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

		//Add JButton f�r das "Neu mischen"
		addButttons(yPosNEU, this);
		this.revalidate();
		this.repaint();
	}



	/**
	 * GUI-Methoden zur Erstellung von
	 * - JPanels f�r die kopfzeile
	 * - JPanel mit eingelagerten JLabels f�r die P�rchen
	 * - JButtons f�r die Action
	 * 
	 */
	private void addCouplePanel( String labelTextT1, String labelTextT2, int yPos, Container formular, int aZahl) {

		JLabel typ1 = new JLabel(labelTextT1 + " & ");
		JLabel typ2 = new JLabel(labelTextT2);
		//		JLabel typ3 = new JLabel(labelTextT3);
		JPanel box = new JPanel();
		box.setLayout(new BorderLayout());
		box.setBorder(BorderFactory.createTitledBorder("P�rchen " + aZahl));


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
		gbcButton.gridx = 1;
		gbcButton.gridy = yPos;
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


	//public abstract void updateData();


	/*public abstract void refreshTable();

	/**VON BASTI �BERNOMMEN
	 * Soll von refreshTable() aufgerufen werden. Stellt �bergebenes Resultset dar.
	 * @param rs
	 * @throws SQLException
	 */

	/*public void refreshTable(ResultSet rs) throws SQLException
	{
		ResultSetMetaData md = null;
		Vector<Vector<Object>> tabelle = new Vector<Vector<Object>>();
		Vector<String> columnNames = new Vector<String>();

		try {
			md = rs.getMetaData();
			for(int i=1; i<=md.getColumnCount(); i++)
			{
				columnNames.add(md.getColumnName(i));
			} 	
		} catch (SQLException e) {
			e.printStackTrace();
		}

		while(rs.next())
		{
			Vector<Object> row = new Vector<Object>(columnNames.size());
			for(int i=1; i<=md.getColumnCount(); i++)
			{
				row.addElement(rs.getObject(i));
			}
			tabelle.add(row);
		}

	}*/


}