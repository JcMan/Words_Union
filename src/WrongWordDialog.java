import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class WrongWordDialog extends JDialog implements ActionListener{
	private int nowPosition = 0;
	private JButton lastWordButton,nextWordButton,saveButton,lastViewButton,exitButton;
	private JLabel label1,label2,label3;
	private JTextField field1,field2;
	private JTextField text[] = new JTextField[5];
	private JLabel positionLabel;
	public WrongWordDialog(){
		int h=Toolkit.getDefaultToolkit().getScreenSize().height;
		int w=Toolkit.getDefaultToolkit().getScreenSize().width;
		this.setSize(w, h);
		this.setTitle("单词复习");
		this.setUndecorated(true);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.addLabels();
		this.addWordField();
		this.addWordMeaningField();
		this.addButtons();
		this.addButtonListener();
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==exitButton){
			setVisible(false);
			RepeatWord.backGroundClip.loop();
		}else if(e.getSource()==lastViewButton){               //上次复习
			onClickedLastViewButton();
		}else if(e.getSource()==saveButton){
			onClickedSaveButton();
		}else if(e.getSource()==lastWordButton){
			onClickedLastWordButton();
		}else if(e.getSource()==nextWordButton){
			onClickedNextWordButton();
		}
	}
	static int getLastWordPosition(){
		int position=0;
		try{
			BufferedReader bf = new BufferedReader(new FileReader("wrongword.txt"));
			String str;
			while((str=bf.readLine())!=null){
				position++;
			}
		}catch(Exception e){}
		return position;
	}
	/**
	 * “下一个按钮”
	 */
	void onClickedNextWordButton(){
		if(nowPosition==getLastWordPosition())
			JOptionPane.showMessageDialog(null, "已经是最后一个单词了！");
		else{
			nowPosition++;
			positionLabel.setText(""+nowPosition);
			try{
				for(int i=0;i<text.length;i++)
					text[i].setVisible(false);
				BufferedReader bf = new BufferedReader(new FileReader("wrongword.txt"));
				String str;
				while((str=bf.readLine())!=null){
					if(str.endsWith(""+nowPosition)){
						String ss[]=str.split("#");
						field1.setText(ss[0]);
						field2.setText(ss[1]);
						for(int i=2;i<text.length;i++){
							if(i<ss.length-1){
								text[i-2].setVisible(true);
								text[i-2].setText(ss[i]);
							}
						}
					}		
				}
				bf.close();
			}catch(Exception ee){}
		}
	}
	/**
	 * "保存"按钮
	 */
	void onClickedSaveButton(){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter("wposition.txt"));
			bw.write(""+nowPosition);
			bw.flush();
			bw.close();
			JOptionPane.showMessageDialog(null, "保存成功!");
		}catch(Exception e1){}
	}
	/**
	 * “上次复习”按钮
	 */
	@SuppressWarnings("resource")
	void onClickedLastViewButton(){
		try{
			for(int i=0;i<text.length;i++)
				text[i].setVisible(false);
			BufferedReader bf = new BufferedReader(new FileReader("wposition.txt"));
			nowPosition = Integer.parseInt(bf.readLine());
			positionLabel.setText(""+nowPosition);
			bf = new BufferedReader(new FileReader("wrongword.txt"));
			String str;
			while((str=bf.readLine())!=null){
				if(str.endsWith(""+nowPosition)){
					String ss[]=str.split("#");
					field1.setText(ss[0]);
					field2.setText(ss[1]);
					for(int i=2;i<text.length;i++){
						if(i<ss.length-1){
							text[i-2].setVisible(true);
							text[i-2].setText(ss[i]);
						}
					}
				}		
			}
			bf.close();
		}catch(Exception ee){}
	}
	/**
	 * "上一个"按钮
	 */
	void onClickedLastWordButton(){
		if(nowPosition==0){}
		else if(nowPosition==1){
			JOptionPane.showMessageDialog(null, "已经是第一个单词了！");
		}
		else{
			nowPosition--;
			positionLabel.setText(""+nowPosition);
			try{
				for(int i=0;i<text.length;i++)
					text[i].setVisible(false);
				BufferedReader bf = new BufferedReader(new FileReader("wrongword.txt"));
				String str;
				while((str=bf.readLine())!=null){
					if(str.endsWith(""+nowPosition)){
						String ss[]=str.split("#");
						field1.setText(ss[0]);
						field2.setText(ss[1]);
						for(int i=2;i<text.length;i++){
							if(i<ss.length-1){
								text[i-2].setVisible(true);
								text[i-2].setText(ss[i]);
							}
						}
					}		
				}
				bf.close();
			}catch(Exception ee){}
			
			
		}
	}
	/**
	 * 给按钮添加监视器
	 */
	void addButtonListener(){
		lastWordButton.addActionListener(this);
		nextWordButton.addActionListener(this);
		saveButton.addActionListener(this);
		lastViewButton.addActionListener(this);
		exitButton.addActionListener(this);
	}
	/**
	 * 添加按钮
	 */
	void addButtons(){
		lastWordButton = new JButton("上一个");
		nextWordButton = new JButton("下一个");
		saveButton = new JButton("保存记录");
		lastViewButton = new JButton("上次复习");
		exitButton = new JButton("退出");
		lastWordButton.setFont(new Font("华文行楷",Font.PLAIN,30));
		nextWordButton.setFont(new Font("华文行楷",Font.PLAIN,30));
		saveButton.setFont(new Font("华文行楷",Font.PLAIN,30));
		lastViewButton.setFont(new Font("华文行楷",Font.PLAIN,30));
		exitButton.setFont(new Font("华文行楷",Font.PLAIN,30));
		lastViewButton.setBounds(50, 650, 200, 100);
		lastWordButton.setBounds(325, 650, 200, 100);
		nextWordButton.setBounds(600, 650, 200, 100);
		saveButton.setBounds(875, 650, 200, 100);
		exitButton.setBounds(1150, 650, 200, 100);
		this.add(exitButton);
		this.add(lastWordButton);
		this.add(nextWordButton);
		this.add(saveButton);
		this.add(lastViewButton);
	}
	/**
	 * 添加单词意思文本框
	 */
	void addWordMeaningField(){
		for(int i=0;i<text.length;i++){
			text[i] = new JTextField();
			text[i].setBounds(600, 325+i*70, 400, 50);
			this.add(text[i]);
			text[i].setFont(new Font("楷体",Font.PLAIN,30));
			text[i].setOpaque(false);
			text[i].setEditable(false);
			text[i].setBorder(null);
		}
	}
	/**
	 * 添加单词标签
	 */
	void addWordField(){
		field1 = new JTextField();
		field2 = new JTextField();
		field1.setBounds(600, 125, 400, 50);
		field2.setBounds(600,225,400,50);
		field1.setFont(new Font("楷体",Font.PLAIN,30));
		field2.setFont(new Font("楷体",Font.PLAIN,30));
		field1.setOpaque(false);
		field1.setBorder(null);
		field1.setEditable(false);
		field2.setOpaque(false);
		field2.setBorder(null);
		field2.setEditable(false);
		this.add(field1);
		this.add(field2);
	}
	/**
	 * 添加标签
	 */
	void addLabels(){
		label1 = new JLabel("单词:");
		label2 = new JLabel("属性:");
		label3 = new JLabel("释义:");
		positionLabel = new JLabel();
		this.add(positionLabel);
		this.add(label1);
		this.add(label2);
		this.add(label3);
		label1.setBounds(500, 100, 200, 100);
		label2.setBounds(500, 200, 200, 100);
		label3.setBounds(500, 300, 200, 100);
		Font font = new Font("仿宋",Font.BOLD,30);
		label1.setFont(font);
		label2.setFont(font);
		label3.setFont(font);
		positionLabel.setFont(font);
		positionLabel.setBounds(1300, 0, 50, 50);
	}
}
