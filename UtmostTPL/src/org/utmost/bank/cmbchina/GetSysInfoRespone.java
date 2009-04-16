package org.utmost.bank.cmbchina;

import com.thoughtworks.xstream.XStream;

public class GetSysInfoRespone {

	INFO INFO = null;

	public INFO getINFO() {
		return new INFO();
	}

	public void setINFO(INFO info) {
		INFO = info;
	}

	static class INFO {
		String DATTYP = "";
		String ERRMSG = "";

		public String getDATTYP() {
			return DATTYP;
		}

		public void setDATTYP(String dattyp) {
			DATTYP = dattyp;
		}

		public String getERRMSG() {
			return ERRMSG;
		}

		public void setERRMSG(String errmsg) {
			ERRMSG = errmsg;
		}

		public String getFUNNAM() {
			return FUNNAM;
		}

		public void setFUNNAM(String funnam) {
			FUNNAM = funnam;
		}

		public String getRETCOD() {
			return RETCOD;
		}

		public void setRETCOD(String retcod) {
			RETCOD = retcod;
		}

		String FUNNAM = "";
		String RETCOD = "";
	}

	String DETAIL = "";

	public String getDETAIL() {
		return DETAIL;
	}

	public void setDETAIL(String detail) {
		DETAIL = detail;
	}

	public static void main(String[] args) {
		XStream x = new XStream();
		GetSysInfoRespone gsi = new GetSysInfoRespone();
		x.alias("RESPONSE", GetSysInfoRespone.class);
		GetSysInfoRespone.INFO info = gsi.getINFO();
		info.setDATTYP("dd");
		info.setRETCOD("0");
		gsi.setINFO(info);
		gsi.setDETAIL("wahaha");
		System.out.println(x.toXML(gsi));
	}
}
