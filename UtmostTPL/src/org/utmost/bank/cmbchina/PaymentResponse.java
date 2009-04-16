package org.utmost.bank.cmbchina;

import java.util.HashMap;

import com.thoughtworks.xstream.XStream;

public class PaymentResponse {
	public INFO INFO = new INFO();
	public NTQPAYRQZ NTQPAYRQZ = new NTQPAYRQZ();

	public INFO getINFO() {
		return INFO;
	}

	public void setINFO(INFO info) {
		INFO = info;
	}

	public NTQPAYRQZ getNTQPAYRQZ() {
		return NTQPAYRQZ;
	}

	public void setNTQPAYRQZ(NTQPAYRQZ ntqpayrqz) {
		NTQPAYRQZ = ntqpayrqz;
	}

	public static class INFO {
		String DATTYP = "";
		String ERRMSG = "";
		String FUNNAM = "";
		String LGNNAM = "";
		String RETCOD = "";

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

		public String getLGNNAM() {
			return LGNNAM;
		}

		public void setLGNNAM(String lgnnam) {
			LGNNAM = lgnnam;
		}

		public String getRETCOD() {
			return RETCOD;
		}

		public void setRETCOD(String retcod) {
			RETCOD = retcod;
		}
	}

	public static class NTQPAYRQZ {
		String ERRCOD = "";
		String REQNBR = "";
		String REQSTS = "";
		String SQRNBR = "";
		String YURREF = "";
		String ERRTXT = "";
		String RTNFLG = "";
		String OPRALS = "";
		String OPRSQN = "";

		public String getOPRALS() {
			return OPRALS;
		}

		public void setOPRALS(String oprals) {
			OPRALS = oprals;
		}

		public String getOPRSQN() {
			return OPRSQN;
		}

		public void setOPRSQN(String oprsqn) {
			OPRSQN = oprsqn;
		}

		public String getREQNBR() {
			return REQNBR;
		}

		public void setREQNBR(String reqnbr) {
			REQNBR = reqnbr;
		}

		public String getSQRNBR() {
			return SQRNBR;
		}

		public void setSQRNBR(String sqrnbr) {
			SQRNBR = sqrnbr;
		}

		public String getERRCOD() {
			return ERRCOD;
		}

		public void setERRCOD(String errcod) {
			ERRCOD = errcod;
		}

		public String getERRTXT() {
			return ERRTXT;
		}

		public void setERRTXT(String errtxt) {
			ERRTXT = errtxt;
		}

		public String getREQSTS() {
			return REQSTS;
		}

		public void setREQSTS(String reqsts) {
			REQSTS = reqsts;
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

	}

	public XStream processXStream() {
		XStream x = new XStream();
		x.alias("CMBSDKPGK", PaymentResponse.class);
		return x;
	}

	public String getXML() {
		XStream x = processXStream();
		PaymentResponse obj = new PaymentResponse();
		obj.INFO.setDATTYP("1243");
		obj.INFO.setERRMSG("asdf");
		return x.toXML(obj);
	}

	public HashMap returnSaveMap(PaymentResponse rsp) {
		HashMap map = new HashMap();
		// map.put("", pr.getINFO().getDATTYP());
		map.put("errmsg", rsp.getINFO().getERRMSG());
		// map.put("", pr.getINFO().getFUNNAM());
		// map.put("", pr.getINFO().getLGNNAM());
		map.put("retcod", rsp.getINFO().getRETCOD());
		if (rsp.getNTQPAYRQZ() != null) {
			map.put("errcod", rsp.getNTQPAYRQZ().getERRCOD());
			map.put("errtxt", rsp.getNTQPAYRQZ().getERRTXT());
			map.put("yurref", rsp.getNTQPAYRQZ().getYURREF());
			map.put("sqrnbr", rsp.getNTQPAYRQZ().getSQRNBR());
			map.put("rtnflg", rsp.getNTQPAYRQZ().getRTNFLG());
			map.put("reqsts", rsp.getNTQPAYRQZ().getREQSTS());
			map.put("reqnbr", rsp.getNTQPAYRQZ().getREQNBR());
		}
		return map;

	}

	public static void main(String[] args) {
		PaymentResponse obj = new PaymentResponse();
		System.out.println(obj.getXML());

		XStream x = obj.processXStream();
		PaymentResponse pr = (PaymentResponse) x.fromXML(obj.getXML());
		System.out.println(pr.getINFO().getDATTYP());
	}

}
