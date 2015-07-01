package GUI_Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;



import javax.swing.SwingUtilities;

import net.sourceforge.jdatepicker.DateModel;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

/**
 * Kalenderkomponente in GUI
 * @author Lukas
 * @version 1.0 beta
 */

public class JDatePicker_new extends JDatePickerImpl
{
	private static final long serialVersionUID = 1L;

	UtilDateModel mModel;
	
	public JDatePicker_new(JDatePanelImpl panel, UtilDateModel model) 
	{
		super(panel);	
		
		mModel = model;
	}
	
	public static JDatePicker_new createDatePicker()
	{
		UtilDateModel model = new UtilDateModel();
		model.setDate(model.getYear(), model.getMonth(), model.getDay());
		model.setSelected(true);	
		
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		JDatePicker_new picker = new JDatePicker_new(datePanel, model);
		
		return picker;
	}
	
	public Date getDate()
	{
		return mModel.getValue();
	}

	public static void main(String[] args) 
	{
		JFrame frame = new JFrame("DatePicker Test");

		JPanel panel = new JPanel();
		frame.add(panel);

		JDatePicker_new  datePicker = createDatePicker();
		panel.add(datePicker);
		
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 300);



	}
}
