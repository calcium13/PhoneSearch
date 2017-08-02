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
	 * 端口号和搜索文件位置启动服务器
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
		this("Z:\\信息\\信息运检班\\电话IAD\\本部大楼电话配线（实测）.xlsx");
	}
	
	class Processor implements Runnable{

		public void run() {
			// TODO Auto-generated method stub
			try{
				DataInputStream fromClient=new DataInputStream(socket.getInputStream());
				DataOutputStream toClient=new DataOutputStream(socket.getOutputStream());
				//查询过程
				while(true){
					String phoneNumber=fromClient.readUTF();	//接收查询的号码
					List<String> searchResult=phoneSearcher.getResult(phoneNumber);	//得到查询结果
					//向客户端传送查询结果
					toClient.writeInt(searchResult.size());	//客户端准备接收的结果数量
					toClient.flush();
					for(int i=0;i<searchResult.size();i++){
						toClient.writeUTF(searchResult.get(i));	//依次传输结果
						toClient.flush();
					}			
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	}
	public static void main(String[] args) throws Exception{
		//从文件读取设置的端口号和文件的位置
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
