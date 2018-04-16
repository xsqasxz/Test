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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Authoc xueshiqi
 * @Date 2018/1/9 15:18
 */
public class 小贷exec进行数据导出 {
    public static void test() throws Base64DecodingException {
        String fileToBeRead = "C:\\Users\\darendai\\Desktop\\aaa.xls"; // excel位置
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(fileToBeRead));
            HSSFSheet sheet = workbook.getSheetAt(0);
            RefreshEncryptedData red = new RefreshEncryptedData();
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
//            for (int i = 0; i <= 3; i++) {
                System.out.println("rownum="+i);
                HSSFRow row = sheet.getRow( i);
                if (null == row|| i==0) {
                    continue;
                } else {
                    HSSFCell cell0 = row.getCell(0);
                    HSSFCell cell3 = row.getCell(3);
                    HSSFCell cell4 = row.getCell(4);
                    HSSFCell cell5 = row.getCell(5);
                    HSSFCell cell6 = row.getCell(6);
                    Date sd = cell0.getDateCellValue();
                    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String s3 = cell3.getStringCellValue();
                    String s0=formater.format(sd);
                    if(!"".equals(s0)&&!"".equals(s3)){
                        //进行签名，后进行查询找到对应的数据
                        String sql ="select c.pastOneMonth,c.pastThreeMonth,c.pastTwelveMonth from \n" +
                                "(\n" +
                                "SELECT r.projectId, f1.applyDate AS applyDate,p.cardnumber_m AS cardnumber_m\n" +
                                "FROM process_run r\n" +
                                "JOIN sl_smallloan_project s ON r.projectId = s.projectId\n" +
                                "JOIN (\n" +
                                "SELECT f.runId,f.endtime AS applyDate\n" +
                                "FROM (\n" +
                                "SELECT MIN(formId) AS id\n" +
                                "FROM process_form\n" +
                                "WHERE activityName = '客服录入' AND STATUS = 1\n" +
                                "GROUP BY runId) t0\n" +
                                "INNER JOIN process_form f ON f.formId=t0.id) AS f1 ON f1.runId=r.runId "+ // and f1.applyDate='"+s0+"'\n" +
                                "JOIN cs_person p ON p.id = s.oppositeID and p.cardnumber_m='"+ SecurityUtils.BUSINESS.getSign(s3)+"'\n" +
                                "ORDER BY f1.applyDate DESC\n" +
                                ") aa left join cs_Person_Credit_Inquiry_Count c on aa.projectId = c.projectId";
//                        System.out.println("sql："+sql);
                        String[] as = red.initializationData(sql);
                        System.out.println("数据："+as[0]+"::"+as[1]+"::"+as[2]);
                        cell4.setCellValue(as[0]);
                        cell5.setCellValue(as[1]);
                        cell6.setCellValue(as[2]);
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
        test();
    }
}
