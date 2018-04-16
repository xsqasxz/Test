<%@ page import="java.util.Map" import="demo.ServiceDemo" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String mobile = request.getParameter(ServiceDemo.MOBILE_TAG);
    Map<String,Object> param = new HashMap<String,Object>();
    param.put(ServiceDemo.MOBILE_TAG,mobile);
    Map<String,Object> resp = ServiceDemo.sendMsgCode(param);
    System.out.println(resp);
    String msg = (String)resp.get("remsg");
%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
    <title>DEMO</title>
</head>
<body>
    <p>Response: <%=msg%></p>
</body>
</html>
