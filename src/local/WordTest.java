package local;

import java.io.FileInputStream;  	
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;  
import org.apache.poi.hwpf.usermodel.Paragraph;  
import org.apache.poi.hwpf.usermodel.Range;  
import org.apache.poi.hwpf.usermodel.Table;  
import org.apache.poi.hwpf.usermodel.TableCell;  
import org.apache.poi.hwpf.usermodel.TableIterator;  
import org.apache.poi.hwpf.usermodel.TableRow;  
import org.apache.poi.poifs.filesystem.POIFSFileSystem;  
	  
public class WordTest{  
	public  static void main(String[] args){  
		try {  
			List<String> lStr=new ArrayList<String>();  
			FileInputStream in=new FileInputStream("D:\\常州供电公司信息通信设备维护单（定稿）.doc");  
			POIFSFileSystem pfs=new POIFSFileSystem(in);  
			HWPFDocument hwpf=new HWPFDocument(pfs);  
			Range range =hwpf.getRange();
			TableIterator it=new TableIterator(range);  
			int index=0;  
			while(it.hasNext()){  
				Table tb=(Table)it.next();  
				for(int i=0;i<tb.numRows();i++){  
//					System.out.println("Numrows :"+tb.numRows());  
					TableRow tr=tb.getRow(i);  
					for(int j=0;j<tr.numCells();j++){  
						//System.out.println("numCells :"+tr.numCells());  
//						System.out.println("j   :"+j);  
						TableCell td=tr.getCell(j); 
						lStr.add(td.text().trim());	
						System.out.println(i+"行"+j+"列:	"+td.text().trim());
						/*
						for(int k=0;k<td.numParagraphs();k++){  
							//System.out.println("numParagraphs :"+td.numParagraphs());  
							Paragraph para=td.getParagraph(k);  
							lStr.add(para.text().trim());  
							index++;  
						} */ 
					}  
				} 
			}  
	//          System.out.println(s.toString());  
			for(int i=0;i<lStr.size();i++){  
//				System.out.println(lStr.get(i));  
			}  
		} catch (Exception e) {  
			e.printStackTrace();  
		}  
		}  
}

