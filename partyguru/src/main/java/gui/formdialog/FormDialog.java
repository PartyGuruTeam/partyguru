package gui.formdialog;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

public class FormDialog extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;

	JFrame mFrame;
	JButton mSubmit;
	FormElement[] mElements;

	Vector<String> mResult;

	public FormDialog(String titel, FormElement[] elements, Vector<String> result)
	{
		super();
		mFrame = new JFrame(titel);
		mFrame.add(this);
		
		//this.setLayout(new GroupLayout(this));
		
		mElements = elements;
		mResult = result;
		
		for(FormElement f: mElements)
		{
			this.add(new JLabel(f.mLabel));
			if(f.mType==FormElement.TEXT_FIELD)
			{
				JTextField t = new JTextField(20);
				this.add(t);
				f.setComponent(t);
			} else if(f.mType==FormElement.DROP_DOWN)
			{
				JComboBox<String> b = new JComboBox<String>(f.mInitVector);
				this.add(b);
				f.setComponent(b);
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
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource().equals(mSubmit))
		{	
			for(FormElement element: mElements)
			{
				switch(element.mType)
				{
				case FormElement.TEXT_FIELD:
					mResult.add(((JTextComponent) element.getComponent()).getText());
					break;
				case FormElement.DROP_DOWN:
					if(element.getComponent() instanceof JComboBox<?>)
					{
						@SuppressWarnings("unchecked")
						JComboBox<String> box = (JComboBox<String>) element.getComponent();
						mResult.add((String) box.getSelectedItem());
					}	
					break;
				default:
					mResult.add(null);
					break;
				}
				
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
				new FormElement("Hello", FormElement.TEXT_FIELD),
				new FormElement("Drop Down", FormElement.DROP_DOWN, new String[] {
						"Hello", "World" })
		});
		
		for(String s: result)
		{
			System.out.println(s);
		}
	}

}
