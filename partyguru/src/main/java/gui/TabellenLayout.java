package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public abstract class TabellenLayout extends JPanel implements ActionListener, TableModelListener
{
	private static final long serialVersionUID = 1L;

	private JButton mNeuButton;
	private JButton mLoeschenButton;

	private JTable mTabelle;
	private DefaultTableModel mModell;

	public TabellenLayout(ResultSet rs, MutterLayout parent)
	{
		mTabelle = new JTable();
		this.add(mTabelle);
		//TODO Spaltennamen anzeigen
		try {
			refreshTable(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		mModell.addTableModelListener(this);

		mNeuButton = new JButton("Neu...");
		this.add(mNeuButton);
		mNeuButton.addActionListener(this);

		mLoeschenButton = new JButton("Löschen...");
		this.add(mLoeschenButton);
		mLoeschenButton.addActionListener(this);
	}

	public abstract void refreshTable();

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

	public void tableChanged(TableModelEvent e) 
	{
		if(e.getType()==TableModelEvent.UPDATE)
		{
			updateRow(e.getFirstRow(), mModell);
			refreshTable();
		}
	}

	public abstract void deleteRow(int id);
	public abstract void addRow();
	public abstract void updateRow(int row, DefaultTableModel modell);

}
