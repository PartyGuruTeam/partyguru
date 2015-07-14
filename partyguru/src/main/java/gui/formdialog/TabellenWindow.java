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
	
	public TabellenWindow(TabellenLayout panel, String titel)
	{
		super(titel);
		mPanel = panel;
		this.add(mPanel);
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		mSubmit = new JButton("Auswählen");
		mSubmit.addActionListener(this);
		panel.getButtonPanel().add(mSubmit);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(mSubmit))
		{
			if(mPanel.getTabelle().getSelectedRow()>=0)
			{
				Integer result = (Integer) mPanel.getTabelle().getValueAt(mPanel.getTabelle().getSelectedRow(), 0);
				if(result!=null)
				{
					//TODO return type
					this.dispose();
				}
			} else
			{
				JOptionPane.showMessageDialog(this, "Wählen Sie eine Party aus.");
			}
		}
	}

}
