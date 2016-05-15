import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class AddWordsDialog extends JDialog{
	private JButton addMeaningButton,exitButton,addWordButton;
	private JLabel label1,label2,label3;
	private JComboBox<String> list;
	private JTextField textWord,meaningText[];
	private int nowY=330;      //记录现在按钮的左标X
	private int count=0;
	private int nowPosition;
	String allStr="";             //要存入文件的总字符串
	public AddWordsDialog(){
		int h=Toolkit.getDefaultToolkit().getScreenSize().height;
		int w=Toolkit.getDefaultToolkit().getScreenSize().width;
		this.setSize(w, h);
		this.setTitle("添加单词");
		this.setUndecorated(true);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.addLabels();
		this.addTextFields();
		this.addComboBox();
		this.addMeaningText();
		this.addAddMeaningButton();
		this.addWordButton();
		this.addExitButton();
		exitButton = new JButton();
	}
	/**
	 * 添加单词按钮
	 */
	void addWordButton(){
		addWordButton = new JButton("添加");
		this.add(addWordButton);
		addWordButton.setBounds(1000,700,100,50);
		addWordButton.setFont(new Font("华文行楷",Font.PLAIN,30));
		addWordButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				try{
					String s=textWord.getText();
					if(s.length()==0)
						JOptionPane.showMessageDialog(null, "内容为空，保存失败！");
					else if(isAdd(s)==true)
						JOptionPane.showMessageDialog(null, "该单词已添加！");
					else{
						nowPosition=getLastWordPosition();
						nowPosition++;
						BufferedWriter bw = new BufferedWriter(new FileWriter("word.txt",true));
						allStr+=(textWord.getText()+"#"+list.getSelectedItem().toString()+"#");
						for(int i=0;i<meaningText.length;i++){
							if(meaningText[i].isVisible())
								allStr+=(meaningText[i].getText()+"#");
						}
						allStr+=(""+nowPosition);
						bw.write(allStr);
						bw.newLine();
						bw.flush();
						bw.close();
						allStr="";
						count=0;
						nowY=330;
						addMeaningButton.setBounds(900,330,100,50);
						textWord.setText(null);
						list.setSelectedItem(null);
						for(int i=0;i<meaningText.length;i++){
							meaningText[i].setText(null);
							if(i>=1)
							meaningText[i].setVisible(false);
						}
						JOptionPane.showMessageDialog(null, "保存成功!");
						textWord.requestFocus();
					}
				}catch(Exception e1){}
				
			}
		});
	}
	/**
	 * 获取文件最后一个单词的位置
	 */
	static int getLastWordPosition(){
		int position=0;
		try{
			BufferedReader bf = new BufferedReader(new FileReader("word.txt"));
			String str;
			while((str=bf.readLine())!=null){
				position++;
			}
		}catch(Exception e){}
		return position;
	}
	/**
	 * 判断单词是否已添加
	 */
	boolean isAdd(String s){
		try{
			@SuppressWarnings("resource")
			BufferedReader bf = new BufferedReader(new FileReader("word.txt"));
			String str;
			while((str=bf.readLine())!=null){
				if(str.startsWith(s))
					return true;
			}
			bf.close();
		}catch(Exception e){}
		return false;
	}
	/**
	 * 添加退出按钮
	 */
	void addExitButton(){
		exitButton = new JButton("退出");
		this.add(exitButton);
		exitButton.setFont(new Font("华文行楷",Font.PLAIN,30));
		exitButton.setBounds(1200, 700, 100, 50);
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				RepeatWord.backGroundClip.loop();
				setVisible(false);
			}
		});
	}
	/**
	 * 添加添加单词按钮
	 */
	void addAddMeaningButton(){
		addMeaningButton = new JButton("+");
		this.add(addMeaningButton);
		addMeaningButton.setFont(new Font("楷体",Font.BOLD,30));
		addMeaningButton.setBounds(900,nowY,100,50);
		addMeaningButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ee){
				if(count==4){}
				else{
					nowY+=70;
					meaningText[++count].setVisible(true);
					meaningText[count].requestFocus();
					addMeaningButton.setBounds(900,nowY,100,50);
				}
			}
		});
	}
	/**
	 * 添加释义文本框
	 */
	void addMeaningText(){
		meaningText = new JTextField[5];
		for(int i=0;i<meaningText.length;i++){
			meaningText[i]= new JTextField();
			meaningText[i].setBounds(480, 330+i*70, 400, 50);
			meaningText[i].setFont(new Font("楷体",Font.PLAIN,30));
			this.add(meaningText[i]);
			if(i>=1)
				meaningText[i].setVisible(false);
		}
		meaningText[0].setFont(new Font("楷体",Font.PLAIN,30));
		
	}
	/**
	 * 添加下拉列表
	 */
	void addComboBox(){
		list = new JComboBox<String>();
		this.add(list);
		list.addItem("n-名词");
		list.addItem("vi-不及物动词");
		list.addItem("vt-及物动词");
		list.addItem("adj-形容词");
		list.addItem("adv-副词");
		list.addItem("conj-连词");
		list.addItem("prep-介词");
		list.addItem("art-冠词");
		list.setBounds(480,230,400,50);
		list.setFont(new Font("楷体",Font.PLAIN,20));
		list.setSelectedItem(null);
		list.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				meaningText[0].requestFocus();
			}
		});
	}
	/**
	 * 添加标签
	 */
	void addLabels(){
		label1 = new JLabel("单词:");
		label2 = new JLabel("属性:");
		label3 = new JLabel("释义:");
		this.add(label1);
		this.add(label2);
		this.add(label3);
		label1.setBounds(400, 100, 200, 100);
		label2.setBounds(400, 200, 200, 100);
		label3.setBounds(400, 300, 200, 100);
		Font font = new Font("仿宋",Font.BOLD,30);
		label1.setFont(font);
		label2.setFont(font);
		label3.setFont(font);
	}
	/**
	 * 添加文本框
	 */
	void addTextFields(){
		textWord = new JTextField();
		this.add(textWord);
		textWord.setBounds(480, 120, 400, 50);
		textWord.setFont(new Font("楷体",Font.PLAIN,30));
		
	}
}
