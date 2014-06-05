package V3;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * 程序入口,窗体
 * @author 斌
 *
 */
public class Game extends JFrame {
	
	public static int[][] data=new int[4][4];
	static JLabel status = new JLabel("步数:0步");
	static JLabel score = new JLabel("得分:0分");
	static JLabel bigS = new JLabel("最大数字");
	JLabel about = new JLabel("版本号:0604");
	static JButton[][] jbtn=new JButton[4][4];
	static Color c[]=new Color[16];
	MyListener lis = new MyListener();
	File fAuto = new File("planAuto.dat");
	Config con=new Config();
	Undo temp;
	//初始化4*4方块
	{
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				jbtn[i][j]=new JButton();
				}
		}
	}
	
	ImageIcon iup=con.getImage("up.png");
	JButton up = new JButton(iup);
	
	ImageIcon idown=con.getImage("down.png");
	JButton down = new JButton(idown);
	ImageIcon ileft=con.getImage("left.png");
	JButton left = new JButton(ileft);
	ImageIcon iright=con.getImage("right.png");
	JButton right = new JButton(iright);
	
	JButton undo = new JButton("撤销");
	ImageIcon isave = con.getImage("save.png");
	JButton save = new JButton(isave);
	ImageIcon irec = con.getImage("rec.png");
	JButton rec= new JButton(irec);
	ImageIcon inew = con.getImage("new.png");
	JButton newGame= new JButton(inew);
	public static void main(String[] args) {
		Game game=new Game();
		game.init();
		game.initWidget();
		game.setVisible(true);
		game.initColor();
		game.start();
		game.setFocusable(true);
	}
	
	public void init(){
		this.setSize(560,450);
		this.setTitle("2048");
		this.setDefaultCloseOperation(3);
		this.setBackground(Color.gray);	
		this.setResizable(false);
		//设置窗体显示在屏幕中间
		this.setLocationRelativeTo(null);		
		//获得窗体的内容区域
		this.setLayout(null);
		this.addKeyListener(lis);
		this.addWindowListener(lis);
	}
	
	/**
	 * 初始化按钮
	 */
	public void initWidget(){
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				jbtn[i][j].setBounds(100*j, 100*i, 100, 100);
				//jbtn[i][j].setContentAreaFilled(false);
				jbtn[i][j].setBackground(new Color(200,200,200));
				jbtn[i][j].setFont(new Font("Dialog", Font.BOLD | Font.PLAIN, 24));
				this.add(jbtn[i][j]);
				//**********************************************************************************************//
				jbtn[i][j].setFocusable(false);//所有JButton不能设置为焦点，否则一点击按钮就键盘失效
				}
		}
		up.setBounds(450, 250, 48, 48);
		down.setBounds(450, 350, 48, 48);
		left.setBounds(400, 300, 48, 48);
		right.setBounds(500, 300, 48, 48);
		undo.setBounds(420,150,80,30);
		save.setBounds(420,190,30,30);
		rec.setBounds(460,190,30,30);
		newGame.setBounds(500,190,30,30);
		up.setContentAreaFilled(false);
		down.setContentAreaFilled(false);
		left.setContentAreaFilled(false);
		right.setContentAreaFilled(false);
		undo.setContentAreaFilled(false);
		save.setContentAreaFilled(false);
		rec.setContentAreaFilled(false);
		newGame.setContentAreaFilled(false);
		up.setActionCommand("上");
		down.setActionCommand("下");
		left.setActionCommand("左");
		right.setActionCommand("右");
		save.setActionCommand("保存");
		rec.setActionCommand("恢复");
		newGame.setActionCommand("新游戏");
		up.addActionListener(lis);
		down.addActionListener(lis);
		left.addActionListener(lis);
		right.addActionListener(lis);
		undo.addActionListener(lis);
		save.addActionListener(lis);
		rec.addActionListener(lis);
		newGame.addActionListener(lis);
		this.add(up);
		this.add(down);
		this.add(left);
		this.add(right);
		this.add(undo);
		this.add(save);
		this.add(rec);
		this.add(newGame);
		
		up.setFocusable(false);
		down.setFocusable(false);
		left.setFocusable(false);
		right.setFocusable(false);
		undo.setFocusable(false);
		save.setFocusable(false);
		rec.setFocusable(false);
		newGame.setFocusable(false);
		
		status.setBounds(420, 30, 100, 30);
		score.setBounds(420, 70, 100, 30);
		bigS.setBounds(420, 110, 100, 30);
		about.setBounds(420, 10, 100, 20);
		this.add(status);
		this.add(score);
		this.add(bigS);
		this.add(about);
		
	}

	/**
	 * 开始游戏
	 * 
	 */
	public void start(){
		if(fAuto.exists()){
			//如果记录存在,读取上一次记录,否则开始新的游戏
			planSave.readF(fAuto);
			temp = (Undo) MyListener.undoAl.get(MyListener.undoAl.size() - 1);

			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					Game.data[i][j] = temp.data[i][j];
				}
			}
			MyListener.S = temp.S;
			MyListener.score = temp.score;
			lis.show();
		}else{
			initGame();
		}
	}
	
	/**
	 * 初始化游戏
	 */
	public static void initGame(){
		Random rd = new Random();
		int first[]=new int [6];
		for(int i=0;i<4;i++){
			first[i]=rd.nextInt(4);
		}
		if(first[1]==first[3]&&first[0]==first[2]){
			if(first[0]!=3){
				first[0]+=1;
			}else{
				first[0]-=1;
			}
			
		}
		first[4]=2;
		first[5]=rd.nextInt(20);
		if(first[5]<19){
			first[5]=2;
		}else {
			first[5]=4;
		}
		
		data[first[0]][first[1]]=first[4];
		data[first[2]][first[3]]=first[5];
		
		
		if(first[4]==2){
			jbtn[first[0]][first[1]].setBackground(c[1]);
		}else{
			jbtn[first[0]][first[1]].setBackground(c[2]);
		}
		if(first[5]==2){
			jbtn[first[2]][first[3]].setBackground(c[1]);
		}else{
			jbtn[first[2]][first[3]].setBackground(c[2]);
		}
		jbtn[first[0]][first[1]].setText(""+first[4]);
		jbtn[first[2]][first[3]].setText(""+first[5]);

		if(MyListener.S<first[4]){
			MyListener.S=first[4];
		}else if(MyListener.S<first[5]){
			MyListener.S=first[5];
		}
		

		Undo undo = new Undo();
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				undo.data[i][j]=Game.data[i][j];
			}
		}
		MyListener.undoAl.add(undo);
//		System.out.println(MyListener.undoAl.size());
	}
	
	/**
	 * 重新开始
	 */
	public static void restart(){
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				Game.data[i][j]=0;
			}
		}
		MyListener.undoAl.clear();
		MyListener.S=2;
		MyListener.score=0;
		MyListener.status=0;
		initGame();
		
	}
	/**
	 * 设置颜色的方法
	 */
	public void initColor(){
		c[0]=new Color(200,200,200);
		c[1]= new Color(250,230,220);
		c[2]= new Color(250,190,140);
		c[3]= new Color(250,220,140);
		c[4]= new Color(250,250,140);
		c[5]= new Color(220,250,130);
		c[6]= new Color(190,250,130);
		c[7]= new Color(130,250,140);
		c[8]= new Color(140,250,240);
		c[9]= new Color(140,220,240);
		c[10]= new Color(140,180,240);
		c[11]= new Color(140,150,240);
		c[12]= new Color(180,140,240);
		c[13]= new Color(200,140,240);
		c[14]= new Color(240,140,240);
		c[15]=new Color(250,0,0);
		
	}
}
