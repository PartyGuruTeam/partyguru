package gui.formdialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.TabellenLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TabellenWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	JButton mSubmit;
	TabellenLayout mPanel;
	int mColumn;

	private Integer mID;
	
	public TabellenWindow(TabellenLayout panel, String titel, int column)
	{
		super(titel);
		mPanel = panel;
		mColumn = column;
		this.add(mPanel);
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		mSubmit = new JButton("Ausw�hlen");
		mSubmit.addActionListener(this);
		panel.getButtonPanel().add(mSubmit);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(mSubmit))
		{
			if(mPanel.getTabelle().getSelectedRow()>=0)
			{
				Integer result = (Integer) mPanel.getTabelle().getValueAt(mPanel.getTabelle().getSelectedRow(), mColumn);
				if(result!=null)
				{
					mID = result;
				}
			} else
			{
				JOptionPane.showMessageDialog(this, "W�hlen Sie eine Party aus.");
			}
		}
	}
}
