@echo off
set JAVA_DIR="C:\Java\jdk1.6\bin\java.exe"

if exist backupAndResotre\ObjectLoader\LoaderOutput\*.csv goto deldir
goto run

:deldir
del backupAndResotre\ObjectLoader\LoaderOutput\*.csv

:run
java -classpath ..\WEB-INF\lib\db2jcc.jar;..\WEB-INF\lib\db2jcc_license_cisuz.jar;..\WEB-INF\lib\db2java.jar;..\WEB-INF\lib\db2jcc_license_cu.jar;..\WEB-INF\lib\db2jcc_javax.jar;..\WEB-INF\lib\xercesImpl.jar;..\WEB-INF\lib\hsqldb.jar;..\WEB-INF\lib\xml-apis.jar;..\WEB-INF\lib\csvjdbc.jar;..\WEB-INF\lib\Octopus.jar org.webdocwf.util.loader.Loader -u admin -r -v defaultsprache=DE_AT backupAndResotre\ObjectLoader\U_TPL_TEMPLATE_ToDB.olj
pause


