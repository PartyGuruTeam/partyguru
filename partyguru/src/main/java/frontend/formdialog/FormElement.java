package frontend.formdialog;


import java.util.Vector;

import javax.swing.JComponent;

/**
 * Elemente, die in FormDialog eingefügt werden können. 
 * @author Bastian
 *
 */
public class FormElement 
{
	public static final int TEXT_FIELD = 1;
	public static final int DROP_DOWN = 2;
	public static final int DATE_PICKER = 3;
	public static final int TIME_PICKER = 4;
	//TODO Add Checkbox
	
	
	String mLabel;
	int mType;
	Vector<String> mInitVector;
	JComponent mComp;
	
	/** 
	 * Erstellen eines neuen Elements.
	 * @param label Schriftzug, der neben dem Element dargestellt wird.
	 * @param type Sollte eines der statischen FormElement Konstanten sein (außer DROP_DOWN).
	 */
	public FormElement(String label, int type)
	{
		mLabel = label;
		mType = type;
		mInitVector = null;
		mComp = null;
	}
	
	/**
	 * Erstellen eines neuen Elements.
	 * @param label Schriftzug, der neben dem Element dargestellt wird.
	 * @param type Sollte eines der statischen FormElement Konstanten sein
	 * @param init Für DROP_DOWN: Elemente, die im DropDown stehen sollen.
	 */
	public FormElement(String label, int type, Vector<String> init)
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
