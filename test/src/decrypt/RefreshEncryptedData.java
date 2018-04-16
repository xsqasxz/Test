/**
 * 用于刷新加密数据使用
 */
package decrypt;

import com.daren.common.security.SecurityUtils;
import java.sql.*;

public class RefreshEncryptedData {
    //驱动程序就是之前在classpath中配置的JDBC的驱动程序的JAR 包中
    private static final String DBDRIVER ="com.mysql.jdbc.Driver";
    //连接地址是由各个数据库生产商单独提供的，所以需要单独记住
//    private static final String DBURL = "jdbc:mysql://192.168.1.239:63306/hurong_proj_gd_szdrd?useUnicode=true&amp;characterEncoding=utf-8";
    private static final String DBURL = "jdbc:mysql://192.168.1.238:3306/encrypt_hurong_proj_gd_szdrd?useUnicode=true&amp;characterEncoding=utf-8 ";
//    private static final String DBURL = "jdbc:mysql://192.168.1.238:3306/hurong_proj_gd_szdrd?useUnicode=true&amp;characterEncoding=utf-8 ";
//    private static final String DBURL = "jdbc:mysql://192.168.1.239:63306/xuesq?useUnicode=true&amp;characterEncoding=utf-8 ";
//    //连接数据库的用户名
    private static final String DBUSER = "proxy";
//    //连接数据库的密码
    private static final String DBPASS = "dr2015";
    //连接数据库的用户名
//    private static final String DBUSER = "yujj";
//    private static final String DBUSER = "xuesq";
    //连接数据库的密码
//    private static final String DBPASS = "yujj2018";
//    private static final String DBPASS = "xuesq123";
    //yujj，yujj2018
    private Connection con = null; //表示数据库的连接对象

    public Connection getCon(){
        try {
            Class.forName(DBDRIVER); //1、使用CLASS 类加载驱动程序
            con = DriverManager.getConnection(DBURL,DBUSER,DBPASS); //2、连接数据库
            return con;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] initializationData(String sql){
        con = getCon();
        try {
            //先查询出需要加密的数据
            Statement sm =  con.createStatement();
            ResultSet resultSet = sm.executeQuery(sql);
            String[] as = new String[3];
            while (resultSet.next()){
                as[0]=resultSet.getString(1);
                as[1]=resultSet.getString(2);
                as[2]=resultSet.getString(3);
            }
            con.close();
            return as;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public long countsql(String sql){
        con = getCon();
        try {
            //先查询出需要加密的数据
            Statement sm =  con.createStatement();
            ResultSet resultSet = sm.executeQuery(sql);
            long l = 0;
            while (resultSet.next()){
               l = Long.parseLong(resultSet.getString(1));
            }
            con.close();
            return l;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void add(String sql){
        con = getCon();
        try {
            //先查询出需要加密的数据
            Statement sm =  con.createStatement();
            sm.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化数据
     * @param tableName 表名
     * @param idName 表id
     * @param fields 字段
     */
    public void initializationData(String tableName,String idName,String... fields){
        con = getCon();
        try {
            //先查询出需要加密的数据
            Statement sm =  con.createStatement();
            ResultSet resultSet = sm.executeQuery(getSqlQuery2(tableName,idName,fields));
            PreparedStatement preparedStatement = null;
            String cryptText;
            String signText;

            while (resultSet.next()){
                int j  = 1;
                preparedStatement = con.prepareStatement(getSqlUpdte(tableName,idName,fields));
                for(int i = 2,len = fields.length+2 ; i < len ; i ++){
                    cryptText = SecurityUtils.BUSINESS.cryptText(resultSet.getString(i));
                    signText = SecurityUtils.BUSINESS.getSign(resultSet.getString(i));
                    preparedStatement.setString(j,cryptText);
                    j ++;
                    preparedStatement.setString(j,signText);
                    j ++;
                }
                preparedStatement.setString(j,resultSet.getString(1));
                preparedStatement.executeUpdate();
            }
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解密数据
     * @param tableName 表名
     * @param idName 表id
     * @param fields 字段
     */
    public void deCrypData(String tableName,String idName,String... fields){
        con = getCon();
        try {
            //先查询出需要加密的数据
            Statement sm =  con.createStatement();
            ResultSet resultSet = sm.executeQuery(getSqlQuery2(tableName,idName,fields));
            PreparedStatement preparedStatement = null;
            String deCryptText;
            String signText;

            while (resultSet.next()){
                int j  = 1;
                preparedStatement = con.prepareStatement(getSqlUpdte(tableName,idName,fields));
                for(int i = 2,len = fields.length+2 ; i < len ; i ++){
                    deCryptText = SecurityUtils.BUSINESS.deCryptText(resultSet.getString(i));
                    if(deCryptText !=null ) {
                        if (deCryptText.length() < 60) {
                            if (deCryptText.length() > 5) {
                                deCryptText = deCryptText.substring(0, deCryptText.length() - 5) + "*****";
                            }
                        }
                    }
                    signText = SecurityUtils.BUSINESS.getSign(deCryptText);
                    deCryptText = SecurityUtils.BUSINESS.cryptText(deCryptText);
                    preparedStatement.setString(j, deCryptText);
                    j++;
                    preparedStatement.setString(j, signText);
                    j++;

                }
                if (j > 1) {
                    preparedStatement.setString(j,resultSet.getString(1));
                    preparedStatement.executeUpdate();
                }
            }
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到查询语句
     * @param tableName
     * @param idName
     * @param fields
     * @return
     */
    private String getSqlQuery(String tableName,String idName,String... fields){
        StringBuffer sqlUpdate = new StringBuffer("select ");
        int fieldLen = fields.length;

        if (fieldLen > 0) {
            sqlUpdate.append(idName);
            for(String field : fields){
                sqlUpdate.append(" ,").append(field);
            }
            sqlUpdate.append(" from ").append(tableName);
            sqlUpdate .append(" where ");
            for(String field : fields){
                sqlUpdate.append(" (").append(field).append(" is not null and ").append(field).append(" <> ''").append(") or");
            }
            sqlUpdate = new StringBuffer(sqlUpdate.toString().substring(0,sqlUpdate.length()-2));
            return sqlUpdate.toString();
        }
        return null;
    }

    /**
     * 得到查询语句
     * @param tableName
     * @param idName
     * @param fields
     * @return
     */
    private String getSqlQuery2(String tableName,String idName,String... fields){
        StringBuffer sqlUpdate = new StringBuffer("select ");
        int fieldLen = fields.length;

        if (fieldLen > 0) {
            sqlUpdate.append(idName);
            for(String field : fields){
                sqlUpdate.append(" ,").append(field).append("_a");
            }
            sqlUpdate.append(" from ").append(tableName);
            sqlUpdate .append(" where ");
            for(String field : fields){
                sqlUpdate.append(" (").append(field).append("_a").append(" is not null and ").append(field).append("_a").append(" <> ''").append(") or");
            }
            sqlUpdate = new StringBuffer(sqlUpdate.toString().substring(0,sqlUpdate.length()-2));
            return sqlUpdate.toString();
        }
        return null;
    }

    /**
     * 得到修改语句
     * @param tableName
     * @param idName
     * @param fields
     * @return
     */
    private String getSqlUpdte(String tableName,String idName,String... fields){
        StringBuffer sqlUpdate = new StringBuffer("update ");
        sqlUpdate.append(tableName).append(" set ");

        int fieldLen = fields.length;
        if (fieldLen > 0) {
            for(String field : fields){
                sqlUpdate.append(field).append("_a").append(" = ? ,").append(field).append("_m").append(" = ? ,");
            }
            sqlUpdate = new StringBuffer(sqlUpdate.toString().substring(0,sqlUpdate.length()-1));
            sqlUpdate .append(" where ").append(idName).append(" = ? ");
            return sqlUpdate.toString();
        }
        return null;
    }

}
