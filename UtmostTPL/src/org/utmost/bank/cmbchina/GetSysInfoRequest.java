package org.utmost.bank.cmbchina;

import com.thoughtworks.xstream.XStream;

public class GetSysInfoRequest {

	INFO INFO = null;

	public INFO getINFO() {
		return new INFO();
	}

	public void setINFO(INFO info) {
		INFO = info;
	}

	static class INFO {
		String FUNNAM = "";

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

		String DATTYP = "";
		String LGNNAM = "";
	}

	String SDKSYINFX = "";

	public String getSDKSYINFX() {
		return SDKSYINFX;
	}

	public void setSDKSYINFX(String sdksyinfx) {
		SDKSYINFX = sdksyinfx;
	}

	public static String getXML() {
		XStream x = new XStream();
		GetSysInfoRequest gsi = new GetSysInfoRequest();
		x.alias("CMBSDKPGK", GetSysInfoRequest.class);
		GetSysInfoRequest.INFO info = gsi.getINFO();
		info.setDATTYP("2");
		info.setFUNNAM("GetSysInfo");
		info.setLGNNAM("USRA01");
		gsi.setINFO(info);
		gsi.setSDKSYINFX("USRINF");
		String res = x.toXML(gsi);
		return res;
	}

	public static void main(String[] args) {
		GetSysInfoRequest.getXML();
	}
}
