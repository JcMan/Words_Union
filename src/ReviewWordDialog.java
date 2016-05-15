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


public class ReviewWordDialog extends JDialog implements ActionListener{
	private int nowPosition = 0;
	private JButton lastWordButton,nextWordButton,saveButton,lastViewButton,exitButton;
	private JLabel label1,label2,label3;
	private JTextField field1,field2;
	private JTextField text[] = new JTextField[5];
	private JLabel positionLabel;
	public ReviewWordDialog(){
		int h=Toolkit.getDefaultToolkit().getScreenSize().height;
		int w=Toolkit.getDefaultToolkit().getScreenSize().width;
		this.setSize(w, h);
		this.setTitle("���ʸ�ϰ");
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
		}else if(e.getSource()==lastViewButton){               //�ϴθ�ϰ
			onClickedLastViewButton();
		}else if(e.getSource()==saveButton){
			onClickedSaveButton();
		}else if(e.getSource()==lastWordButton){
			onClickedLastWordButton();
		}else if(e.getSource()==nextWordButton){
			onClickedNextWordButton();
		}
	}
	/**
	 * ����һ����ť��
	 */
	void onClickedNextWordButton(){
		if(nowPosition==AddWordsDialog.getLastWordPosition())
			JOptionPane.showMessageDialog(null, "�Ѿ������һ�������ˣ�");
		else{
			nowPosition++;
			positionLabel.setText(""+nowPosition);
			try{
				for(int i=0;i<text.length;i++)
					text[i].setVisible(false);
				BufferedReader bf = new BufferedReader(new FileReader("word.txt"));
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
	 * "����"��ť
	 */
	void onClickedSaveButton(){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter("position.txt"));
			bw.write(""+nowPosition);
			bw.flush();
			bw.close();
			JOptionPane.showMessageDialog(null, "����ɹ�!");
		}catch(Exception e1){}
	}
	/**
	 * ���ϴθ�ϰ����ť
	 */
	@SuppressWarnings("resource")
	void onClickedLastViewButton(){
		try{
			for(int i=0;i<text.length;i++)
				text[i].setVisible(false);
			BufferedReader bf = new BufferedReader(new FileReader("position.txt"));
			nowPosition = Integer.parseInt(bf.readLine());
			positionLabel.setText(""+nowPosition);
			bf = new BufferedReader(new FileReader("word.txt"));
			String str;
			while((str=bf.readLine())!=null){
				if(str.endsWith(""+nowPosition)){
					String ss[]=str.split("#");
					field1.setText(ss[0]);
					field2.setText(ss[1]);
					for(int i=2;i<text.length;i++){
						if(!ss[i].equals(""+nowPosition)){
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
	 * "��һ��"��ť
	 */
	void onClickedLastWordButton(){
		if(nowPosition==0){}
		else if(nowPosition==1){
			JOptionPane.showMessageDialog(null, "�Ѿ��ǵ�һ�������ˣ�");
		}
		else{
			nowPosition--;
			positionLabel.setText(""+nowPosition);
			try{
				for(int i=0;i<text.length;i++)
					text[i].setVisible(false);
				BufferedReader bf = new BufferedReader(new FileReader("word.txt"));
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
	 * ����ť��Ӽ�����
	 */
	void addButtonListener(){
		lastWordButton.addActionListener(this);
		nextWordButton.addActionListener(this);
		saveButton.addActionListener(this);
		lastViewButton.addActionListener(this);
		exitButton.addActionListener(this);
	}
	/**
	 * ��Ӱ�ť
	 */
	void addButtons(){
		lastWordButton = new JButton("��һ��");
		nextWordButton = new JButton("��һ��");
		saveButton = new JButton("�����¼");
		lastViewButton = new JButton("�ϴθ�ϰ");
		exitButton = new JButton("�˳�");
		lastWordButton.setFont(new Font("�����п�",Font.PLAIN,30));
		nextWordButton.setFont(new Font("�����п�",Font.PLAIN,30));
		saveButton.setFont(new Font("�����п�",Font.PLAIN,30));
		lastViewButton.setFont(new Font("�����п�",Font.PLAIN,30));
		exitButton.setFont(new Font("�����п�",Font.PLAIN,30));
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
	 * ��ӵ�����˼�ı���
	 */
	void addWordMeaningField(){
		for(int i=0;i<text.length;i++){
			text[i] = new JTextField();
			text[i].setBounds(600, 325+i*70, 400, 50);
			this.add(text[i]);
			text[i].setFont(new Font("����",Font.PLAIN,30));
			text[i].setOpaque(false);
			text[i].setEditable(false);
			text[i].setBorder(null);
		}
	}
	/**
	 * ��ӵ��ʱ�ǩ
	 */
	void addWordField(){
		field1 = new JTextField();
		field2 = new JTextField();
		field1.setBounds(600, 125, 400, 50);
		field2.setBounds(600,225,400,50);
		field1.setFont(new Font("����",Font.PLAIN,30));
		field2.setFont(new Font("����",Font.PLAIN,30));
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
	 * ��ӱ�ǩ
	 */
	void addLabels(){
		label1 = new JLabel("����:");
		label2 = new JLabel("����:");
		label3 = new JLabel("����:");
		positionLabel = new JLabel();
		this.add(positionLabel);
		this.add(label1);
		this.add(label2);
		this.add(label3);
		label1.setBounds(500, 100, 200, 100);
		label2.setBounds(500, 200, 200, 100);
		label3.setBounds(500, 300, 200, 100);
		Font font = new Font("����",Font.BOLD,30);
		label1.setFont(font);
		label2.setFont(font);
		label3.setFont(font);
		positionLabel.setFont(font);
		positionLabel.setBounds(1300, 0, 50, 50);
	}
}
