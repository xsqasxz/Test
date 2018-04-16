package demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.keyczar.exceptions.Base64DecodingException;
import org.keyczar.util.Base64Coder;

import com.daren.common.security.CryptManager;



public class updatexsl {
	public static void test(int[] indexs) throws Base64DecodingException {
        String fileToBeRead = "C:\\Users\\darendai\\Desktop\\模型信息提取字段1.xls"; // excel位置
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
                    fileToBeRead));
            HSSFSheet sheet = workbook.getSheetAt(0);
            CryptManager manager =  CryptManager.instance(Byte.parseByte("14"), System.getenv("SECURITY_LOCATION")+"business");

            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            	 System.out.println("rownum="+i);
                HSSFRow row = sheet.getRow( i);
                if (null == row|| i==0) {
                	 System.out.println("rownum000="+i);
                    continue;
                } else {
                	
                	for(int index:indexs){
                		  HSSFCell cell = row.getCell((short) index);
                          if (null == cell) {
                              continue;
                          } else {
                        	  System.out.println("rownum="+i+"data="+cell.getStringCellValue());
                            // System.out.println(cell.getStringCellValue()+"----"+ new String(manager.decrypt(Base64Coder.decodeWebSafe("APEHfAjPnIWA5iDWRGtIGsVvmZLRG9BkejlI1z2vtop2iJE-Ph")),"utf-8"));
                              //int temp = Integer.parseInt(cell.getStringCellValue());
                        	  String s = cell.getStringCellValue();
                        	  String[] sa=s.split(",");
                        	  String a = "";
                        	  for(String ss : sa) {
//                        		  String sb = new String(manager.decrypt(Base64Coder.decodeWebSafe(ss)),"utf-8");
                        		  a = new String(manager.decrypt(Base64Coder.decodeWebSafe(ss)),"utf-8");
                        		  if(a.length()==11) {
                        			  a = a.substring(0, a.length()-4)+"****"+",";                        			 
                        		  }else {
                        			  a +=",";
                        		  }
                        	  }
                        	  a = a.substring(0, a.length()-1);
                        	  cell.setCellValue(a);
//                        	  cell.setCellValue(new String(manager.decrypt(Base64Coder.decodeWebSafe(cell.getStringCellValue())),"utf-8"));
                             // Base64Coder.encodeWebSafe(manager.encodeSalt("B13MSU5XVc4c5RK1sD2FolhrCikmNw1IlSnOQuiKrNJz9yQBigOCN4tw8GyJg".getAPEHfAik8t08uNUj2vjWHFhQlMZhwUyBYmqPxxA3mqX6BbpAiydY0SOTxP9KIVLz02hgldAA8iX3Bytes()))); 
                	}
                }
            }
            }
            FileOutputStream out=new FileOutputStream(fileToBeRead);
            workbook.write(out);
            out.close();
            workbook.close();
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	

	
	public static void test2(int[] indexs) throws Base64DecodingException {
        String fileToBeRead = "D:\\import.xls"; // excel位置
        String fileToBeRead2 = "D:\\import.txt"; // excel位置
        try {  
        FileOutputStream o = new FileOutputStream(fileToBeRead2);  
       
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
                    fileToBeRead));
            HSSFSheet sheet = workbook.getSheetAt(0);
            CryptManager manager =  CryptManager.instance(Byte.parseByte("80"), System.getenv("SECURITY_LOCATION")+"business");
           	
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            	
                HSSFRow row = sheet.getRow( i);
                if (null == row|| i==0) {
                    continue;
                } else {
                	for(int index:indexs){
                		  HSSFCell cell = row.getCell((short) index);
                		
                          if (null == cell) {
                              continue;
                          } else {
                        	  System.out.println("rownum="+i+"data="+cell.getStringCellValue());
                            // System.out.println(cell.getStringCellValue()+"----"+ new String(manager.decrypt(Base64Coder.decodeWebSafe("APEHfAjPnIWA5iDWRGtIGsVvmZLRG9BkejlI1z2vtop2iJE-Ph")),"utf-8"));
                              //int temp = Integer.parseInt(cell.getStringCellValue());
                             // cell.setCellValue( Base64Coder.encodeWebSafe(manager.encrypt(cell.getStringCellValue().getBytes("utf-8"))));
                              o.write(("insert usertemp values('"+row.getCell((short) 0).getStringCellValue()+"','"+Base64Coder.encodeWebSafe(manager.encrypt(cell.getStringCellValue().getBytes("utf-8")))+"');\n").getBytes("utf-8"));  
                             // Base64Coder.encodeWebSafe(manager.encodeSalt("B13MSU5XVc4c5RK1sD2FolhrCikmNw1IlSnOQuiKrNJz9yQBigOCN4tw8GyJg".getAPEHfAik8t08uNUj2vjWHFhQlMZhwUyBYmqPxxA3mqX6BbpAiydY0SOTxP9KIVLz02hgldAA8iX3Bytes())));
                	}
                }
            }
            }
            
            FileOutputStream out=new FileOutputStream(fileToBeRead);
            workbook.write(out);
            out.close();
            workbook.close();
            o.close();  
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
	
	public static void genarateSQL(int id,int idfromXM) throws Base64DecodingException {
        String fileToBeRead = "D:\\达人贷-厦门国际数据迁移0619.xls"; // excel位置
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
                    fileToBeRead));
            HSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            	 System.out.println("rownum="+i);
                HSSFRow row = sheet.getRow( i);
                if (null == row|| i==0) {
                	
                    continue;
                } else {
                	
                	 
                	 
   /*	 
                	for(int index:indexs){
                		  HSSFCell cell = row.getCell((short) index);
                		  System.out.println("rownum22="+i);
                          if (null == cell) {
                              continue;
                          } else {
                        	  System.out.println("rownum33="+i);
                              System.out.println(cell.getStringCellValue()+"----"+ new String(manager.decrypt(Base64Coder.decodeWebSafe(cell.getStringCellValue())),"utf-8"));
                              //int temp = Integer.parseInt(cell.getStringCellValue());
                              cell.setCellValue( new String(manager.decrypt(Base64Coder.decodeWebSafe(cell.getStringCellValue())),"utf-8"));
                             // Base64Coder.encodeWebSafe(manager.encodeSalt("B13MSU5XVc4c5RK1sD2FolhrCikmNw1IlSnOQuiKrNJz9yQBigOCN4tw8GyJg".getAPEHfAik8t08uNUj2vjWHFhQlMZhwUyBYmqPxxA3mqX6BbpAiydY0SOTxP9KIVLz02hgldAA8iX3Bytes())));
                             
                	}
                      
                }*/
            }
            }
            FileOutputStream out=new FileOutputStream(fileToBeRead);
            workbook.write(out);
            out.close();
            workbook.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	 
	public static void test2() throws Base64DecodingException {

		CryptManager manager =  CryptManager.instance(Byte.parseByte("80"), System.getenv("SECURITY_LOCATION")+"business");
          try {
			System.out.println(  new String(manager.decrypt(Base64Coder.decodeWebSafe("APEHfAjJq8nvJcefRpLeLh0sUkIE_YkPbioUgtRLF2Tav0RqNaOQEVuO5P3OH62JiKwCdnvbr1A71ZU7gijLN0rLWtOsmv4a3w1")),"utf-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}                           
    }
	
	//update pt_user set PARTY_CAPITAL_ACCOUNT_ID='2017060511233518511547910'    where customer_no='I10312002
   
	public static void main(String[] arg) throws Base64DecodingException{
	int[] index=new int[] {5,35,37,39};
//	int[] index=new int[] {2};
	//updatexsl.test2();
	updatexsl.test(index);
		
	//int[] index2=new int[] {11};
	//updatexsl.test2(index2);
		
	}
	
}
