package gui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import partyguru.sendMail;
import date.CalendarFunction;
import date.TimeFunction;
import db.Database;


public abstract  class FormularLayout extends JPanel implements ActionListener {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	//Erstellung der Buttons für unten
	private JButton mSpeichernButton;
	private JButton mEmailEinladung;
	private JButton mEmailFeedback;
	private JButton mEmailMitbring;
	//private JPanel mButtonPanel;
	
	Database mDB;
	int mPID;
	String aName;
	String aMotto;
	String aOrt;
	ResultSet rs;
	ResultSet result;
	ResultSet rsMat;
	
	String mAusgabe;
	Vector<JTextField> tfArray = new Vector<JTextField>();
	CalendarFunction mDate;
	TimeFunction mTime;

	public FormularLayout(Database db, MutterLayout parent)
	{	
		super();
		
		MutterLayout mParent;
		mPID = parent.getPID();
		mDB = db;
		
		
		
		mSpeichernButton = new JButton("Speichern");
		mEmailEinladung = new JButton("Einladungen versenden");
		mEmailFeedback = new JButton("Feedbackbogen versenden");
		mEmailMitbring = new JButton("Mitbringliste versenden");
		//mButtonPanel = new JPanel();

		Border border = this.getBorder();
		Border margin = new EmptyBorder(10, 10, 10, 10);
		this.setBorder(new CompoundBorder(border, margin));

		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[] {86, 86, 0};
		gbl.rowHeights = new int[] { 50, 20, 20, 20, 20, 20, 20, 20, 20, 20, 30 };
		gbl.columnWeights = new double[] { 0.0, 1.0,0.0, 0.0, Double.MIN_VALUE };
		gbl.rowWeights = new double[] { 0.0, 0.0,  0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 ,Double.MIN_VALUE };
		this.setLayout(gbl);

		

		addLabel("Gebe hier deine Party-Stammdaten ein!", 0, this);
		
		addLabelAndTextField("Partyname:", 1, this);
		addLabelAndTextField( "Gastgeber:*", 2, this);
		addLabelAndTextField( "Ort:*", 3, this);
		//addLabelAndTextField( "Datum:*", 4, this);
		addLabelAndDateField("Datum", 4, this);
		//addLabelAndTextField( "Startzeit:", 5, this);
		addLabelAndTimeField("Startzeit", 5, this);
		addLabelAndTextField("Motto:", 6, this);
		
		addButttons(7, this);
		addButttons(8, this);
		addButttons(9, this);
		addButttons(10, this);
	}
	private void addLabelAndTimeField(String labelText, int yPos, Container formular)
	{
		JLabel faxLabel = new JLabel(labelText);
		GridBagConstraints gbcLabel = new GridBagConstraints();
		gbcLabel.fill = GridBagConstraints.BOTH;
		gbcLabel.insets = new Insets(0, 0, 5, 5);
		gbcLabel.gridx = 0;
		gbcLabel.gridy = yPos;
		formular.add(faxLabel, gbcLabel);

		mTime = TimeFunction.createTime();
		GridBagConstraints gbltf = new GridBagConstraints();
		gbltf.fill = GridBagConstraints.BOTH;
		gbltf.insets = new Insets(0, 0, 5, 0);
		gbltf.gridx = 1;
		gbltf.gridy = yPos;
		
		formular.add(mTime, gbltf);
	}
	
	private void addLabelAndDateField(String labelText, int yPos, Container formular)
	{
		JLabel faxLabel = new JLabel(labelText);
		GridBagConstraints gbcLabel = new GridBagConstraints();
		gbcLabel.fill = GridBagConstraints.BOTH;
		gbcLabel.insets = new Insets(0, 0, 5, 5);
		gbcLabel.gridx = 0;
		gbcLabel.gridy = yPos;
		formular.add(faxLabel, gbcLabel);

		mDate = CalendarFunction.createDatePicker(); 
		GridBagConstraints gbltf = new GridBagConstraints();
		gbltf.fill = GridBagConstraints.BOTH;
		gbltf.insets = new Insets(0, 0, 5, 0);
		gbltf.gridx = 1;
		gbltf.gridy = yPos;
		
		formular.add(mDate, gbltf);
	}
	

	/**
	 * Dynamisch Erstellung der Zeilen mit Label und Textfeld: yPos steht für die Zeilenangaben, Container 
	 * ist das JPanel und trägt sie in das tf-Array ein (Array von Textfeldern)
	 * 
	 */
	private void addLabelAndTextField( String labelText, int yPos, Container formular) {

		JLabel faxLabel = new JLabel(labelText);
		GridBagConstraints gbcLabel = new GridBagConstraints();
		gbcLabel.fill = GridBagConstraints.BOTH;
		gbcLabel.insets = new Insets(0, 0, 5, 5);
		gbcLabel.gridx = 0;
		gbcLabel.gridy = yPos;
		formular.add(faxLabel, gbcLabel);

		JTextField textField = new JTextField();
		GridBagConstraints gbltf = new GridBagConstraints();
		gbltf.fill = GridBagConstraints.BOTH;
		gbltf.insets = new Insets(0, 0, 5, 0);
		gbltf.gridx = 1;
		gbltf.gridy = yPos;
		formular.add(textField, gbltf);
		textField.setColumns(10);
		
		tfArray.add(textField);
		
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
		
		
		if (yPos == 7)
		{
			mSpeichernButton.addActionListener((ActionListener) this);	
		}
		else if (yPos == 8)
		{
			mEmailEinladung.addActionListener((ActionListener) this);
		}
		else if (yPos == 9)
		{
			mEmailFeedback.addActionListener((ActionListener) this);
		}
		else if (yPos == 10)
		{
			mEmailMitbring.addActionListener((ActionListener) this);
		}
		
		
		GridBagConstraints gbcButton = new GridBagConstraints();
		gbcButton.fill = GridBagConstraints.BOTH;
		gbcButton.insets = new Insets(0, 0, 0, 0);
		gbcButton.gridx = 1;
		gbcButton.gridy = yPos;
		
		
		if (yPos == 7)
		{
			formular.add(mSpeichernButton, gbcButton);
		}
		else if (yPos == 8)
		{
			formular.add(mEmailEinladung, gbcButton);
		}
		else if (yPos == 9)
		{
			formular.add(mEmailFeedback, gbcButton);
		}
		else if (yPos == 10)
		{
			formular.add(mEmailMitbring, gbcButton);
		}
	}
	
	
	



	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(mSpeichernButton))
		{
			//Methode, die den Datensatz in die Datenbank speichert
			updateData();
			/*TODO Ausgabe
			mAusgabe = "Die Daten wurden gespeichert.";*/
		
			
		}
		else if (e.getSource().equals(mEmailEinladung))
		{

			
			try {
				result = mDB.executeQuery("SELECT * FROM PARTY WHERE PID=" + mPID);
				
				if(result.next())
				{
					aName = result.getString("NAME");
					aMotto = result.getString("MOTTO");
					aOrt = result.getString("ORT");
					if(result.getDate("ZEIT")!=null)
					{
						mDate.setDate(result.getDate("ZEIT"));
						mTime.setTime(result.getTimestamp("ZEIT"));
					}
				}	
			
				rs = mDB.executeQuery("SELECT EMAIL, NAME FROM GAESTELISTE LEFT JOIN PERSONEN ON GAESTELISTE.PERSID = PERSONEN.PERSID WHERE PID =" + mPID);
				
				
				
				while(rs.next())
				{
					String nachricht = "<p><span style=\"font-size: large;\"><strong>Einladung zur Party</strong></span></p><p>&nbsp;</p><p><span style=\"font-size: medium;\"><span style=\"font-size: xx-small;\">Hi "+rs.getString(2) +" du bist auf unsere Party eingeladen.</span><br /></span></p><p><span style=\"font-size: xx-small;\">Das Motto ist "+aMotto +".</span></p><p><span style=\"font-size: xx-small;\">Die Party findet am "+mDate.getDate() +" um "+mTime.getTime() +" Uhr statt.</span></p><p><span style=\"font-size: xx-small;\">Bitte gib bescheid ob du kommst, eine Mitfahr oder Schlafgelegenheit brauchst oder anbieten kannst.</span></p><p>&nbsp;</p><p><span style=\"font-size: medium;\"><span style=\"font-size: medium;\">Dein Gastgeber</span></span></p>";
//					System.out.println(rs.getString(1));
//					System.out.println("Einladung zur " + aMotto + " Party am " + mDate.getDate());
//					System.out.println(nachricht);
					sendMail.emailSenden(rs.getString(1), "Einladung zur " + aMotto + " Party am " + mDate.getDate(), nachricht);
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
		else if (e.getSource().equals(mEmailFeedback))
		{
			
			try {
				result = mDB.executeQuery("SELECT * FROM PARTY WHERE PID=" + mPID);
				
				if(result.next())
				{
					aName = result.getString("NAME");
					aMotto = result.getString("MOTTO");
					aOrt = result.getString("ORT");
					if(result.getDate("ZEIT")!=null)
					{
						mDate.setDate(result.getDate("ZEIT"));
						mTime.setTime(result.getTimestamp("ZEIT"));
					}
				}	
				
				rs = mDB.executeQuery("SELECT EMAIL, NAME FROM GAESTELISTE LEFT JOIN PERSONEN ON GAESTELISTE.PERSID = PERSONEN.PERSID WHERE PID =" + mPID);
				
				JFrame frame1 = new JFrame("URL Abfrage");
			    String umfrage;
				umfrage = JOptionPane.showInputDialog(
			        frame1, 
			        "Bitte Link zur Umfrage eingeben: ", 
			        "Umfrage URL wird benötigt!", 
			        JOptionPane.QUESTION_MESSAGE
			        );
			        
				
				while(rs.next())
				{
					String nachricht = "<p><span style=\"font-size: small;\"><strong>Umfrage</strong></span></p><p>&nbsp;</p><p><span style=\"font-size: small;\">Hi "+rs.getString(2) +" ,</span></p>	<p>hat dir die "+ aMotto +" Party vom "+mDate.getDate() +" gefallen?</p><p>Dann beantworte bitte unsere Umfrage auf "+umfrage +" , damit die n&auml;chste Party noch besser wird.</p><p>&nbsp;</p><p>Dein Gastgeber&nbsp;</p>";
//					System.out.println(rs.getString(1));
//					System.out.println("Umfrage zur " + aMotto + " Party vom " + mDate.getDate());
//					System.out.println(nachricht);
					sendMail.emailSenden(rs.getString(1), "Umfrage zur " + aMotto + " Party vom " + mDate.getDate(), nachricht);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			
			
		}
		else if (e.getSource().equals(mEmailMitbring))
		{
			try {
				result = mDB.executeQuery("SELECT * FROM PARTY WHERE PID=" + mPID);
				
				if(result.next())
				{
					aName = result.getString("NAME");
					aMotto = result.getString("MOTTO");
					aOrt = result.getString("ORT");
					if(result.getDate("ZEIT")!=null)
					{
						mDate.setDate(result.getDate("ZEIT"));
						mTime.setTime(result.getTimestamp("ZEIT"));
					}
				}	
				
				rs = mDB.executeQuery("SELECT EMAIL, NAME, PERSONEN.PERSID FROM GAESTELISTE LEFT JOIN PERSONEN ON GAESTELISTE.PERSID = PERSONEN.PERSID WHERE PID =" + mPID);
				   
				
				while(rs.next())
				{
					rsMat = mDB.executeQuery("Select NAME, ANZAHL, EINHEIT, Notiz from MATERIAL left Join MATERIALTEMPLATE on MATERIAL.MTID = MATERIALTEMPLATE.MTID Where Persid="+rs.getString(3) +" and PID="+mPID);
					String material = "";
					int counter = 0;
					while(rsMat.next())
					{
						if (rsMat.getString(2) != null){
							material = material +rsMat.getString(2)+" "+rsMat.getString(3)+" "+ rsMat.getString(1) + " Notiz: "+rsMat.getString(4) +"<p>&nbsp;</p>";
							counter++;
						}
						
					}
					
					if (counter != 0){
						
						String nachricht = "<p><span style=\"font-size: small;\"><strong>Letzte Infos</strong></span></p><p>&nbsp;</p><p><span style=\"font-size: small;\">Hi "+rs.getString(2) +",</span></p><p>die "+ aMotto +" Party findet am "+mDate.getDate() +" um "+mTime.getTime() +" statt.</p><p>Bitte bring zur Party mit:</p><p>&nbsp;</p><p>"+ material +"</p><p>&nbsp;</p><p>Dein Gastgeber&nbsp;</p>";
//						System.out.println(rs.getString(1));
//						System.out.println("Infos zur " + aMotto + " Party am " + mDate.getDate());
//						System.out.println(nachricht);
						sendMail.emailSenden(rs.getString(1), "Infos zur " + aMotto + " Party am " + mDate.getDate(), nachricht);
						
						
					}

				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}	
	
	
	public abstract void updateData();
	
	
	public abstract void refreshTable();

	/**VON BASTI ÜBERNOMMEN
	 * Soll von refreshTable() aufgerufen werden. Stellt übergebenes Resultset dar.
	 * @param rs
	 * @throws SQLException
	 */
	public void refreshTable(ResultSet rs) throws SQLException
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
		
	}
	
}