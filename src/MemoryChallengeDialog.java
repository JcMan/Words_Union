import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class MemoryChallengeDialog extends JDialog implements ActionListener{
	private JLabel label1,label2,label3,label4;
	private JTextField answer;
	private JButton button1,button2,button3,button4,startButton;
	private JLabel label[] = new JLabel[5];
	final JButton b1 = new JButton("确定");
	final JTextField t1 = new JTextField();
	final JButton b2 = new JButton("确定");
	final JTextField t2 = new JTextField();
	private int number=0,second=0;
	private int num[],time=0;
	String ss[];
	boolean isStop = false;
	private JLabel timelabel;
	private JLabel numLabel;
	public MemoryChallengeDialog(){
		int h=Toolkit.getDefaultToolkit().getScreenSize().height;
		int w=Toolkit.getDefaultToolkit().getScreenSize().width;
		this.setSize(w, h);
		this.setTitle("单词复习");
		this.setUndecorated(true);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(null);
		addLabels();
		addAnswerField();
		addMeaningLabels();
		addButtons();
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==button1){
			t1.setText(null);
			b1.setVisible(true);
			t1.setVisible(true);
		}else if(e.getSource()==b1){
			try{
				second = Integer.parseInt(t1.getText());
			}catch(Exception ee){}
			b1.setVisible(false);
			t1.setVisible(false);
		}else if(e.getSource()==button4){
			RepeatWord.backGroundClip.loop();
			setVisible(false);
		}else if(e.getSource()==b2){
			try{
				number = Integer.parseInt(t2.getText());
			}catch(Exception e1){}
			if(number>AddWordsDialog.getLastWordPosition()){
				JOptionPane.showMessageDialog(null, "单词数量超过所存的单词数，请重新输入！");
				t2.setText(null);
				t2.requestFocus();
				number=0;
			}else{
				b2.setVisible(false);
				t2.setVisible(false);
			}
		}else if(e.getSource()==button2){
			t2.setText(null);
			b2.setVisible(true);
			t2.setVisible(true);
		}else if(e.getSource()==startButton){
			if(number==0||second==0)
				return ;
			isStop = false;
			Thread th = new Thread(new Runnable(){
				public void run(){
					while(true){
						timelabel.setText(""+second);
						try{
							Thread.sleep(1000);
							if(isStop == true)
								return ;
							second--;
							if(second==0){
								timelabel.setText("时间到！");
								isStop = true;
								timelabel.setText(null);
								number=0;
								second=0;
								answer.setText(null);
								for(int i=0;i<label.length;i++)
									label[i].setVisible(false);
								numLabel.setText(null);
								label4.setText(null);
								JOptionPane.showMessageDialog(null, "挑战失败！");
								return ;
							}
						}catch(Exception eq){}
					}
				}
			});
			th.start();
			num = new int[number];
			int a ;
			for(int i=0;i<num.length;i++){
				a = new Random().nextInt(AddWordsDialog.getLastWordPosition())+1;
				int j=0;
				while(true){
					if(num[j]!=a){
						j++;
						if(j==number)
							break;                                         //算法，产生乱序数
						if(num[j]==0){
							num[i]=a;
							break;
						}
					}else{
						j=0;
						a = new Random().nextInt(number)+1;
					}
					
				}
			}
			number=0;
			try{
				BufferedReader bf = new BufferedReader(new FileReader("word.txt"));
				String str;
				while((str=bf.readLine())!=null){
					if(str.endsWith(""+num[number])){
						ss=str.split("#");
						label4.setText(ss[1]);
						for(int i=2;i<label.length;i++){
							if(!ss[i].equals(""+num[number])){
								label[i-2].setVisible(true);
								label[i-2].setText(ss[i]);
							}
						}
					}		
				}
				bf.close();
			}catch(Exception er){}
		}else if(e.getSource()==button3){
			isSuccessful();
		}
		else if(e.getSource()==answer){
			isSuccessful();
		}
	}
	void isSuccessful(){
		String st=answer.getText();
		String str;
		if(st.length()>0){
			if(st.equals(ss[0])){
				answer.setText(null);
				for(int i=0;i<label.length;i++)
					label[i].setVisible(false);
				number++;
				numLabel.setText(""+number);
				if(number==num.length){
					isStop = true;
					timelabel.setText(null);
					numLabel.setText(null);
					label4.setText(null);
					for(int i=0;i<label.length;i++)
						label[i].setVisible(false);
					number=0;
					second=0;
					JOptionPane.showMessageDialog(null, "挑战成功！");
				}
				else{
					try{
						BufferedReader bf = new BufferedReader(new FileReader("word.txt"));
						while((str=bf.readLine())!=null){
							if(str.endsWith(""+num[number])){
								ss=str.split("#");
								label4.setText(ss[1]);
								for(int i=2;i<label.length;i++){
									if(i<ss.length-1){
										label[i-2].setVisible(true);
										label[i-2].setText(ss[i]);
									}
								}
							}		
						}
						bf.close();
					}catch(Exception er){}
				}
				
			}else{
				isStop = true;
				timelabel.setText(null);
				number=0;
				second=0;
				answer.setText(null);
				for(int i=0;i<label.length;i++)
					label[i].setVisible(false);
				numLabel.setText(null);
				label4.setText(null);
				try{
					BufferedWriter bw = new BufferedWriter(new BufferedWriter(new FileWriter("wrongword.txt",true)));
					for(int i=0;i<ss.length-1;i++){
						bw.write(ss[i]+"#");
					}
					bw.write(""+(WrongWordDialog.getLastWordPosition()+1));
					bw.flush();
					bw.close();
				}catch(Exception ee){}
				JOptionPane.showMessageDialog(null, "挑战失败！");
			}
		}
	}
	/**
	 * 添加按钮
	 */
	void addButtons(){
		button1 = new JButton("设置时间");
		button1.setToolTipText("单位：秒");
		b1.setBounds(1210,100,100,50);
		b2.setBounds(1210,250,100,50);
		t1.setBounds(1080,100,100,50);
		t2.setBounds(1080,250,100,50);
		t1.setFont(new Font("楷体",Font.PLAIN,30));
		t2.setFont(new Font("楷体",Font.PLAIN,30));
		b1.setVisible(false);
		t1.setVisible(false);
		b2.setVisible(false);
		t2.setVisible(false);
		this.add(b1);
		this.add(b2);
		this.add(t1);
		this.add(t2);
		button2 = new JButton("设置单词个数");
		button3 = new JButton("下一个");
		button4 = new JButton("退出");
		startButton = new JButton("开始");
		button1.setBounds(900,100,150,50);
		button2.setBounds(900,250,150,50);
		button3.setBounds(900,400,150,50);
		button4.setBounds(900,550,150,50);
		startButton.setBounds(550,600,200,100);
		this.add(button1);
		this.add(button2);
		this.add(button3);
		this.add(button4);
		this.add(startButton);
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		startButton.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		button1.setFont(new Font("楷体",Font.PLAIN,18));
		button2.setFont(new Font("楷体",Font.PLAIN,18));
		button3.setFont(new Font("楷体",Font.PLAIN,18));
		button4.setFont(new Font("楷体",Font.PLAIN,18));
		b1.setFont(new Font("楷体",Font.PLAIN,18));
		b2.setFont(new Font("楷体",Font.PLAIN,18));
	}
	/**
	 * 添加“答案”文本框
	 */
	void addAnswerField(){
		answer = new JTextField();
		answer.setBounds(400,125,300,50);
		answer.setFont(new Font("楷体",Font.PLAIN,30));
		this.add(answer);
		answer.addActionListener(this);
	}
	/**
	 * 添加”释义“标签
	 */
	void addMeaningLabels(){
		for(int i=0;i<label.length;i++){
			label[i] = new JLabel("");
			label[i].setVisible(false);
			label[i].setBounds(400,300+i*50,200,100);
			this.add(label[i]);
			label[i].setFont(new Font("楷体",Font.PLAIN,30));
		}
	}
	/**
	 * 添加标签
	 */
	void addLabels(){
		label1= new JLabel("答案:");
		label2= new JLabel("属性:");
		label3 = new JLabel("释义:");
		label4 =new JLabel();
		timelabel = new JLabel();
		numLabel = new JLabel();
		timelabel.setBounds(80,20,200,100);
		label1.setBounds(300,100,100,100);
		label2.setBounds(300,200,100,100);
		label3.setBounds(300,300,100,100);
		label4.setBounds(400,225,200,50);
		numLabel.setBounds(80,500,200,100);
		this.add(label1);
		this.add(label2);
		this.add(label3);
		this.add(label4);
		this.add(timelabel);
		this.add(numLabel);
		label1.setFont(new Font("楷体",Font.PLAIN,30));
		label2.setFont(new Font("楷体",Font.PLAIN,30));
		label3.setFont(new Font("楷体",Font.PLAIN,30));
		label4.setFont(new Font("楷体",Font.PLAIN,30));
		timelabel.setFont(new Font("楷体",Font.PLAIN,50));
		numLabel.setFont(new Font("楷体",Font.PLAIN,50));

	}
}
