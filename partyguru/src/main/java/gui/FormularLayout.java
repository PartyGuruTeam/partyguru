package gui;


import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.FormatFlagsConversionMismatchException;

import javax.swing.*;



public  class FormularLayout {

	public FormularLayout()
	{	
		JFrame bsp = new JFrame();

		JPanel formular = new JPanel();

		GridBagLayout gbl = new GridBagLayout();
		gbl.columnWidths = new int[] {86, 86, 0};
		gbl.rowHeights = new int[] { 20, 20, 20, 20, 20, 0 };
	    gbl.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
	    gbl.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
	    formular.setLayout(gbl);

	    
	    /** in methode abgedeckt
		JLabel gastgeber = new JLabel();
		JLabel ort = new JLabel();
		JLabel datum = new JLabel();
		JLabel startzeit = new JLabel();
		JLabel endzeit = new JLabel();
		JLabel motto = new JLabel();

		JTextField tfgastgeber = new JTextField();
		JTextField tfort = new JTextField();
		JTextField tfdatum = new JTextField();
		JTextField tfstartzeit = new JTextField();
		JTextField tfendzeit = new JTextField();
		JTextField tfmotto = new JTextField();
	*/
	    
	    addLabel("KOPFZEILE", 0, formular);

	    addLabelAndTextField("Gastgeber:*", 1, formular);
	    addLabelAndTextField("Ort:*", 2, formular);
	    addLabelAndTextField("Datum:*", 3, formular);
	    addLabelAndTextField("Startzeit:", 4, formular);
	    addLabelAndTextField("Motto:", 5, formular);
	    
	    bsp.add(formular);
	    bsp.setVisible(true);
	    bsp.setSize(800, 500);
	   
	}
	


	private void addLabelAndTextField(String labelText, int yPos, Container formular) {

	    JLabel faxLabel = new JLabel(labelText);
	    GridBagConstraints gridBagConstraintForLabel = new GridBagConstraints();
	    gridBagConstraintForLabel.fill = GridBagConstraints.BOTH;
	    gridBagConstraintForLabel.insets = new Insets(0, 0, 5, 5);
	    gridBagConstraintForLabel.gridx = 0;
	    gridBagConstraintForLabel.gridy = yPos;
	    formular.add(faxLabel, gridBagConstraintForLabel);

	    JTextField textField = new JTextField();
	    GridBagConstraints gridBagConstraintForTextField = new GridBagConstraints();
	    gridBagConstraintForTextField.fill = GridBagConstraints.BOTH;
	    gridBagConstraintForTextField.insets = new Insets(0, 0, 5, 0);
	    gridBagConstraintForTextField.gridx = 1;
	    gridBagConstraintForTextField.gridy = yPos;
	    formular.add(textField, gridBagConstraintForTextField);
	    textField.setColumns(10);
	}
	
	
	private void addLabel(String labelText, int yPos, Container formular ){
		
		JLabel kopfzeile = new JLabel(labelText);
		GridBagConstraints gbcLabel = new GridBagConstraints();
		gbcLabel.fill = GridBagConstraints.BOTH;
		gbcLabel.insets = new Insets(0, 0, 0, 0);
		gbcLabel.gridx = 0;
		gbcLabel.gridy = yPos;
		formular.add(kopfzeile, gbcLabel);
		
	}
	
	public static void main (String[]args)
	{
		new FormularLayout();
	}
}