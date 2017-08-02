package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import simbol.ConnectSignal;

import local.PhoneSearcher;

public class ServerDemo{
	PhoneSearcher phoneSearcher=new PhoneSearcher("D://本部大楼电话配线.xlsx");
	ServerSocket serverSocket;
	public ServerDemo(){
		try {
			serverSocket=new ServerSocket(8003);
			Socket socket=serverSocket.accept();
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		new ServerDemo();
	}
}
