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
 * 填写工单的图形界面
 * @author Administrator
 *
 */
public class CreateTableGUI extends JFrame{
	List<String> data;
	JButton[] createButton;
	JTextArea[] showData;
	JTextField jt;
	String fileOrigin="常州供电公司信息通信设备维护单（定稿）.doc";
	String fileOutput="常州供电公司信息通信设备维护单（输出）.doc";
	
	/**
	 * 通过搜索结果和默认路径填写工单
	 * @param data：	搜索结果
	 */
	public CreateTableGUI(List<String> data){
		this.data=new ArrayList<String>(data);
		createButton=new JButton[this.data.size()];
		showData=new JTextArea[this.data.size()];
		for(int i=0;i<createButton.length;i++){
			createButton[i]=new JButton("创建工单");
			showData[i]=new JTextArea(this.data.get(i));
		}
		this.setLayout(new BorderLayout());
		
		JPanel centerPanel=new JPanel();
		centerPanel.setLayout(new GridLayout(this.data.size()+1,1));
		this.add(centerPanel,BorderLayout.CENTER);
		//计数栏
		JPanel jtCon=new JPanel();
		jtCon.setLayout(new GridLayout(1,2));
		jt=new JTextField();
		jtCon.add(new Label("维护单计数"));
		jtCon.add(jt);
		add(jtCon,BorderLayout.NORTH);
		//信息栏
		JPanel[] jpArr=new JPanel[this.data.size()];
		for(int j=0;j<jpArr.length;j++){
			jpArr[j]=new JPanel();
			jpArr[j].setLayout(new BorderLayout());
			jpArr[j].add(showData[j],BorderLayout.CENTER);
			//创建工单按键
			JPanel buttonTempPanel=new JPanel();
			buttonTempPanel.setLayout(new GridLayout(1,3));
			buttonTempPanel.add(new JLabel());	//空标签占位
			buttonTempPanel.add(createButton[j]);
			buttonTempPanel.add(new JLabel());	//空标签占位
			
			jpArr[j].add(buttonTempPanel,BorderLayout.SOUTH);
			createButton[j].addActionListener(new CreateButtonListener(showData[j]));
		}		
		for(int k=0;k<this.data.size();k++){
			jpArr[k].setSize(400,200);
			centerPanel.add(jpArr[k]);
		}
	}
	
	/**
	 * 通过搜索结果和文件名填写工单
	 * @param data:	搜索结果
	 * @param fileOrigin：	原始工单路径
	 * @param fileOutput：	填写后的工单路径
	 */
	public CreateTableGUI(List<String> data,String fileOrigin,String fileOutput){
		this(data);
		this.fileOrigin=fileOrigin;
		this.fileOutput=fileOutput;		
	}
	
	/**
	 * 通过搜索结果和配置文件填写工单
	 * @param data:	搜索结果
	 * @param fileName:	配置文件，其中只需要两行，第一行为原始工单路径，第二行为填写后输出的工单路径
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
	 * 按键监听，按下则填写工单
	 * @author Administrator
	 *
	 */
	class CreateButtonListener implements ActionListener{
		JTextArea jta;
		//多个搜索结果每个对应一个按键
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
				JOptionPane.showMessageDialog(null, "工单创建完成");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "请输入一个合法数字(0-9999)");
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
