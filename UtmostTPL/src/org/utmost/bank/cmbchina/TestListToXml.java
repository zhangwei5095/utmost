package org.utmost.bank.cmbchina;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

public class TestListToXml {

	INFO INFO = null;

	public INFO getINFO() {
		return new INFO();
	}

	public void setINFO(INFO info) {
		INFO = info;
	}

	SDKPAYQYX SDKPAYQYX = null;

	List<SDKPAYQYX> list = null;

	public List<SDKPAYQYX> getList() {
		return list;
	}

	public void setList(List<SDKPAYQYX> list) {
		this.list = list;
	}

	public SDKPAYQYX getSDKPAYQYX() {
		return new SDKPAYQYX();
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

	public static class SDKPAYQYX {
		String BUSCOD = "";
		String BGNDAT = "";
		String ENDDAT = "";
		String YURREF = null;
		String RTNFLG = null;

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
		TestListToXml obj = new TestListToXml();
		x.alias("CMBSDKPGK", TestListToXml.class);
		x.alias("SDKPAYQYX", TestListToXml.SDKPAYQYX.class);
		
		x.addImplicitCollection(TestListToXml.class, "list");
		
		TestListToXml.INFO info = obj.getINFO();
		info.setFUNNAM("GetPaymentInfo");
		info.setDATTYP("2");
		info.setLGNNAM("USRA01");
		obj.setINFO(info);
//		TestListToXml.SDKPAYQYX sdk = obj.getSDKPAYQYX();
//		sdk.setBUSCOD("N02031");
//		sdk.setBGNDAT("20090401");
//		sdk.setENDDAT("20090407");
//		sdk.setYURREF("ABD060928001255");
//		obj.setSDKPAYQYX(sdk);
		
		ArrayList list = new ArrayList();

		list.add(obj.getSDKPAYQYX());
		list.add(obj.getSDKPAYQYX());
		list.add(obj.getSDKPAYQYX());
		list.add(obj.getSDKPAYQYX());
		list.add(obj.getSDKPAYQYX());
		obj.setList(list);
		String res = x.toXML(obj);
		return res;
	}

	public static void main(String[] args) {
		System.out.println(TestListToXml.getXML());
	}
}
