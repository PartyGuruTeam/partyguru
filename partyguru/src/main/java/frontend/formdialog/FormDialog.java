package frontend.formdialog;

import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.text.JTextComponent;

/**
 * Klasse zum automatischen Erstellen von Dialogen. Diese werden u.a. verwendet, um neue Einträge in 
 * die Tabellen einzufügen. 
 * @author Bastian
 *
 */
public class FormDialog extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;

	JDialog mFrame;
	JButton mSubmit;
	FormElement[] mElements;

	Vector<String> mResult;

	/**
	 * Erstellen eines neuen Fensters
	 * @param titel Titel des Fensters
	 * @param elements Array von FormElementen, die im Dialog dargestellt werden sollen.
	 * @param result Vektor, in den die ausgewählten Ergebnisse geschrieben werden sollen.
	 * @param parent Eltern Fenster
	 */
	private FormDialog(String titel, FormElement[] elements, Vector<String> result, Window parent)
	{
		super();
		mFrame = new JDialog(parent, titel, Dialog.ModalityType.DOCUMENT_MODAL);
		mFrame.add(this);
		
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		mElements = elements;
		mResult = result;
		
		SequentialGroup hGroup = layout.createSequentialGroup();
		ParallelGroup hLabelGroup = layout.createParallelGroup();
		ParallelGroup hCompGroup = layout.createParallelGroup();
		
		SequentialGroup vGroup = layout.createSequentialGroup();
		
		for(FormElement f: mElements)
		{
			ParallelGroup vRowGroup = layout.createParallelGroup(Alignment.BASELINE);
			JLabel label = new JLabel(f.mLabel);
			vRowGroup.addComponent(label);
			hLabelGroup.addComponent(label);
			if(f.mType==FormElement.TEXT_FIELD)
			{
				JTextField t = new JTextField(20);
				hCompGroup.addComponent(t);
				vRowGroup.addComponent(t);
				f.setComponent(t);
			} else if(f.mType==FormElement.DROP_DOWN)
			{
				JComboBox<String> b = new JComboBox<String>(f.mInitVector);
				hCompGroup.addComponent(b);
				vRowGroup.addComponent(b);
				f.setComponent(b);
			}
			vGroup.addGroup(vRowGroup);
		}
		
		mSubmit = new JButton("Anlegen");
		mSubmit.addActionListener(this);
		hCompGroup.addComponent(mSubmit);
		vGroup.addGroup(layout.createParallelGroup(Alignment.TRAILING).addComponent(mSubmit));
		
		hGroup.addGroup(hLabelGroup);
		hGroup.addGroup(hCompGroup);
		layout.setHorizontalGroup(hGroup);
		layout.setVerticalGroup(vGroup);
		
		
		mFrame.setSize(400, 400);
		mFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mFrame.setVisible(true);
	}


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
	
	/**
	 * Statische Funktion, um ein FormDialog aufzurufen und das Ergebnis auszugeben. 
	 * @param string Titel des Fensters
	 * @param formElements FormElemente, die in den Dialog eingefügt werden sollen
	 * @param parent Eltern Fenster
	 * @return String Vektor, mit allen ausgewählten/eingegebenen Daten.
	 */
	public static Vector<String> getDialog(String string, FormElement[] formElements, Window parent)
	{
		Vector<String> result = new Vector<String>();
		
		FormDialog f = new FormDialog(string, formElements, result, parent);
		
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

}
