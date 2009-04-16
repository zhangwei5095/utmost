package org.utmost.bank.cmbchina;

import java.util.HashMap;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.thoughtworks.xstream.XStream;

public class PaymentRequest {
	INFO INFO = null;

	public INFO getINFO() {
		return new INFO();
	}

	public void setINFO(INFO info) {
		INFO = info;
	}

	public SDKPAYRQX getSDKPAYRQX() {
		return new SDKPAYRQX();
	}

	public void setSDKPAYRQX(SDKPAYRQX sdkpayrqx) {
		SDKPAYRQX = sdkpayrqx;
	}

	public SDKPAYDTX getSDKPAYDTX() {
		return new SDKPAYDTX();
	}

	public void setSDKPAYDTX(SDKPAYDTX sdkpaydtx) {
		SDKPAYDTX = sdkpaydtx;
	}

	SDKPAYRQX SDKPAYRQX = null;
	SDKPAYDTX SDKPAYDTX = null;

	public static class INFO {
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

	public static class SDKPAYRQX {
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

	static class SDKPAYDTX {

		public String getYURREF() {
			return YURREF;
		}

		public void setYURREF(String yurref) {
			YURREF = yurref;
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

		public String getDBTACC() {
			return DBTACC;
		}

		public void setDBTACC(String dbtacc) {
			DBTACC = dbtacc;
		}

		// public String getDBTBBK() {
		// return DBTBBK;
		// }
		//
		// public void setDBTBBK(String dbtbbk) {
		// DBTBBK = dbtbbk;
		// }

		public String getTRSAMT() {
			return TRSAMT;
		}

		public void setTRSAMT(String trsamt) {
			TRSAMT = trsamt;
		}

		public String getC_CCYNBR() {
			return C_CCYNBR;
		}

		public void setC_CCYNBR(String c_ccynbr) {
			C_CCYNBR = c_ccynbr;
		}

		public String getC_STLCHN() {
			return C_STLCHN;
		}

		public void setC_STLCHN(String c_stlchn) {
			C_STLCHN = c_stlchn;
		}

		public String getNUSAGE() {
			return NUSAGE;
		}

		public void setNUSAGE(String nusage) {
			NUSAGE = nusage;
		}

		public String getCRTACC() {
			return CRTACC;
		}

		public void setCRTACC(String crtacc) {
			CRTACC = crtacc;
		}

		public String getCRTBBK() {
			return CRTBBK;
		}

		public void setCRTBBK(String crtbbk) {
			CRTBBK = crtbbk;
		}

		String YURREF = "";
		String EPTDAT = "";
		String EPTTIM = "";
		String DBTACC = "";
		String C_DBTBBK = null;
		String TRSAMT = "";
		String C_CCYNBR = "";
		String C_STLCHN = "";
		String NUSAGE = "";
		String CRTACC = "";
		String CRTNAM = "";
		String CRTBNK = "";
		//		
		// String DBTBBK = "";

		String CRTPVC = "";
		String CRTCTY = "";
		String CRTSQN = "";

		String CRTBBK = null;

		public String getC_DBTBBK() {
			return C_DBTBBK;
		}

		public void setC_DBTBBK(String c_dbtbbk) {
			C_DBTBBK = c_dbtbbk;
		}

		public String getCRTNAM() {
			return CRTNAM;
		}

		public void setCRTNAM(String crtnam) {
			CRTNAM = crtnam;
		}

		public String getCRTBNK() {
			return CRTBNK;
		}

		public void setCRTBNK(String crtbnk) {
			CRTBNK = crtbnk;
		}

		public String getCRTPVC() {
			return CRTPVC;
		}

		public void setCRTPVC(String crtpvc) {
			CRTPVC = crtpvc;
		}

		public String getCRTCTY() {
			return CRTCTY;
		}

		public void setCRTCTY(String crtcty) {
			CRTCTY = crtcty;
		}

		public String getCRTSQN() {
			return CRTSQN;
		}

		public void setCRTSQN(String crtsqn) {
			CRTSQN = crtsqn;
		}

	}

	public XStream processXStream() {
		XStream x = new XStream();
		x.alias("CMBSDKPGK", PaymentRequest.class);
		return x;
	}

	public static String getXML() {
		XStream x = new XStream();
		PaymentRequest pr = new PaymentRequest();
		x.alias("CMBSDKPGK", PaymentRequest.class);
		PaymentRequest.INFO info = pr.getINFO();
		//
		info.setFUNNAM("Payment");
		info.setDATTYP("2");
		info.setLGNNAM("USRA01");
		pr.setINFO(info);
		PaymentRequest.SDKPAYRQX sdkpayrqx = pr.getSDKPAYRQX();
		//
		sdkpayrqx.setBUSCOD("N02031");
		sdkpayrqx.setBUSMOD("00001");
		pr.setSDKPAYRQX(sdkpayrqx);
		PaymentRequest.SDKPAYDTX sdkpaydtx = pr.getSDKPAYDTX();
		//
		sdkpaydtx.setYURREF("ADF060928001255"); // 业务参考号
		sdkpaydtx.setEPTDAT("20090409");
		sdkpaydtx.setEPTTIM("");
		sdkpaydtx.setDBTACC("571905400810812");// 付方
		sdkpaydtx.setC_DBTBBK("杭州");
		// sdkpaydtx.setDBTACC("1280022310002");
		// sdkpaydtx.setDBTBBK("75");
		sdkpaydtx.setTRSAMT("1.01");
		sdkpaydtx.setC_CCYNBR("人民币");
		sdkpaydtx.setC_STLCHN("普通");
		sdkpaydtx.setNUSAGE("测试");
		sdkpaydtx.setCRTACC("571905400910516");// 收方
		// sdkpaydtx.setCRTBBK("75");
		sdkpaydtx.setCRTNAM("NEXT TEST");
		sdkpaydtx.setCRTBNK("招商银行");
		sdkpaydtx.setCRTPVC("浙江省");
		sdkpaydtx.setCRTCTY("杭州市");
		sdkpaydtx.setCRTSQN("APP060928001255");
		pr.setSDKPAYDTX(sdkpaydtx);

		String res = x.toXML(pr);
		return res;
	}

	public String processStructure(JCoFunction fun) {
		JCoStructure sru = fun.getImportParameterList().getStructure(
				"PAYMENT_INFO");
		PaymentRequest pr = new PaymentRequest();
		PaymentRequest.INFO info = pr.getINFO();
		info.setFUNNAM("Payment");
		info.setDATTYP("2");
		info.setLGNNAM("USRA01");// 正式系统需修改
		pr.setINFO(info);
		PaymentRequest.SDKPAYRQX sdkpayrqx = pr.getSDKPAYRQX();
		sdkpayrqx.setBUSCOD(sru.getValue("BUSCOD").toString());
		sdkpayrqx.setBUSMOD("00001");
		pr.setSDKPAYRQX(sdkpayrqx);
		PaymentRequest.SDKPAYDTX sdkpaydtx = pr.getSDKPAYDTX();
		sdkpaydtx.setYURREF(sru.getValue("YURREF").toString()); // 业务参考号
		sdkpaydtx.setEPTDAT(sru.getString("EPTDAT"));
		sdkpaydtx.setEPTTIM(sru.getValue("EPTTIM").toString());
		sdkpaydtx.setDBTACC(sru.getValue("DBTACC").toString());// 付方
		sdkpaydtx.setC_DBTBBK(sru.getValue("C_DBTBBK").toString());
		sdkpaydtx.setTRSAMT(sru.getValue("TRSAMT").toString());
		sdkpaydtx.setC_CCYNBR(sru.getValue("C_CCYNBR").toString());
		sdkpaydtx.setC_STLCHN(sru.getValue("C_STLCHN").toString());
		sdkpaydtx.setNUSAGE(sru.getValue("NUSAGE").toString());
		sdkpaydtx.setCRTACC(sru.getValue("CRTACC").toString());// 收方
		sdkpaydtx.setCRTNAM(sru.getValue("CRTNAM").toString());
		sdkpaydtx.setCRTBNK(sru.getValue("CRTBNK").toString());
		sdkpaydtx.setCRTPVC(sru.getValue("CRTPVC").toString());
		sdkpaydtx.setCRTCTY(sru.getValue("CRTCTY").toString());
		sdkpaydtx.setCRTSQN(sru.getValue("CRTSQN").toString());
		pr.setSDKPAYDTX(sdkpaydtx);
		return this.processXStream().toXML(pr);
	}

	public HashMap returnSaveMap(JCoFunction fun) {
		JCoStructure sru = fun.getImportParameterList().getStructure(
				"PAYMENT_INFO");
		HashMap map = new HashMap();
		map.put("payment_date", org.utmost.util.DateUtil.getNowDate());// 写死
		map.put("payment_cmp_name", sru.getString("PAYMENT_CMP_NAME"));
		map.put("payment_cmp_id", sru.getString("PAYMENT_CMP_ID"));
		map.put("payment_bank_name", sru.getString("PAYMENT_BANK_NAME"));
		map.put("dbtacc", sru.getString("DBTACC"));
		map.put("eptdat", sru.getString("EPTDAT"));
		map.put("epttim", sru.getString("EPTTIM"));
		map.put("buscod", "N2030");// 写死
		map.put("trsamt", sru.getString("TRSAMT"));
		map.put("c_ccynbr", sru.getString("C_CCYNBR"));
		map.put("c_stlchn", sru.getString("C_STLCHN"));
		map.put("c_dbtbbk", sru.getString("C_DBTBBK"));
		map.put("nusage", sru.getString("NUSAGE"));
		map.put("recive_cmp_id", sru.getString("RECIVE_CMP_ID"));
		map.put("recive_cmp_type", sru.getString("RECIVE_CMP_TYPE"));
		map.put("crtbnk", sru.getString("CRTBNK"));
		map.put("brdnbr", sru.getString("BRDNBR"));
		map.put("crtacc", sru.getString("CRTACC"));
		map.put("crtnam", sru.getString("CRTNAM"));
		map.put("crtpvc", sru.getString("CRTPVC"));
		map.put("crtcty", sru.getString("CRTCTY"));
		map.put("yurref", sru.getString("YURREF"));
		map.put("crtsqn", sru.getString("CRTSQN"));
		map.put("operater_id", sru.getString("OPERATOR_ID"));
		map.put("operater_name", sru.getString("OPERATOR_NAME"));

		map.put("operate_type", "支付申请");
		map.put("opertter_flag", "");//
		map.put("send_date", "");//
		map.put("recive_date", "");//
		map.put("state", "01");//

		System.out.println("-------------->CRTSQN" + sru.getString("CRTSQN"));
		return map;
		// AutoService service = (AutoService) SpringContext
		// .getBean("AutoService");
	}

	public static void main(String[] args) {
		System.out.println(PaymentRequest.getXML());
	}

}
