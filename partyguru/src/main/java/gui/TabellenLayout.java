package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

import javax.swing.JButton;
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

	JTable mTabelle;
	private DefaultTableModel mModell;
	
	JPanel mButtonPanel;
	
	Boolean[] mIsEditable;

	/**
	 * Konstruktor von Tabellen Layout.
	 * @param rs ResultSet einer SQL Abfrage wird übergeben, um damit die Tabelle zu füllen.
	 */
	public TabellenLayout(ResultSet rs, Boolean[] isEditable)
	{
		mIsEditable = isEditable;
		
		this.setLayout(new BorderLayout());
		mTabelle = new JTable();
		this.add(new JScrollPane(mTabelle), BorderLayout.CENTER);
		try {
			printTable(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		mModell.addTableModelListener(this);
		mTabelle.getTableHeader().setReorderingAllowed(false);
		
		mButtonPanel = new JPanel();

		mNeuButton = new JButton("Neu...");
		mButtonPanel.add(mNeuButton);
		mNeuButton.addActionListener(this);

		mLoeschenButton = new JButton("Löschen");
		mButtonPanel.add(mLoeschenButton);
		mLoeschenButton.addActionListener(this);
		
		this.add(mButtonPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Funktion soll benötigte Tabelle von DB ziehen und daraufhin an refreshTable(ResultSet rs) übergeben.
	 */
	public abstract void printTable();

	/**
	 * Soll von printTable() aufgerufen werden. Stellt übergebenes Resultset dar.
	 * @param rs
	 * @throws SQLException
	 */
	public void printTable(ResultSet rs) throws SQLException
	{
		ResultSetMetaData md = null;
		Vector<Vector<Object>> tabelle = new Vector<Vector<Object>>();
		Vector<String> columnNames = new Vector<String>();
		try {
			md = rs.getMetaData();
			for(int i=1; i<=md.getColumnCount(); i++)
			{
				columnNames.add(md.getColumnName(i));					
				//System.out.println(md.getTableName(i)+" - "+md.getColumnName(i)+" - "+md.getColumnType(i));
			} 	
		} catch (SQLException e) {
			e.printStackTrace();
		}

		while(rs.next())
		{
			Vector<Object> row = new Vector<Object>(columnNames.size());
			for(int i=1; i<=md.getColumnCount(); i++)
			{
				if(rs.getObject(i)!=null)
				{
					switch(md.getColumnType(i))
					{
					case Types.VARCHAR:
						row.addElement(rs.getString(i));
						break;
					case Types.BOOLEAN:
						row.addElement(new Boolean(rs.getBoolean(i)));
						break;
					default:
						row.addElement(rs.getObject(i));
						break;
					}
				} else
				{
					row.addElement(new String(""));
				}
			}
			tabelle.add(row);
		}
		mModell = new MyTableModel(tabelle, columnNames, mIsEditable);
		mTabelle.setModel(mModell);
	}

	/**
	 * Wird aufgerufen, wenn auf Neu oder Löschen Button geklickt wird und führt entsprechende Funktion aus.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(mNeuButton))
		{
			addRow();
			printTable();
		} else if(e.getSource().equals(mLoeschenButton))
		{
			Vector<String> v = new Vector<String>();
			for(int i=0; i<mTabelle.getColumnCount(); i++)
			{
				v.add(mTabelle.getValueAt(mTabelle.getSelectedRow(), i).toString());
			}
			deleteRow(v);
			printTable();
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
			printTable();
		}
	}

	/**
	 * Soll Tupel aus Datenbank löschen. Wird von actionPerformed() aufgerufen.
	 * @param v Id mithilfe dessen das Tupel identifiziert werden kann.
	 */
	public abstract void deleteRow(Vector<String> v);
	
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
	//TODO updateRow verbessern

}



class MyTableModel extends DefaultTableModel
{
	private static final long serialVersionUID = 1L;
	
	Boolean[] mIsEditable;

	public MyTableModel(Vector<Vector<Object>> tabelle,
			Vector<String> columnNames, Boolean[] isEditable) {
		super(tabelle, columnNames);
		mIsEditable = isEditable;
	}

	@Override
	public Class<? extends Object> getColumnClass(int c)
	{
		return getValueAt(0, c).getClass();	
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		Boolean b = null;
		try
		{ 
			b = mIsEditable[column];
		} catch(ArrayIndexOutOfBoundsException e)
		{
			b = null;
		}
		if(b!=null)
			return b;
		else
			return false;
	}
	
	
	
}