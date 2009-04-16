package org.utmost.bank.cmbchina;

import org.utmost.bank.cmbchina.ListModeRequest.INFO;

import com.thoughtworks.xstream.XStream;

public class GetNewNoticeRequest {
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

	public static String getXML() {
		XStream x = new XStream();
		GetNewNoticeRequest obj = new GetNewNoticeRequest();
		x.alias("CMBSDKPGK", GetNewNoticeRequest.class);
		GetNewNoticeRequest.INFO info = obj.getINFO();
		info.setDATTYP("2");
		info.setFUNNAM("GetNewNotice");
		info.setLGNNAM("USRA01");
		obj.setINFO(info);
		return x.toXML(obj);
	}

	public static void main(String[] args) {
		System.out.println(GetNewNoticeRequest.getXML());
	}

}
