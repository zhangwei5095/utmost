<jsp:include page="header.jsp"></jsp:include>
<wml>
<card title="">
<p>
<img src="https://mobile.cmbchina.com/MBank/UI/wap/images/logocmb.png" alt="" align="top"/>
</p>
<p mode="wrap" align="center">
<b>&#x4e00;&#x7f51;&#x901a;&#x7528;&#x6237;</b>|<a href="https://mobile.cmbchina.com/MBank/UI/wap/wml/Login/LoginA.aspx">&#x4e00;&#x5361;&#x901a;</a>|<a href="https://mobile.cmbchina.com/MBank/UI/wap/wml/Login/LoginC.aspx">&#x4fe1;&#x7528;&#x5361;</a>
</p>
<p mode="wrap">
--------------------<br/>
&#x767b;&#x5f55;&#x540d;:<br/>
<input name="NBUser" maxlength="32" size="12" format="*m" value=""/><br/>
&#x7f51;&#x94f6;&#x5bc6;&#x7801;:<br/>
<input name="Pwd" maxlength="32" size="12" type="password" format="*m"/><br/>
&#x9644;&#x52a0;&#x7801;:<br/>

<input name="ExtraPwd" maxlength="4" size="5" format="*N"/><img src="https://mobile.cmbchina.com/MBank/UI/wap/wml/Login/ExtraPwd.aspx?ClientNo=61055EEBF4644402309470678300082602" alt=""/>&#x6216;<img src="https://mobile.cmbchina.com/MBank/UI/wap/wml/Login/ExtraPwd.aspx?ClientNo=61055EEBF4644402309470678300082602&amp;Format=PNG" alt=""/><br/>
&#x662f;&#x5426;&#x8bb0;&#x4f4f;&#x767b;&#x5f55;&#x540d;:<br/>
<select name="RememberFlag" value="N">
	<option value="N">&#x4e0d;&#x8981;&#x8bb0;&#x4f4f;</option>
	<option value="Y">&#x5e2e;&#x6211;&#x8bb0;&#x4f4f;</option>

</select><br/>
<anchor>&#x767b;&#x5f55;<go method="post" href="https://mobile.cmbchina.com/MBank/UI/wap/wml/Login/Login.aspx">
	<postfield name="ClientNo" value="61055EEBF4644402309470678300082602"/>
	<postfield name="Command" value="CMD_DOLOGIN"/>
	<postfield name="NBUser" value="$(NBUser)"/>
	<postfield name="Pwd" value="$(Pwd)"/>
	<postfield name="ExtraPwd" value="$(ExtraPwd)"/>
	<postfield name="RememberFlag" value="$(RememberFlag)"/>
</go></anchor><br/>

--------------------<br/>
<anchor>&#x7f51;&#x70b9;&#x5feb;&#x67e5;<go method="post" href="https://mobile.cmbchina.com/MBank/UI/wap/wml/Toolkit/ServiceTerminal/ST_QueryServiceTerminal.aspx">
	<postfield name="ReturnURL" value="/wap/wml/Login/Login.aspx"/>

</go></anchor><img src="https://mobile.cmbchina.com/MBank/UI/wap/images/newtip1.png" alt="" align="top"/><br/>
<anchor>&#x4e1a;&#x52a1;&#x4ecb;&#x7ecd;<go method="post" href="https://mobile.cmbchina.com/MBank/UI/wap/wml/Help/MBank_Introduce.aspx">
	<postfield name="ReturnURL" value="/wap/wml/Login/Login.aspx"/>
</go></anchor>&nbsp;&nbsp;
<anchor>&#x6700;&#x8fd1;&#x66f4;&#x65b0;<go method="post" href="https://mobile.cmbchina.com/MBank/UI/wap/wml/Help/UpdateOverview.aspx">
	<postfield name="ReturnURL" value="/wap/wml/Login/Login.aspx"/>

</go></anchor><br/>
<anchor>&#x4ec0;&#x4e48;&#x662f;&#x4e00;&#x7f51;&#x901a;&#x7528;&#x6237;?<go method="post" href="https://mobile.cmbchina.com/MBank/UI/wap/wml/Help/WhatIsNBUser.aspx">
	<postfield name="ReturnURL" value="/wap/wml/Login/Login.aspx"/>
</go></anchor><br/>

<anchor>&#x4e86;&#x89e3;&#x767b;&#x5f55;&#x540d;&#x8bb0;&#x5fc6;&#x529f;&#x80fd;<go method="post" href="https://mobile.cmbchina.com/MBank/UI/wap/wml/Help/HowRememberNBUser.aspx">
	<postfield name="ReturnURL" value="/wap/wml/Login/Login.aspx"/>
</go></anchor><br/>

--------------------<br/>
<a href="https://mobile.cmbchina.com/wapbank/wapbank.dll?LoadLogPage?connpt=">&#x65e7;&#x624b;&#x673a;&#x94f6;&#x884c;&#x767b;&#x5f55;&#x5165;&#x53e3;</a><br/>


</p>
</card>
</wml>
