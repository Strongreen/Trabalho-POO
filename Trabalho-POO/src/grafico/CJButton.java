package grafico;

import java.awt.Cursor;

import javax.swing.JButton;

public class CJButton extends JButton{
	public CJButton(){
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	public CJButton(String text){
		super(text);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
}
