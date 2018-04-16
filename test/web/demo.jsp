<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html style="height: 251px; ">
<head>
    <title>发送短信验证码</title>
</head>
<body>
	<form action="./jsp/sendMsgCode.jsp" method="post">
	    输入手机号码：
	    <input type="text" name ="platcust" >
	    <input type="submit"  value="发送">
	</form>

</body>
</html>