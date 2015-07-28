package frontend.misc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

/**
 * Kalenderkomponente in GUI
 * @author Lukas
 */

public class CalendarFunction extends JDatePickerImpl
{
	private static final long serialVersionUID = 1L;

	UtilDateModel mModel;

	private CalendarFunction(JDatePanelImpl panel, UtilDateModel model) 
	{
		super(panel);	

		mModel = model;
	}

	public static CalendarFunction createDatePicker()
	{
		UtilDateModel model = new UtilDateModel();
		model.setDate(model.getYear(), model.getMonth(), model.getDay());
		model.setSelected(true);	

		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		CalendarFunction picker = new CalendarFunction(datePanel, model);

		return picker;
	}

	public Date getDate()
	{
		return mModel.getValue();
	}

	public void setDate(Date d)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		mModel.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
	}

	//Only for testing
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame("DatePicker Test");

		JPanel panel = new JPanel();
		frame.add(panel);

		final CalendarFunction datePicker = createDatePicker();
		panel.add(datePicker);

		JButton button = new JButton("senden");
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(datePicker.getDate());
			}

		});
		panel.add(button);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 300);

	}
}
