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
		this.setTitle("д����ϰ");
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
	 * ��ӱ���ͼƬ
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
	 * ���ð�ť����
	 */
	void setButtonFont(){
		Font font = new Font("�����п�",Font.PLAIN,25);
		openButton.setFont(font);
		saveButton.setFont(font);
		exitButton.setFont(font);
	}
	/**
	 * ��ӱ����ı���
	 */
	JTextField getTextTitle(){
		if(textTitle== null){
			textTitle = new JTextField();
			textTitle.setBackground(Color.darkGray);
			textTitle.setFont(new Font("����",Font.PLAIN,20));
			textTitle.setForeground(Color.CYAN);
			textTitle.setBounds(210, 30, 650, 50);
		}
		return textTitle;
	}
	/**
	 *��������ı���
	 */
	JTextArea getAreaContent(){
		if(areaContent==null){
			areaContent = new JTextArea();
			areaContent.setBackground(Color.darkGray);
			areaContent.setFont(new Font("����",Font.PLAIN,20));
			areaContent.setForeground(Color.CYAN);
			areaContent.setBounds(210, 85, 650, 500);
			areaContent.setLineWrap(true);
		}
		return areaContent;
	}
	/**
	 * ����б�
	 */
	List getList(){
		if(list==null){
			list = new List();
			list.setBackground(Color.darkGray);
			list.setFont(new Font("����",Font.PLAIN,20));
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
	 * ��Ӵ򿪰�ť
	 */
	JButton getOpenButton(){
		if(openButton==null){
			openButton = new JButton("��");
			openButton.setBounds(210, 600, 200, 100);
		}
		return openButton;
	}
	/**
	 * ��ӱ��水ť
	 */
	JButton getSaveButton(){
		if(saveButton==null){
			saveButton = new JButton("����");
			saveButton.setBounds(610, 600, 200, 100);
		}
		return saveButton;
	}
	/**
	 *����˳���ť
	 */
	JButton getExitButton(){
		if(exitButton == null){
			exitButton = new JButton("�˳�");
			exitButton.setBounds(980, 600, 200, 100);
		}
		return exitButton;
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==exitButton){              //�˳���ť
			RepeatWord.backGroundClip.loop();               
			setVisible(false);
		}else if(e.getSource()==openButton){
			list.removeAll();             //�򿪰�ť
			try{
				BufferedReader br = new BufferedReader(new FileReader("data.txt"));
				String str;
				while((str=br.readLine())!=null){
					list.add(str);
				}
				br.close();
			}catch(Exception e1){}
		}else if(e.getSource()==saveButton){            //���水ť
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
					JOptionPane.showMessageDialog(null, "����ɹ�");
				}else
					JOptionPane.showMessageDialog(null, "�ļ�����ͬ�������!");
				
			}catch(Exception e1){}
			
		}
	}
	/**
	 * �ж��Ƿ��ѱ���
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
