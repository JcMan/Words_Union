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
	public static boolean isStop = true;              //控制播放线程
	public static boolean hasStop = true;             //播放线程状态
	AudioInputStream audioInputStream=null ;  //文件流
	AudioFormat audioFormat=null;             //文件格式
	public static SourceDataLine sourceDataLine;            //输出设备
	public ListeneDialog(){
		int h=Toolkit.getDefaultToolkit().getScreenSize().height;
		int w=Toolkit.getDefaultToolkit().getScreenSize().width;
		this.setSize(w, h);
		this.setTitle("听力练习");
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
	 * 添加“打开文件”按钮
	 */
	JButton getOpenButton(){
		if(openButton == null){
			openButton = new JButton("打开文件");
			openButton.setFont(new Font("华文行楷",Font.PLAIN,25));
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
	 * 添加“开始播放”按钮
	 */
	JButton getStartButton(){
		if(startButton == null){
			startButton = new JButton("开始播放");
			startButton.setFont(new Font("华文行楷",Font.PLAIN,25));
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
	 * 添加“停止播放”按钮
	 */
	JButton getCloseButton(){
		if(closeButton == null){
			closeButton = new JButton("停止播放");
			closeButton.setFont(new Font("华文行楷",Font.PLAIN,25));
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
	 * 添加“退出”按钮
	 */
	JButton getExitButton(){
		if(exitButton == null){
			exitButton = new JButton("退出");
			exitButton.setFont(new Font("华文行楷",Font.PLAIN,25));
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
	 * 添加"文件目录"标签
	 */
	JLabel getFilePathLabel(){
		if(filePathLabel == null){
			filePathLabel = new JLabel();
			filePathLabel.setBounds(30, 150, 400, 100);
			filePathLabel.setFont(new Font("华文行楷",Font.BOLD,20));
			filePathLabel.setForeground(Color.GRAY);
		}
		return filePathLabel;
	}
	
	/**
	 * 添加”文件名称“标签
	 */
	JLabel getFileNameLabel(){
		if(fileNameLabel == null){
			fileNameLabel = new JLabel();
			fileNameLabel.setBounds(930, 150, 400, 100);
			fileNameLabel.setFont(new Font("华文行楷",Font.BOLD,20));
			fileNameLabel.setForeground(Color.GRAY);
		}
		return fileNameLabel;
	}
	/**
	 * 添加”时间“标签
	 */
	JLabel getTimeLabel(){
		if(timeLabel==null){
			timeLabel = new JLabel();
			timeLabel.setBounds(570, 120, 200, 100);
			timeLabel.setForeground(Color.RED);
			timeLabel.setFont(new Font("华文行楷",Font.BOLD,35));
		}
		return timeLabel;
	}
	/**
	 * 打开文件对话框
	 */
	void openFileDialog(){
		FileDialog fileDialog = new FileDialog(this,"Open",0);
		fileDialog.setVisible(true);
		filePath = fileDialog.getDirectory();
		if(filePath!=null){
			filePathLabel.setText("播放目录："+filePath);
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
	 * 添加左右两边图片
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
	 * 设置标签颜色线程
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
	 * 添加列表
	 */
	List getList(){
		if(list == null){
			list = new List();
			list.setBounds(450, 200, 450, 540);
			list.setBackground(Color.darkGray);
			list.setFont(new Font("仿宋",Font.PLAIN,20));
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
	 * 实现定时器的方法
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
	 * 双击列表的响应
	 */
	void listClicked(){
		h=0;m=0;s=0;
		if(time.isRunning())
			time.stop();
		time.start();
		try{
			isStop = true;  //停止播放线程
			//等待播放线程停止
			while(!hasStop){
				try{
					Thread.sleep(10);
				}catch(Exception e){}
			}
			File file = new File(filePath+fileName+".mp3");
			if(!file.exists())
				file = new File(filePath+fileName+".wav");
			fileNameLabel.setText("播放文件：" + fileName);
			//取得文件输入流
			try{
				audioInputStream = AudioSystem.getAudioInputStream(file);
				audioFormat = audioInputStream.getFormat();
				//转换MP3文件编码
				if(audioFormat.getEncoding()!=AudioFormat.Encoding.PCM_SIGNED){
					audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
							audioFormat.getSampleRate(),16,audioFormat.getChannels(),audioFormat.getChannels()*2,
							audioFormat.getSampleRate(),false);
					audioInputStream = AudioSystem.getAudioInputStream(audioFormat,audioInputStream);
				}
				//打开输出设备
				DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,audioFormat,AudioSystem.NOT_SPECIFIED);
				sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
				sourceDataLine.open(audioFormat);
				sourceDataLine.start();
			}catch(Exception e){}
			//创建独立线程进行播放
			isStop = false;
			Thread playThread = new Thread(new PlayThread());
			playThread.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//定义内部类PalyThread
	 class PlayThread extends Thread{
		byte[] tempBuffer = new byte[320];
		public void run(){
			try{
				int cnt;
				hasStop = false;
				//读取数据到缓存数据
				try{
					while((cnt = audioInputStream.read(tempBuffer,0,tempBuffer.length))!=-1){
						if(isStop)
							break;
						if(cnt>0)
							//写入缓存数据
							sourceDataLine.write(tempBuffer, 0, cnt);
					}
					//Block等待临时数据输出为空
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
