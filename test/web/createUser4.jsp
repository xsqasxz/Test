
<%@ page import="java.util.Map" import="demo.ServiceDemo" %>
<%@ page import="java.util.Map" import="demo.DateFormat" %>
 
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("UTF-8");
    String data=request.getParameter("data");
    String datajson =request.getParameter("datajson");
   
 
    if(!"".equals(data)&& data!=null)
    	data=data.trim();
    
    if(!"".equals(datajson)&& datajson!=null)
    	datajson=datajson.trim();
    
    String urlMethod=request.getParameter("urlMethod");
  
     System.out.println(data);
    String resp ="";
    
    Map<String,Object> param = new HashMap<String,Object>();
    
    if(data!=null &&!"".equals(data)){
    
	String[] params=data.split(",");
	
   for (String keyvalue : params) {
    	//System.out.println("key=="+keyvalue.substring(0,keyvalue.indexOf(":")) +"   value="+keyvalue.substring(keyvalue.indexOf(":")));
     
        param.put(keyvalue.substring(0,keyvalue.indexOf(":")).trim(),keyvalue.substring(keyvalue.indexOf(":")+1).trim());
    }

    
    
    if(!"".equals(datajson)&& datajson!=null){
    	
    	String[] datajsons=datajson.split(";");
    	for(String indexs:datajsons){
    		 if(!"".equals(indexs)&& indexs!=null)
    		 param.put(indexs.substring(0,indexs.indexOf("=")).trim(),indexs.substring(indexs.indexOf("=")+1).trim());
    		  
    	}
    }
   
   
   
    	resp = ServiceDemo.sendMsg2(param,urlMethod);
    
    
    System.out.println(resp);
    }
   
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
<table width="900" height="345" border="1">

<form  method="post" action="./createUser4.jsp">

  <tr>
    <td colspan="2" align="center"><strong>接口测试</strong></td>
      <td width="532"  align="center"><strong>填写示例</strong></td>
  </tr>
  
  <tr>
    <td>URL 路径：</td>
    <td>
        <label for="urlMethod"></label>
        <input name="urlMethod" type="text" value="<%=urlMethod%>" size="30" />
  </td>
  <td>
       userAction_batchRegistExt3
  </td>
  </tr>
  
  <tr>
    <td width="92">参数：</td>
    <td width="465">
       <textarea name="data" cols="80" rows="20" > <%  if(data==null || "".equals(data)){%>
plat_no:XIB-DRD-B-24109753, 
order_no:1111121110, 
partner_trans_date:20170516,
partner_trans_time:112400, 
total_num:1 

 <% } else {out.println(data);} %>
       </textarea>
    
    </td>
      <td>
      plat_no:XIB-DRD-B-24109753,
order_no:1111121110,
partner_trans_date:20170516,
partner_trans_time:112400,
total_num:1

  </td>
  </tr>
  
   <tr>
    <td><strong>参数json</strong></td>
    <td>
      <label for="data"></label>
      <textarea name="datajson" cols="80" rows="20" ><%  if(datajson==null || "".equals(datajson)){%>
data=[ 
       {detail_no:9991111,
         name:111,
       id_code:44999991,
       mobile:18820015250,
       cus_type:1 
  } ]
 <% } else {
             out.println(datajson);}
 
		 %>
      </textarea>
   </td>
         <td>
   data=[
{detail_no:9991111,
name:111,
id_code:44999991,
mobile:18820015250,
cus_type:1
}

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
      <textarea name="response" cols="80" rows="50" id="response"><%=resp%></textarea>
   </td>
    </tr>

</table>
</body>
</html>
