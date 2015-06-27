package gui.formdialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

public class FormDialog extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;

	JFrame mFrame;
	ArrayList<JTextComponent> comps; 
	JButton mSubmit;

	Vector<String> mResult;

	public FormDialog(String titel, FormElement[] elements, Vector<String> result)
	{
		super();
		mFrame = new JFrame(titel);
		mFrame.add(this);
		
		mResult = result;
		
		comps = new ArrayList<JTextComponent>();

		for(FormElement f: elements)
		{
			this.add(new JLabel(f.mLabel));
			if(f.mType==FormElement.TEXT_FIELD)
			{
				JTextField t = new JTextField(20);
				this.add(t);
				comps.add(t);
			}
		}
		
		mSubmit = new JButton("Anlegen");
		mSubmit.addActionListener(this);
		this.add(mSubmit);
		
		mFrame.setSize(400, 400);
		mFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mFrame.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource().equals(mSubmit))
		{	
			for(JTextComponent c: comps)
			{
				mResult.add(c.getText());
			}
			mFrame.dispose();
		}
	}
	
	public static Vector<String> getDialog(String string, FormElement[] formElements)
	{
		Vector<String> result = new Vector<String>();
		
		FormDialog f = new FormDialog(string, formElements, result);
		
		while(f.mFrame.isVisible())
		{
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public static void main(String[] args) {
		
		Vector<String> result = getDialog("Hello World", new FormElement[] {
				new FormElement("Hello", FormElement.TEXT_FIELD)
		});
		
		for(String s: result)
		{
			System.out.println(s);
		}
	}

}
