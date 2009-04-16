package org.utmost.bank.cmbchina;

import java.util.HashMap;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.thoughtworks.xstream.XStream;

public class GetPaymentInfoRequest {

	INFO INFO = new INFO();

	public INFO getINFO() {
		return INFO;
	}

	public void setINFO(INFO info) {
		INFO = info;
	}

	SDKPAYQYX SDKPAYQYX = new SDKPAYQYX();

	public SDKPAYQYX getSDKPAYQYX() {
		return SDKPAYQYX;
	}

	public void setSDKPAYQYX(SDKPAYQYX sdk) {
		SDKPAYQYX = sdk;
	}

	static class INFO {
		String FUNNAM = "";
		String DATTYP = "";
		String LGNNAM = "";

		public String getFUNNAM() {
			return FUNNAM;
		}

		public void setFUNNAM(String funnam) {
			FUNNAM = funnam;
		}

		public String getDATTYP() {
			return DATTYP;
		}

		public void setDATTYP(String dattyp) {
			DATTYP = dattyp;
		}

		public String getLGNNAM() {
			return LGNNAM;
		}

		public void setLGNNAM(String lgnnam) {
			LGNNAM = lgnnam;
		}
	}

	static class SDKPAYQYX {
		String BUSCOD = "";
		String BGNDAT = "";
		String ENDDAT = "";
		String MINAMT = null;
		String MAXAMT = null;
		String YURREF = null;
		String RTNFLG = null;

		public String getMINAMT() {
			return MINAMT;
		}

		public void setMINAMT(String minamt) {
			MINAMT = minamt;
		}

		public String getMAXAMT() {
			return MAXAMT;
		}

		public void setMAXAMT(String maxamt) {
			MAXAMT = maxamt;
		}

		public String getRTNFLG() {
			return RTNFLG;
		}

		public void setRTNFLG(String rtnflg) {
			RTNFLG = rtnflg;
		}

		public String getYURREF() {
			return YURREF;
		}

		public void setYURREF(String yurref) {
			YURREF = yurref;
		}

		public String getBUSCOD() {
			return BUSCOD;
		}

		public void setBUSCOD(String buscod) {
			BUSCOD = buscod;
		}

		public String getBGNDAT() {
			return BGNDAT;
		}

		public void setBGNDAT(String bgndat) {
			BGNDAT = bgndat;
		}

		public String getENDDAT() {
			return ENDDAT;
		}

		public void setENDDAT(String enddat) {
			ENDDAT = enddat;
		}

	}

	public static String getXML() {
		XStream x = new XStream();
		GetPaymentInfoRequest obj = new GetPaymentInfoRequest();
		x.alias("CMBSDKPGK", GetPaymentInfoRequest.class);
		GetPaymentInfoRequest.INFO info = obj.getINFO();
		info.setFUNNAM("GetPaymentInfo");
		info.setDATTYP("2");
		info.setLGNNAM("USRA01");
		obj.setINFO(info);
		GetPaymentInfoRequest.SDKPAYQYX sdk = obj.getSDKPAYQYX();
		sdk.setBUSCOD("N02031");
		sdk.setBGNDAT("20090401");
		sdk.setENDDAT("20090407");

		sdk.setYURREF("ABD060928001255");
		// sdk.setRTNFLG("");
		obj.setSDKPAYQYX(sdk);
		String res = x.toXML(obj);
		return res;
	}

	public XStream processXStream() {
		XStream x = new XStream();
		x.alias("CMBSDKPGK", GetPaymentInfoRequest.class);
		return x;
	}

	public String processStructure(JCoFunction fun) {
		JCoStructure sru = fun.getImportParameterList().getStructure(
				"PAYMENT_INFO");
		GetPaymentInfoRequest gpir = new GetPaymentInfoRequest();
		gpir.getINFO().setFUNNAM("GetPaymentInfo");
		gpir.getINFO().setDATTYP("2");
		gpir.getINFO().setLGNNAM("USRA01");// 正式系统需修改
		gpir.getSDKPAYQYX().setBUSCOD("N02030");
		gpir.getSDKPAYQYX().setBGNDAT(sru.getString("BGNDAT"));
		gpir.getSDKPAYQYX().setENDDAT(sru.getString("ENDDAT"));
		gpir.getSDKPAYQYX().setYURREF(sru.getString("YURREF"));
		gpir.getSDKPAYQYX().setRTNFLG(sru.getString("RTNFLG"));
		return gpir.processXStream().toXML(gpir);
	}
	public HashMap returnSaveMap(JCoFunction fun) {
		JCoStructure sru = fun.getImportParameterList().getStructure(
				"PAYMENT_INFO");
		HashMap map = new HashMap();
		map.put("operator_date", org.utmost.util.DateUtil.getNowDate());// 写死
		//map.put("buscod", "N2030");// 写死
		map.put("buscod", sru.getString("BUSCOD"));
		map.put("bgndat", sru.getString("BGNDAT"));
		map.put("enddat", sru.getString("ENDDAT"));
		map.put("minamt", sru.getString("MINAMT"));
		map.put("maxamt", sru.getString("MAXAMT"));
		map.put("yurref", sru.getString("YURREF"));//
		map.put("rtnflg", sru.getString("RTNFLG"));
		map.put("operater_id", sru.getString("OPERATOR_ID"));
		map.put("operater_name", sru.getString("OPERATOR_NAME"));
		map.put("state", "01");//操作时间

		//System.out.println("-------------->CRTSQN" + sru.getString("CRTSQN"));
		return map;
		// AutoService service = (AutoService) SpringContext
		// .getBean("AutoService");
	}
	public static void main(String[] args) {
		System.out.println(GetPaymentInfoRequest.getXML());
	}

	

}
