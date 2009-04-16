package org.utmost.bank.cmbchina;

import org.utmost.bank.cmbchina.ListModeRequest.SDKMDLSTX;

import com.thoughtworks.xstream.XStream;

public class ListAccountRequest {
	public INFO INFO = null;

	public INFO getINFO() {
		return new INFO();
	}

	public void setINFO(INFO info) {
		INFO = info;
	}

	public SDKACLSTX getSDKACLSTX() {
		return new SDKACLSTX();
	}

	public void setSDKACLSTX(SDKACLSTX sdkaclstx) {
		SDKACLSTX = sdkaclstx;
	}

	public SDKACLSTX SDKACLSTX = null;

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

	public static class SDKACLSTX {
		String BUSCOD = "";

		public String getBUSCOD() {
			return BUSCOD;
		}

		public void setBUSCOD(String buscod) {
			BUSCOD = buscod;
		}

		public String getBUSMOD() {
			return BUSMOD;
		}

		public void setBUSMOD(String busmod) {
			BUSMOD = busmod;
		}

		String BUSMOD = "";
	}

	public static String getXML() {
		XStream x = new XStream();
		ListAccountRequest obj = new ListAccountRequest();
		x.alias("CMBSDKPGK", ListAccountRequest.class);
		ListAccountRequest.INFO info = obj.getINFO();
		info.setDATTYP("2");
		info.setFUNNAM("ListAccount");
		info.setLGNNAM("USRA01");
		obj.setINFO(info);
		ListAccountRequest.SDKACLSTX sdk = obj.getSDKACLSTX();
		sdk.setBUSCOD("N02030");
		sdk.setBUSMOD("00001");
		obj.setSDKACLSTX(sdk);
		String res = x.toXML(obj);
		return res;

	}

	public static void main(String[] args) {
		System.out.println(ListAccountRequest.getXML());
	}

}
