package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 * Abstrakte Klasse, die als Grundlage für alle Views in einem Tabellen Layout dient. Enthält Tabelle 
 * und Buttons zum Anlegen und Löschen von Einträgen.
 * @author Bastian
 *
 */
public abstract class TabellenLayout extends JPanel implements ActionListener, TableModelListener
{
	private static final long serialVersionUID = 1L;

	private JButton mNeuButton;
	private JButton mLoeschenButton;

	private JTable mTabelle;
	private DefaultTableModel mModell;
	
	private JPanel mButtonPanel;

	/**
	 * Konstruktor von Tabellen Layout.
	 * @param rs ResultSet einer SQL Abfrage wird übergeben, um damit die Tabelle zu füllen.
	 */
	public TabellenLayout(ResultSet rs)
	{
		this.setLayout(new BorderLayout());
		mTabelle = new JTable();
		this.add(new JScrollPane(mTabelle), BorderLayout.CENTER);
		try {
			refreshTable(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		mModell.addTableModelListener(this);
		
		mButtonPanel = new JPanel();

		mNeuButton = new JButton("Neu...");
		mButtonPanel.add(mNeuButton);
		mNeuButton.addActionListener(this);

		mLoeschenButton = new JButton("Löschen...");
		mButtonPanel.add(mLoeschenButton);
		mLoeschenButton.addActionListener(this);
		
		this.add(mButtonPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Funktion soll benötigte Tabelle von DB ziehen und daraufhin an refreshTable(ResultSet rs) übergeben.
	 */
	public abstract void refreshTable();

	/**
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
		mModell = new DefaultTableModel(tabelle, columnNames);
		mTabelle.setModel(mModell);
	}

	/**
	 * Wird aufgerufen, wenn auf Neu oder Löschen Button geklickt wird und führt entsprechende Funktion aus.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(mNeuButton))
		{
			addRow();
			refreshTable();
		} else if(e.getSource().equals(mLoeschenButton))
		{
			String result = JOptionPane.showInputDialog("ID eingeben:");
			if(result!=null)
			{
				Integer r=null;
				try
				{
					r = Integer.parseInt(result);
				} catch(NumberFormatException e2)
				{
					JOptionPane.showMessageDialog(this, "Bitte überprüfen Sie Ihre Eingabe!");
				}
				if(r!=null)
				{
					deleteRow(r);
					refreshTable();
				}
			}
		}
	}

	/**
	 * Wird aufgerufen, wenn Tabelle geupdated wurde und führt entsprechende Funktion aus.
	 */
	public void tableChanged(TableModelEvent e) 
	{
		if(e.getType()==TableModelEvent.UPDATE)
		{
			updateRow(e.getFirstRow(), mModell);
			refreshTable();
		}
	}

	/**
	 * Soll Tupel aus Datenbank löschen. Wird von actionPerformed() aufgerufen.
	 * @param id Id mithilfe dessen das Tupel identifiziert werden kann.
	 */
	public abstract void deleteRow(int id);
	
	/**
	 * Neues Tupel der Datenbank hinzufügen. Wird von actionPerformed() aufgerufen.
	 */
	public abstract void addRow();
	
	/**
	 * Bestimmtes Tupel verändern. Wird von tableChanged() aufgerufen.
	 * @param row Zeile, in der Veränderung vorgenommen wurde.
	 * @param modell TableModel, welches verwendet wird.
	 */
	public abstract void updateRow(int row, DefaultTableModel modell);

}
