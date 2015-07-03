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


public abstract  class FormularLayout extends JTabbedPane implements ActionListener {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	//Erstellung der Buttons für unten
	private JButton mSpeichernButton;
	//private JPanel mButtonPanel;
	
	private JPanel mForm;
	
	String mAusgabe;
	Vector<JTextField> tfArray = new Vector<JTextField>();

	public FormularLayout()
	{	
		mSpeichernButton = new JButton("Speichern");
		//mButtonPanel = new JPanel();
		
		mForm = new JPanel();
		this.add(mForm);

		Border border = mForm.getBorder();
		Border margin = new EmptyBorder(10, 10, 10, 10);
		mForm.setBorder(new CompoundBorder(border, margin));

		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[] {86, 86, 0};
		gbl.rowHeights = new int[] { 50, 20, 20, 20, 20, 20, 20, 30 };
		gbl.columnWeights = new double[] { 0.0, 1.0,0.0, 0.0, Double.MIN_VALUE };
		gbl.rowWeights = new double[] { 0.0, 0.0,  0.0, 0.0, 0.0, 0.0, 0.0, 0.0,Double.MIN_VALUE };
		mForm.setLayout(gbl);

		

		addLabel("Gebe hier deine Party-Stammdaten ein!", 0, mForm);
		
		addLabelAndTextField("Partyname:", 1, mForm);
		addLabelAndTextField( "Gastgeber:*", 2, mForm);
		addLabelAndTextField( "Ort:*", 3, mForm);
		addLabelAndTextField( "Datum:*", 4, mForm);
		addLabelAndTextField( "Startzeit:", 5, mForm);
		addLabelAndTextField("Motto:", 6, mForm);
		
		addButttons(7, mForm);

		
		
		
		
	}

	/*
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
		
		
		mSpeichernButton.addActionListener((ActionListener) this);
		
		GridBagConstraints gbcButton = new GridBagConstraints();
		gbcButton.fill = GridBagConstraints.BOTH;
		gbcButton.insets = new Insets(0, 0, 0, 0);
		gbcButton.gridx = 1;
		gbcButton.gridy = yPos;
		formular.add(mSpeichernButton, gbcButton);
		
	}
	
	
	



	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(mSpeichernButton))
		{
			//Methode, die den Datensatz in die Datenbank speichert
			updateData();
			/*TODO Ausgabe
			mAusgabe = "Die Daten wurden gespeichert.";*/
		
			
		}
		else 
		{
			/* fehlt
			 * 
			 * */
			 
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