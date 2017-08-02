package local;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * ��д������ͼ�ν���
 * @author Administrator
 *
 */
public class CreateTableGUI extends JFrame{
	List<String> data;
	JButton[] createButton;
	JTextArea[] showData;
	JTextField jt;
	String fileOrigin="���ݹ��繫˾��Ϣͨ���豸ά���������壩.doc";
	String fileOutput="���ݹ��繫˾��Ϣͨ���豸ά�����������.doc";
	
	/**
	 * ͨ�����������Ĭ��·����д����
	 * @param data��	�������
	 */
	public CreateTableGUI(List<String> data){
		this.data=new ArrayList<String>(data);
		createButton=new JButton[this.data.size()];
		showData=new JTextArea[this.data.size()];
		for(int i=0;i<createButton.length;i++){
			createButton[i]=new JButton("��������");
			showData[i]=new JTextArea(this.data.get(i));
		}
		this.setLayout(new BorderLayout());
		
		JPanel centerPanel=new JPanel();
		centerPanel.setLayout(new GridLayout(this.data.size()+1,1));
		this.add(centerPanel,BorderLayout.CENTER);
		//������
		JPanel jtCon=new JPanel();
		jtCon.setLayout(new GridLayout(1,2));
		jt=new JTextField();
		jtCon.add(new Label("ά��������"));
		jtCon.add(jt);
		add(jtCon,BorderLayout.NORTH);
		//��Ϣ��
		JPanel[] jpArr=new JPanel[this.data.size()];
		for(int j=0;j<jpArr.length;j++){
			jpArr[j]=new JPanel();
			jpArr[j].setLayout(new BorderLayout());
			jpArr[j].add(showData[j],BorderLayout.CENTER);
			//������������
			JPanel buttonTempPanel=new JPanel();
			buttonTempPanel.setLayout(new GridLayout(1,3));
			buttonTempPanel.add(new JLabel());	//�ձ�ǩռλ
			buttonTempPanel.add(createButton[j]);
			buttonTempPanel.add(new JLabel());	//�ձ�ǩռλ
			
			jpArr[j].add(buttonTempPanel,BorderLayout.SOUTH);
			createButton[j].addActionListener(new CreateButtonListener(showData[j]));
		}		
		for(int k=0;k<this.data.size();k++){
			jpArr[k].setSize(400,200);
			centerPanel.add(jpArr[k]);
		}
	}
	
	/**
	 * ͨ������������ļ�����д����
	 * @param data:	�������
	 * @param fileOrigin��	ԭʼ����·��
	 * @param fileOutput��	��д��Ĺ���·��
	 */
	public CreateTableGUI(List<String> data,String fileOrigin,String fileOutput){
		this(data);
		this.fileOrigin=fileOrigin;
		this.fileOutput=fileOutput;		
	}
	
	/**
	 * ͨ����������������ļ���д����
	 * @param data:	�������
	 * @param fileName:	�����ļ�������ֻ��Ҫ���У���һ��Ϊԭʼ����·�����ڶ���Ϊ��д������Ĺ���·��
	 */
	public CreateTableGUI(List<String> data,String fileName){
		this(data);
		File f=new File(fileName);
		try{
			FileReader fr=new FileReader(f);
			BufferedReader br=new BufferedReader(fr);
			this.fileOrigin=br.readLine();
			this.fileOutput=br.readLine();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ������������������д����
	 * @author Administrator
	 *
	 */
	class CreateButtonListener implements ActionListener{
		JTextArea jta;
		//����������ÿ����Ӧһ������
		public CreateButtonListener(JTextArea jta){
			this.jta=jta;
		}
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String str=jta.getText();
			String[] strArr=Additional.getWriteInfo(str);
			HwpfDealer hd=new HwpfDealer(fileOrigin,fileOutput);
			try {
				hd.docTableWrite(strArr,Integer.parseInt(jt.getText()));
				JOptionPane.showMessageDialog(null, "�����������");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "������һ���Ϸ�����(0-9999)");
			}
		}
		
	}
	public static void main(String[] args){
		List<String> listStr=new ArrayList<String>();
		listStr.add("1");
		listStr.add("2");
		CreateTableGUI ctu=new CreateTableGUI(listStr);
		ctu.setVisible(true);
		ctu.setLocationRelativeTo(null);
		ctu.setSize(400,400);
		ctu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
