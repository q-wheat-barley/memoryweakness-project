package memoryweakness;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class MainPanel extends JPanel {
	
	JPanel[][] cards = new JPanel[4][5];
	int cs = 150;
	int[][] cn = new int[4][5];
	
	ArrayList<Integer> mc = new ArrayList<>();// memory coordinate
	ArrayList<Integer> mn = new ArrayList<>();
	ArrayList<Integer> pn = new ArrayList<>();// pair number
	
	int[][] toc = new int[2][2];
	//turn over card めくったカードの座標
	//{{一枚目y座標, 一枚目x座標}, {二枚目y座標, 二枚目x座標}}
	
	int ptoc; //player turn over count(自分のめくった数)
	int etoc; //enemy turn over count(相手のめくった数)
	
	boolean discard;
	
	int remainingCards;
	int playerPoint;
	int enemyPoint;
	
	JLabel ppl = new JLabel();//player point label
	JLabel epl = new JLabel();//computer point label
	
	int level = 1;
	
	public MainPanel() {
		this.setLayout(null);
		this.setBackground(new Color(100, 200, 100));
		
		reset();
		
		ppl.setText(Integer.toString(playerPoint));
		ppl.setBounds(0, 650, 100, 100);
		ppl.setFont(new Font(Font.DIALOG, Font.PLAIN, 50));
		ppl.setHorizontalAlignment(JLabel.CENTER);
		ppl.setForeground(Color.BLUE);
		this.add(ppl);
		
		epl.setText(Integer.toString(enemyPoint));
		epl.setBounds(880, 10, 100, 100);
		epl.setFont(new Font(Font.DIALOG, Font.PLAIN, 50));
		epl.setHorizontalAlignment(JLabel.CENTER);
		epl.setForeground(Color.RED);
		this.add(epl);
	}
	
	public void reset() {
		ptoc = 0;
		etoc = 0;
		discard = false;
		remainingCards = 20;
		playerPoint = 0;
		enemyPoint = 0;
		ppl.setText(Integer.toString(playerPoint));
		epl.setText(Integer.toString(enemyPoint));
		
		for (int i = 0; i < cards.length; i++) {
			for (int j = 0; j < cards[i].length; j++) {
				cards[i][j] = new JPanel();
				cards[i][j].setLayout(null);
				cards[i][j].setBounds(calcX(j), calcY(i), cs, cs);
				cards[i][j] = faceDown(cards[i][j]);
				this.add(cards[i][j]);
			}
		}
		
		ArrayList<Integer> n = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			n.add(i);
			n.add(i);
		}
		Collections.shuffle(n);
		for (int i = 0; i < cn.length; i++) {
			for (int j = 0; j < cn[i].length; j++) {
				cn[i][j] = n.get(0);
				n.remove(0);
			}
		}
	}
	
	public void moveEnemy() {
		switch (level) {
		case 0:// easy
			switch (etoc) {
			case 1:
				moveEasyFirst();
				break;
			case 2:
				moveEasySecond();
				break;
			case 3:
				endPhase();
				break;
			}
			break;
		case 1:// normal
			switch (etoc) {
			case 1:
				moveNormal();
				break;
			case 2:
				moveNormal();
				break;
			case 3:
				endPhase();
				break;
			}
			break;
		case 2:// hard
			switch (etoc) {
			case 1:
				moveHardFirst();
				break;
			case 2:
				moveHardSecond();
				break;
			case 3:
				endPhase();
				break;
			}
			break;
		}
	}
	
	private void moveEasyFirst() {
		int first = (int)(Math.random() * remainingCards);
		int i = 0;
		while (i <= first) {
			if (cards[i / 5][i % 5] == null) {
				first = (first + 1) % 20;
			}
			i++;
		}
		toc[0][0] = first / 5;
		toc[0][1] = first % 5;
		faceUp(cards[toc[0][0]][toc[0][1]], cn[toc[0][0]][toc[0][1]]);
		remember(first, cn[toc[0][0]][toc[0][1]]);
	}
	
	private void moveEasySecond() {
		int second = (int)(Math.random() * (remainingCards - 1));
		int i = 0;
		while (i <= second) {
			boolean not = cards[i / 5][i % 5] == null;
			boolean duplicate = i / 5 == toc[0][0] && i % 5 == toc[0][1];
			if (not || duplicate) {
				second = (second + 1) % 20;
			}
			i++;
		}
		toc[1][0] = second / 5;
		toc[1][1] = second % 5;
		faceUp(cards[toc[1][0]][toc[1][1]], cn[toc[1][0]][toc[1][1]]);
		remember(second, cn[toc[1][0]][toc[1][1]]);
	}
	
	private void moveNormal() {
		int choose = 0;
		int a = etoc - 1;
		boolean d = duplicate();
		if (d) {
			choose = mc.size() - a;
		} else {
			choose = remainingCards - a - mc.size();
		}
		int select = (int)(Math.random() * choose);
		int i = 0;
		if (d) {
			while (i <= select) {
				boolean not = cards[i / 5][i % 5] == null;
				boolean notSelected = !(mc.contains(i));
				boolean sameFirst = a == 1 && i == toc[0][0] * 5 + toc[0][1];
				if (not || notSelected || sameFirst) {
					select = (select + 1) % 20;
				}
				i++;
			}
		} else {
			while (i <= select) {
				boolean not = cards[i / 5][i % 5] == null;
				boolean selected = mc.contains(i);
				boolean sameFirst = a == 1 && i == toc[0][0] * 5 + toc[0][1];
				if (not || selected || sameFirst) {
					select = (select + 1) % 20;
				}
				i++;
			}
		}
		toc[a][0] = select / 5;
		toc[a][1] = select % 5;
		faceUp(cards[toc[a][0]][toc[a][1]], cn[toc[a][0]][toc[a][1]]);
		remember(select, cn[toc[a][0]][toc[a][1]]);
	}
	
	private void moveHardFirst() {
		boolean d = duplicate();
		if (d) {
			int first = mc.get(mn.indexOf(pn.get(0)));
			
			toc[0][0] = first / 5;
			toc[0][1] = first % 5;
			faceUp(cards[toc[0][0]][toc[0][1]], cn[toc[0][0]][toc[0][1]]);
			remember(first, cn[toc[0][0]][toc[0][1]]);
		} else {
			randomFU();
		}
	}
	
	private void moveHardSecond() {
		int firstN = cn[toc[0][0]][toc[0][1]];
		if (pn.contains(firstN)) {
			int second = mc.get(mn.indexOf(firstN));
			if (second == toc[0][0] * 5 + toc[0][1]) {
				second = mc.get(mn.lastIndexOf(firstN));
			}
			toc[1][0] = second / 5;
			toc[1][1] = second % 5;
			faceUp(cards[toc[1][0]][toc[1][1]], cn[toc[1][0]][toc[1][1]]);
			remember(second, cn[toc[1][0]][toc[1][1]]);
		} else {
			randomFU();
		}
	}
	
	private void randomFU() {
		int f = etoc - 1;
		int choices = remainingCards - mc.size() - f;
		int select = (int)(Math.random() * choices);
		int i = 0;
		while (i <= select) {
			boolean not = cards[i / 5][i % 5] == null;
			boolean selected = mc.contains(i);
			boolean sameFirst = f == 1 && i == toc[0][0] * 5 + toc[0][1];
			if (not || selected || sameFirst) {
				select = (select + 1) % 20;
			}
			i++;
		}
		toc[f][0] = select / 5;
		toc[f][1] = select % 5;
		faceUp(cards[toc[f][0]][toc[f][1]], cn[toc[f][0]][toc[f][1]]);
		remember(select, cn[toc[f][0]][toc[f][1]]);
	}
	
	public JPanel faceDown(JPanel card) {
		card.removeAll();
		card.setBackground(new Color(255, 0, 0));
		card.setBorder(new LineBorder(new Color(250, 250, 250)));
		return(card);
	}
	
	public JPanel faceUp(JPanel card, int number) {
		card.setBackground(Color.WHITE);
		
		JLabel label = new JLabel();
		label.setText(Integer.toString(number));
		label.setFont(new Font(Font.DIALOG, Font.PLAIN, 70));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBounds(0, 0, 150, 150);
		card.add(label);
		
		return(card);
	}
	
	private void endPhase() {
		discard = cn[toc[0][0]][toc[0][1]] == cn[toc[1][0]][toc[1][1]];
		if (discard) {
			discards();
			etoc = 0;
			egp();
		} else {
			faceDown(cards[toc[0][0]][toc[0][1]]);
			faceDown(cards[toc[1][0]][toc[1][1]]);
			ptoc = 0;
		}
	}
	
	public void discards() {
		remove(cards[toc[0][0]][toc[0][1]]);
		remove(cards[toc[1][0]][toc[1][1]]);
		cards[toc[0][0]][toc[0][1]] = null;
		cards[toc[1][0]][toc[1][1]] = null;
		forget();
		remainingCards -= 2;
		setBackground(Color.BLACK);
		setBackground(new Color(100, 200, 100));
		discard = false;
	}
	
	public void touch(int y, int x) {
		if (ptoc != 1 || toc[0][0] != y || toc[0][1] != x) {
			faceUp(cards[y][x], cn[y][x]);
			remember(y * 5 + x, cn[y][x]);
			toc[ptoc][0] = y;
			toc[ptoc][1] = x;
			ptoc++;
			if (ptoc >= 2) {
				if (cn[toc[0][0]][toc[0][1]] == cn[toc[1][0]][toc[1][1]]) {
					discard = true;
				}
			}
		}
	}
	
	private void remember(int cardCoordinate, int cardNumber) {
		if (!(mc.contains(cardCoordinate))) {
			mc.add(cardCoordinate);
			if (mn.contains(cardNumber)) {
				pn.add(cardNumber);
			}
			mn.add(cardNumber);
		}
	}
	
	private void forget() {
		int e = toc[0][0] * 5 + toc[0][1];
		int f = mc.indexOf(e);
		pn.remove(pn.indexOf(mn.get(f)));
		mc.remove(f);
		mn.remove(f);
		int r = toc[1][0] * 5 + toc[1][1];
		int s = mc.indexOf(r);
		mc.remove(s);
		mn.remove(s);
		
	}
	
	private boolean duplicate() {
		return(pn.size() > 0);
	}
	
	//player get point
	public void pgp() {
		playerPoint++;
		ppl.setText(Integer.toString(playerPoint));
	}
	
	//computer get point
	public void egp() {
		enemyPoint++;
		epl.setText(Integer.toString(enemyPoint));
	}
	
	public boolean end() {
		return(remainingCards <= 0);
	}
	
	private int calcX(int n) {
		n = 100 + n *(cs + 10);
		return(n);
	}
	
	private int calcY(int n) {
		n = 90 + n * (cs + 10);
		return(n);
	}
}
