<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<wml>
<head><meta http-equiv="Content-Type" content="text/vnd.wap.wml;charset=UTF-8"/></head>
<card id="top" title="黄金搭档移动商务演示系统"><p>
黄金搭档移动商务演示系统<br/>
----------------------------<br/>
登录名:<br/>
<input name="InputUser" maxlength="32" size="12" format="*m" value=""/><br/>
密码:<br/>
<input name="InputPwd" maxlength="32" size="12" type="password" format="*m"/><br/>
<!--
登陆test<go method="post" href="index.jsp">
	<postfield name="NBUser" value="$(NBUser)"/>
	<postfield name="Pwd" value="$(Pwd)"/>
</go><br/>

<input type="button" value="登陆" name="login">
-->
<anchor title="登陆">登陆
<go href="index.jsp" method="post">
	<postfield name="user" value="$(InputUser)"/>
	<postfield name="pwd" value="$(InputPwd)"/>
</go>
</anchor>
</card>
</wml>