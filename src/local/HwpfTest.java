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
		String templatePath = "D:\\���ݹ��繫˾��Ϣͨ���豸ά���������壩.doc";  
		InputStream is = new FileInputStream(templatePath);  
		HWPFDocument doc = new HWPFDocument(is);  
		Range range = doc.getRange(); 
		//��range��Χ�ڵ�${reportDate}�滻Ϊ��ǰ������  
		range.replaceText("${start}", new SimpleDateFormat("hh:mm").format(new Date()));  
		range.replaceText("${phone}", "100.00");  
		range.replaceText("${floor}", "200.00");  
		range.replaceText("${originport}", "300.00");  
		OutputStream os = new FileOutputStream("D:\\���ݹ��繫˾��Ϣͨ���豸ά�����������.doc");  
		//��doc������������  
		doc.write(os);  
		this.closeStream(os);  
		this.closeStream(is);  
	}  
	
		/** 
		 * �ر������� 
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
		 * �ر������ 
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
