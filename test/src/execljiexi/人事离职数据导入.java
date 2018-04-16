package execljiexi;

import decrypt.RefreshEncryptedData;
import demo.ObejctUtil;

import java.io.*;
import java.util.List;

/**
 * @Authoc xueshiqi
 * @Date 2018/3/5 11:57
 */
public class 人事离职数据导入 {
    static String url ="C:\\Users\\darendai\\Desktop\\离职人员数据统计.xlsx";
    public static void getrslzsjdr() {
        try {
            //解析execl
            List<List<Object>> list = ImportsExcel.readExcel(url);
            addRslszj(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addRslszj(List<List<Object>> list){
        RefreshEncryptedData red = new RefreshEncryptedData();
        int id= 444;
        for(List<Object> s:list) {
            System.out.println("姓名：" + s.get(3));
//            System.out.println("序号：" + s.get(0));
//            System.out.println("ID：" + id);
            String organizingId = "184";//所属分行  部门Id
            String jobNumber = String.valueOf(s.get(2));//工号
            String staffName = String.valueOf(s.get(3));//姓名
            String inductionDate = String.valueOf(s.get(4));//入职日期
            String recruitmentChannels = String.valueOf(s.get(5));//招聘渠道
            String sex = String.valueOf(s.get(6));//性别
            if ("女".equals(sex)) {
                sex = "1";
            } else {
                sex = "0";
            }
            String staffDetailsGroup = String.valueOf(s.get(7));//组别  部门
            String newPositionName = String.valueOf(s.get(8));//岗位
            String married = String.valueOf(s.get(9));// 婚姻状况  是否已婚
            if ("已婚".equals(married)) {
                married = "1";
            } else {
                married = "0";
            }
            String citizenIdNumber = String.valueOf(s.get(10)); //身份证号码
//            System.out.println("出生日期：" + String.valueOf(s.get(11)));
//            System.out.println("年龄：" + String.valueOf(s.get(12)));
//            System.out.println("工龄：" + String.valueOf(s.get(13)));
//            System.out.println("农历生日：" + String.valueOf(s.get(14)));
//            System.out.println("行业从业经验：" + String.valueOf(s.get(15)));
            String phoneNumber = String.valueOf(s.get(16));//联系电话
            String mailbox = String.valueOf(s.get(17)); //邮箱
            String mail = String.valueOf(s.get(17)); //邮箱
            String diploma = String.valueOf(s.get(18));//学历
            String major = String.valueOf(s.get(19));//专业
            String quit = String.valueOf(s.get(20));//离职日期
            String quitReason = String.valueOf(s.get(21));//离职原因
            String school = String.valueOf(s.get(22));//毕业院校
            String presentPlaceOfResidence = String.valueOf(s.get(23));//现住址
            String permanentAddress = String.valueOf(s.get(24));//户口地址
            String placeOfOrigin = String.valueOf(s.get(25));//籍贯
            String socialSecurityAccount = String.valueOf(s.get(26));//社保电脑号
            String providentFundAccount = String.valueOf(s.get(27));//公积金账号
            Long socialSecurityBase = ObejctUtil.getStringTransformationLong(s.get(28));//社保购买基数
            Long providentBase = ObejctUtil.getStringTransformationLong(s.get(29));//公积金购买基数
            String contractStartDate = String.valueOf(s.get(30));//劳动合同开始日期
            String endOfContractDate = String.valueOf(s.get(31));//劳动合同结束日期
            String payrollCardNumber = String.valueOf(s.get(33));//银行账号
            String openingBranch = String.valueOf(s.get(34));//开户支行
            String remarks = String.valueOf(s.get(35));//备注
//        String sql="select staffId,staffName,jobNumber from pm_staff_information where jobNumber='"+jobNumber+"'";
//        String [] aa = red.initializationData(sql);
//        String sql1 ="update pm_staff_information_details set staffDetailsGroup='"+staffDetailsGroup+"' where staffId='"+aa[0]+"'";
//        red.add(sql1);
            String sql = "insert into pm_staff_information (staffId,staffName,jobNumber,phoneNumber,citizenIdNumber,sex,mailbox,workingHours,mail,recruitmentChannels," +
                    "lunarBirthday,fertile,married,placeOfOrigin,permanentAddress,presentPlaceOfResidence,education,major,school,diploma,contacts,payrollCardNumber," +
                    "openingBranch,socialSecurityAccount,providentFundAccount,socialSecurityBase,providentBase,remarks,modifierId,createrId,modifierDate,createrDate,enabled) " +
                    "values ('" + id + "','" + staffName + "','" + jobNumber + "','" + phoneNumber + "','" + citizenIdNumber + "','" + sex + "','" + mailbox + "',null" +
                    ",'" + mail + "','" + recruitmentChannels + "',null,0,'" +
                    married + "','" + placeOfOrigin + "','" + permanentAddress + "','" + presentPlaceOfResidence + "','" + diploma + "','" +
                    major + "','" + school + "',null,null,'" + payrollCardNumber + "','" + openingBranch + "','" +
                    socialSecurityAccount + "','" + providentFundAccount + "','" + socialSecurityBase + "','" + providentBase + "','" +
                    remarks + "',1,1,now(),now(),1)";
            red.add(sql);
            System.out.println(quit);
            String sql1 = "insert into pm_staff_information_details (staffDetailsId,staffId,organizingId,newPositionId,newPositionName,inductionDate,formalDate," +
                    "quitDate,quitReason,quitType,trialTime,workingPlace,workTime,state,contractCompany,staffDetailsGroup,contractNumber,contractStartDate," +
                    "contractPeriod,contractRemarks,endOfContractDate,appendicesOfContract,ModifierId,createrId,ModifierDate,createrDate,enabled)" +
                    " values('" + id + "','" + id + "','" + organizingId + "',null,'" + newPositionName + "','" + inductionDate + "',null" +
                    ",'"+quit+"','无','"+quitReason+"',3,null,null,3,null,'" + staffDetailsGroup + "',null,'" + contractStartDate
                    + "',null,null,'" + endOfContractDate + "',null,1,1,now(),now(),1)";
            if("".equals(quit))
                sql1 = "insert into pm_staff_information_details (staffDetailsId,staffId,organizingId,newPositionId,newPositionName,inductionDate,formalDate," +
                    "quitDate,quitReason,quitType,trialTime,workingPlace,workTime,state,contractCompany,staffDetailsGroup,contractNumber,contractStartDate," +
                    "contractPeriod,contractRemarks,endOfContractDate,appendicesOfContract,ModifierId,createrId,ModifierDate,createrDate,enabled)" +
                    " values('" + id + "','" + id + "','" + organizingId + "',null,'" + newPositionName + "','" + inductionDate + "',null" +
                    ",null,'无','"+quitReason+"',3,null,null,3,null,'" + staffDetailsGroup + "',null,'" + contractStartDate
                    + "',null,null,'" + endOfContractDate + "',null,1,1,now(),now(),1)";
            red.add(sql1);
            id++;
        }
    }

    public static void main(String[] arg){
        getrslzsjdr();
    }
}
