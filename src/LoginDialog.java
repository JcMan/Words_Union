import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LoginDialog extends JDialog{
	private JLabel label;
	private JButton closeButton;
	private JTextField text;
	private JPasswordField password;
	private JButton loginButton;
	public LoginDialog(){
		init();
		addJLabel();
		addCloseButton();
		addJTextField();
		addJPasswordField();
		addLoginButton();
	}
	void init(){                 //��ʼ������
		this.setSize(300, 350);
		this.setAlwaysOnTop(true);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setLayout(null);
	}
	void addJLabel(){                 //��ӱ�����ǩ
		if(label == null){
			label = new JLabel();
			label.setIcon(new ImageIcon(LoginDialog.class.getResource("/res/loginDialog.jpg")));
			this.add(label);
			label.setBounds(0, 0, 300, 350);
		}
	}
	void addCloseButton(){               //��ӹرհ�ť
		if(closeButton == null){
			closeButton = new JButton();
			closeButton.setBounds(274,0,26,20);
			closeButton.setIcon(new ImageIcon(LoginDialog.class.getResource("/res/normal.png")));
			closeButton.setPressedIcon(new ImageIcon(LoginDialog.class.getResource("/res/press.png")));
			closeButton.setRolloverIcon(new ImageIcon(LoginDialog.class.getResource("/res/enter.png")));
			closeButton.setContentAreaFilled(false);
			closeButton.setBorder(null);
			closeButton.setBorderPainted(false);
			label.add(closeButton);
			closeButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					setVisible(false);
				}
			});
		}
	}
	void addJTextField(){              //����û�����
		if(text == null){
			text = new JTextField();
			text.setBounds(32, 68, 233, 28);
			label.add(text);
			text.setOpaque(false);
			text.setBorder(null);
			text.setCaretColor(Color.BLUE);
			text.setForeground(Color.CYAN);
			text.requestFocus();
			text.setFont(new Font("����",Font.PLAIN,18));
		}
	}
	void addJPasswordField(){            //��������
		if(password == null){
			password = new JPasswordField();
			password.setBounds(32,135,233,28);
			label.add(password);
			password.setOpaque(false);
			password.setBorder(null);
			password.setCaretColor(Color.BLUE);
			password.setForeground(Color.CYAN);
			
		}
	}
	void addLoginButton(){                      //��ӵ�¼��ť
		if(loginButton == null){
			loginButton = new JButton();
			loginButton.setBounds(83, 277, 129, 44);
			
			loginButton.setContentAreaFilled(false);
			loginButton.setBorder(null);
			loginButton.setIcon(new ImageIcon(LoginDialog.class.getResource("/res/loginButton.JPG")));
			loginButton.setRolloverIcon(new ImageIcon(LoginDialog.class.getResource("/res/loginButton-in.JPG")));
			label.add(loginButton);
			loginButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					String name = text.getText();
					String passwordText = new String(password.getPassword());
					if(name.equals("panjiancheng")&&passwordText.equals("123456")){ 
						setVisible(false);
						addShowRightMessageDialog();
					}
					else
						addShowWorngMessageDialog();
						
				}
			});
		}
	}
	void addShowRightMessageDialog(){
		JOptionPane.showMessageDialog(this,"��¼�ɹ�!", "ϵͳ��ʾ",JOptionPane.INFORMATION_MESSAGE);
		this.setVisible(false);
		RepeatWord.isLogin = true;
	}
	void addShowWorngMessageDialog(){
		JOptionPane.showMessageDialog(this,"��¼ʧ��!", "ϵͳ��ʾ",JOptionPane.ERROR_MESSAGE);
	}
}

