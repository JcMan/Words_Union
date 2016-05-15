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
		addBackGroundMusic();       //添加背景音乐
		this.getLayeredPane().add(getMainPanel(),new Integer(Integer.MAX_VALUE));
		
		
	}
	
	/**
	 * 初始化主窗体
	 */
	private void init(){
		this.setTitle("单词联盟");
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
	 * 添加背景音乐
	 */
	void addBackGroundMusic(){
		URL path = RepeatWord.class.getResource("/music/背景音乐.wav");
		backGroundClip = Applet.newAudioClip(path);
		backGroundClip.loop();
	}
	/**
	 * 初始化猪面板
	 */
	public JPanel getMainPanel(){
		if(mainPanel==null){
			mainPanel = new JPanel();
			mainPanel.setLayout(null);
			
			button = new JButton[8];
			for(int i = 0; i < button.length; i++){       //初始化按钮组
				button[i] = new JButton();
			}
			setButtonInit();          //设置按钮的属性
			JLabel label = new JLabel();
			label.setIcon(new ImageIcon(RepeatWord.class.getResource("/res/backgroud.JPG")));    //背景标签
			mainPanel.add(label);
			label.setBounds(0, 0, 1366, 768);
			JLabel label2 = new JLabel();
			label2.setIcon(new ImageIcon(RepeatWord.class.getResource("/res/分隔.jpg")));        //分隔符标签
			label.add(label2);
			label2.setBounds(144, 359, 1080, 50);
			for(JButton b:button){
				label.add(b);              //将按钮添加到主面板
			}
			addButtonListener();            //添加按钮的监视器
			setButtonEnteredIcon();             //设置鼠标进入的图片
			addButtonMouseListener();       //设置单击按钮事件
			mainPanel.setBounds(0, 0, 1366, 768);
			mainPanel.setVisible(true);
			
			
		}
		return mainPanel;
	}
	/**
	 * 设置按钮的属性
	 */
	private void setButtonInit(){
		button[0].setIcon(new ImageIcon(RepeatWord.class.getResource("/res/登录.jpg")));
		button[1].setIcon(new ImageIcon(RepeatWord.class.getResource("/res/添加单词.JPG")));
		button[2].setIcon(new ImageIcon(RepeatWord.class.getResource("/res/单词复习.JPG")));
		button[3].setIcon(new ImageIcon(RepeatWord.class.getResource("/res/听力练习.JPG")));
		button[4].setIcon(new ImageIcon(RepeatWord.class.getResource("/res/错词复习.JPG")));
		button[5].setIcon(new ImageIcon(RepeatWord.class.getResource("/res/写作练习.JPG")));
		button[6].setIcon(new ImageIcon(RepeatWord.class.getResource("/res/记忆挑战.JPG")));
		button[7].setIcon(new ImageIcon(RepeatWord.class.getResource("/res/退出.JPG")));
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
	 * 设置按钮变大
	 */
	void setButtonBigger(int i,int x,int y){
		x-=85;
		y-=165;
		button[i].setBounds(x,y,170,330);
	}
	
	/**
	 * 设置按钮正常大小
	 */
	void setButtonNormal(int i,int x,int y){
		x-=80;
		y-=150;
		button[i].setBounds(x,y,160,300);
	}
	
	/**
	 * 添加按钮的监视器
	 */
	void addButtonListener(){
		for(int i=0; i<button.length;i++){
			button[i].addActionListener(new ButtonListener());
		}
	}
	
	/**
	 * 添加按钮的鼠标监视器
	 */
	void addButtonMouseListener(){
		for(int i=0;i<button.length;i++){
			button[i].addMouseListener(new ButtonMouseAdapter());
		}
	}
	
	/**
	 * 设置鼠标进入的图片
	 */
	void setButtonEnteredIcon(){
		button[0].setRolloverIcon(new ImageIcon(RepeatWord.class.getResource("/res/登录-in.JPG")));
		button[1].setRolloverIcon(new ImageIcon(RepeatWord.class.getResource("/res/添加单词-in.JPG")));
		button[2].setRolloverIcon(new ImageIcon(RepeatWord.class.getResource("/res/单词复习-in.JPG")));
		button[3].setRolloverIcon(new ImageIcon(RepeatWord.class.getResource("/res/听力练习-in.JPG")));
		button[4].setRolloverIcon(new ImageIcon(RepeatWord.class.getResource("/res/错词复习-in.JPG")));
		button[5].setRolloverIcon(new ImageIcon(RepeatWord.class.getResource("/res/写作练习-in.JPG")));
		button[6].setRolloverIcon(new ImageIcon(RepeatWord.class.getResource("/res/记忆挑战-in.JPG")));
		button[7].setRolloverIcon(new ImageIcon(RepeatWord.class.getResource("/res/退出-in.JPG")));
	}
	
	/**
	 * 添加主面板
	 */
	public void addMainPanel(){
		this.getLayeredPane().add(getMainPanel(),new Integer(Integer.MIN_VALUE));
		mainPanel.setBounds(0, 0, 1366, 768);
	}
	
	/**
	 * 定义按钮的鼠标事件
	 */
	class ButtonMouseAdapter extends MouseAdapter{
		public void mouseClicked(MouseEvent e){
			if(buttonEnteredClip!=null)
				buttonEnteredClip.stop();
			URL path = RepeatWord.class.getResource("/music/yourturn.wav");          //鼠标按下按钮的响应
			buttonClip =Applet.newAudioClip(path);
			buttonClip.play();
		}
		
		public void  mouseEntered(MouseEvent e){
			if(e.getSource()==button[0]){
				setButtonBigger(0,225,192);
				if(buttonEnteredClip!=null)
					buttonEnteredClip.stop();
				buttonEnteredClip = Applet.newAudioClip(RepeatWord.class.getResource("/music/德玛.wav"));
				buttonEnteredClip.play();
			}else if(e.getSource()==button[1]){
				setButtonBigger(1,530,192);
				if(buttonEnteredClip!=null)
					buttonEnteredClip.stop();
				buttonEnteredClip = Applet.newAudioClip(RepeatWord.class.getResource("/music/炮手.wav"));
				buttonEnteredClip.play();
			}else if(e.getSource()==button[2]){
				setButtonBigger(2,835,192);
				if(buttonEnteredClip!=null)
					buttonEnteredClip.stop();
				buttonEnteredClip = Applet.newAudioClip(RepeatWord.class.getResource("/music/阿狸.wav"));
				buttonEnteredClip.play();
			}else if(e.getSource()==button[3]){
				setButtonBigger(3,1140,192);
				if(buttonEnteredClip!=null)
					buttonEnteredClip.stop();
				buttonEnteredClip = Applet.newAudioClip(RepeatWord.class.getResource("/music/安妮.wav"));
				buttonEnteredClip.play();
			}else if(e.getSource()==button[4]){
				setButtonBigger(4,225,576);
				if(buttonEnteredClip!=null)
					buttonEnteredClip.stop();
				buttonEnteredClip = Applet.newAudioClip(RepeatWord.class.getResource("/music/暴萝.wav"));
				buttonEnteredClip.play();
			}else if(e.getSource()==button[5]){
				setButtonBigger(5,530,576);
				if(buttonEnteredClip!=null)
					buttonEnteredClip.stop();
				buttonEnteredClip = Applet.newAudioClip(RepeatWord.class.getResource("/music/刀妹.wav"));
				buttonEnteredClip.play();
			}else if(e.getSource()==button[6]){
				setButtonBigger(6,835,576);
				if(buttonEnteredClip!=null)
					buttonEnteredClip.stop();
				buttonEnteredClip = Applet.newAudioClip(RepeatWord.class.getResource("/music/寒冰.wav"));
				buttonEnteredClip.play();
			}else if(e.getSource()==button[7]){
				setButtonBigger(7,1140,576);
				if(buttonEnteredClip!=null)
					buttonEnteredClip.stop();
				buttonEnteredClip = Applet.newAudioClip(RepeatWord.class.getResource("/music/伊泽.wav"));
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
	 * 定义按钮的监视器类
	 */
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==button[7])
				System.exit(0);
			else if(e.getSource()==button[0]){
				if(isLogin==true)
					JOptionPane.showMessageDialog(null,"您已登录!", "系统提示",JOptionPane.INFORMATION_MESSAGE);
				else
					new LoginDialog();
			}else if(e.getSource()==button[1]){
				if(isLogin){
					backGroundClip.stop();
					new AddWordsDialog();
				}else
					JOptionPane.showMessageDialog(null, "您还没有登录，无法使用此功能!");
			}else if(e.getSource()==button[2]){
				if(isLogin){
					backGroundClip.stop();
					new ReviewWordDialog();
				}else
					JOptionPane.showMessageDialog(null, "您还没有登录，无法使用此功能!");
			}else if(e.getSource()==button[3]){
				if(isLogin==true){
					backGroundClip.stop();
					new ListeneDialog();
				}else
					JOptionPane.showMessageDialog(null, "您还没有登录，无法使用此功能!");
			}else if(e.getSource()==button[4]){
				if(isLogin){
					backGroundClip.stop();
					new WrongWordDialog();
				}else
					JOptionPane.showMessageDialog(null, "您还没有登录，无法使用此功能!");
			}else if(e.getSource()==button[5]){
				if(isLogin==true){
					backGroundClip.stop();
					new WriterDialog();
				}else
					JOptionPane.showMessageDialog(null, "您还没有登录，无法使用此功能!");
			}else if(e.getSource()==button[6]){
				backGroundClip.stop();
				new MemoryChallengeDialog();
			}
		}
	}
	/**
	 * 主函数
	 * @param args
	 */
	
	public static void main(String[] args) {
		new RepeatWord();

	}

}
