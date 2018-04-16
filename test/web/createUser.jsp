
<%@ page import="java.util.Map" import="demo.ServiceDemo" %>
<%@ page import="java.util.Map" import="demo.DateFormat" %>
 
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");
    String plat_no =request.getParameter("plat_no");
    String index =request.getParameter("index")==null?"0":"1";
    String order_no =DateFormat.getUserDate("yyyyMMddHHmmss");
    String partner_trans_date=request.getParameter("partner_trans_date")==null?DateFormat.getUserDate("yyyyMMdd"):request.getParameter("partner_trans_date");
    String partner_trans_time= request.getParameter("partner_trans_time")==null?DateFormat.getUserDate("HHmmss"):request.getParameter("partner_trans_time");
    int total_num=1;
    String data = request.getParameter("data");
    
    System.out.println(data);
    
    
    Map<String,Object> param = new HashMap<String,Object>();
    param.put("plat_no",plat_no);
    param.put("order_no",order_no);
    param.put("partner_trans_date",partner_trans_date);
    param.put("partner_trans_time",partner_trans_time);
    param.put("total_num",1);
    param.put("data",data);
    
    Map<String,Object> resp=null;
    
    
    if(index.equals("1")){
    	resp = ServiceDemo.sendMsg(param,"userAction_batchRegistExt3");
    }
    
    System.out.println(resp);
    
   
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<style type="text/css">
body,td,th {
	font-size: 24px;
}
</style>
</head>

<body>
<table width="914" height="345" border="1">

<form  method="post" action="./createUser.jsp">

  <tr>
    <td colspan="2" align="center"><strong>开户</strong></td>
  </tr>
  <tr>
    <td width="167">平台编号：</td>
    <td width="731">
      <input name="plat_no" type="text"  value="XIB-DRD-B-24109753" size="30" />
         <input name="index"  value="1" type="hidden"/>
    
    </td>
  </tr>
  <tr>
    <td>批量订单号：</td>
    <td>
        <label for="order_no"></label>
        <input name="order_no" type="text" value="<%=order_no%>" size="30" />
  </td>
  </tr>
   <tr>
    <td><strong>数据</strong></td>
    <td>
      <label for="data"></label>
      <textarea name="data" cols="100" rows="20" >
      <%  if(data==null || "".equals(data)){%>
      
      
      
      [
        	{
                  "detail_no":"001",
                  "name":"test",
                  "id_code":"441521198408031112",
                   "mobile":"18820015259",
                   "cus_type":"1"

          }
        	

]

 <% } else {
             out.println(data);}
 
		 %>
      </textarea>
   </td>
  </tr>
   <tr>
    <td>
    </td>
    <td><input type="submit" name="提交" id="提交" value="提交" /></td>
    
    
  </tr>
  
   </form>
   <tr>
    <td>reponse 数据:</td>
    <td>&nbsp;</td>
    </tr> <tr>
    <td>&nbsp;</td>
    <td>
      <label for="response"></label>
      <textarea name="response" cols="100" rows="50" id="response"><%=resp%></textarea>
   </td>
    </tr>

</table>
</body>
</html>
