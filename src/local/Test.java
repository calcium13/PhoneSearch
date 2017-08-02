package local;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class Test {
	public static void main(String[] args) throws Exception {   
 //       extractor();   
        readTable();  
//		testWrite();
    }   
	
	public static void testWrite() throws Exception{
		InputStream ips=new FileInputStream("d://test2-1.xlsx");
		XSSFWorkbook wb=new XSSFWorkbook(ips);
		XSSFSheet sheet=(XSSFSheet)wb.getSheetAt(0);
		XSSFRow row=sheet.getRow(0);
		XSSFCell cell=row.getCell(0);
		cell.setCellValue(true);
	}
    //ͨ���Ե�Ԫ���������ʽ����ȡ��Ϣ ������Ҫ�жϵ�Ԫ������Ͳſ���ȡ��ֵ  
    public static void readTable() throws Exception{   
        InputStream ips=new FileInputStream("D://������¥�绰����(ʵ��).xlsx");   
        XSSFWorkbook wb=new XSSFWorkbook(ips); 
        int total=wb.getNumberOfSheets();
        for(int i=0;i<total;i++){
        	XSSFSheet sheet=(XSSFSheet)wb.getSheetAt(i);
        	for(Iterator ite=sheet.rowIterator();ite.hasNext();){   
                XSSFRow row=(XSSFRow)ite.next();   
                System.out.println();   
                for(Iterator itet=row.cellIterator();itet.hasNext();){   
                    XSSFCell cell=(XSSFCell)itet.next();   
                    switch(cell.getCellType()){     
                    case XSSFCell.CELL_TYPE_BOOLEAN:     
                        //�õ�Boolean����ķ���     
                        System.out.print(cell.getBooleanCellValue()+" ");     
                        break;     
                    case XSSFCell.CELL_TYPE_NUMERIC:   
                        //��ȡ����     
                    	double dTemp=cell.getNumericCellValue();
                    	BigDecimal bd=new BigDecimal(dTemp);
                        System.out.print(bd.toString()+" ");     
                        break;     
                    case XSSFCell.CELL_TYPE_FORMULA:     
                        //��ȡ��ʽ     
                        System.out.print(cell.getCellFormula()+" ");     
                        break;     
                    case XSSFCell.CELL_TYPE_STRING:     
                        //��ȡString     
                        System.out.print(cell.getRichStringCellValue().toString()+" ");     
                        break;                       
                    }    
                } 
                System.out.println();                
            }       
        }
//        System.out.println(wb.getNumberOfSheets());
    }   
    //ֱ�ӳ�ȡexcel�е�����   
    public static void extractor() throws Exception{   
        InputStream ips=new FileInputStream("d://test2-1.xls");   
        HSSFWorkbook wb=new HSSFWorkbook(ips);   
        ExcelExtractor ex=new ExcelExtractor(wb);   
        ex.setFormulasNotResults(true);   
        ex.setIncludeSheetNames(false);   
        String text=ex.getText();   
        System.out.println(text);    
    }   
}