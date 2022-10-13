package memoryweakness;

import java.awt.Color;

import javax.swing.JPanel;

public class StartPanel extends JPanel {
	
	SetLabel tLabel = new SetLabel("MEMORYWEAKNESS", 50, 75, 900, 320);
	SetLabel sLabel = new SetLabel("はじめる", 300, 550, 400, 250);
	SetLabel aLabel = new SetLabel("プレイヤーが先攻", 0, 300, 600, 200);
	SetLabel eLabel = new SetLabel("クリックで操作", 0, 370, 600, 200);
	
	int sx = 550, sy = 300, sw = 400, sh = 200, si = 64;
	SetLabel[] strength = new SetLabel[3];
	String[] st = {"弱い", "普通", "強い"};
	
	public StartPanel() {
		setLayout(null);
		setBackground(new Color(40, 40, 200));
		
		add(tLabel);
		add(sLabel);
		add(aLabel);
		add(eLabel);
		
		for (int i = 0; i < strength.length; i++) {
			strength[i] = new SetLabel(st[i], sx, sy + si * i, sw, sh);
			add(strength[i]);
		}
		
	}
	
	public void setStrength(int level) {
		for (int i = 0; i < strength.length; i++) {
			if (i == level) {
				strength[i].setForeground(new Color(255, 170, 0));
			} else {
				strength[i].setForeground(Color.WHITE);
			}
		}
	}
}
