package V3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * 监听器,窗体刷新
 * @author 斌
 * 
 */
public class MyListener implements ActionListener, KeyListener ,WindowListener {

	File f = new File("plan.dat");
	File fAuto = new File ("planAuto.dat");
	static ArrayList<Undo> undoAl = new ArrayList<Undo>();
	Random r = new Random();
	static int status = 0;
	static int score = 0;// 记录分数
	int data1;
	Undo temp;
	int idata;
	int count = 0;
	static int S = 2;// 用来统计是否达到2048
	int ctrl = 0, s = 0, z = 0;// 复合功能键
	Boolean enter = true;
	

	/**
	 * 监听器
	 */
	public void actionPerformed(ActionEvent e) {
		String source = e.getActionCommand();
		count = 0;

		// 判断是否能够继续
		int k;
		for (k = 0; k < 3; k++) {
			int j;
			for (j = 0; j < 3; j++) {

				if (Game.data[k][j] == 0
						|| Game.data[k][j] == Game.data[k][j + 1]
						|| Game.data[k][j] == Game.data[k + 1][j]) {
					break;
				} else if (Game.data[k][j + 1] == 0) {
					break;
				} else if (Game.data[k + 1][j] == 0) {
					break;
				}
			}
			if (j < 3) {
				break;
			}
		}
		if (k == 3) {
			// 已经无路可走了
			if (source.equals("上")) {
				setUp();
			}
			if (source.equals("下")) {
				setDown();
			}
			if (source.equals("左")) {
				setLeft();
			}
			if (source.equals("右")) {
				setRight();
			}
			show();// 先刷新屏幕
			JOptionPane.showMessageDialog(null, "你输了！");
			Game.restart();
			show();// 刷新屏幕
			return;
		}

		if (source.equals("上")) {
			beUp();
		} else if (source.equals("下")) {
			beDown();
		} else if (source.equals("左")) {
			beLeft();
		} else if (source.equals("右")) {
			beRight();
		} else if (source.equals("撤销")) {
			undo();
		} else if (source.equals("保存")) {
			planSave.writeF(f);
		} else if (source.equals("恢复")) {
			// JOptionPane.YES_NO_OPTION("");
			planSave.readF(f);
			temp = (Undo) undoAl.get(undoAl.size() - 1);

			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					Game.data[i][j] = temp.data[i][j];
				}
			}
			S = temp.S;
			score = temp.score;
			
			//undoAl.clear();
			show();
		} else if (source.equals("新游戏")) {
			Game.restart();
			show();// 刷新屏幕
			return;
		}

		nextStatu();

	}

	/**
	 * 撤销的方法
	 * 
	 */
	public void undo() {
		//System.out.println(undoAl.size());
		if (undoAl.size() > 1) {

			undoAl.remove(undoAl.size() - 1);
			status--;

			temp = (Undo) undoAl.get(undoAl.size() - 1);

			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					Game.data[i][j] = temp.data[i][j];
				}
			}
			S = temp.S;
			score = temp.score;
			show();
		}
		
	}

	public void setUp() {

		for (int k = 0; k < 4; k++) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 4; j++) {
					if (Game.data[i][j] == 0 && Game.data[i + 1][j] != 0) {
						Game.data[i][j] = Game.data[i + 1][j];
						Game.data[i + 1][j] = 0;
						count = 1;
					}
				}
			}
		}

	}

	public void setDown() {
		for (int k = 0; k < 4; k++) {
			for (int i = 3; i > 0; i--) {
				for (int j = 0; j < 4; j++) {
					if (Game.data[i][j] == 0 && Game.data[i - 1][j] != 0) {
						Game.data[i][j] = Game.data[i - 1][j];
						Game.data[i - 1][j] = 0;
						count = 1;
					}

				}

			}
		}

	}

	public void setLeft() {
		for (int k = 0; k < 4; k++) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 3; j++) {
					if (Game.data[i][j] == 0 && Game.data[i][j + 1] != 0) {
						Game.data[i][j] = Game.data[i][j + 1];
						Game.data[i][j + 1] = 0;
						count = 1;
					}

				}

			}
		}

	}

	public void setRight() {
		for (int k = 0; k < 4; k++) {
			for (int i = 0; i < 4; i++) {
				for (int j = 3; j > 0; j--) {
					if (Game.data[i][j] == 0 && Game.data[i][j - 1] != 0) {
						Game.data[i][j] = Game.data[i][j - 1];
						Game.data[i][j - 1] = 0;
						count = 1;
					}

				}

			}
		}

	}

	/**
	 * 刷新屏幕上的数字
	 */
	public void show() {
		// 结果显示在窗体上
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (Game.data[i][j] == 0) {
					Game.jbtn[i][j].setText("");
					Game.jbtn[i][j].setBackground(Game.c[0]);
				} else {

					Game.jbtn[i][j].setText("" + Game.data[i][j]);
					if (Game.data[i][j] == 2) {
						Game.jbtn[i][j].setBackground(Game.c[1]);
					} else if (Game.data[i][j] == 4) {
						Game.jbtn[i][j].setBackground(Game.c[2]);
					} else if (Game.data[i][j] == 8) {
						Game.jbtn[i][j].setBackground(Game.c[3]);
					} else if (Game.data[i][j] == 16) {
						Game.jbtn[i][j].setBackground(Game.c[4]);
					} else if (Game.data[i][j] == 32) {
						Game.jbtn[i][j].setBackground(Game.c[5]);
					} else if (Game.data[i][j] == 64) {
						Game.jbtn[i][j].setBackground(Game.c[6]);
					} else if (Game.data[i][j] == 128) {
						Game.jbtn[i][j].setBackground(Game.c[7]);
					} else if (Game.data[i][j] == 256) {
						Game.jbtn[i][j].setBackground(Game.c[8]);
					} else if (Game.data[i][j] == 512) {
						Game.jbtn[i][j].setBackground(Game.c[9]);
					} else if (Game.data[i][j] == 1024) {
						Game.jbtn[i][j].setBackground(Game.c[10]);
					} else if (Game.data[i][j] == 2048) {
						Game.jbtn[i][j].setBackground(Game.c[11]);
					} else if (Game.data[i][j] == 4096) {
						Game.jbtn[i][j].setBackground(Game.c[12]);
					} else if (Game.data[i][j] == 8192) {
						Game.jbtn[i][j].setBackground(Game.c[13]);
					} else if (Game.data[i][j] == 16384) {
						Game.jbtn[i][j].setBackground(Game.c[14]);
					} else {
						Game.jbtn[i][j].setBackground(Game.c[15]);
					}

					if (S < Game.data[i][j]) {
						S = Game.data[i][j];
					}

				}

			}
		}

		Game.status.setText("步数:" + status + "步");
		Game.score.setText("得分:" + score + "分");
		Game.bigS.setText("最大数字:" + S);
	}

	/**
	 * 按下上键执行的方法
	 */
	public void beUp() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				if (Game.data[i][j] != 0) {
					// 相同则相加赋值到上面那个
					if (Game.data[i][j] == Game.data[i + 1][j]) {
						Game.data[i][j] *= 2;
						count = 1;
						score += Game.data[i][j];
						Game.data[i + 1][j] = 0;
						continue;
					}

					if (i < 2) {
						if (Game.data[i + 1][j] == 0
								&& Game.data[i][j] == Game.data[i + 2][j]) {
							Game.data[i][j] *= 2;
							count = 1;
							score += Game.data[i][j];
							Game.data[i + 2][j] = 0;
							continue;
						}
					}

					if (i == 0) {
						if (Game.data[i + 1][j] == 0
								&& Game.data[i + 2][j] == 0
								&& Game.data[i][j] == Game.data[i + 3][j]) {
							Game.data[i][j] *= 2;
							count = 1;
							score += Game.data[i][j];
							Game.data[i + 3][j] = 0;
						}
					}
				}

			}
		}
		// 整体上移
		setUp();
	}

	/**
	 * 按下下键执行的方法
	 */
	public void beDown() {
		for (int i = 3; i > 0; i--) {
			for (int j = 0; j < 4; j++) {
				if (Game.data[i][j] != 0) {
					// 相同则相加赋值到上面那个
					if (Game.data[i][j] == Game.data[i - 1][j]) {
						Game.data[i][j] *= 2;
						count = 1;
						score += Game.data[i][j];
						Game.data[i - 1][j] = 0;
						continue;
					}
					if (i >= 2) {
						if (Game.data[i - 1][j] == 0
								&& Game.data[i][j] == Game.data[i - 2][j]) {
							Game.data[i][j] *= 2;
							count = 1;
							score += Game.data[i][j];
							Game.data[i - 2][j] = 0;
							continue;
						}
					}

					if (i == 3) {
						if (Game.data[i - 1][j] == 0
								&& Game.data[i - 2][j] == 0
								&& Game.data[i][j] == Game.data[i - 3][j]) {
							Game.data[i][j] *= 2;
							count = 1;
							score += Game.data[i][j];
							Game.data[i - 3][j] = 0;
						}
					}

				}

			}
		}
		// 整体下移
		setDown();
	}

	/**
	 * 按下左键执行的方法
	 * 
	 */

	public void beLeft() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				if (Game.data[i][j] != 0) {
					// 相同则相加赋值到上面那个
					if (Game.data[i][j] == Game.data[i][j + 1]) {
						Game.data[i][j] *= 2;
						count = 1;
						score += Game.data[i][j];
						Game.data[i][j + 1] = 0;
						continue;
					}
					if (j < 2) {
						if (Game.data[i][j + 1] == 0
								&& Game.data[i][j] == Game.data[i][j + 2]) {
							Game.data[i][j] *= 2;
							count = 1;
							score += Game.data[i][j];
							Game.data[i][j + 2] = 0;
							continue;
						}
					}

					if (j == 0) {
						if (Game.data[i][j + 1] == 0
								&& Game.data[i][j + 2] == 0
								&& Game.data[i][j] == Game.data[i][j + 3]) {
							Game.data[i][j] *= 2;
							count = 1;
							score += Game.data[i][j];
							Game.data[i][j + 3] = 0;
						}
					}
				}

			}
		}
		// 整体左移
		setLeft();
	}

	/**
	 * 按下右键执行的方法
	 */
	public void beRight() {
		for (int i = 0; i < 4; i++) {
			for (int j = 3; j > 0; j--) {
				if (Game.data[i][j] != 0) {
					// 相同则相加赋值到上面那个
					if (Game.data[i][j] == Game.data[i][j - 1]) {
						Game.data[i][j] *= 2;
						count = 1;
						score += Game.data[i][j];
						Game.data[i][j - 1] = 0;
						j--;
						continue;
					}
					if (j >= 2) {
						if (Game.data[i][j - 1] == 0
								&& Game.data[i][j] == Game.data[i][j - 2]) {
							Game.data[i][j] *= 2;
							count = 1;
							score += Game.data[i][j];
							Game.data[i][j - 2] = 0;
							continue;
						}
					}

					if (j == 3) {
						if (Game.data[i][j - 1] == 0
								&& Game.data[i][j - 2] == 0
								&& Game.data[i][j] == Game.data[i][j - 3]) {
							Game.data[i][j] *= 2;
							count = 1;
							score += Game.data[i][j];
							Game.data[i][j - 3] = 0;
						}
					}
				}

			}
		}
		// 整体右移
		setRight();
	}

	/**
	 * 继续下一步操作
	 */
	public void nextStatu(){
		// 生成新的数字

		if (count == 1) {

			for (int i = 0; i < 4; i++) {
				int j;
				for (j = 0; j < 4; j++) {
					if (Game.data[i][j] == 0) {// 进到这里说明发现了一个空的格子,可以准备生成下一个数字
						status++;
						data1 = r.nextInt(16);// 随机生成一个位置
						while (Game.data[(data1) / 4][(data1) % 4] != 0) {
							data1 = r.nextInt(16);
						}
						idata = r.nextInt(80);// 新创建一个数字
						if (idata < 74) {
							idata = 2;

						} else if (idata < 77) {
							idata = 4;
						} else if (idata < 79) {
							idata = 8;
						} else {
							idata = 16;
						}
						break;
					} else {
						continue;
					}
				}
				if (j < 4) {
					break;
				}
			}

			Game.data[(data1) / 4][(data1) % 4] = idata;

			Undo undo = new Undo();
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					undo.data[i][j] = Game.data[i][j];
				}
			}
			undo.S = S;
			undo.score = score;
			undoAl.add(undo);

			show();
		}

		// 如果达到2048,弹出提示框
		if (S == 2048) {
			JOptionPane.showMessageDialog(null, "你赢了！");
		}
	}
	public void keyPressed(KeyEvent e) {

		// 1运行
		int jian = e.getKeyCode();
		// System.out.println("keyPressed");
		// System.out.println(jian);
		if (jian == 83) {// s键被按下

			s = 1;
		} else if (jian == 17) {// Ctrl键被按下
			ctrl = 1;
		} else if (jian == 90) {// z键被按下
			z = 1;
		}
		if (enter) {
			if (s == 1) {//第一个按下的是不是ctrl,如果第一个是ctrl则为保存（此处逻辑上正好相反）
				if(ctrl == 1){
					//System.out.println(">>>>>保存");
					planSave.writeF(f);
				}
				enter = false;
			}else if(z ==1) {
				if(ctrl == 1){
					//System.out.println(">>>>>撤销");
					undo();
				}
				enter = false;
			}
			
		}

	}

	public void keyReleased(KeyEvent e) {
		// 3运行
		// System.out.println("keyReleased");

		
		
		int jian = e.getKeyCode();

		if (jian == 17) {
			ctrl = 0;

		}
		
		// 判断是否能够继续
		int k;
		for (k = 0; k < 3; k++) {
			int j;
			for (j = 0; j < 3; j++) {

				if (Game.data[k][j] == 0
						|| Game.data[k][j] == Game.data[k][j + 1]
						|| Game.data[k][j] == Game.data[k + 1][j]) {
					break;
				} else if (Game.data[k][j + 1] == 0) {
					break;
				} else if (Game.data[k + 1][j] == 0) {
					break;
				}
			}
			if (j < 3) {
				break;
			}
		}
		if (k == 3) {
			// 已经无路可走了
			if (jian == 38 || jian == 87 || jian == 104) {
				setUp();
			}
			if ((ctrl == 0 && jian == 83) || jian == 40 || jian == 98) {
				setDown();
			}
			if (jian == 37 || jian == 65 || jian == 100) {
				setLeft();
			}
			if (jian == 39 || jian == 68 || jian == 102) {
				setRight();
			}
			show();// 先刷新屏幕
			JOptionPane.showMessageDialog(null, "你输了！");
			Game.restart();
			show();// 刷新屏幕
			return;
		}
		
		
		// 37左38上39右40下65a90z 65 87 68 83（awds） 100 104 102 98(小键盘4862)
		if (jian == 37 || jian == 65 || jian == 100) {
			// 左键
			//System.out.println("左键");
			beLeft();
			nextStatu();
		} else if (jian == 38 || jian == 87 || jian == 104) {
			// 上键
			//System.out.println("上键");
			beUp();
			nextStatu();
		} else if (jian == 39 || jian == 68 || jian == 102) {
			// 右键
			//System.out.println("右键");
			beRight();
			nextStatu();
		} else if ((ctrl == 0 && jian == 83) || jian == 40 || jian == 98) {
			// 下键
			//System.out.println("下键");
			beDown();
			nextStatu();
		}

		
		if (jian == 83) {
			s = 0;
			enter = true;
		}
		if(jian == 90){
			z = 0;
			enter = true;
		}

	}

	public void windowClosing(WindowEvent e) {
		//关闭窗口时保存当前数据
		planSave.writeF(fAuto);
		
	}
	
	public void keyTyped(KeyEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowClosed(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}

}
