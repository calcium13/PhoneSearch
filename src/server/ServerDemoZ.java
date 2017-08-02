package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import simbol.ConnectSignal;

import local.PhoneSearcher;

public class ServerDemoZ{
	PhoneSearcher phoneSearcher;
	ServerSocket serverSocket;
	Socket socket;
	
	/**
	 * �˿ںź������ļ�λ������������
	 * @param port
	 * @param fileName
	 */
	public ServerDemoZ(int port,String fileName){
		phoneSearcher=new PhoneSearcher(fileName);
		try {
			serverSocket=new ServerSocket(port);
			while(true){
				socket=serverSocket.accept();
				Thread t=new Thread(new Processor());
				t.start();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ServerDemoZ(String fileName){
		this(8000,fileName);		
	}
	
	public ServerDemoZ(){
		this("Z:\\��Ϣ\\��Ϣ�˼��\\�绰IAD\\������¥�绰���ߣ�ʵ�⣩.xlsx");
	}
	
	class Processor implements Runnable{

		public void run() {
			// TODO Auto-generated method stub
			try{
				DataInputStream fromClient=new DataInputStream(socket.getInputStream());
				DataOutputStream toClient=new DataOutputStream(socket.getOutputStream());
				//��ѯ����
				while(true){
					String phoneNumber=fromClient.readUTF();	//���ղ�ѯ�ĺ���
					List<String> searchResult=phoneSearcher.getResult(phoneNumber);	//�õ���ѯ���
					//��ͻ��˴��Ͳ�ѯ���
					toClient.writeInt(searchResult.size());	//�ͻ���׼�����յĽ������
					toClient.flush();
					for(int i=0;i<searchResult.size();i++){
						toClient.writeUTF(searchResult.get(i));	//���δ�����
						toClient.flush();
					}			
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	}
	public static void main(String[] args) throws Exception{
		//���ļ���ȡ���õĶ˿ںź��ļ���λ��
		File file=new File("serverSetting.ini");
		FileReader fr=new FileReader(file);
		BufferedReader br=new BufferedReader(fr);
		String fileName=br.readLine();
		String portStr=br.readLine();
		System.out.println(fileName);
		int port=Integer.parseInt(portStr);
		new ServerDemoZ(port,fileName);
		System.out.println(fileName);
	}
}
