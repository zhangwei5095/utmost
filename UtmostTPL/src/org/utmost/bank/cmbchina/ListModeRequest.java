package org.utmost.bank.cmbchina;

import com.thoughtworks.xstream.XStream;

public class ListModeRequest {
	INFO INFO = null;

	public INFO getINFO() {
		return new INFO();
	}

	public void setINFO(INFO info) {
		INFO = info;
	}

	static class INFO {
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

		String FUNNAM = "";
		String DATTYP = "";
		String LGNNAM = "";
	}

	public SDKMDLSTX getSDKMDLSTX() {
		return new SDKMDLSTX();
	}

	public void setSDKMDLSTX(SDKMDLSTX sdkmdlstx) {
		this.SDKMDLSTX = sdkmdlstx;
	}

	SDKMDLSTX SDKMDLSTX = null;

	static class SDKMDLSTX {
		String BUSCOD = "";

		public String getBUSCOD() {
			return BUSCOD;
		}

		public void setBUSCOD(String buscod) {
			BUSCOD = buscod;
		}
	}

	public static String getXML() {
		XStream x = new XStream();
		ListModeRequest gsi = new ListModeRequest();
		x.alias("CMBSDKPGK", ListModeRequest.class);
		ListModeRequest.INFO info = gsi.getINFO();
		info.setDATTYP("2");
		info.setFUNNAM("ListMode");
		info.setLGNNAM("USRA01");
		gsi.setINFO(info);
		SDKMDLSTX sdk = gsi.getSDKMDLSTX();
		sdk.setBUSCOD("N02031");
		gsi.setSDKMDLSTX(sdk);
		String res = x.toXML(gsi);
		return res;

	}
}
