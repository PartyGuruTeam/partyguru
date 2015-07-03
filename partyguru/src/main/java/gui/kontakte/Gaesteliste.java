package gui.kontakte;

import java.sql.ResultSet;

import javax.swing.table.DefaultTableModel;

import gui.MutterLayout;
import gui.TabellenLayout;

public class Gaesteliste extends TabellenLayout 
{	
	private static final long serialVersionUID = 1L;
	
	public Gaesteliste(ResultSet rs, MutterLayout parent) {
		super(rs);
	}


	@Override
	public void refreshTable() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRow(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addRow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRow(int row, DefaultTableModel modell) {
		// TODO Auto-generated method stub

	}

}
