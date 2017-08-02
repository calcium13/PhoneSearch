package local;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PhoneSearcherGUI extends JFrame{
	JLabel labelNumber=new JLabel("�绰����");
	JTextField textNumber=new JTextField(15);
	JButton buttonOk=new JButton("��ѯ");
	JButton buttonBackward=new JButton("����");
	JButton buttonClear=new JButton("���");
	JButton[] numbers=new JButton[12];
	PhoneSearcher ps=new PhoneSearcher("D:\\������¥�绰���ߣ�ʵ�⣩.xlsx");
	List<String> result=new ArrayList<String>();
	int height=300;
	int width=240;
	
	
	
	public PhoneSearcherGUI(){
		setLayout(null);
		add(this.labelNumber);
		add(this.textNumber);
		add(this.buttonOk);
		buttonOk.addActionListener(new OKListener());
		textNumber.addActionListener(new OKListener());
	}
	
	public PhoneSearcherGUI(String fileName){
		this.ps=new PhoneSearcher(fileName);
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
//		System.out.println(jpKeyboard.getHeight());
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
	
	/**
	 * ����ѯ����س���ʾ���
	 * @author Administrator
	 *
	 */
	class OKListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String str=textNumber.getText();
			try {
				result=ps.getResult(str);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(result.size()==0){
				JOptionPane.showMessageDialog(null, "��ѯ�ĵ绰���벻���ڻ�·������,��ȷ��");
			}
			else{
				PhoneSearcherGUI.printGUI(result);
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
	
	/**
	 * ��ͼ�ν����ӡ�������
	 * @param strs
	 */
	public static void printGUI(List<String> strs){
		JFrame frame=new JFrame();
		JTextArea jta=new JTextArea();
		frame.setTitle("������¥�绰���߲�ѯ���");
		for(int i=0;i<strs.size();i++){
			jta.append(strs.get(i));
		}		
		frame.add(jta);
		frame.setLocationRelativeTo(null);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,400);
		frame.setVisible(true);
	}
	
	public static void main(String[] args){
		PhoneSearcherGUI psg=new PhoneSearcherGUI("D:\\������¥�绰����(ʵ��).xlsx");
		psg.setTitle("�绰������Ϣ��ѯ");
		psg.setLocationRelativeTo(null);
		psg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		psg.setSize(800,400);
		psg.setVisible(true);
	}
}

