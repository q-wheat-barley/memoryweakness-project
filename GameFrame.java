package memoryweakness;

import java.awt.CardLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameFrame extends JFrame implements MouseListener, ActionListener {
	
	CardLayout layouts = new CardLayout();
	JPanel cardPanel = new JPanel();
	
	ArrayList<JPanel> panels = new ArrayList<>();
	ArrayList<String> panelName = new ArrayList<>();
	int panelNumber;
	
	StartPanel startPanel = new StartPanel();
	MainPanel mainPanel = new MainPanel();
	ResultPanel resultPanel = new ResultPanel();
	
	Timer pTimer = new Timer(500, this); //player timer
	Timer eTimer = new Timer(500, this); //enemy timer
	
	public static void main(String[] args) {
		GameFrame gameframe = new GameFrame();
		gameframe.setVisible(true);
	}
	
	public GameFrame() {
		this.setTitle("神経衰弱");
		this.setSize(1000, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.addMouseListener(this);
		
		startPanel.setStrength(mainPanel.level);
		
		pTimer.setActionCommand("player");
		eTimer.setActionCommand("enemy");
		
		panels.add(startPanel);
		panels.add(mainPanel);
		panels.add(resultPanel);
		
		panelName.add("startPanel");
		panelName.add("mainPanel");
		panelName.add("resultPanel");
		
		panelNumber = 0;
		
		cardPanel.setLayout(layouts);
		for (int i = 0; i < panelName.size(); i++) {
			cardPanel.add(panels.get(i), panelName.get(i));
		}
		
		layouts.show(cardPanel, panelName.get(panelNumber));
		this.getContentPane().add(cardPanel);
	}
	
	public void actionPerformed(ActionEvent e) {
		String turn = e.getActionCommand();
		if (turn.equals("player")) {
			if (mainPanel.discard) {
				mainPanel.discards();
				mainPanel.ptoc = 0;
				mainPanel.pgp();
			} else {
				mainPanel.faceDown(mainPanel.cards[mainPanel.toc[0][0]][mainPanel.toc[0][1]]);
				mainPanel.faceDown(mainPanel.cards[mainPanel.toc[1][0]][mainPanel.toc[1][1]]);
				mainPanel.etoc = 0;
				eTimer.start();
			}
			pTimer.stop();
			
		} else if (turn.equals("enemy")) {
			mainPanel.etoc++;
			mainPanel.moveEnemy();
			if (!(mainPanel.discard) && mainPanel.etoc >= 3) {
				eTimer.stop();
			}
		}
		if (mainPanel.end()) {
			panelNumber = 2;
			eTimer.stop();
			resultPanel.setResult(mainPanel.playerPoint);
		}
		layouts.show(cardPanel, panelName.get(panelNumber));
	}
	
	public void mouseClicked(MouseEvent e) {
		Point point = e.getPoint();
		switch (panelNumber) {
		case 0:
			if (startPanel.sx <= point.x && point.x <= startPanel.sx + startPanel.sw && point.y >= 400 && point.y <= 600) {
				int py = (point.y - 400) / startPanel.si;
				if (py < startPanel.strength.length) {
					mainPanel.level = py;
				}
				startPanel.setStrength(mainPanel.level);
			} else if (390 <= point.x && point.x <= 620 && 680 <= point.y && point.y <= 730) {
				panelNumber = 1;
			}
			break;
		case 1:
			if (mainPanel.ptoc < 2) {
				boolean tx = point.x >= 107 && (point.x + 53) % 160 <= 150 && point.x <= 897;
				boolean ty = point.y >= 119 && (point.y + 41) % 160 <= 150 && point.y <= 749;
				if (tx && ty) {
					int cx = (point.x - 107) / 160;
					int cy = (point.y - 119) / 160;
					if (mainPanel.cards[cy][cx] != null) {
						mainPanel.touch(cy, cx);
						if (mainPanel.ptoc >= 2) {
							pTimer.start();
						}
					}
				}
			}
			break;
		case 2:
			if (707 <= point.x && point.x <= 780 && 580 <= point.y && point.y <= 620) {
				System.exit(0);
			} else if (707 <= point.x && point.x <= 863 && 520 <= point.y && point.y <= 560) {
				panelNumber = 1;
			} else if (707 <= point.x && point.x <= 945 && 463 <= point.y && point.y <= 500) {
				panelNumber = 0;
			}
			mainPanel.reset();
			break;
		}
		layouts.show(cardPanel, panelName.get(panelNumber));
	}
	
	public void mousePressed(MouseEvent e) {
		
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void mouseExited(MouseEvent e) {
		
	}
}
