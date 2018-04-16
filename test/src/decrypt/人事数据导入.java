package decrypt;

import com.daren.common.security.SecurityUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.keyczar.exceptions.Base64DecodingException;

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
public class 人事数据导入 {
    public static void test() throws Base64DecodingException {
        String fileToBeRead = "C:\\Users\\darendai\\Desktop\\xls\\总部达人员工档案信息表.xls"; // excel位置
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(fileToBeRead));
            HSSFSheet sheet = workbook.getSheetAt(0);
            RefreshEncryptedData red = new RefreshEncryptedData();
            int id = 410;
            id++;
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                System.out.println("rownum="+i);
                HSSFRow row = sheet.getRow( i);
                if (null == row|| i==0) {
                    continue;
                } else {
                    HSSFCell[] s = new HSSFCell[36];
                    for(int j = 0 ; j<36;j++){
                        HSSFCell cell0 = row.getCell(j);
                        s[j] = cell0;
                    }
                    // `staffId`,`staffName`'姓名',`jobNumber`'工号',`phoneNumber`  '手机号',`citizenIdNumber` '身份证号码',`sex`  '性别 (0 男 ，1 女)',`mailbox` '邮箱',
                    // `workingHours`  '参加工作时间',`mail` '企业邮箱',`recruitmentChannels`  '招聘渠道',`lunarBirthday`  '农历生日',`fertile`  '是否已育（0 未育 , 1 已育 ）',
                    // `married`  '是否已婚(0 未婚 ， 1 已婚)',`placeOfOrigin`  '籍贯',`permanentAddress`  '户籍地址',`presentPlaceOfResidence`  '现居住地',`education`  '学历',
                    // `major`  '毕业专业',`school`  '毕业院校',`diploma`'学历 留空',`contacts`'联系人 留空',`payrollCardNumber` '工资卡卡号',`openingBranch`  '开户支行',
                    // `socialSecurityAccount` '个人社保账号',`providentFundAccount` '个人公积金账号',`socialSecurityBase` '社保购买基数',`providentBase` '公积金购买基数',
                    // `remarks`'备注',`modifierId` '修改人id',`createrId` '创建人id',`modifier` '修改时间',`creater` '创建时间',`enabled` '是否有效（0 有效 1 无效）'

                    //`staffDetailsId`,`staffId`'员工基本信息id',`organizingId`'部门Id',`newPositionId`'岗位Id',`newPositionName`'岗位名称',`induction`'入职时间',
                    // `formal`'转正日期',`quit`'离职日期',`quitReason`'离职原因',`quitType`'离职类型',`trial`'试用期(月份)',`workingPlace`'工作地点',
                    // `work`'工作经验',`state`'员工状态',`contractCompany`'合同公司',`staffDetailsGroup`'组别',`contractNumber`'合同编号',`contractStart`'合同开始日期',
                    // `contractPeriod`'合同期限',`contractRemarks`'合同备注',`endOfContract`'合同结束日期',`appendicesOfContract`'合同附件(根目录地址)',
                    // `ModifierId`'修改人id',`createrId`'创建人id',`Modifier`'修改时间',`creater`'创建时间',`enabled` '是否有效（0 有效 1 无效）
                    System.out.println("序号："+s[0].getNumericCellValue());
//                    String organizingId = "184";//所属分行  部门Id
                    String jobNumber =s[2].getStringCellValue();//工号
//                    String staffName = s[3].getStringCellValue();//姓名
//                    Date induction = s[4].getDateCellValue();//入职日期
//                    SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    String inductionDate=null;
//                    if(induction!=null)
//                        inductionDate=formater.format(induction);
//
//                    String recruitmentChannels=s[5].getStringCellValue();//招聘渠道
//                    String sex =s[6].getStringCellValue();//性别
//                    if("女".equals(sex)){
//                        sex ="1";
//                    }else{
//                        sex ="0";
//                    }
                    String staffDetailsGroup=s[7].getStringCellValue();//组别  部门
//                    String newPositionName = s[8].getStringCellValue();//岗位
//                    //newPositionId  岗位Id
//                    String married=s[9].getStringCellValue();// 婚姻状况  是否已婚
//                    if("已婚".equals(married)){
//                        married = "1";
//                    }else{
//                        married = "0";
//                    }
//                    String citizenIdNumber = s[10].getStringCellValue(); //身份证号码
////                    System.out.println("出生日期："+s[11].getDateCellValue());
////                    System.out.println("年龄："+s[12].getNumericCellValue());
////                    System.out.println("工龄："+s[13].getNumericCellValue());
////                    System.out.println("农历生日："+s[14].getStringCellValue());
////                    System.out.println("行业从业经验："+s[15].getStringCellValue());
//                    Number phoneNumber1 = s[16].getNumericCellValue();//联系电话
//                    Long phoneNumber = null;
//                    if(phoneNumber1!= null){
//                        phoneNumber = phoneNumber1.longValue();
//                    }
//                    String mailbox =s[17].getStringCellValue(); //邮箱
//                    String mail =s[17].getStringCellValue(); //邮箱
//                    String diploma =s[18].getStringCellValue();//学历
//                    String major =s[19].getStringCellValue();//专业
////                    Date quit = s[20].getDateCellValue();//离职日期
////                    String quitReason = s[21].getStringCellValue();//离职原因
//                    String school = s[22].getStringCellValue();//毕业院校
//                    String presentPlaceOfResidence = s[23].getStringCellValue();//现住址
//                    String permanentAddress = s[24].getStringCellValue();//户口地址
//                    String placeOfOrigin = s[25].getStringCellValue();//籍贯
//                    Number socialSecurityAccount1 = s[26].getNumericCellValue();//社保电脑号
//                    Long socialSecurityAccount = null;
//                    if(socialSecurityAccount1!= null){
//                        socialSecurityAccount = socialSecurityAccount1.longValue();
//                    }
//                    Number providentFundAccount1 = s[27].getNumericCellValue();//公积金账号
//                    Long providentFundAccount = null;
//                    if(providentFundAccount1!= null){
//                        providentFundAccount = providentFundAccount1.longValue();
//                    }
//                    Number socialSecurityBase1 = s[28].getNumericCellValue();//社保购买基数
//                    Long socialSecurityBase = null;
//                    if(socialSecurityBase1!= null){
//                        socialSecurityBase = socialSecurityBase1.longValue();
//                    }
//                    Number providentBase1 = s[29].getNumericCellValue();//公积金购买基数
//                    Long providentBase = null;
//                    if(providentBase1!= null){
//                        providentBase = providentBase1.longValue();
//                    }
//
//                    Date contractStart = s[30].getDateCellValue();//劳动合同开始日期
//                    String contractStartDate=null;
//                    if(contractStart!=null)
//                        contractStartDate=formater.format(contractStart);
//                    Date endOfContract = s[31].getDateCellValue();//劳动合同结束日期
//                    String endOfContractDate=null;
//                    if(endOfContract!=null)
//                        endOfContractDate=formater.format(endOfContract);
//                    String payrollCardNumber = s[33].getStringCellValue();//银行账号
//                    String openingBranch = s[34].getStringCellValue();//开户支行
//                    String remarks = s[35].getStringCellValue();//备注
                    // `staffId`,`staffName`'姓名',`jobNumber`'工号',`phoneNumber`  '手机号',`citizenIdNumber` '身份证号码',`sex`  '性别 (0 男 ，1 女)',`mailbox` '邮箱',
                    // `workingHours`  '参加工作时间',`mail` '企业邮箱',`recruitmentChannels`  '招聘渠道',`lunarBirthday`  '农历生日',`fertile`  '是否已育（0 未育 , 1 已育 ）',
                    // `married`  '是否已婚(0 未婚 ， 1 已婚)',`placeOfOrigin`  '籍贯',`permanentAddress`  '户籍地址',`presentPlaceOfResidence`  '现居住地',`education`  '学历',
                    // `major`  '毕业专业',`school`  '毕业院校',`diploma`'学历 留空',`contacts`'联系人 留空',`payrollCardNumber` '工资卡卡号',`openingBranch`  '开户支行',
                    // `socialSecurityAccount` '个人社保账号',`providentFundAccount` '个人公积金账号',`socialSecurityBase` '社保购买基数',`providentBase` '公积金购买基数',
                    // `remarks`'备注',`modifierId` '修改人id',`createrId` '创建人id',`modifier` '修改时间',`creater` '创建时间',`enabled` '是否有效（0 有效 1 无效）'
//                        String sql ="insert into pm_staff_information (staffId,staffName,jobNumber,phoneNumber,citizenIdNumber,sex,mailbox,workingHours,mail,recruitmentChannels," +
//                                "lunarBirthday,fertile,married,placeOfOrigin,permanentAddress,presentPlaceOfResidence,education,major,school,diploma,contacts,payrollCardNumber," +
//                                "openingBranch,socialSecurityAccount,providentFundAccount,socialSecurityBase,providentBase,remarks,modifierId,createrId,modifierDate,createrDate,enabled) " +
//                                "values ('"+id+"','"+staffName+"','"+jobNumber+"','"+phoneNumber+"','"+citizenIdNumber+"','"+sex+"','"+mailbox+"',null" +
//                                ",'"+mail+"','"+recruitmentChannels+"',null,0,'"+
//                                married+"','"+placeOfOrigin+"','"+permanentAddress+"','"+presentPlaceOfResidence+"','"+diploma+"','"+
//                                major+"','"+school+"',null,null,'"+payrollCardNumber+"','"+openingBranch+"','"+
//                                socialSecurityAccount+"','"+providentFundAccount+"','"+socialSecurityBase+"','"+providentBase+"','"+
//                                remarks+"',1,1,now(),now(),0)";
//                        red.add(sql);
                        //`staffDetailsId`,`staffId`'员工基本信息id',`organizingId`'部门Id',`newPositionId`'岗位Id',`newPositionName`'岗位名称',`induction`'入职时间',
                    // `formal`'转正日期',`quit`'离职日期',`quitReason`'离职原因',`quitType`'离职类型',`trial`'试用期(月份)',`workingPlace`'工作地点',
                    // `work`'工作经验',`state`'员工状态',`contractCompany`'合同公司',`staffDetailsGroup`'组别',`contractNumber`'合同编号',`contractStart`'合同开始日期',
                    // `contractPeriod`'合同期限',`contractRemarks`'合同备注',`endOfContract`'合同结束日期',`appendicesOfContract`'合同附件(根目录地址)',
                    // `ModifierId`'修改人id',`createrId`'创建人id',`Modifier`'修改时间',`creater`'创建时间',`enabled` '是否有效（0 有效 1 无效）'
//                        String sql1 ="insert into pm_staff_information_details (staffDetailsId,staffId,organizingId,newPositionId,newPositionName,inductionDate,formalDate," +
//                                "quitDate,quitReason,quitType,trialTime,workingPlace,workTime,state,contractCompany,staffDetailsGroup,contractNumber,contractStartDate," +
//                                "contractPeriod,contractRemarks,endOfContractDate,appendicesOfContract,ModifierId,createrId,ModifierDate,createrDate,enabled)"+
//                                " values('"+id+"','"+id+"','"+organizingId+"',null,'"+newPositionName+"','"+inductionDate+"',null" +
//                                ",null,null,null,3,null,null,1,null,'"+staffDetailsGroup+"',null,'"+contractStartDate
//                                +"',null,null,'"+endOfContractDate+"',null,1,1,now(),now(),0)";
//                    red.add(sql1);
//                    id++;
                    String sql="select staffId,staffName,jobNumber from pm_staff_information where jobNumber='"+jobNumber+"'";
                    String [] aa = red.initializationData(sql);
                    String sql1 ="update pm_staff_information_details set staffDetailsGroup='"+staffDetailsGroup+"' where staffId='"+aa[0]+"'";
                    red.add(sql1);
                }
            }
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
