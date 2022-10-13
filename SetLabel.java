package memoryweakness;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class SetLabel extends JLabel {
	
	
	public SetLabel(String text, int x, int y, int width, int height) {
		setText(text);
		setBounds(x, y, width, height);
		setHorizontalAlignment(JLabel.CENTER);
		setFont(new Font(Font.DIALOG, Font.PLAIN, height / 4));
		setForeground(Color.WHITE);
	}
}
