package local;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class PhoneSearcher {
	String fileName;
	
	public PhoneSearcher(String fileName){
		this.fileName=fileName;
	}
/*	
	public void printInfo(String phoneNumber)throws Exception{
//		InputStream ips=new FileInputStream("D://本部大楼电话配线.xlsx");   
		InputStream ips=new FileInputStream(fileName); 
        XSSFWorkbook wb=new XSSFWorkbook(ips); 
        int total=wb.getNumberOfSheets();
        for(int i=0;i<total;i++){
        	XSSFSheet sheet=(XSSFSheet)wb.getSheetAt(i);
        	if(i==0){
        		String sheetResult=searchSheet(phoneNumber,sheet,2);
        		if(sheetResult==null){
        			;
        		}
        		else{
 //       			System.out.println("所在表格："+sheet.getSheetName());
        			PhoneSearcherGUI.printGUI("所在表格："+sheet.getSheetName()+'\n'+sheetResult);
        		}
        	}
        	else{
        		String sheetResult=searchSheet(phoneNumber,sheet,1);
        		if(sheetResult==null){
        			;
        		}
        		else{
  //      			System.out.println("所在表格："+sheet.getSheetName());
        			PhoneSearcherGUI.printGUI("所在表格："+sheet.getSheetName()+'\n'+sheetResult);
        		}
        	}      	
        }
	}
*/	
	/**
	 * 将所有搜索结果按字符串存储
	 * @param phoneNumber
	 * @return
	 * @throws Exception
	 */
	public List<String> getResult(String phoneNumber)throws Exception{
//		InputStream ips=new FileInputStream("D://本部大楼电话配线.xlsx");   
		InputStream ips=new FileInputStream(fileName); 
        XSSFWorkbook wb=new XSSFWorkbook(ips); 
        int total=wb.getNumberOfSheets();
        List<String> result=new ArrayList<String>();
        for(int i=0;i<total;i++){
        	XSSFSheet sheet=(XSSFSheet)wb.getSheetAt(i);
        	if(i==0){
        		String sheetResult=searchSheet(phoneNumber,sheet,2);
        		if(sheetResult==null){
        			;
        		}
        		else{
        			result.add("\n所在表格：	"+sheet.getSheetName()+'\n'+sheetResult);
        		}
        	}
        	else{
        		String sheetResult=searchSheet(phoneNumber,sheet,1);
        		if(sheetResult==null){
        			;
        		}
        		else{
        			result.add("\n所在表格:	"+sheet.getSheetName()+'\n'+sheetResult);
        		}
        	}      	
        }
		return result;
	}
	
	/**
	 * 在表sheet的第key列上搜索关键字phoneNumber，返回结果的字符串
	 * @param phoneNumber
	 * @param sheet
	 * @param key
	 * @return
	 */
	public String searchSheet(String phoneNumber,XSSFSheet sheet,int key){
        String str=getRowContent(sheet.getRow(0))+"\n";
		for(Iterator ite=sheet.rowIterator();ite.hasNext();){			
            XSSFRow row=(XSSFRow)ite.next();           
            if(row.getLastCellNum()<=key){
            	continue;
            }
            XSSFCell cellKey=row.getCell(key);
            if(cellKey.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
            	double dTemp=cellKey.getNumericCellValue();
            	BigDecimal bd=new BigDecimal(dTemp);
                String str1=bd.toString(); 
                if(str1.equals(phoneNumber)){
                	str+=getRowContent(row);
                	return str;
                }  
            }            
    	}
		return null;
	}
	
	/**
	 * 获取一行的内容，以"	"隔开各列
	 * @param row
	 * @return
	 */
	public String getRowContent(XSSFRow row){
		String str="";
		for(Iterator itet=row.cellIterator();itet.hasNext();){   
            XSSFCell cell=(XSSFCell)itet.next();   
            switch(cell.getCellType()){     
            case XSSFCell.CELL_TYPE_BOOLEAN:     
                //得到Boolean对象的方法     
            	str+=Boolean.toString(cell.getBooleanCellValue()); 
            	str+="	";
                break;     
            case XSSFCell.CELL_TYPE_NUMERIC:   
                //读取数字     
            	double dTemp=cell.getNumericCellValue();
            	BigDecimal bd=new BigDecimal(dTemp);
            	str+=bd.toString();    
            	str+="	";
                break;         
            case XSSFCell.CELL_TYPE_STRING:     
                //读取String     
            	str+=cell.getStringCellValue();  
            	str+="	";
                break; 
            case XSSFCell.CELL_TYPE_BLANK:
            	//无内容的单元格
            	str+="	";
            	break;
            }    
        }
		return str;
	}
	
	public static void main(String[] args) throws Exception{
		PhoneSearcher phoneSearcher=new PhoneSearcher("D:\\本部大楼电话配线(实测).xlsx");
		List<String> ls=phoneSearcher.getResult("1250");
		InputStream ips=new FileInputStream("D:\\本部大楼电话配线(实测).xlsx"); 
        XSSFWorkbook wb=new XSSFWorkbook(ips); 
        XSSFSheet sheet=(XSSFSheet)wb.getSheetAt(3);
        System.out.println(phoneSearcher.searchSheet("8888", sheet, 1));
//		Scanner scanner=new Scanner(System.in);
//		String number=scanner.next();
		
	}
}
