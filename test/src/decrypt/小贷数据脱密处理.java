package decrypt;

import org.keyczar.exceptions.Base64DecodingException;

/**
 * @Authoc xueshiqi
 * @Date 2017/11/7 16:03
 */
public class 小贷数据脱密处理 {

    public static void main(String[] args) throws Base64DecodingException {
        deCrypData("sl_Smallloan_Project");
        deCrypData("pst_user");
        deCrypData("cs_enterprise_bank");
        deCrypData("cs_person_phonecheck");
        deCrypData("cs_person_Relation");
        deCrypData("cs_person");
        deCrypData("cs_spouse");

//        RefreshEncryptedData("sl_Smallloan_Project");
//        RefreshEncryptedData("pst_user");
//        RefreshEncryptedData("cs_enterprise_bank");
//        RefreshEncryptedData("cs_person_phonecheck");
//        RefreshEncryptedData("cs_person_Relation");
//        RefreshEncryptedData("cs_person");
//        RefreshEncryptedData("cs_spouse");
    }

    public static void RefreshEncryptedData(String table) {
        System.out.println(table+"表：数据刷新开始"+System.nanoTime());
        RefreshEncryptedData red = new RefreshEncryptedData();
        switch (table){
            case "sl_Smallloan_Project":
                red.initializationData("sl_Smallloan_Project", "projectId", "accountNumber");
                break;
            case "pst_user":
                red.initializationData("pst_user", "id", "phone");
                break;
            case "cs_enterprise_bank":
                red.initializationData("cs_enterprise_bank", "id", "accountnum");
                break;
            case "cs_person_phonecheck":
                red.initializationData("cs_person_phonecheck", "id", "phoneNumber");
                break;
            case "cs_person_Relation":
                red.initializationData("cs_person_Relation", "id", "relationPhone", "relationCellPhone", "relationCompanyPhone", "relationCardnumber");
                break;
            case "cs_person":
                red.initializationData("cs_person", "id", "cardnumber", "telphone", "cellphone", "unitphone", "qq", "microMessage", "companyTelephone", "wageaccount", "mateaccount");
                break;
            case "cs_spouse":
                red.initializationData("cs_spouse", "spouseId", "cardnumber", "linkTel", "unitPhoneNO");
                break;
            case "t_inner_communication_history":
                red.initializationData("t_inner_communication_history", "id", "message");
                break;
            default:
                System.out.println(table+"无表参数传入"+System.nanoTime());
                break;
        }
        System.out.println(table+"表：数据刷新完毕"+System.nanoTime());
    }

    public static void deCrypData(String table) throws Base64DecodingException {
        System.out.println(table+"表：数据解密开始"+System.nanoTime());
        RefreshEncryptedData red = new RefreshEncryptedData();
        switch (table){
            case "sl_Smallloan_Project":
                red.deCrypData("sl_Smallloan_Project", "projectId", "accountNumber");
                break;
            case "pst_user":
                red.deCrypData("pst_user", "id", "phone");
                break;
            case "cs_enterprise_bank":
                red.deCrypData("cs_enterprise_bank", "id", "accountnum");
                break;
            case "cs_person_phonecheck":
                red.deCrypData("cs_person_phonecheck", "id", "phoneNumber");
                break;
            case "cs_person_Relation":
                red.deCrypData("cs_person_Relation", "id", "relationPhone", "relationCellPhone", "relationCompanyPhone", "relationCardnumber");
                break;
            case "cs_person":
                red.deCrypData("cs_person", "id", "cardnumber", "telphone", "cellphone", "unitphone", "qq", "microMessage", "companyTelephone", "wageaccount", "mateaccount");
                break;
            case "cs_spouse":
                red.deCrypData("cs_spouse", "spouseId", "cardnumber", "linkTel", "unitPhoneNO");
                break;
            case "t_inner_communication_history":
                red.deCrypData("t_inner_communication_history", "id", "message");
                break;
            default:
                System.out.println(table+"无表参数传入"+System.nanoTime());
                break;
        }
        System.out.println(table+"表：数据解密完毕"+System.nanoTime());
    }
}
