package decrypt;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @Authoc xueshiqi
 * @Date 2018/3/13 12:15
 */
public class 解析身份证地区生成sql1 {
    public static void main(String args[]) {
        RefreshEncryptedData red = new RefreshEncryptedData();
        try {
            Connection con = red.getCon();
            try {
                //先查询出需要加密的数据
                Statement sm =  con.createStatement();
                ResultSet resultSet = sm.executeQuery("select a.id,(select c.remarks from cs_dic_area_dynam c where c.id in (select b.parentid from cs_dic_area_dynam b where b.id=a.parentid)) as name,(select b.remarks from cs_dic_area_dynam b where b.id=a.parentid) as name1,remarks,remarks2 from cs_dic_area_dynam a where  a.id between 6592 and 10231 and a.remarks2 is null");
                String[] as = new String[4];
                String pathname = "C:\\Users\\darendai\\Desktop\\身份证.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
                File filename = new File(pathname); // 要读取以上路径的input。txt文件
                InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
                BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
                File writename = new File("C:\\Users\\darendai\\Desktop\\身份证sql1.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
                writename.createNewFile(); // 创建新文件
                BufferedWriter out = new BufferedWriter(new FileWriter(writename));
                File writename1 = new File("C:\\Users\\darendai\\Desktop\\查询.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
                writename1.createNewFile(); // 创建新文件
                BufferedWriter out1 = new BufferedWriter(new FileWriter(writename1));
                out1.write("select * from cs_dic_area_dynam where id in(");
                List<String> list = new ArrayList<>();
                int i =1;
                for(String line = br.readLine();line!=null;line = br.readLine()){
                    if(!"".equals(line)) {
                        list.add(line.trim());
                    }
                    if("".equals(line)) {
                       i++;
                    }
                }
                System.out.println(i);
//                while (resultSet.next()){
//                    as[0]=resultSet.getString(1);
//                    as[1]=resultSet.getString(2);
//                    as[2]=resultSet.getString(3);
//                    as[3]=resultSet.getString(4);
//                    if(as[1]!=null){
//                        as[1] = as[1].replaceAll(" ","").replace("　","");
//                    }
//                    if(as[2]!=null){
//                        as[2] = as[2].replaceAll(" ","").replace("　","");
//                    }
//                    if(as[3]!=null){
//                        as[3] = as[3].replaceAll(" ","").replace("　","");
//                    }
//                    int i=0,k=0;
//                    for(String s : list){
//                            if(s.substring(7).equals(as[1])){
//                                i =1;
//                            }
//                            if(i==1 && s.substring(7).equals(as[2])){
//                                k=1;
//                            }
//                            if(k==1 && s.substring(7).equals(as[3])){
//                                out.write("update cs_dic_area_dynam set remarks2='" + s.substring(0, 6) + "' where id = " + as[0] + ";\r\n");
//                                out1.write(as[0] + ",");
//                                break;
//                            }
//                    }
//                }
                out1.write(")");
                out1.flush(); // 把缓存区内容压入文件
                out1.close(); // 最后记得关闭文件
                out.flush(); // 把缓存区内容压入文件
                out.close(); // 最后记得关闭文件
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
