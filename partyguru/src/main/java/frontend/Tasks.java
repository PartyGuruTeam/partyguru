package frontend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import backend.Database;

/**
 * Stellt die Taskliste im Hauptfenster dar. Kommuniziert mit der DB, um Status zu abzurufen 
 * und zu �ndern. 
 * @author PartyGuru
 *
 */
public class Tasks extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;

	ArrayList<JCheckBox> cbArray = new ArrayList<JCheckBox>();
	Database mDB;
	MutterLayout mParent;
	JLabel mContent;
	JCheckBox mBox;
	//private JButton mNeuButton;
	int mPosButton;

	ResultSet resultKID, resultTID, resultUpdate;


	//Konstruktoraufruf
	public Tasks(Database db, MutterLayout parent) throws SQLException
	{
		//mNeuButton = new JButton("Neuer Eintrag");


		mContent = new JLabel();
		mBox = new JCheckBox();

		this.add(mContent);

		mDB = db;
		mParent = parent;		 

		Border border = this.getBorder();
		Border margin = new EmptyBorder(0, 0, 0, 0);
		this.setBorder(new CompoundBorder(border, margin));

		GridBagLayout gbl = new GridBagLayout();
//		gbl.columnWidths = new int[] {86, 86, 0};
//		gbl.rowHeights = new int[] { 50, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20};
//		gbl.columnWeights = new double[] { 0.0, 1.0,0.0, 0.0, Double.MIN_VALUE };
//		gbl.rowWeights = new double[] { 0.0, 0.0,  0.0, 0.0, 0.0, 0.0, 0.0, 0.0,Double.MIN_VALUE };
		this.setLayout(gbl);


		addLabel("To-Do Liste", 0, this, true);
		createList(mDB, mParent);

		//addTaskButton(mPosButton, this);
	}
	private void addLabelAndTextField(String labelText, int yPos, Container formular, int atid, int cbStatus) 
	{
		
		JCheckBox checkb = new JCheckBox(labelText);
		GridBagConstraints gbcCheckbox = new GridBagConstraints();
		gbcCheckbox.fill = GridBagConstraints.BOTH;
		gbcCheckbox.insets = new Insets(0, 0, 5, 5);
		gbcCheckbox.gridx = 0;
		gbcCheckbox.gridy = yPos;
		formular.add(checkb, gbcCheckbox);
		
		if (cbStatus == 1)
		{
			checkb.setSelected(true);
		}
		else
			checkb.setSelected(false);

		ActionListener checkboxaL = new ActionListener()
		{
			

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				int cbStatusNEU = 0;
				
				if (checkb.isSelected())
				{
					cbStatusNEU = 1;
				}
				else
				{
					cbStatusNEU = 0;
				}
				
				
				try 
				{
					//System.out.println("try: set="+cbStatusNEU+" where="+atid);
					mDB.executeUpdate("UPDATE TASKS SET STATUS = " + cbStatusNEU + " WHERE TID = " + atid);
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
			}
		};


		cbArray.add(checkb);
		checkb.addActionListener(checkboxaL);


	}
	//Methode f�r Kopfzeile
	private void addLabel(String labelText, int yPos, Container formular, boolean header)
	{

		JLabel kopfzeile = new JLabel(labelText);
		if(header)
		{
			Font font = new Font(kopfzeile.getFont().getName(), 
					kopfzeile.getFont().getStyle(), kopfzeile.getFont().getSize()+3);
			kopfzeile.setFont(font);
		}
		GridBagConstraints gbcLabel = new GridBagConstraints();
		gbcLabel.fill = GridBagConstraints.BOTH;
		gbcLabel.insets = new Insets(0, 0, 5, 5);
		gbcLabel.gridx = 0;
		gbcLabel.gridy = yPos;
		formular.add(kopfzeile, gbcLabel);			
	}



	/*private void addTaskButton(int yPos, Container formular) 
	{
		mNeuButton.addActionListener((ActionListener)this);

		GridBagConstraints gbcButton = new GridBagConstraints();
		gbcButton.fill = GridBagConstraints.BOTH;
		gbcButton.insets = new Insets(0, 0, 5, 5);
		gbcButton.gridx = 0;
		gbcButton.gridy = yPos;
		formular.add(mNeuButton, gbcButton);

	}*/

	private void createList(Database db, MutterLayout parent) throws SQLException
	{
		ArrayList<String> checkRID = new ArrayList<String>();
		resultKID = db.executeQuery("SELECT * FROM TASKKATEGORIE");

		while (resultKID.next())
		{
			checkRID.add(resultKID.getString("KATEGORIE"));
		}


		int pos = 1;
		for (int i=1; i <=checkRID.size(); i++)
		{
			String cat = checkRID.get(i-1);
			addLabel(cat, pos, this, false);
			pos = pos + 1;
			resultTID = db.executeQuery("SELECT * FROM TASKS WHERE KID = " + i + "AND PID = " + mParent.getPID());

			while (resultTID.next())
			{
				String name = resultTID.getString("NAME");
				int aStatus = resultTID.getInt("STATUS");
				int atid = resultTID.getInt("TID");
				//System.out.println("Status="+aStatus);
				addLabelAndTextField(name, pos, this, atid, aStatus);
				pos = pos + 1;
			}
			mPosButton = pos;
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		//System.out.println("Test");

	}


}
