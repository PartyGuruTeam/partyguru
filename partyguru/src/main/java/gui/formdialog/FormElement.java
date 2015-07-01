package gui.formdialog;


import javax.swing.JComponent;

public class FormElement 
{
	public static final int TEXT_FIELD = 1;
	public static final int DROP_DOWN = 2;
	public static final int DATE_PICKER = 3;
	public static final int TIME_PICKER = 4;
	
	
	String mLabel;
	int mType;
	String[] mInitVector;
	JComponent mComp;
	
	public FormElement(String label, int type)
	{
		mLabel = label;
		mType = type;
		mInitVector = null;
		mComp = null;
	}
	
	public FormElement(String label, int type, String[] init)
	{
		mLabel = label;
		mType = type;
		mInitVector = init;
		mComp = null;
	}
	
	public void setComponent(JComponent comp)
	{
		mComp = comp;
	}
	
	public JComponent getComponent()
	{
		return mComp;
	}
	
}
