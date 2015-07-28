package frontend.misc;

import java.util.Date;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.text.DateFormatter;

/**
 * Zeitkomponente in GUI mittels JSpinner.
 * @author Lukas
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
        formatter.setAllowsInvalid(false);
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

	    
}