<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<wml>
<head><meta http-equiv="Content-Type" content="text/vnd.wap.wml;charset=UTF-8"/></head>
<card id="top" title="黄金搭档移动商务演示系统"><p>
黄金搭档移动商务演示系统<br/>
----------------------------<br/>
登录名:
<%
String user=request.getParameter("user");
out.print(user);
%>
<br/>
密码:
<%
String pwd=request.getParameter("pwd");
out.print(pwd);
%>
<br/>
</card>
</wml>