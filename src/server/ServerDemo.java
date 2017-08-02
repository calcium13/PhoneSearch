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
	PhoneSearcher phoneSearcher=new PhoneSearcher("D://������¥�绰����.xlsx");
	ServerSocket serverSocket;
	public ServerDemo(){
		try {
			serverSocket=new ServerSocket(8003);
			Socket socket=serverSocket.accept();
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
