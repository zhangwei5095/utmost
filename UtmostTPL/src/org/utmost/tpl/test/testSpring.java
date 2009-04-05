package org.utmost.tpl.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.python.util.PythonInterpreter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.utmost.common.DBSupport;
import org.utmost.common.SpringContext;
import org.utmost.tpl.service.TemplateService;
import org.utmost.tpl.test.service.DBTest;

public class testSpring {

	public void runSpring() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"/conf/applicationContext.xml");
		SessionFactory sf = (SessionFactory) ctx.getBean("SessionFactory");
		// System.out.println("-->" + SpringContext.getContext());
		DBSupport d = (DBSupport) SpringContext.getBean("DBSupport");
		// System.out.println("-->" + d.getSessionFactory());

		TemplateService ts = (TemplateService) SpringContext
				.getBean("TemplateService");

		PythonInterpreter interp = new PythonInterpreter();
		interp.exec("from org.utmost.common import SpringContext");
		interp.set("sc", ctx.getBean("SpringContext"));
		interp.exec("dbs=sc.getBean('DBSupport')");
		String hql = "from U_TPL_TEMPLATE";
		interp.exec("pmap=dbs.findByHql('" + hql + "')");
		interp.exec("print pmap");
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		testSpring t = new testSpring();
		t.runSpring();
		// ts.processHbm("4889e5811dbf3832011dbf537b200002");
		// List<HashMap> list = ts.findAll();
		// for (HashMap m : list) {
		// System.out.println(m);
		// }

		// ts.processTreeForData();
		//		
		// DBTest test = (DBTest) SpringContext.getBean("DBTest");
		// test.saveDB(d);
		// Session session = sf.openSession();
		// session.beginTransaction();
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("uuid", "99999");
		// map.put("nodecode", "娃哈哈ha");
		// session.save("U_TPL_TEMPLATE", map);
		// session.getTransaction().commit();
		// session.close();
		//
		// session = sf.openSession();
		// List<Map<String, Object>> list = session.createQuery(
		// "from U_TPL_TEMPLATE").list();
		// for (Map<String, Object> m : list) {
		// System.out.println(m);
		// }
	}
}
