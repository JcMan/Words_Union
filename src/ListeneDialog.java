import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class ListeneDialog extends JFrame implements ActionListener{
	int h=0,m=0,s=0;
	String hs,ms,ss;
	private JButton openButton,startButton,closeButton,exitButton;
	private Timer time;
	private List list;
	String filePath;
	String fileName;
	JLabel filePathLabel,fileNameLabel;
	JLabel timeLabel;
	JLabel pictureLabelL,pictureLabelR;
	public static boolean isStop = true;              //���Ʋ����߳�
	public static boolean hasStop = true;             //�����߳�״̬
	AudioInputStream audioInputStream=null ;  //�ļ���
	AudioFormat audioFormat=null;             //�ļ���ʽ
	public static SourceDataLine sourceDataLine;            //����豸
	public ListeneDialog(){
		int h=Toolkit.getDefaultToolkit().getScreenSize().height;
		int w=Toolkit.getDefaultToolkit().getScreenSize().width;
		this.setSize(w, h);
		this.setTitle("������ϰ");
		this.setUndecorated(true);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.add(getOpenButton());
		this.add(getStartButton());
		this.add(getCloseButton());
		this.add(getExitButton());
		this.add(getFilePathLabel());
		this.add(getFileNameLabel());
		this.add(getList());
		this.add(getTimeLabel());
		addPictureLabel();
		setLabelColor();
		
	}
	/**
	 * ��ӡ����ļ�����ť
	 */
	JButton getOpenButton(){
		if(openButton == null){
			openButton = new JButton("���ļ�");
			openButton.setFont(new Font("�����п�",Font.PLAIN,25));
			openButton.setBounds(125, 20, 200, 100);
			openButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					openFileDialog();
				}
			});
		}
		return openButton;
	}
	
	/**
	 * ��ӡ���ʼ���š���ť
	 */
	JButton getStartButton(){
		if(startButton == null){
			startButton = new JButton("��ʼ����");
			startButton.setFont(new Font("�����п�",Font.PLAIN,25));
			startButton.setBounds(425, 20, 200, 100);
			startButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					fileName = list.getSelectedItem();
					listClicked();
				}
			});
		}
		return startButton;
	}
	
	/**
	 * ��ӡ�ֹͣ���š���ť
	 */
	JButton getCloseButton(){
		if(closeButton == null){
			closeButton = new JButton("ֹͣ����");
			closeButton.setFont(new Font("�����п�",Font.PLAIN,25));
			closeButton.setBounds(725, 20, 200, 100);
			closeButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(sourceDataLine!=null)
						sourceDataLine.close();
					timeLabel.setText(null);
					time.stop();
				}
			});
		}
		return closeButton;
	}
	
	/**
	 * ��ӡ��˳�����ť
	 */
	JButton getExitButton(){
		if(exitButton == null){
			exitButton = new JButton("�˳�");
			exitButton.setFont(new Font("�����п�",Font.PLAIN,25));
			exitButton.setBounds(1025, 20, 200, 100);
			exitButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(sourceDataLine!=null)
						sourceDataLine.close();
					setVisible(false);
					RepeatWord.backGroundClip.loop();
					time.stop();
				}
			});
		}
		return exitButton;
	}
	
	/**
	 * ���"�ļ�Ŀ¼"��ǩ
	 */
	JLabel getFilePathLabel(){
		if(filePathLabel == null){
			filePathLabel = new JLabel();
			filePathLabel.setBounds(30, 150, 400, 100);
			filePathLabel.setFont(new Font("�����п�",Font.BOLD,20));
			filePathLabel.setForeground(Color.GRAY);
		}
		return filePathLabel;
	}
	
	/**
	 * ��ӡ��ļ����ơ���ǩ
	 */
	JLabel getFileNameLabel(){
		if(fileNameLabel == null){
			fileNameLabel = new JLabel();
			fileNameLabel.setBounds(930, 150, 400, 100);
			fileNameLabel.setFont(new Font("�����п�",Font.BOLD,20));
			fileNameLabel.setForeground(Color.GRAY);
		}
		return fileNameLabel;
	}
	/**
	 * ��ӡ�ʱ�䡰��ǩ
	 */
	JLabel getTimeLabel(){
		if(timeLabel==null){
			timeLabel = new JLabel();
			timeLabel.setBounds(570, 120, 200, 100);
			timeLabel.setForeground(Color.RED);
			timeLabel.setFont(new Font("�����п�",Font.BOLD,35));
		}
		return timeLabel;
	}
	/**
	 * ���ļ��Ի���
	 */
	void openFileDialog(){
		FileDialog fileDialog = new FileDialog(this,"Open",0);
		fileDialog.setVisible(true);
		filePath = fileDialog.getDirectory();
		if(filePath!=null){
			filePathLabel.setText("����Ŀ¼��"+filePath);
			list.removeAll();
			File filedir = new File(filePath);
			File fileList[]=filedir.listFiles();
			for(File file:fileList){
				fileName = file.getName().toLowerCase();
				if(fileName.endsWith("mp3")||fileName.endsWith("wav")){
					String str = fileName.substring(0, fileName.length()-4);
					list.add(str);
				}
			}
		}
		
	}
	/**
	 * �����������ͼƬ
	 * 
	 */
	void addPictureLabel(){
		pictureLabelL = new JLabel();
		pictureLabelR= new JLabel();
		this.add(pictureLabelL);
		this.add(pictureLabelR);
		pictureLabelL.setBounds(100, 240, 250,454);
		pictureLabelL.setIcon(new ImageIcon(ListeneDialog.class.getResource("/img/1.jpg")));
		pictureLabelR.setBounds(1010, 240, 250, 454);
		pictureLabelR.setIcon(new ImageIcon(ListeneDialog.class.getResource("/img/2.jpg")));
		Thread t = new Thread(new Runnable(){
			int i,j;
			public void run(){
				while(true){
					try{
						Thread.sleep(2000);
					}catch(Exception e){}
					i= new Random().nextInt(28);
					j=i+1;
					ImageIcon icon1 = new ImageIcon(ListeneDialog.class.getResource("/img/"+i+".jpg"));
					ImageIcon icon2 = new ImageIcon(ListeneDialog.class.getResource("/img/"+j+".jpg"));
					pictureLabelL.setIcon(icon1);
					try{
						Thread.sleep(2000);
					}catch(Exception e){}
					pictureLabelR.setIcon(icon2);
				}
			}
		});
		t.start();
	}
	/**
	 * ���ñ�ǩ��ɫ�߳�
	 */
	void setLabelColor(){
		Thread t= new Thread(new Runnable(){
			Color color;
			int i;
			public void run(){
				while(true){
					try{
						Thread.sleep(1000);
					}catch(Exception e){}
					i=new Random().nextInt(7);
					switch(i){
					case 0:color=Color.BLACK;break;
					case 1:color=Color.BLUE;break;
					case 2:color=Color.CYAN;break;
					case 3:color=Color.GRAY;break;
					case 4:color=Color.GREEN;break;
					case 5:color=Color.PINK;break;
					case 6:color=Color.RED;break;
					}
					filePathLabel.setForeground(color);
					fileNameLabel.setForeground(color);
				}
			}
		});
		t.start();
	}
	
	/**
	 * ����б�
	 */
	List getList(){
		if(list == null){
			list = new List();
			list.setBounds(450, 200, 450, 540);
			list.setBackground(Color.darkGray);
			list.setFont(new Font("����",Font.PLAIN,20));
			list.setForeground(Color.CYAN);
			list.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					if(e.getClickCount()==2){
						fileName = list.getSelectedItem();
						listClicked();
					}
				}
			});
			time = new Timer(1000,this);
		}
		return list;
	}
	/**
	 * ʵ�ֶ�ʱ���ķ���
	 */
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==time){
			s++;
			if(s==60){
				s=0;
				m++;
				if(m==60){
					m=0;
					h++;
				}
			}
			hs=String.format("%02d",h);
			ms=String.format("%02d",m);
			ss=String.format("%02d",s);
			String S=hs+":"+ms+":"+ss;
			timeLabel.setText(S);
		}
		
	}
	/**
	 * ˫���б����Ӧ
	 */
	void listClicked(){
		h=0;m=0;s=0;
		if(time.isRunning())
			time.stop();
		time.start();
		try{
			isStop = true;  //ֹͣ�����߳�
			//�ȴ������߳�ֹͣ
			while(!hasStop){
				try{
					Thread.sleep(10);
				}catch(Exception e){}
			}
			File file = new File(filePath+fileName+".mp3");
			if(!file.exists())
				file = new File(filePath+fileName+".wav");
			fileNameLabel.setText("�����ļ���" + fileName);
			//ȡ���ļ�������
			try{
				audioInputStream = AudioSystem.getAudioInputStream(file);
				audioFormat = audioInputStream.getFormat();
				//ת��MP3�ļ�����
				if(audioFormat.getEncoding()!=AudioFormat.Encoding.PCM_SIGNED){
					audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
							audioFormat.getSampleRate(),16,audioFormat.getChannels(),audioFormat.getChannels()*2,
							audioFormat.getSampleRate(),false);
					audioInputStream = AudioSystem.getAudioInputStream(audioFormat,audioInputStream);
				}
				//������豸
				DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,audioFormat,AudioSystem.NOT_SPECIFIED);
				sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
				sourceDataLine.open(audioFormat);
				sourceDataLine.start();
			}catch(Exception e){}
			//���������߳̽��в���
			isStop = false;
			Thread playThread = new Thread(new PlayThread());
			playThread.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//�����ڲ���PalyThread
	 class PlayThread extends Thread{
		byte[] tempBuffer = new byte[320];
		public void run(){
			try{
				int cnt;
				hasStop = false;
				//��ȡ���ݵ���������
				try{
					while((cnt = audioInputStream.read(tempBuffer,0,tempBuffer.length))!=-1){
						if(isStop)
							break;
						if(cnt>0)
							//д�뻺������
							sourceDataLine.write(tempBuffer, 0, cnt);
					}
					//Block�ȴ���ʱ�������Ϊ��
					sourceDataLine.drain();
					sourceDataLine.close();
				}catch(Exception e){}
				hasStop = true;
			}catch(Exception e){
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
}
