package MainPackage;
import javax.swing.JOptionPane;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class DialogBox {
	
	public static int DialogBox(String title, String text)
	{
		return JOptionPane.showConfirmDialog(null, text, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}
}