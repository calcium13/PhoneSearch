package client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import simbol.ConnectSignal;

import local.Additional;
import local.CreateTableGUI;
import local.HwpfDealer;
import local.PhoneSearcher;
import local.PhoneSearcherGUI;

public class ClientDemo extends JFrame{
	JLabel labelNumber=new JLabel("电话号码");
	JTextField textNumber=new JTextField(15);
	JButton buttonOk=new JButton("查询");
	JButton buttonBackward=new JButton("后退");
	JButton buttonClear=new JButton("清除");
	JButton[] numbers=new JButton[12];
	List<String> result=new ArrayList<String>();
	int height=300;
	int width=240;

	Socket socket;
	DataInputStream fromServer;
	DataOutputStream toServer;
	
	
	
	public ClientDemo(String ip,int port){
		try {				
			socket=new Socket(ip,port);
			fromServer=new DataInputStream(socket.getInputStream());
			toServer=new DataOutputStream(socket.getOutputStream());		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "请检查连接问题(服务器是否开启?)");
			return ;
		}
		
		setLayout(new BorderLayout());
		for(int i=0;i<10;i++){
			numbers[i]=new JButton(Integer.toString(i));;
		}		
		numbers[10]=new JButton("#");
		numbers[11]=new JButton("*");		
		textNumber.addActionListener(new OKListener());
		this.setSize(width,height);
		
		//查询框
		JPanel jpSearch=new JPanel();
		jpSearch.setLayout(new GridLayout(1,2,5,5));
		jpSearch.add(labelNumber);
		jpSearch.add(textNumber);
		add(jpSearch,BorderLayout.NORTH);
		
		//拨号键盘
		JPanel jpKeyboard=new JPanel();
		jpKeyboard.setLayout(null);
		jpKeyboard.setSize(200,200);
		int startX=(width-150)/2-10,startY=6;
		int locationX=startX;
		int locationY=startY;
		for(int i=0;i<numbers.length;i++){
			int size=50;
			jpKeyboard.add(numbers[i]);
			numbers[i].addActionListener(new NumberListener(numbers[i]));
			numbers[i].setBounds(locationX, locationY, size, size);
			locationX+=size;
			if(i%3==2){
				locationY+=size;
				locationX=startX;
			}
		}
		add(jpKeyboard,BorderLayout.CENTER);
		
		//按键：查询，后退和清除
		JPanel jpFunction=new JPanel();
//		jpFunction.setSize(800,200);
		jpFunction.setLayout(new GridLayout(1,3,5,5));
		jpFunction.add(buttonBackward);
		jpFunction.add(buttonClear);
		jpFunction.add(buttonOk);
		buttonOk.addActionListener(new OKListener());
		buttonBackward.addActionListener(new BackwardListener());
		buttonClear.addActionListener(new ClearListener());
		add(jpFunction,BorderLayout.SOUTH);
	}
	
	public ClientDemo(){
		this("localhost",8000);
	}
	
	/**
	 * 按查询键或回车显示结果
	 * @author Administrator
	 *
	 */
	class OKListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub			
			String str=textNumber.getText();
			List<String> result=new ArrayList<String>();
			try {
				toServer.flush();
				toServer.writeUTF(str);	//发送查询的号码
				toServer.flush();
				int total=fromServer.readInt();	//接收结果总数
				for(int i=0;i<total;i++){	//逐个获取查询结果
					String sTmp=fromServer.readUTF();
					result.add(sTmp);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//打印结果
			if(result.size()==0){
				JOptionPane.showMessageDialog(null, "查询的电话号码不存在或服务器路径错误,请确认");
			}
			else{
				CreateTableGUI ctu=new CreateTableGUI(result,"clientSetting.ini");
				ctu.setVisible(true);
				ctu.setLocationRelativeTo(null);
				ctu.setSize(600,result.size()*150+150);
				/*
				PhoneSearcherGUI.printGUI(result);
				String[] strArr=Additional.getWriteInfo(result.get(0));
				HwpfDealer hd=new HwpfDealer("常州供电公司信息通信设备维护单（定稿）.doc","常州供电公司信息通信设备维护单（输出）.doc");
				try {
					hd.docTableWrite(strArr);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}			
		}
	}
	
	/**
	 * 按后退键清除上一个输入的字符，回退到上一步
	 * @author Administrator
	 *
	 */
	class BackwardListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String str=textNumber.getText();
			textNumber.setText(str.substring(0,str.length()-1));
		}
		
	}
	
	/**
	 * 按清除键清除搜索框中的所有内容
	 * @author Administrator
	 *
	 */
	class ClearListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			textNumber.setText("");
		}
		
	}
	
	/**
	 * 点相应的数字键在框中出现相应的数字或符号
	 * @author Administrator
	 *
	 */
	class NumberListener implements ActionListener{
		JButton number;
		public NumberListener(JButton number){
			this.number=number;
		}
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			textNumber.setText(textNumber.getText()+this.number.getText());
		}
		
	}
	
	
	public static void main(String[] args) throws Exception{
		//从文件读取服务器地址和端口号
		File file=new File("serverAddress.ini");
		FileReader fr=new FileReader(file);
		BufferedReader br=new BufferedReader(fr);
		String ip=br.readLine();
		String portStr=br.readLine();
		int port=Integer.parseInt(portStr);
		ClientDemo cD=new ClientDemo(ip,port);
		cD.setLocationRelativeTo(null);
		cD.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cD.setVisible(true);
	}
}
