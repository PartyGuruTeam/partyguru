package general.functions;

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
public class TimeFunction 
{
	SpinnerDateModel mModel;
	Date date;
	JSpinner js1;
	
	public TimeFunction(JSpinner jSpinner)
	{
		date = new Date();
		mModel = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
		
		js1 = jSpinner;
		JSpinner jSpinner1 = new JSpinner(mModel);
	
		
		JSpinner.DateEditor de = new JSpinner.DateEditor(jSpinner1, "HH:mm");
		jSpinner1.setEditor(de);
	}

    public static JSpinner createJSpinner() 
    {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);

        SpinnerDateModel model = new SpinnerDateModel();
        model.setValue(calendar.getTime());

        JSpinner spinner = new JSpinner(model);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "HH:mm");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(false);

        spinner.setEditor(editor);

        
        return spinner;
    }
    
	public int getTime()
	{
		//return (Date) js1.getValue();
		return (Integer) js1.getValue();
	}

	//Only for testing
	//
    public static void main(String[] args) 
    {
    	JFrame frame = new JFrame("Time-Function JSPinner");
    	JPanel content = new JPanel();
    	frame.add(content);
    	
    	final JSpinner timePicker = createJSpinner();
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