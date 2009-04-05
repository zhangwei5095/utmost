package org.utmost.etl.service;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import org.springframework.stereotype.Service;
import org.utmost.tpl.service.TemplateService;
import org.webdocwf.util.loader.BufferOctopusClass;
import org.webdocwf.util.loader.Loader;
import org.webdocwf.util.loader.LoaderException;
import org.webdocwf.util.loader.ReturnCode;

/**
 * ETL服务类及对外接口
 * 
 * @author wanglm
 * 
 */
@Service("ETLService")
public class ETLService {
	/**
	 * 
	 * @param type
	 *            ("backup","restore")
	 */
	public void backupAndResotre(String type) {
		String syspath = this.getClass().getResource("").getPath();
		syspath = syspath.substring(1, syspath.indexOf("WEB-INF"));
		syspath += "ETL/";
		// System.out.println(syspath);
		String path = "";
		if (type.equals("backup")) {
			path = syspath
					+ "backupAndResotre/ObjectLoader/U_TPL_TEMPLATE_ToCSV.olj";
		} else if (type.equals("restore")) {
			path = syspath
					+ "backupAndResotre/ObjectLoader/U_TPL_TEMPLATE_ToDB.olj";
		} else {
			System.out.println("type is not valid!");
		}
		path = path.replaceAll("%20", " ");
		System.out.println(path);
		String[] cmd = { "-u admin", "-r", "-v defaultsprache=DE_AT", path };
		try {
			// Loader.main(cmd);//停机bug
			ETLService.LoaderMain(cmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ETLService es = new ETLService();
		es.backupAndResotre("backup");// 备份测试
		// es.backupAndResotre("restore");// 恢复测试
	}

	// 重写Loader class
	public static void LoaderMain(java.lang.String[] paramArrayOfString) {
		java.lang.String str1 = null;
		java.lang.String str2 = null;
		java.lang.String str3 = null;
		java.lang.String str4 = null;
		java.lang.String str5 = null;
		boolean bool1 = false;
		HashMap localHashMap = null;
		java.lang.String str6 = null;
		java.lang.String str7 = null;
		boolean bool2 = false;
		java.lang.String str8 = null;
		int i = 1;
		int j = 0;
		java.lang.String[] arrayOfString = null;
		java.lang.String str9 = null;
		if ((paramArrayOfString.length > 0) && (paramArrayOfString.length < 26)) {
			str1 = paramArrayOfString[(paramArrayOfString.length - 1)];
			int k = 0;
			while (k < paramArrayOfString.length - 1) {
				if (paramArrayOfString[k].equalsIgnoreCase("-m")) {
					str2 = paramArrayOfString[(++k)];
				} else if (paramArrayOfString[k].equalsIgnoreCase("-r")) {
					bool1 = true;
				} else if (paramArrayOfString[k].equalsIgnoreCase("-u")) {
					str3 = paramArrayOfString[(++k)];
				} else if (paramArrayOfString[k].equalsIgnoreCase("-l")) {
					str4 = paramArrayOfString[(++k)];
				} else if (paramArrayOfString[k].equalsIgnoreCase("-f")) {
					str5 = paramArrayOfString[(++k)];
				} else if (paramArrayOfString[k].equalsIgnoreCase("-d")) {
					str7 = paramArrayOfString[(++k)];
				} else if (paramArrayOfString[k].equalsIgnoreCase("-e")) {
					bool2 = true;
				} else if (paramArrayOfString[k].equalsIgnoreCase("-p")) {
					str8 = paramArrayOfString[(++k)];
				} else if (paramArrayOfString[k].equalsIgnoreCase("-c")) {
					j = new Integer(paramArrayOfString[(++k)]).intValue();
				} else if (paramArrayOfString[k].equalsIgnoreCase("-v")) {
					str6 = paramArrayOfString[(++k)];
					localHashMap = new HashMap(convertToMap(str6));
				} else if (paramArrayOfString[k].equalsIgnoreCase("-rc")) {
					i = new Integer(paramArrayOfString[(++k)]).intValue();
					ReturnCode.isParameter = true;
				} else if (paramArrayOfString[k].equalsIgnoreCase("-it")) {
					java.lang.String str10 = paramArrayOfString[(++k)];
					StringTokenizer localStringTokenizer = new StringTokenizer(
							str10, ";");
					arrayOfString = new java.lang.String[localStringTokenizer
							.countTokens()];
					for (int l = 0; localStringTokenizer.hasMoreTokens(); ++l)
						arrayOfString[l] = localStringTokenizer.nextToken();
				} else if (paramArrayOfString[k].equalsIgnoreCase("-cjs")) {
					str9 = paramArrayOfString[(++k)];
				}
				k += 1;
			}
		} else {
			printUsage();
		}
		Loader localLoader = new Loader(str1, str2, str3, str4, str5, bool1,
				localHashMap, str7, bool2, str8, j, i, arrayOfString, str9);
		try {
			localLoader.load();
			BufferOctopusClass.getInstance().writeToBuffer(
					java.lang.String.valueOf(ReturnCode.getOKReturnCode()));
		} catch (LoaderException localLoaderException) {
			BufferOctopusClass.getInstance().writeToBuffer(
					localLoaderException.getCause() + "\n");
			BufferOctopusClass.getInstance().writeToBuffer(
					java.lang.String.valueOf(ReturnCode.getErrorReturnCode()));
			// System.exit(ReturnCode.getErrorReturnCode());
		}
	}

	// 重写Loader class
	private static Map convertToMap(java.lang.String paramString) {
		int k;
		java.lang.String str2;
		java.lang.String str3;
		HashMap localHashMap = new HashMap();
		int i = paramString.indexOf(";");
		int j = 0;
		java.lang.String str1 = new java.lang.String(paramString);
		if (i != -1) {
			while (i != -1) {
				str1 = new java.lang.String(paramString.substring(j, j + i));
				k = str1.indexOf("=");
				str2 = str1.substring(0, k);
				str3 = str1.substring(k + 1);
				if (str3.equals(""))
					str3 = null;
				localHashMap.put(str2, str3);
				j += i + 1;
				i = paramString.substring(j).indexOf(";");
			}
			if ((!(paramString.substring(j).trim().equals("")))
					&& (!(paramString.substring(j).trim().equals(";")))) {
				str1 = new java.lang.String(paramString.substring(j));
				k = str1.indexOf("=");
				str2 = str1.substring(0, k);
				str3 = str1.substring(k + 1);
				if (str3.equals(""))
					str3 = null;
				localHashMap.put(str2, str3);
			}
		} else {
			k = paramString.indexOf("=");
			str2 = paramString.substring(j, k);
			str3 = paramString.substring(k + 1);
			if (str3.equals(""))
				str3 = null;
			localHashMap.put(str2, str3);
		}
		return localHashMap;
	}

	// 重写Loader class
	private static void printUsage() {
		System.out
				.println("Usage: java org.webdocwf.util.loader.Loader [options] loadJob_xml_filename");
		System.out.println(" Options:");
		System.out
				.println(" -m defines the default logmode. Possible values are 'none', 'normal' (is the default) and 'full'.");
		System.out
				.println(" -r starts the Loader in restart mode after abnormal termination in a prior execution. \n");
		System.out
				.println(" -u defines the current UserID used in UserID value columns. \n");
		System.out
				.println(" -v defines variables used in variable columns.  \n");
		System.out
				.println(" -l defines the logfile directory. The default is the current working directory.   \n");
		System.out
				.println(" -f defines the logfile name. The default is 'LoaderLog-YYYY-MM-DD-HH-mm-SS.txt'. \n");
		System.out
				.println(" -d the filename of the XML DB-vendor configuration file. The default is 'OctopusDBVendors.xml'. \n");
		System.out
				.println(" -e defines to set the default of 'onErrorContinue' to 'true'. Is false otherwise. \n");
		System.out.println(" -p Extend the classpath with additional paths \n");
		System.out
				.println(" -c Sets the default commit count. System default is '100'.  \n");
		System.out
				.println(" -rc Sets the default error return code. System default is '1'.  \n");
		System.out
				.println(" -it Sets the table names which will be proccesed ");
	}
}
