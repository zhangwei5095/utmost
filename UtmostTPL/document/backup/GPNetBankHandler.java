package org.utmost.jco.handler;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.transaction.annotation.Transactional;
import org.utmost.bank.cmbchina.GetPaymentInfoRequest;
import org.utmost.bank.cmbchina.GetPaymentInfoRespone;
import org.utmost.bank.cmbchina.PaymentRequest;
import org.utmost.bank.cmbchina.PaymentResponse;
import org.utmost.bank.cmbchina.GetPaymentInfoRespone.NTQPAYQYZ;
import org.utmost.bank.http.HttpPost;
import org.utmost.common.SpringContext;
import org.utmost.portal.service.AutoService;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.server.JCoServerContext;
import com.sap.conn.jco.server.JCoServerFunctionHandler;

@Transactional
public class GPNetBankHandler implements JCoServerFunctionHandler {

	@Transactional(readOnly = false)
	public void handleRequest(JCoServerContext ctx, JCoFunction fun)
			throws AbapException {
		AutoService service = (AutoService) SpringContext
				.getBean("AutoService");
		try {
			System.out.println("call              : " + fun.getName());
			if (fun.getName().equals("Z_FI_NB001")) {
				// fun.getImportParameterList().getStructure("PAYMENT_INFO")
				// .getValue("PAYMENT_BANK_NAME");
				PaymentRequest pr = new PaymentRequest();

				service.exteriorSave("GP_PAYMENT_REQUEST_RECODER", pr
						.returnSaveMap(fun));// 保存到数据库
				String input = pr.processStructure(fun);
				// 输出
				JCoStructure jcoPut = fun.getExportParameterList()
						.getStructure("RETURN_INFO");
				String response = null;
				try {
					response = HttpPost.post(input);
				} catch (Exception ex) {
					jcoPut.setValue("ERRMSG", "网银服务连接失败");
					jcoPut.setValue("RETCOD", "网银服务连接失败");
					ex.printStackTrace();
					System.out.println("over");
					return;
				}
				PaymentResponse rsp = (PaymentResponse) new PaymentResponse()
						.processXStream().fromXML(response);

				System.out.println("rsp:" + rsp);
				HashMap rspMap = rsp.returnSaveMap(rsp);// 返回map
				service.save("GP_PAYMENT_REQUEST_FEEDBACK", rspMap);
				System.out.println("rspMap:" + rspMap);

				jcoPut.setValue("RETCOD", rsp.getINFO().getRETCOD());
				jcoPut.setValue("ERRMSG", rsp.getINFO().getERRMSG());
				if (rsp.getNTQPAYRQZ() != null) {
					jcoPut.setValue("SQRNBR", rsp.getNTQPAYRQZ().getSQRNBR());
					jcoPut.setValue("YURREF", rsp.getNTQPAYRQZ().getYURREF());
					jcoPut.setValue("REQNBR", rsp.getNTQPAYRQZ().getREQNBR());
					jcoPut.setValue("REQSTS", rsp.getNTQPAYRQZ().getREQSTS());
					jcoPut.setValue("RTNFLG", rsp.getNTQPAYRQZ().getRTNFLG());
					jcoPut.setValue("ERRCOD", rsp.getNTQPAYRQZ().getERRCOD());
					jcoPut.setValue("ERRTXT", rsp.getNTQPAYRQZ().getERRTXT());
					jcoPut.setValue("OPRALS", rsp.getNTQPAYRQZ().getOPRALS());
					jcoPut.setValue("OPRSQN", rsp.getNTQPAYRQZ().getOPRSQN());
				} else {
					System.out.println("Error: rsp.getNTQPAYRQZ()="
							+ rsp.getNTQPAYRQZ());
				}
			} else if (fun.getName().equals("Z_FI_NB002")) {
				GetPaymentInfoRequest obj = new GetPaymentInfoRequest();
				String input = obj.processStructure(fun);// 请求报文
				GetPaymentInfoRequest pr = new GetPaymentInfoRequest();
				service.save("GP_PAYMENT_CONFIRM_RECODER", pr
						.returnSaveMap(fun));// 保存到数据库
				JCoStructure jcoPut = fun.getExportParameterList()
						.getStructure("RETURN_INFO");
				String response = null;
				try {
					response = HttpPost.post(input);
				} catch (Exception ex) {
					jcoPut.setValue("ERRMSG", "网银服务连接失败");
					jcoPut.setValue("RETCOD", "网银服务连接失败");
					ex.printStackTrace();
					System.out.println("over");
					return;
				}

				GetPaymentInfoRespone gpir = new GetPaymentInfoRespone();
				HashMap map = gpir.processHashMap(response);
				HashMap INFO = (HashMap) map.get("INFO");

				// jcoPut.setValue("DATTYP", INFO.get("DATTYP").toString());
				jcoPut.setValue("ERRMSG", INFO.get("ERRMSG").toString());
				// jcoPut.setValue("FUNNAM", INFO.get("FUNNAM").toString());
				// jcoPut.setValue("LGNNAM", INFO.get("LGNNAM").toString());
				jcoPut.setValue("RETCOD", INFO.get("RETCOD").toString());
				ArrayList list = (ArrayList) map.get("NTQPAYQYZ_List");
				JCoTable tab = fun.getTableParameterList().getTable(
						"RETURN_TAB");

				for (int x = 0; x < list.size(); x++) {
					tab.appendRow();
					HashMap imap = (HashMap) list.get(x);
					tab.setValue("C_BUSCOD", imap.get("C_BUSCOD").toString());
					tab.setValue("BUSCOD", imap.get("BUSCOD").toString());
					// tab.setValue("C_BUSMOD",
					// imap.get("C_BUSMOD").toString());
					tab.setValue("BUSMOD", imap.get("BUSMOD").toString());
					// tab.setValue("C_DBTBBK",
					// imap.get("C_DBTBBK	").toString());
					tab.setValue("DBTBBK", imap.get("DBTBBK").toString());
					tab.setValue("DBTACC", imap.get("DBTACC").toString());
					tab.setValue("DBTNAM", imap.get("DBTNAM").toString());
					tab.setValue("C_DBTREL", imap.get("C_DBTREL").toString());
					tab.setValue("DBTBNK", imap.get("DBTBNK").toString());
					tab.setValue("DBTADR", imap.get("DBTADR").toString());
					tab.setValue("C_CRTBBK", imap.get("C_CRTBBK").toString());
					tab.setValue("CRTBBK", imap.get("CRTBBK").toString());
					tab.setValue("CRTACC", imap.get("CRTACC").toString());
					tab.setValue("CRTNAM", imap.get("CRTNAM").toString());
					tab.setValue("C_CRTREL", imap.get("C_CRTREL").toString());
					tab.setValue("CRTBNK", imap.get("CRTBNK").toString());
					tab.setValue("CRTADR", imap.get("CRTADR").toString());
					tab.setValue("C_GRPBBK", imap.get("C_GRPBBK").toString());
					tab.setValue("GRPBBK", imap.get("GRPBBK").toString());
					// tab.setValue("GRPACC", imap.get("GRPACC").toString());
					// tab.setValue("GRPNAM", imap.get("GRPNAM").toString());
					tab.setValue("C_CCYNBR", imap.get("C_CCYNBR").toString());
					tab.setValue("CCYNBR", imap.get("CCYNBR").toString());
					tab.setValue("TRSAMT", imap.get("TRSAMT").toString());
					tab.setValue("EPTDAT", imap.get("EPTDAT").toString());
					tab.setValue("EPTTIM", imap.get("EPTTIM").toString());
					tab.setValue("BNKFLG", imap.get("BNKFLG").toString());
					tab.setValue("REGFLG", imap.get("REGFLG").toString());
					tab.setValue("C_STLCHN", imap.get("C_STLCHN").toString());
					tab.setValue("STLCHN", imap.get("STLCHN").toString());
					tab.setValue("NUSAGE", imap.get("NUSAGE").toString());
					// tab.setValue("NTFCH1", imap.get("NTFCH1").toString());
					// tab.setValue("NTFCH2", imap.get("NTFCH2").toString());
					tab.setValue("OPRDAT", imap.get("OPRDAT").toString());
					tab.setValue("YURREF", imap.get("YURREF").toString());
					// tab.setValue("BUSNAR", imap.get("BUSNAR").toString());
					tab.setValue("C_REQSTS", imap.get("C_REQSTS").toString());
					tab.setValue("REQSTS", imap.get("REQSTS").toString());
					tab.setValue("C_RTNFLG", imap.get("C_RTNFLG").toString());
					tab.setValue("RTNFLG", imap.get("RTNFLG").toString());
					tab.setValue("OPRALS", imap.get("OPRALS").toString());
					// tab.setValue("RTNNAR", imap.get("RTNNAR").toString());
					// tab.setValue("RTNDAT", imap.get("RTNDAT").toString());
					tab.setValue("ATHFLG", imap.get("ATHFLG").toString());
					tab.setValue("LGNNAM", imap.get("LGNNAM").toString());
					tab.setValue("USRNAM", imap.get("USRNAM").toString());
				}
			} else {
				System.out.println("Error funname!");
			}
			// System.out.println(fun.getImportParameterList().getStructure(
			// "PAYMENT_INFO").getValue("PAYMENT_BANK_NAME"));
			// fun.getExportParameterList().getStructure("RETURN_INFO").setValue(
			// "ERRMSG", "王利民Test");
			System.out.println("success!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
