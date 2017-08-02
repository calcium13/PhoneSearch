package local;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

public class HwpfDealer {
	String originFileName;
	String toFileName;
	
	public HwpfDealer(String originFileName,String toFileName){
		this.originFileName=originFileName;
		this.toFileName=toFileName;
	}
	
	public HwpfDealer(){
		this.originFileName="D:\\常州供电公司信息通信设备维护单（定稿）.doc";
		this.toFileName="D:\\常州供电公司信息通信设备维护单（输出）.doc";
	}
	
	/**
	 * 将搜索结果写入维护单中
	 * @param strArr：	处理后的搜索结果
	 * @throws Exception
	 */
	public void docTableWrite(String[] strArr) throws Exception {   
		InputStream is = new FileInputStream(originFileName);  
		HWPFDocument doc = new HWPFDocument(is);  
		Range range = doc.getRange(); 
		//把range范围内的${reportDate}替换为当前的日期  
		range.replaceText("${start}", new SimpleDateFormat("HH:mm").format(new Date()));  
		if(strArr[0].equals("ordinary")){
			range.replaceText("${phone}", strArr[3]);  
			range.replaceText("${floor}", strArr[1]);  
			range.replaceText("${originport}", strArr[5]);
		}
		if(strArr[0].equals("special")){
			range.replaceText("${phone}", strArr[4]);  
			range.replaceText("${floor}", strArr[1]);  
			range.replaceText("${originport}", strArr[6]);
		}
		OutputStream os = new FileOutputStream(toFileName); 
		//把doc输出到输出流中  
		doc.write(os);  
		this.closeStream(os);  
		this.closeStream(is);  
	}  
	
	public void docTableWrite(String[] strArr,int billNum) throws Exception{
		InputStream is = new FileInputStream(originFileName);  
		HWPFDocument doc = new HWPFDocument(is);  
		Range range = doc.getRange(); 
		//把range范围内的${reportDate}替换为当前的日期  
		range.replaceText("${start}", new SimpleDateFormat("HH:mm").format(new Date()));  
		if(Additional.tranferK(billNum)!=null){
			range.replaceText("${billNum}", ("SJD"+new SimpleDateFormat("yyyyMMdd").format(new Date())+Additional.tranferK(billNum)));
		}
		else{
			range.replaceText("${billNum}","");
		}
		if(strArr[0].equals("ordinary")){
			range.replaceText("${phone}", strArr[3]);  
			range.replaceText("${floor}", strArr[1]);  
			range.replaceText("${originport}", strArr[5]);
		}
		if(strArr[0].equals("special")){
			range.replaceText("${phone}", strArr[4]);  
			range.replaceText("${floor}", strArr[1]);  
			range.replaceText("${originport}", strArr[6]);
		}
		OutputStream os = new FileOutputStream(toFileName); 
		//把doc输出到输出流中  
		doc.write(os);  
		this.closeStream(os);  
		this.closeStream(is); 
	}
	
		/** 
		 * 关闭输入流 
		 * @param is 
		 */  
	private void closeStream(InputStream is) {  
		if (is != null) {  
			try {  
				is.close();  
			} catch (IOException e) {  
				e.printStackTrace();  
			}  
		}  
	}  

		/** 
		 * 关闭输出流 
		 * @param os 
		 */  
	private void closeStream(OutputStream os) {  
		if (os != null) {  
			try {  
				os.close();  
			} catch (IOException e) {  
				e.printStackTrace();  
			}  
		}  
	} 
	
	public static void main(String[] args) throws Exception{
		HwpfTest ht=new HwpfTest();
		ht.testWrite();
	}
}
