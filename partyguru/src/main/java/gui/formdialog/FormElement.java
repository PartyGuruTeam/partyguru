package gui.formdialog;

public class FormElement 
{
	public static final int TEXT_FIELD = 1;
	public static final int DROP_DOWN = 2;
	public static final int DATE_PICKER = 3;
	public static final int TIME_PICKER = 4;
	
	
	String mLabel;
	int mType;
	
	public FormElement(String label, int type)
	{
		mLabel = label;
		mType = type;
		
	}
}
