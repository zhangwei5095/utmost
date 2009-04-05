@echo off
set JAVA_DIR="C:\Java\jdk1.6\bin\java.exe"

if exist ObjectLoader\LoaderOutput\TestDatabase\*.* goto deldir
goto run

:deldir
del ObjectLoader\LoaderOutput\TestDatabase\*.*

:run
%JAVA_DIR% -classpath ..\WEB-INF\lib\db2jcc.jar;..\WEBINF\\lib\db2jcc_license_cisuz.jar;..\lib\db2java.jar;..\lib\db2jcc_license_cu.jar;..\lib\db2jcc_javax.jar;..\lib\xercesImpl.jar;..\lib\hsqldb.jar;..\lib\xml-apis.jar;..\lib\csvjdbc.jar;..\lib\Octopus.jar org.webdocwf.util.loader.Loader -u admin -r -v defaultsprache=DE_AT ObjectLoader\U_TPL_TEMPLATE.olj
pause