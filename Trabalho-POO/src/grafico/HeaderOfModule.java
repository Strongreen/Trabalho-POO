package grafico;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class HeaderOfModule extends JPanel{
	public HeaderOfModule(String text){
		add(new JLabel(text));
		setPreferredSize(new Dimension(2000,30));
		setBackground(new Color(210,210,255));
	}
}
