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
	JLabel labelNumber=new JLabel("�绰����");
	JTextField textNumber=new JTextField(15);
	JButton buttonOk=new JButton("��ѯ");
	JButton buttonBackward=new JButton("����");
	JButton buttonClear=new JButton("���");
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
			JOptionPane.showMessageDialog(null, "������������(�������Ƿ���?)");
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
		
		//��ѯ��
		JPanel jpSearch=new JPanel();
		jpSearch.setLayout(new GridLayout(1,2,5,5));
		jpSearch.add(labelNumber);
		jpSearch.add(textNumber);
		add(jpSearch,BorderLayout.NORTH);
		
		//���ż���
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
		
		//��������ѯ�����˺����
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
	 * ����ѯ����س���ʾ���
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
				toServer.writeUTF(str);	//���Ͳ�ѯ�ĺ���
				toServer.flush();
				int total=fromServer.readInt();	//���ս������
				for(int i=0;i<total;i++){	//�����ȡ��ѯ���
					String sTmp=fromServer.readUTF();
					result.add(sTmp);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//��ӡ���
			if(result.size()==0){
				JOptionPane.showMessageDialog(null, "��ѯ�ĵ绰���벻���ڻ������·������,��ȷ��");
			}
			else{
				CreateTableGUI ctu=new CreateTableGUI(result,"clientSetting.ini");
				ctu.setVisible(true);
				ctu.setLocationRelativeTo(null);
				ctu.setSize(600,result.size()*150+150);
				/*
				PhoneSearcherGUI.printGUI(result);
				String[] strArr=Additional.getWriteInfo(result.get(0));
				HwpfDealer hd=new HwpfDealer("���ݹ��繫˾��Ϣͨ���豸ά���������壩.doc","���ݹ��繫˾��Ϣͨ���豸ά�����������.doc");
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
	 * �����˼������һ��������ַ������˵���һ��
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
	 * �����������������е���������
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
	 * ����Ӧ�����ּ��ڿ��г�����Ӧ�����ֻ����
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
		//���ļ���ȡ��������ַ�Ͷ˿ں�
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
