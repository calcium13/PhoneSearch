package local;

import java.util.List;

public class Additional {
	
	/**
	 * ��ȡ�п���д������������ݣ���������˳��Ϊ��
	 * ����(��һ�ű��������,��һ�ű�Ϊspecial,����Ϊordinary)�������(¥��)����ţ��绰���룬�ο�ɫ�ף����߼ܳ��߶˿ڣ���ע
	 * @param str��	�������֮һ
	 * @return
	 */
	public static String[] getWriteInfo(String str){
		String[] temp=str.split("\n");
		String[] tempLine1=temp[1].split("\t");
		String[] tempLine2=temp[2].split("\t");
		String[] tempLine3=temp[3].split("\t");
		String[] info=new String[tempLine3.length+2];
		if(tempLine2.length!=4){
			info[0]="special";
		}
		else{
			info[0]="ordinary";
		}
		info[1]=tempLine1[1];
		for(int i=2;i<info.length;i++){
			info[i]=tempLine3[i-2];
		}
		return info;
	}
	
	public static String tranferK(int i){
		if(i<10&&i>=0){
			return ("000"+Integer.toString(i));
		}
		else if(i>=10&&i<100){
			return ("00"+Integer.toString(i));
		}
		else if(i>=100&&i<1000){
			return ("0"+Integer.toString(i));
		}
		else if(i>=1000&&i<10000){
			return Integer.toString(i);
		}
		else{
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception{
		PhoneSearcher phoneSearcher=new PhoneSearcher("D:\\������¥�绰����(ʵ��).xlsx");
		List<String> ls=phoneSearcher.getResult("88101648");
		String[] strArr=getWriteInfo(ls.get(0));
		for(int i=0;i<strArr.length;i++){
			System.out.println(strArr[i]);
		}
	}
}
