//package decrypt;
//import java.io.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * @Authoc xueshiqi
// * @Date 2018/3/13 12:15
// */
//public class 解析身份证地区生成sql {
//    public static void main(String args[]) {
//        HTML爬取 h = new HTML爬取();
//        String s = h.getTransResult("13020320000101729x");
//        String firstCellHzDec =  s.replaceAll("[^\u4E00-\u9FA5]", "");
////        System.out.println(s);
////        System.out.println(firstCellHzDec.substring(firstCellHzDec.lastIndexOf("发证地")+3,firstCellHzDec.lastIndexOf("位身份证号升")));
//        s = s.substring(s.lastIndexOf("地：</td><td class=")+24);
//        s = s.substring(0,s.indexOf("<br/></td></tr>"));
//        System.out.println(s);
//
//        RefreshEncryptedData red = new RefreshEncryptedData();
//
//        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
//
//                /* 读入TXT文件 */
//            String pathname = "C:\\Users\\darendai\\Desktop\\身份证.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
//            File filename = new File(pathname); // 要读取以上路径的input。txt文件
//            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
//            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
//            File writename = new File("C:\\Users\\darendai\\Desktop\\身份证sql.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
//            writename.createNewFile(); // 创建新文件
//            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
//            for(String line = br.readLine();line!=null;line = br.readLine()){
//                if(!"".equals(line)) {
//                    line = line.trim();
//                    if(1==red.countsql("select count(1) from cs_dic_area_dynam where  id between 6592 and 10231 and title ='"+line.substring(7)+"'")) {
//                        String[] s = red.initializationData("select id,parentId,title from cs_dic_area_dynam where  id between 6592 and 10231 and title ='" + line.substring(7) + "' limit 1");
//                        if (s[0] == null)
//                            System.out.println(line.substring(7));
//                        else {
//                            out.write("update cs_dic_area_dynam set remarks2='" + line.substring(0, 6) + "' where id = " + s[0] + ";\r\n");
//                        }
//                    }else{
//                        System.out.println(line.substring(7));
//                    }
//                }
//            }
//            out.flush(); // 把缓存区内容压入文件
//            out.close(); // 最后记得关闭文件
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
