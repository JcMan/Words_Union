import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class RepeatWord extends JFrame {
	private JPanel mainPanel = null;
	private AudioClip buttonClip;
	public static AudioClip backGroundClip;
	private AudioClip buttonEnteredClip;
	private JButton button[];
	public static boolean isLogin = false;
	
	public RepeatWord(){
		try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        }catch(Exception exe){
            exe.printStackTrace();
        }
		init();	
		addBackGroundMusic();       //��ӱ�������
		this.getLayeredPane().add(getMainPanel(),new Integer(Integer.MAX_VALUE));
		
		
	}
	
	/**
	 * ��ʼ��������
	 */
	private void init(){
		this.setTitle("��������");
		this.setLayout(null);
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setBounds(0, 0, width, height);
		this.setUndecorated(true);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	/**
	 * ��ӱ�������
	 */
	void addBackGroundMusic(){
		URL path = RepeatWord.class.getResource("/music/��������.wav");
		backGroundClip = Applet.newAudioClip(path);
		backGroundClip.loop();
	}
	/**
	 * ��ʼ�������
	 */
	public JPanel getMainPanel(){
		if(mainPanel==null){
			mainPanel = new JPanel();
			mainPanel.setLayout(null);
			
			button = new JButton[8];
			for(int i = 0; i < button.length; i++){       //��ʼ����ť��
				button[i] = new JButton();
			}
			setButtonInit();          //���ð�ť������
			JLabel label = new JLabel();
			label.setIcon(new ImageIcon(RepeatWord.class.getResource("/res/backgroud.JPG")));    //������ǩ
			mainPanel.add(label);
			label.setBounds(0, 0, 1366, 768);
			JLabel label2 = new JLabel();
			label2.setIcon(new ImageIcon(RepeatWord.class.getResource("/res/�ָ�.jpg")));        //�ָ�����ǩ
			label.add(label2);
			label2.setBounds(144, 359, 1080, 50);
			for(JButton b:button){
				label.add(b);              //����ť��ӵ������
			}
			addButtonListener();            //��Ӱ�ť�ļ�����
			setButtonEnteredIcon();             //�����������ͼƬ
			addButtonMouseListener();       //���õ�����ť�¼�
			mainPanel.setBounds(0, 0, 1366, 768);
			mainPanel.setVisible(true);
			
			
		}
		return mainPanel;
	}
	/**
	 * ���ð�ť������
	 */
	private void setButtonInit(){
		button[0].setIcon(new ImageIcon(RepeatWord.class.getResource("/res/��¼.jpg")));
		button[1].setIcon(new ImageIcon(RepeatWord.class.getResource("/res/��ӵ���.JPG")));
		button[2].setIcon(new ImageIcon(RepeatWord.class.getResource("/res/���ʸ�ϰ.JPG")));
		button[3].setIcon(new ImageIcon(RepeatWord.class.getResource("/res/������ϰ.JPG")));
		button[4].setIcon(new ImageIcon(RepeatWord.class.getResource("/res/��ʸ�ϰ.JPG")));
		button[5].setIcon(new ImageIcon(RepeatWord.class.getResource("/res/д����ϰ.JPG")));
		button[6].setIcon(new ImageIcon(RepeatWord.class.getResource("/res/������ս.JPG")));
		button[7].setIcon(new ImageIcon(RepeatWord.class.getResource("/res/�˳�.JPG")));
		for(int i = 0; i < 4; i++){
			button[i].setContentAreaFilled(false);
			button[i].setBorderPainted(false);
			button[i].setBounds(145+i*305,42,160,300);
		}
		for(int i = 0; i < 4; i++){
			button[i+4].setContentAreaFilled(false);
			button[i+4].setBorderPainted(false);
			button[i+4].setBounds(145+i*305,426,160,300);
		}
		
	}
	
	/**
	 * ���ð�ť���
	 */
	void setButtonBigger(int i,int x,int y){
		x-=85;
		y-=165;
		button[i].setBounds(x,y,170,330);
	}
	
	/**
	 * ���ð�ť������С
	 */
	void setButtonNormal(int i,int x,int y){
		x-=80;
		y-=150;
		button[i].setBounds(x,y,160,300);
	}
	
	/**
	 * ��Ӱ�ť�ļ�����
	 */
	void addButtonListener(){
		for(int i=0; i<button.length;i++){
			button[i].addActionListener(new ButtonListener());
		}
	}
	
	/**
	 * ��Ӱ�ť����������
	 */
	void addButtonMouseListener(){
		for(int i=0;i<button.length;i++){
			button[i].addMouseListener(new ButtonMouseAdapter());
		}
	}
	
	/**
	 * �����������ͼƬ
	 */
	void setButtonEnteredIcon(){
		button[0].setRolloverIcon(new ImageIcon(RepeatWord.class.getResource("/res/��¼-in.JPG")));
		button[1].setRolloverIcon(new ImageIcon(RepeatWord.class.getResource("/res/��ӵ���-in.JPG")));
		button[2].setRolloverIcon(new ImageIcon(RepeatWord.class.getResource("/res/���ʸ�ϰ-in.JPG")));
		button[3].setRolloverIcon(new ImageIcon(RepeatWord.class.getResource("/res/������ϰ-in.JPG")));
		button[4].setRolloverIcon(new ImageIcon(RepeatWord.class.getResource("/res/��ʸ�ϰ-in.JPG")));
		button[5].setRolloverIcon(new ImageIcon(RepeatWord.class.getResource("/res/д����ϰ-in.JPG")));
		button[6].setRolloverIcon(new ImageIcon(RepeatWord.class.getResource("/res/������ս-in.JPG")));
		button[7].setRolloverIcon(new ImageIcon(RepeatWord.class.getResource("/res/�˳�-in.JPG")));
	}
	
	/**
	 * ��������
	 */
	public void addMainPanel(){
		this.getLayeredPane().add(getMainPanel(),new Integer(Integer.MIN_VALUE));
		mainPanel.setBounds(0, 0, 1366, 768);
	}
	
	/**
	 * ���尴ť������¼�
	 */
	class ButtonMouseAdapter extends MouseAdapter{
		public void mouseClicked(MouseEvent e){
			if(buttonEnteredClip!=null)
				buttonEnteredClip.stop();
			URL path = RepeatWord.class.getResource("/music/yourturn.wav");          //��갴�°�ť����Ӧ
			buttonClip =Applet.newAudioClip(path);
			buttonClip.play();
		}
		
		public void  mouseEntered(MouseEvent e){
			if(e.getSource()==button[0]){
				setButtonBigger(0,225,192);
				if(buttonEnteredClip!=null)
					buttonEnteredClip.stop();
				buttonEnteredClip = Applet.newAudioClip(RepeatWord.class.getResource("/music/����.wav"));
				buttonEnteredClip.play();
			}else if(e.getSource()==button[1]){
				setButtonBigger(1,530,192);
				if(buttonEnteredClip!=null)
					buttonEnteredClip.stop();
				buttonEnteredClip = Applet.newAudioClip(RepeatWord.class.getResource("/music/����.wav"));
				buttonEnteredClip.play();
			}else if(e.getSource()==button[2]){
				setButtonBigger(2,835,192);
				if(buttonEnteredClip!=null)
					buttonEnteredClip.stop();
				buttonEnteredClip = Applet.newAudioClip(RepeatWord.class.getResource("/music/����.wav"));
				buttonEnteredClip.play();
			}else if(e.getSource()==button[3]){
				setButtonBigger(3,1140,192);
				if(buttonEnteredClip!=null)
					buttonEnteredClip.stop();
				buttonEnteredClip = Applet.newAudioClip(RepeatWord.class.getResource("/music/����.wav"));
				buttonEnteredClip.play();
			}else if(e.getSource()==button[4]){
				setButtonBigger(4,225,576);
				if(buttonEnteredClip!=null)
					buttonEnteredClip.stop();
				buttonEnteredClip = Applet.newAudioClip(RepeatWord.class.getResource("/music/����.wav"));
				buttonEnteredClip.play();
			}else if(e.getSource()==button[5]){
				setButtonBigger(5,530,576);
				if(buttonEnteredClip!=null)
					buttonEnteredClip.stop();
				buttonEnteredClip = Applet.newAudioClip(RepeatWord.class.getResource("/music/����.wav"));
				buttonEnteredClip.play();
			}else if(e.getSource()==button[6]){
				setButtonBigger(6,835,576);
				if(buttonEnteredClip!=null)
					buttonEnteredClip.stop();
				buttonEnteredClip = Applet.newAudioClip(RepeatWord.class.getResource("/music/����.wav"));
				buttonEnteredClip.play();
			}else if(e.getSource()==button[7]){
				setButtonBigger(7,1140,576);
				if(buttonEnteredClip!=null)
					buttonEnteredClip.stop();
				buttonEnteredClip = Applet.newAudioClip(RepeatWord.class.getResource("/music/����.wav"));
				buttonEnteredClip.play();
			}
		}
		public void mouseExited(MouseEvent e){
			if(e.getSource()==button[0]){
				setButtonNormal(0,225,192);
			}else if(e.getSource()==button[1]){
				setButtonNormal(1,530,192);
			}else if(e.getSource()==button[2]){
				setButtonNormal(2,835,192);
			}else if(e.getSource()==button[3]){
				setButtonNormal(3,1140,192);
			}else if(e.getSource()==button[4]){
				setButtonNormal(4,225,576);
			}else if(e.getSource()==button[5]){
				setButtonNormal(5,530,576);
			}else if(e.getSource()==button[6]){
				setButtonNormal(6,835,576);
			}else if(e.getSource()==button[7]){
				setButtonNormal(7,1140,576);
			}
		}
		
	}
	
	/**
	 * ���尴ť�ļ�������
	 */
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==button[7])
				System.exit(0);
			else if(e.getSource()==button[0]){
				if(isLogin==true)
					JOptionPane.showMessageDialog(null,"���ѵ�¼!", "ϵͳ��ʾ",JOptionPane.INFORMATION_MESSAGE);
				else
					new LoginDialog();
			}else if(e.getSource()==button[1]){
				if(isLogin){
					backGroundClip.stop();
					new AddWordsDialog();
				}else
					JOptionPane.showMessageDialog(null, "����û�е�¼���޷�ʹ�ô˹���!");
			}else if(e.getSource()==button[2]){
				if(isLogin){
					backGroundClip.stop();
					new ReviewWordDialog();
				}else
					JOptionPane.showMessageDialog(null, "����û�е�¼���޷�ʹ�ô˹���!");
			}else if(e.getSource()==button[3]){
				if(isLogin==true){
					backGroundClip.stop();
					new ListeneDialog();
				}else
					JOptionPane.showMessageDialog(null, "����û�е�¼���޷�ʹ�ô˹���!");
			}else if(e.getSource()==button[4]){
				if(isLogin){
					backGroundClip.stop();
					new WrongWordDialog();
				}else
					JOptionPane.showMessageDialog(null, "����û�е�¼���޷�ʹ�ô˹���!");
			}else if(e.getSource()==button[5]){
				if(isLogin==true){
					backGroundClip.stop();
					new WriterDialog();
				}else
					JOptionPane.showMessageDialog(null, "����û�е�¼���޷�ʹ�ô˹���!");
			}else if(e.getSource()==button[6]){
				backGroundClip.stop();
				new MemoryChallengeDialog();
			}
		}
	}
	/**
	 * ������
	 * @param args
	 */
	
	public static void main(String[] args) {
		new RepeatWord();

	}

}
