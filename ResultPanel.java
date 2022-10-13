package memoryweakness;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResultPanel extends JPanel {
	
	JLabel resultLabel = new JLabel();
	JLabel pointLabel = new JLabel();
	
	JLabel[] selectLabel = new JLabel[3];
	String[] selectText = {"スタート画面", "もう一回", "終了"};
	
	public ResultPanel() {
		setLayout(null);
		setBackground(new Color(150, 50, 150));
		
		resultLabel.setBounds(200, 100, 600, 400);
		resultLabel.setHorizontalAlignment(JLabel.CENTER);
		resultLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 200));
		resultLabel.setForeground(Color.WHITE);
		
		pointLabel.setBounds(300, 300, 400, 400);
		pointLabel.setHorizontalAlignment(JLabel.CENTER);
		pointLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 100));
		pointLabel.setForeground(Color.WHITE);
		
		this.add(resultLabel);
		this.add(pointLabel);
		
		for (int i = 0; i < selectLabel.length; i++) {
			selectLabel[i] = new JLabel(selectText[i]);
			selectLabel[i].setBounds(700, 400 + 60 * i, 300, 100);
			selectLabel[i].setFont(new Font(Font.DIALOG, Font.PLAIN, 40));
			selectLabel[i].setForeground(Color.WHITE);
			this.add(selectLabel[i]);
		}
	}
	
	public void setResult(int point) {
		String r = null;
		if (point > 5) {
			r = "勝ち";
		} else if (point < 5) {
			r = "負け";
		} else {
			r = "同点";
		}
		String p = Integer.toString(point) + "点";
		resultLabel.setText(r);
		pointLabel.setText(p);
	}
}
