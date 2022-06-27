import java.awt.*;
import javax.swing.*;
import java.util.*;
public class DealNoDeal {
	private final int SHUFFLES = 35;
	//chances, out of 1000, that one pattern is picked(based off 5 runs)
	private final int[] CHANCES = {38, 76, 43, 245, 120, 190, 212, 33, 11, 32};
	public static void main(String [] args) {
		DealNoDeal dnd = new DealNoDeal();
		dnd.run();
	}
	
	public void run() {
		JFrame frame = new JFrame("Deal or No Deal shuffler");
		Container c = frame.getContentPane();
		CaseCanvas canvas = new CaseCanvas();
		frame.setSize(850, 650);
		c.add(canvas);
		
		frame.setVisible(true);
		while (true) {
			canvas.showAllValues();
			shuffle(canvas);
			sleep(1000);
		}
	}
	
	private void shuffle(CaseCanvas canvas) {
		canvas.introShuffle();
		int rand;
		int check;
		for (int i=0; i<SHUFFLES; i++) {
			rand = randInt(1, 1000);
			check = CHANCES[0];
			if (rand <= check) {
				canvas.patternA();
				continue;
			}
			check += CHANCES[1];
			if (rand <= check) {
				canvas.patternB();
				continue;
			}
			check += CHANCES[2];
			if (rand <= check) {
				canvas.patternC();
				continue;
			}
			check += CHANCES[3];
			if (rand <= check) {
				canvas.patternD();
				continue;
			}
			check += CHANCES[4];
			if (rand <= check) {
				canvas.patternE();
				continue;
			}
			check += CHANCES[5];
			if (rand <= check) {
				canvas.patternF();
				continue;
			}
			check += CHANCES[6];
			if (rand <= check) {
				canvas.patternG();
				continue;
			}
			check += CHANCES[7];
			if (rand <= check) {
				canvas.patternH();
				continue;
			}
			check += CHANCES[8];
			if (rand <= check) {
				canvas.patternI();
				continue;
			} else {
				canvas.patternJ();
				continue;
			}
		}
		canvas.stopAll(); 
	}
	
	public int randInt(int min, int max) {
		return min + (int)(Math.random() * (max-min + 1));
	}
	
	private void sleep(int ms) {
		try {Thread.sleep(ms);} catch (Exception ex) {}
	}
}

class CaseCanvas extends JPanel {
	//private final ArrayList<Integer> DEAL_VALUES = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 10, 15, 20, 40, 50, 75, 100, 200));
	private final int[] DEAL_VALUES = {1, 2, 3, 4, 5, 6, 7, 8, 10, 15, 20, 40, 50, 75, 100, 200};
	//private final int[] DOUBLE_VALUES = {};
	private final int FRAME_LENGTH = 13;
	int[][] casenums = new int[4][4];
	private Case[] cases = new Case[16];
	
	public CaseCanvas() {
		ArrayList<Integer> casevalues = new ArrayList<Integer>();
		//thanks java i couldn't just use clone() because you keep telling me that a final variable is gonna not be the type i explicitly set it to be
		for (int i : DEAL_VALUES) {
			casevalues.add(i);
		}
		Collections.shuffle(casevalues);
		for (int i=0; i<16; i++) {
			cases[i] = new Case(i%4, i/4, casevalues.get(i));
			casenums[i/4][i%4] = i;
			this.add(cases[i]);
		}
		
	}
	
	public void introShuffle() {
		for (int i=0; i<3; i++) {
			patternJ();
			runFrames(10);
		}
	}
	
	public void patternA() {
		outerCCW();
		runFrames(10);
	}
	
	public void patternB() {
		swap(0, 2, 2);
		runFrames(10);
	}
	
	public void patternC() {
		ArrayList<Integer> takenPoints = new ArrayList<Integer>();
		ArrayList<Integer> directions = new ArrayList<Integer>();
		boolean valid = false;
		int newtest, newdir;
		int otherpoint = 0;
		for (int i=0; i<4; i++) {
			valid = false;
			while (!valid) {
				valid = true;
				newtest = randInt(1, 16);
				newdir = randInt(1, 4);
				switch (newdir) {
					case 1:
						otherpoint = newtest - 1;
						if (newtest % 4 == 1) {
							valid = false;
						}
						break;
					case 2:
						otherpoint = newtest + 1;
						if (newtest % 4 == 0) {
							valid = false;
						}
						break;
					case 3:
						otherpoint = newtest - 4;
						if ((newtest - 1) / 4 == 0) {
							valid = false;
						}
						break;
					case 4:
						otherpoint = newtest + 4;
						if ((newtest - 1) / 4 == 3) {
							valid = false;
						}
						break;
				}
				if (valid) {
					for (int j : takenPoints) {
						if (j == otherpoint || j == newtest) {
							valid = false;
							break;
						}
					}
				}
				if (valid) {
					takenPoints.add(newtest);
					takenPoints.add(otherpoint);
					directions.add(newdir);
				}
			}
		}
	
		for (int i=0; i<4; i++) {
			swap((takenPoints.get(i*2)-1)/4, (takenPoints.get(i*2)-1)%4, directions.get(i));
		}
		runFrames(10);
	}
	
	public void patternD() {
		swap(0, 0, 2);
		swap(1, 0, 2);
		swap(2, 0, 2);
		swap(3, 0, 2);
		rightCCW();
		runFrames(10);
	}
	
	public void patternE() {
		bottomCCW();
		topCCW();
		runFrames(10);
	}
	
	public void patternF() {
		swap(0, 0, 4);
		swap(0, 1, 4);
		swap(0, 2, 4);
		swap(0, 3, 4);
		swap(2, 0, 4);
		swap(2, 1, 4);
		swap(2, 2, 4);
		swap(2, 3, 4);
		runFrames(10);
	}
	
	public void patternG() {
		swap(0, 0, 4);
		swap(0, 1, 4);
		swap(0, 2, 4);
		swap(0, 3, 4);
		swap(2, 0, 2);
		swap(3, 0, 2);
		swap(2, 2, 2);
		swap(3, 2, 2);
		runFrames(10);
	}
	
	public void patternH() {
		swap(1, 1, 4);
		swap(1, 2, 4);
		runFrames(10);
	}
	
	public void patternI() {
		TLCCW();
		TRCW();
		BLCCW();
		BRCCW();
		runFrames(10);
	}
	
	public void patternJ() {
		outerCW();
		innerCCW();
		runFrames(10);
	}
	
	public void stopAll() {
		for (int i=0; i<16; i++) {
			cases[i].moveDir(0);
		}
	}
	
	private void swap(int row, int col, int dir) {
		int newrow = 0;
		int newcol = 0;
		int newdir = 0;
		switch (dir) {
			case 1:
				newrow = row;
				newcol = col - 1;
				newdir = 2;
				break;
			case 2:
				newrow = row;
				newcol = col + 1;
				newdir = 1;
				break;
			case 3:
				newrow = row - 1;
				newcol = col;
				newdir = 4;
				break;
			case 4:
				newrow = row + 1;
				newcol = col;
				newdir = 3;
				break;
			}
		cases[casenums[row][col]].moveDir(dir);
		cases[casenums[newrow][newcol]].moveDir(newdir);
		int placeholder = casenums[row][col];
		casenums[row][col] = casenums[newrow][newcol];
		casenums[newrow][newcol] = placeholder;
	}
	
	private void topCCW() {
		cases[casenums[0][0]].moveDir(4);
		cases[casenums[1][0]].moveDir(2);
		cases[casenums[1][1]].moveDir(2);
		cases[casenums[1][2]].moveDir(2);
		cases[casenums[1][3]].moveDir(3);
		cases[casenums[0][3]].moveDir(1);
		cases[casenums[0][2]].moveDir(1);
		cases[casenums[0][1]].moveDir(1);
		int placeholder = casenums[0][0];
		casenums[0][0] = casenums[0][1];
		casenums[0][1] = casenums[0][2];
		casenums[0][2] = casenums[0][3];
		casenums[0][3] = casenums[1][3];
		casenums[1][3] = casenums[1][2];
		casenums[1][2] = casenums[1][1];
		casenums[1][1] = casenums[1][0];
		casenums[1][0] = placeholder;
	}
	
	private void bottomCCW() {
		cases[casenums[2][0]].moveDir(4);
		cases[casenums[3][0]].moveDir(2);
		cases[casenums[3][1]].moveDir(2);
		cases[casenums[3][2]].moveDir(2);
		cases[casenums[3][3]].moveDir(3);
		cases[casenums[2][3]].moveDir(1);
		cases[casenums[2][2]].moveDir(1);
		cases[casenums[2][1]].moveDir(1);
		int placeholder = casenums[2][0];
		casenums[2][0] = casenums[2][1];
		casenums[2][1] = casenums[2][2];
		casenums[2][2] = casenums[2][3];
		casenums[2][3] = casenums[3][3];
		casenums[3][3] = casenums[3][2];
		casenums[3][2] = casenums[3][1];
		casenums[3][1] = casenums[3][0];
		casenums[3][0] = placeholder;
	}
	
	private void rightCCW() {
		cases[casenums[0][2]].moveDir(2);
		cases[casenums[0][3]].moveDir(4);
		cases[casenums[1][3]].moveDir(4);
		cases[casenums[2][3]].moveDir(4);
		cases[casenums[3][3]].moveDir(1);
		cases[casenums[3][2]].moveDir(3);
		cases[casenums[2][2]].moveDir(3);
		cases[casenums[1][2]].moveDir(3);
		int placeholder = casenums[0][2];
		casenums[0][2] = casenums[1][2];
		casenums[1][2] = casenums[2][2];
		casenums[2][2] = casenums[3][2];
		casenums[3][2] = casenums[3][3];
		casenums[3][3] = casenums[2][3];
		casenums[2][3] = casenums[1][3];
		casenums[1][3] = casenums[0][3];
		casenums[0][3] = placeholder;
	}
	
	private void outerCCW() {
		cases[casenums[0][0]].moveDir(4);
		cases[casenums[1][0]].moveDir(4);
		cases[casenums[2][0]].moveDir(4);
		cases[casenums[3][0]].moveDir(2);
		cases[casenums[3][1]].moveDir(2);
		cases[casenums[3][2]].moveDir(2);
		cases[casenums[3][3]].moveDir(3);
		cases[casenums[2][3]].moveDir(3);
		cases[casenums[1][3]].moveDir(3);
		cases[casenums[0][3]].moveDir(1);
		cases[casenums[0][2]].moveDir(1);
		cases[casenums[0][1]].moveDir(1);
		int placeholder = casenums[0][0];
		casenums[0][0] = casenums[0][1];
		casenums[0][1] = casenums[0][2];
		casenums[0][2] = casenums[0][3];
		casenums[0][3] = casenums[1][3];
		casenums[1][3] = casenums[2][3];
		casenums[2][3] = casenums[3][3];
		casenums[3][3] = casenums[3][2];
		casenums[3][2] = casenums[3][1];
		casenums[3][1] = casenums[3][0];
		casenums[3][0] = casenums[2][0];
		casenums[2][0] = casenums[1][0];
		casenums[1][0] = placeholder;
	}
	
	private void outerCW() {
		cases[casenums[0][0]].moveDir(2);
		cases[casenums[0][1]].moveDir(2);
		cases[casenums[0][2]].moveDir(2);
		cases[casenums[0][3]].moveDir(4);
		cases[casenums[1][3]].moveDir(4);
		cases[casenums[2][3]].moveDir(4);
		cases[casenums[3][3]].moveDir(1);
		cases[casenums[3][2]].moveDir(1);
		cases[casenums[3][1]].moveDir(1);
		cases[casenums[3][0]].moveDir(3);
		cases[casenums[2][0]].moveDir(3);
		cases[casenums[1][0]].moveDir(3);
		int placeholder = casenums[0][0];
		casenums[0][0] = casenums[1][0];
		casenums[1][0] = casenums[2][0];
		casenums[2][0] = casenums[3][0];
		casenums[3][0] = casenums[3][1];
		casenums[3][1] = casenums[3][2];
		casenums[3][2] = casenums[3][3];
		casenums[3][3] = casenums[2][3];
		casenums[2][3] = casenums[1][3];
		casenums[1][3] = casenums[0][3];
		casenums[0][3] = casenums[0][2];
		casenums[0][2] = casenums[0][1];
		casenums[0][1] = placeholder;
	}
	
	private void innerCCW() {
		cases[casenums[1][1]].moveDir(4);
		cases[casenums[2][1]].moveDir(2);
		cases[casenums[2][2]].moveDir(3);
		cases[casenums[1][2]].moveDir(1);
		int placeholder = casenums[1][1];
		casenums[1][1] = casenums[1][2];
		casenums[1][2] = casenums[2][2];
		casenums[2][2] = casenums[2][1];
		casenums[2][1] = placeholder;
	}
	
	private void TLCCW() {
		cases[casenums[0][0]].moveDir(4);
		cases[casenums[1][0]].moveDir(2);
		cases[casenums[1][1]].moveDir(3);
		cases[casenums[0][1]].moveDir(1);
		int placeholder = casenums[0][0];
		casenums[0][0] = casenums[0][1];
		casenums[0][1] = casenums[1][1];
		casenums[1][1] = casenums[1][0];
		casenums[1][0] = placeholder;
	}
	
	private void TRCW() {
		cases[casenums[0][2]].moveDir(2);
		cases[casenums[0][3]].moveDir(4);
		cases[casenums[1][3]].moveDir(1);
		cases[casenums[1][2]].moveDir(3);
		int placeholder = casenums[0][2];
		casenums[0][2] = casenums[1][2];
		casenums[1][2] = casenums[1][3];
		casenums[1][3] = casenums[0][3];
		casenums[0][3] = placeholder;
	}

	private void BLCCW() {
		cases[casenums[2][0]].moveDir(4);
		cases[casenums[3][0]].moveDir(2);
		cases[casenums[3][1]].moveDir(3);
		cases[casenums[2][1]].moveDir(1);
		int placeholder = casenums[2][0];
		casenums[2][0] = casenums[2][1];
		casenums[2][1] = casenums[3][1];
		casenums[3][1] = casenums[3][0];
		casenums[3][0] = placeholder;
	}
	
	private void BRCCW() {
		cases[casenums[2][2]].moveDir(4);
		cases[casenums[3][2]].moveDir(2);
		cases[casenums[3][3]].moveDir(3);
		cases[casenums[2][3]].moveDir(1);
		int placeholder = casenums[2][2];
		casenums[2][2] = casenums[2][3];
		casenums[2][3] = casenums[3][3];
		casenums[3][3] = casenums[3][2];
		casenums[3][2] = placeholder;
	}
	
	private void runFrames(int frames) {
		for (int i=0; i<frames; i++) {
            repaint();
            try {Thread.sleep(FRAME_LENGTH);} catch (Exception ex) {System.out.println("something went wrong lol");}
        }
	}

	public void showAllValues() {
		for (int i=0; i<16; i++) {
			cases[i].showValue();
		}
		runFrames(210);
	}

	private int randInt(int min, int max) {
		return min + (int)(Math.random() * (max-min + 1));
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		for (Case i : cases) {
			i.paint(g);
		}
	}
	
	
}

class Case extends JPanel {
	//make height and width always multiples of 10
	private final int HEIGHT = 100;
	private final int WIDTH = 150;
	private final int BORDER_SIZE = 4;
	private final int FONT_SIZE = 50;
	
	public int framesLeft;
	private int dir; //0 = no dir, 1 = left, 2 = right, 3 = up, 4 = down
	private int xpos;
	private int ypos;
	private int value;
	private boolean showValue;
	public Case() {
		xpos = 25;
		ypos = 25;
		framesLeft = 0;
		value = 0;
		showValue = false;
	}
	
	public Case(int x, int y, int initvalue) {
		xpos = 25 + (WIDTH + 50) * x;
		ypos = 25 + (HEIGHT + 50) * y;
		framesLeft = 0;
		value = initvalue;
		showValue = false;
		
	}
	
	public void paint(Graphics g) {
		if (framesLeft > 0) {
			/*if (value == 1) {
				System.out.println(framesLeft);
			}*/
			framesLeft--;
			switch (dir) {
				case 1:
					xpos = Math.max(0, xpos - ((WIDTH + 50)/10));
					break;
				case 2:
					xpos = Math.min(850, xpos + ((WIDTH + 50)/10));
					break;
				case 3:
					ypos = Math.max(0, ypos - ((HEIGHT + 50)/10));
					break;
				case 4:
					ypos = Math.min(650, ypos + ((HEIGHT + 50)/10));
					break;
			}
		} else {
			showValue = false;
			dir = 0;
		}
		
		g.setColor(Color.BLACK);
		g.fillRect(xpos, ypos, WIDTH, HEIGHT);
		g.setColor(Color.WHITE);
		g.fillRect(xpos + BORDER_SIZE, ypos + BORDER_SIZE, WIDTH - 2*BORDER_SIZE, HEIGHT - 2*BORDER_SIZE);
		if (showValue) {
			g.setFont(new Font("Eurostile", Font.BOLD, FONT_SIZE));
			g.setColor(Color.BLACK);
			g.drawString(Integer.toString(value), textXpos(), ypos + (HEIGHT)/2 + (FONT_SIZE)/3);
		}
	}

	public void moveDir(int direction) {
		framesLeft = 10;
		dir = direction;
	}

	private int textXpos() {
		if (value >= 100) {
			return xpos + (WIDTH - FONT_SIZE)/2 - 15;
		} else if (value >= 10) {
			return xpos + (WIDTH - FONT_SIZE)/2 - 3;
		}
		return xpos + (WIDTH - FONT_SIZE)/2 + 10;
	}

	public void showValue() {
		showValue = true;
		framesLeft = 187;
	}
}