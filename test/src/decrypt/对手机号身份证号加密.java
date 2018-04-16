package decrypt;

import com.daren.common.security.CryptManager;
import com.daren.common.security.SecurityUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.keyczar.exceptions.Base64DecodingException;
import org.keyczar.util.Base64Coder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class 对手机号身份证号加密 {
	public static void test(int[] indexs) throws Base64DecodingException {
        String fileToBeRead = "C:\\Users\\darendai\\Desktop\\aaa.xls"; // excel位置
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(fileToBeRead));
            HSSFSheet sheet = workbook.getSheetAt(0);
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
                              //第3列查询出号码进行解密
                        	  System.out.println("rownum="+i+"data="+cell.getStringCellValue());
                        	  String s = cell.getStringCellValue();
                        	  s = SecurityUtils.BUSINESS.deCryptText(s);
                        	  if(null!=s && !"".equals(s))
                                cell.setCellValue(s.substring(0,s.length()-6)+"******");
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

	public static void main(String[] arg) throws Base64DecodingException{
	int[] index=new int[] {14};
	对手机号身份证号加密.test(index);
	}
	
}
