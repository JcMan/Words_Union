import java.awt.Color;
import java.awt.Font;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class WriterDialog extends JFrame implements ActionListener{
	private JTextField textTitle;
	private JTextArea areaContent;
	private List list;
	private JButton openButton,saveButton,exitButton;
	JLabel label ;
	public WriterDialog(){
		int h=Toolkit.getDefaultToolkit().getScreenSize().height;
		int w=Toolkit.getDefaultToolkit().getScreenSize().width;
		this.setSize(w, h);
		this.setTitle("写作练习");
		this.setUndecorated(true);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(null);
		addLabel();
		JScrollPane p = new JScrollPane(getAreaContent());
		label.add(p);
		p.setBounds(210, 85, 650, 500);
		label.add(getTextTitle());
		label.add(getExitButton());
		label.add(getList());
		label.add(getOpenButton());
		label.add(getSaveButton());
		openButton.addActionListener(this);
		saveButton.addActionListener(this);
		exitButton.addActionListener(this);
		this.setButtonFont();
	}
	/**
	 * 添加背景图片
	 */
	void addLabel(){
		label = new JLabel();
		int h=Toolkit.getDefaultToolkit().getScreenSize().height;
		int w=Toolkit.getDefaultToolkit().getScreenSize().width;
		this.add(label);
		label.setBounds(0, 0, w, h);
		label.setIcon(new ImageIcon(WriterDialog.class.getResource("/res/backgroud.JPG")));  
	}
	/**
	 * 设置按钮字体
	 */
	void setButtonFont(){
		Font font = new Font("华文行楷",Font.PLAIN,25);
		openButton.setFont(font);
		saveButton.setFont(font);
		exitButton.setFont(font);
	}
	/**
	 * 添加标题文本框
	 */
	JTextField getTextTitle(){
		if(textTitle== null){
			textTitle = new JTextField();
			textTitle.setBackground(Color.darkGray);
			textTitle.setFont(new Font("仿宋",Font.PLAIN,20));
			textTitle.setForeground(Color.CYAN);
			textTitle.setBounds(210, 30, 650, 50);
		}
		return textTitle;
	}
	/**
	 *添加内容文本域
	 */
	JTextArea getAreaContent(){
		if(areaContent==null){
			areaContent = new JTextArea();
			areaContent.setBackground(Color.darkGray);
			areaContent.setFont(new Font("仿宋",Font.PLAIN,20));
			areaContent.setForeground(Color.CYAN);
			areaContent.setBounds(210, 85, 650, 500);
			areaContent.setLineWrap(true);
		}
		return areaContent;
	}
	/**
	 * 添加列表
	 */
	List getList(){
		if(list==null){
			list = new List();
			list.setBackground(Color.darkGray);
			list.setFont(new Font("仿宋",Font.PLAIN,20));
			list.setForeground(Color.CYAN);
			list.setBounds(880,30,300,550);
			list.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					if(e.getClickCount()==2){
						areaContent.setText(null);
						String s=list.getSelectedItem();
						textTitle.setText(s);
						try{
							BufferedReader bf = new BufferedReader(new FileReader("text/"+s+".txt"));
							String str;
							while((str=bf.readLine())!=null){
								areaContent.append(str+"\n");
							}
						}catch(Exception e1){}
					}
				}
			});
		}
		return list;
	}
	/**
	 * 添加打开按钮
	 */
	JButton getOpenButton(){
		if(openButton==null){
			openButton = new JButton("打开");
			openButton.setBounds(210, 600, 200, 100);
		}
		return openButton;
	}
	/**
	 * 添加保存按钮
	 */
	JButton getSaveButton(){
		if(saveButton==null){
			saveButton = new JButton("保存");
			saveButton.setBounds(610, 600, 200, 100);
		}
		return saveButton;
	}
	/**
	 *添加退出按钮
	 */
	JButton getExitButton(){
		if(exitButton == null){
			exitButton = new JButton("退出");
			exitButton.setBounds(980, 600, 200, 100);
		}
		return exitButton;
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==exitButton){              //退出按钮
			RepeatWord.backGroundClip.loop();               
			setVisible(false);
		}else if(e.getSource()==openButton){
			list.removeAll();             //打开按钮
			try{
				BufferedReader br = new BufferedReader(new FileReader("data.txt"));
				String str;
				while((str=br.readLine())!=null){
					list.add(str);
				}
				br.close();
			}catch(Exception e1){}
		}else if(e.getSource()==saveButton){            //保存按钮
			try{
				String str=textTitle.getText();
				if(isSaved(str)==false){
					BufferedWriter br = new BufferedWriter(new FileWriter("data.txt",true));
					br.write(str);
					br.newLine();
					br.flush();
					br.close();
					BufferedWriter bs = new BufferedWriter(new FileWriter("text/"+str+".txt"));
					String sArea = areaContent.getText();
					String s[]=sArea.split("\n");
					for(int i=0;i<s.length;i++){
						bs.write(s[i]);
						bs.newLine();
					}
					
					bs.flush();
					bs.close();
					JOptionPane.showMessageDialog(null, "保存成功");
				}else
					JOptionPane.showMessageDialog(null, "文件名相同，请改正!");
				
			}catch(Exception e1){}
			
		}
	}
	/**
	 * 判断是否已保存
	 */
	boolean isSaved(String str){
		try{
			BufferedReader bf = new BufferedReader(new FileReader("data.txt"));
			String s;
			while((s=bf.readLine())!=null){
				if(s.equals(str))
					return true;
			}
			bf.close();
		}catch(Exception e){}
		return false;
	}
}
