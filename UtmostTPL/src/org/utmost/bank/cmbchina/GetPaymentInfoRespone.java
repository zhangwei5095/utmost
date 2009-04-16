package org.utmost.bank.cmbchina;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.utmost.util.StringUtil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

;

public class GetPaymentInfoRespone {
	INFO INFO = new INFO();

	public INFO getINFO() {
		return INFO;
	}

	public void setINFO(INFO info) {
		INFO = info;
	}

	NTQPAYQYZ NTQPAYQYZ = null;

	ArrayList<NTQPAYQYZ> list = new ArrayList();

	public ArrayList<NTQPAYQYZ> getList() {
		return list;
	}

	public void setList(ArrayList<NTQPAYQYZ> list) {
		this.list = list;
	}

	public NTQPAYQYZ getNTQPAYQYZ() {
		return NTQPAYQYZ;
	}

	public void setNTQPAYQYZ(NTQPAYQYZ sdk) {
		NTQPAYQYZ = sdk;
	}

	public static class INFO {
		String FUNNAM = "";
		String DATTYP = "";
		String LGNNAM = "";
		String ERRMSG = "";
		String RETCOD = "";

		public String getERRMSG() {
			return ERRMSG;
		}

		public void setERRMSG(String errmsg) {
			ERRMSG = errmsg;
		}

		public String getRETCOD() {
			return RETCOD;
		}

		public void setRETCOD(String retcod) {
			RETCOD = retcod;
		}

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

	public static class NTQPAYQYZ {
		String ATHFLG = null;
		String BNKFLG = null;
		String BUSCOD = null;
		String BUSMOD = null;
		String CCYNBR = null;
		String CRTACC = null;
		String CRTADR = null;
		String CRTBBK = null;
		String CRTBNK = null;
		String CRTNAM = null;
		String CRTREL = null;
		String C_BUSCOD = null;
		String C_CCYNBR = null;
		String C_CRTBBK = null;
		String C_CRTREL = null;
		String C_DBTBBK = null;
		String C_DBTREL = null;
		String C_GRPBBK = null;
		String C_REQSTS = null;
		String C_RTNFLG = null;
		String C_STLCHN = null;
		String DBTACC = null;
		String DBTADR = null;
		String DBTBBK = null;
		String DBTBNK = null;
		String DBTNAM = null;
		String DBTREL = null;
		String EPTDAT = null;
		String EPTTIM = null;
		String GRPBBK = null;
		String LGNNAM = null;
		String NUSAGE = null;
		String OPRALS = null;
		String OPRDAT = null;
		String OPRSQN = null;
		String REGFLG = null;
		String REQNBR = null;
		String REQSTS = null;
		String RTNFLG = null;
		String STLCHN = null;
		String TRSAMT = null;
		String USRNAM = null;
		String YURREF = null;

		public String getATHFLG() {
			return ATHFLG;
		}

		public void setATHFLG(String athflg) {
			ATHFLG = athflg;
		}

		public String getBNKFLG() {
			return BNKFLG;
		}

		public void setBNKFLG(String bnkflg) {
			BNKFLG = bnkflg;
		}

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

		public String getCCYNBR() {
			return CCYNBR;
		}

		public void setCCYNBR(String ccynbr) {
			CCYNBR = ccynbr;
		}

		public String getCRTACC() {
			return CRTACC;
		}

		public void setCRTACC(String crtacc) {
			CRTACC = crtacc;
		}

		public String getCRTADR() {
			return CRTADR;
		}

		public void setCRTADR(String crtadr) {
			CRTADR = crtadr;
		}

		public String getCRTBBK() {
			return CRTBBK;
		}

		public void setCRTBBK(String crtbbk) {
			CRTBBK = crtbbk;
		}

		public String getCRTBNK() {
			return CRTBNK;
		}

		public void setCRTBNK(String crtbnk) {
			CRTBNK = crtbnk;
		}

		public String getCRTNAM() {
			return CRTNAM;
		}

		public void setCRTNAM(String crtnam) {
			CRTNAM = crtnam;
		}

		public String getCRTREL() {
			return CRTREL;
		}

		public void setCRTREL(String crtrel) {
			CRTREL = crtrel;
		}

		public String getC_BUSCOD() {
			return C_BUSCOD;
		}

		public void setC_BUSCOD(String c_buscod) {
			C_BUSCOD = c_buscod;
		}

		public String getC_CCYNBR() {
			return C_CCYNBR;
		}

		public void setC_CCYNBR(String c_ccynbr) {
			C_CCYNBR = c_ccynbr;
		}

		public String getC_CRTBBK() {
			return C_CRTBBK;
		}

		public void setC_CRTBBK(String c_crtbbk) {
			C_CRTBBK = c_crtbbk;
		}

		public String getC_CRTREL() {
			return C_CRTREL;
		}

		public void setC_CRTREL(String c_crtrel) {
			C_CRTREL = c_crtrel;
		}

		public String getC_DBTBBK() {
			return C_DBTBBK;
		}

		public void setC_DBTBBK(String c_dbtbbk) {
			C_DBTBBK = c_dbtbbk;
		}

		public String getC_DBTREL() {
			return C_DBTREL;
		}

		public void setC_DBTREL(String c_dbtrel) {
			C_DBTREL = c_dbtrel;
		}

		public String getC_GRPBBK() {
			return C_GRPBBK;
		}

		public void setC_GRPBBK(String c_grpbbk) {
			C_GRPBBK = c_grpbbk;
		}

		public String getC_REQSTS() {
			return C_REQSTS;
		}

		public void setC_REQSTS(String c_reqsts) {
			C_REQSTS = c_reqsts;
		}

		public String getC_RTNFLG() {
			return C_RTNFLG;
		}

		public void setC_RTNFLG(String c_rtnflg) {
			C_RTNFLG = c_rtnflg;
		}

		public String getC_STLCHN() {
			return C_STLCHN;
		}

		public void setC_STLCHN(String c_stlchn) {
			C_STLCHN = c_stlchn;
		}

		public String getDBTACC() {
			return DBTACC;
		}

		public void setDBTACC(String dbtacc) {
			DBTACC = dbtacc;
		}

		public String getDBTADR() {
			return DBTADR;
		}

		public void setDBTADR(String dbtadr) {
			DBTADR = dbtadr;
		}

		public String getDBTBBK() {
			return DBTBBK;
		}

		public void setDBTBBK(String dbtbbk) {
			DBTBBK = dbtbbk;
		}

		public String getDBTBNK() {
			return DBTBNK;
		}

		public void setDBTBNK(String dbtbnk) {
			DBTBNK = dbtbnk;
		}

		public String getDBTNAM() {
			return DBTNAM;
		}

		public void setDBTNAM(String dbtnam) {
			DBTNAM = dbtnam;
		}

		public String getDBTREL() {
			return DBTREL;
		}

		public void setDBTREL(String dbtrel) {
			DBTREL = dbtrel;
		}

		public String getEPTDAT() {
			return EPTDAT;
		}

		public void setEPTDAT(String eptdat) {
			EPTDAT = eptdat;
		}

		public String getEPTTIM() {
			return EPTTIM;
		}

		public void setEPTTIM(String epttim) {
			EPTTIM = epttim;
		}

		public String getGRPBBK() {
			return GRPBBK;
		}

		public void setGRPBBK(String grpbbk) {
			GRPBBK = grpbbk;
		}

		public String getLGNNAM() {
			return LGNNAM;
		}

		public void setLGNNAM(String lgnnam) {
			LGNNAM = lgnnam;
		}

		public String getNUSAGE() {
			return NUSAGE;
		}

		public void setNUSAGE(String nusage) {
			NUSAGE = nusage;
		}

		public String getOPRALS() {
			return OPRALS;
		}

		public void setOPRALS(String oprals) {
			OPRALS = oprals;
		}

		public String getOPRDAT() {
			return OPRDAT;
		}

		public void setOPRDAT(String oprdat) {
			OPRDAT = oprdat;
		}

		public String getOPRSQN() {
			return OPRSQN;
		}

		public void setOPRSQN(String oprsqn) {
			OPRSQN = oprsqn;
		}

		public String getREGFLG() {
			return REGFLG;
		}

		public void setREGFLG(String regflg) {
			REGFLG = regflg;
		}

		public String getREQNBR() {
			return REQNBR;
		}

		public void setREQNBR(String reqnbr) {
			REQNBR = reqnbr;
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

		public String getSTLCHN() {
			return STLCHN;
		}

		public void setSTLCHN(String stlchn) {
			STLCHN = stlchn;
		}

		public String getTRSAMT() {
			return TRSAMT;
		}

		public void setTRSAMT(String trsamt) {
			TRSAMT = trsamt;
		}

		public String getUSRNAM() {
			return USRNAM;
		}

		public void setUSRNAM(String usrnam) {
			USRNAM = usrnam;
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
		x.alias("CMBSDKPGK", GetPaymentInfoRespone.class);
		x.alias("NTQPAYQYZ", GetPaymentInfoRespone.NTQPAYQYZ.class);

		// x.addImplicitCollection(GetPaymentInfoRespone.class, "list",
		// java.util.AbstractList.class);

		x.addImplicitCollection(GetPaymentInfoRespone.class, "list",
				java.util.ArrayList.class);
		// x.addImplicitCollection(GetPaymentInfoRespone.NTQPAYQYZ.class,
		// "NTQPAYQYZ",
		// java.util.ArrayList.class);
		return x;
	}

	public HashMap processHashMap(String response) throws DocumentException,
			UnsupportedEncodingException {
		HashMap map = new HashMap();
		// InputStream is = StringUtil.parseInputStream(response);
		byte[] bytes = response.getBytes();
		InputStream in = new ByteArrayInputStream(bytes);
		InputStreamReader is = new InputStreamReader(in, "GBK");
		SAXReader reader = new SAXReader();
		Document doc = reader.read(is);
		List childNodes = doc.selectNodes("//CMBSDKPGK/INFO/*");
		System.out.println("childNodes:" + childNodes.size());
		HashMap info = new HashMap();
		for (Object obj : childNodes) {
			Node childNode = (Node) obj;
			info.put(childNode.getName(), childNode.getText());
			System.out.println(childNode.getName() + "-" + childNode.getText());
		}
		map.put("INFO", info);
		System.out.println("INFO---------------------->");
		childNodes = doc.selectNodes("//CMBSDKPGK/NTQPAYQYZ");
		System.out.println("childNodes:" + childNodes.size());
		ArrayList childList = new ArrayList();
		for (Object obj : childNodes) {
			Node childNode = (Node) obj;
			HashMap nodemap = new HashMap();
			for (Object o : childNode.selectNodes("*")) {
				Node ichildNode = (Node) o;
				System.out.println(ichildNode.getName() + "-"
						+ ichildNode.getText());
				nodemap.put(ichildNode.getName(), ichildNode.getText());
			}
			childList.add(nodemap);
			System.out.println("NTQPAYQYZ---------------------->");
		}
		map.put("NTQPAYQYZ_List", childList);
		return map;
	}

	public static void main(String[] args) {
		String x = "<CMBSDKPGK><INFO><FUNNAM></FUNNAM><DATTYP></DATTYP><LGNNAM></LGNNAM><ERRMSG></ERRMSG><RETCOD></RETCOD></INFO><NTQPAYQYZ><ATHFLG>asdf</ATHFLG><C_CCYNBR>asf</C_CCYNBR></NTQPAYQYZ><NTQPAYQYZ><ATHFLG>asdf</ATHFLG><C_CCYNBR>asf</C_CCYNBR></NTQPAYQYZ></CMBSDKPGK>";
		GetPaymentInfoRespone o = new GetPaymentInfoRespone();
		o.processXStream().fromXML(x);
		// GetPaymentInfoRespone.NTQPAYQYZ sdk = new
		// GetPaymentInfoRespone.NTQPAYQYZ();
		// sdk.setATHFLG("asdf");
		// sdk.setC_CCYNBR("asf");
		// o.getList().add(sdk);
		// sdk = new GetPaymentInfoRespone.NTQPAYQYZ();
		// sdk.setATHFLG("asdf");
		// sdk.setC_CCYNBR("asf");
		// o.getList().add(sdk);
		// System.out.println(o.processXStream().toXML(o));
	}
}
