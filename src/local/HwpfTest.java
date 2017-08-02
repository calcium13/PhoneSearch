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


public class HwpfTest {  
	
	public void testWrite() throws Exception {  
		String templatePath = "D:\\常州供电公司信息通信设备维护单（定稿）.doc";  
		InputStream is = new FileInputStream(templatePath);  
		HWPFDocument doc = new HWPFDocument(is);  
		Range range = doc.getRange(); 
		//把range范围内的${reportDate}替换为当前的日期  
		range.replaceText("${start}", new SimpleDateFormat("hh:mm").format(new Date()));  
		range.replaceText("${phone}", "100.00");  
		range.replaceText("${floor}", "200.00");  
		range.replaceText("${originport}", "300.00");  
		OutputStream os = new FileOutputStream("D:\\常州供电公司信息通信设备维护单（输出）.doc");  
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
