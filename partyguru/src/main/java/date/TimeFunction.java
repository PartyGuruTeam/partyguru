package date;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.text.DateFormatter;

/**
 * @author Lukas
 * @version 1.0 beta
 */
public class TimeFunction extends JSpinner
{
	private static final long serialVersionUID = 1L;
	
	SpinnerDateModel mModel;
	
	private TimeFunction(SpinnerDateModel model)
	{
		super(model);
		mModel = model;

        JSpinner.DateEditor editor = new JSpinner.DateEditor(this, "HH:mm");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false); //TODO Überschreiben
        formatter.setOverwriteMode(false);

        this.setEditor(editor);
	}

    public static TimeFunction createTime() 
    {
        SpinnerDateModel model = new SpinnerDateModel();
        TimeFunction spinner = new TimeFunction(model);

        return spinner;
    }
    
	public Date getTime()
	{
		return (Date) getValue();
	}
	
	public void setTime(Date d)
	{
		mModel.setValue(d);
	}

	//Only for testing
	//
    public static void main(String[] args) 
    {
    	JFrame frame = new JFrame("Time-Function JSPinner");
    	JPanel content = new JPanel();
    	frame.add(content);
    	
    	final TimeFunction timePicker = createTime();
    	timePicker.setTime(new Date(2015, 10, 23, 20, 15));
    	content.add(timePicker);
    	
    	JButton button = new JButton("prüfen");
    	button.addActionListener(new ActionListener() 
    	{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println(timePicker.getValue());
			}
		});
    	content.add(button);

    	frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(100,100);
        //frame.getContentPane().add(content);
        //frame.pack();
        //frame.setLocationRelativeTo(null);
    }    
}