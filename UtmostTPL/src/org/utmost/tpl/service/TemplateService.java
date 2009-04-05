package org.utmost.tpl.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.utmost.common.CommService;
import org.utmost.common.SpringContext;

/**
 * TPL 数据配置核心类 待优化
 * 
 * @author wanglm
 * 
 */
@Service("TemplateService")
public class TemplateService extends CommService {
	private static Log logger = LogFactory.getLog(TemplateService.class);

	public List findAll() {
		return getDb().findAll("U_TPL_TEMPLATE");
	}

	public List findByUUID(String uuid) {
		return getDb().findByHql(
				"from U_TPL_TEMPLATE v where v.uuid='" + uuid + "'");
	}

	public List findByPID(String pid) {
		return getDb().findByHql(
				"from U_TPL_TEMPLATE v where v.pid='" + pid + "'");
	}

	/**
	 * 
	 * @param list
	 * @param type
	 * @return
	 */
	public String processTreeForData() {
		this.findAll();
		ProcessTree p = new ProcessTree(this.findAll(), "data");
		String xml = p.toTree();
		System.out.println(xml);
		return xml;
	}

	public String processTreeForView() {
		this.findAll();
		ProcessTree p = new ProcessTree(this.findAll(), "view");
		String xml = p.toTree();
		System.out.println(xml);
		return xml;
	}

	public void deleteByUUID(String uuid) {
		System.out.println("deleteByUUID:" + uuid);
		List<HashMap<String, Object>> list = this.findByUUID(uuid);
		for (HashMap<String, Object> map : list) {
			// 删除数据表
			getDb().deleteByHql(
					"from U_TPL_TEMPLATEDATA v where v.cid='" + uuid + "'");
			// 删除视图表
			getDb().deleteByHql(
					"from U_TPL_TEMPLATEVIEW v where v.cid='" + uuid + "'");

			// 默认
			this.deleteByPID(uuid);
			getDb().delete(map);
		}
	}

	private void deleteByPID(String pid) {
		List<HashMap<String, Object>> list = this.findByPID(pid);
		for (HashMap<String, Object> map : list) {
			Object obj = map.get("uuid");
			if (obj != null) {
				this.deleteByUUID((String) obj);
			}
			getDb().delete(map);
		}
	}

	public List findTplDataByUUID(String uuid) {
		return getDb().findByHql(
				"from U_TPL_TEMPLATEDATA v where v.cid='" + uuid + "'");
	}

	/**
	 * 生成并拷贝hbm文件
	 * 
	 * @return
	 */
	public String processAllHbm() {
		String hql = "from U_TPL_TEMPLATE v where v.nodetype='Collection'";
		List<HashMap> list = getDb().findByHql(hql);
		for (HashMap map : list) {
			String uuid = (String) map.get("uuid");
			try {
				this.processHbm(uuid);
			} catch (Exception e) {
				e.printStackTrace();
				return "fault";
			}
		}
		return "success";
	}

	/**
	 * 
	 * @param uuid
	 * @return
	 * @throws Exception
	 */
	public String processHbm(String uuid) throws Exception {
		ProcessHbm o = new ProcessHbm();
		List list = this.findTplDataByUUID(uuid);
		List tlist = this.findByUUID(uuid);
		if (tlist.size() != 0) {
			HashMap m = (HashMap) tlist.get(0);
			String tablename = (String) m.get("tablename");
			if (list.size() < 2) {
				logger.fatal(tablename + " :table data fatal");
				return null;
			}
			String xml = o.process(list, tablename.toUpperCase());
			// 写文件
			try {
				String file = new File("../temp/").getCanonicalPath() + "/";
				String filename = file + tablename + ".hbm.xml";
				System.out.println("filename:" + filename);
				// FileWriter fw = new FileWriter(filename, false);
				// fw.write(xml);
				// fw.close();

				FileOutputStream fos = new FileOutputStream(filename);
				fos.write(xml.getBytes("UTF-8"));
				fos.close();

				String system = System.getProperty("os.name");
				System.out.println("os.name:" + system);
				if (system.indexOf("Windows") >= 0) {
					// copy 文件 to classes
					this.cmdCopy(file, tablename);
				} else {
					// 处理Linux
					// this.cpCopy(file, tablename);//待实现
					throw new Exception("暂时不支持此操作系统");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return xml;
		}
		return null;
	}

	/**
	 * windows 平台下采用 cmd copy 进行文件copy
	 * 
	 * @param file
	 * @param tablename
	 * @throws IOException
	 */
	private void cmdCopy(String file, String tablename) throws IOException {
		String copyfrom = file;
		copyfrom = copyfrom.replaceAll("/", "\\\\");
		copyfrom = copyfrom.replaceAll("\\\\", "\"\\\\\"");
		copyfrom = copyfrom.replaceFirst("\"", "");
		copyfrom = copyfrom.substring(0, copyfrom.length() - 1);
		copyfrom = copyfrom + tablename + ".hbm.xml";

		String copyto = TemplateService.class.getResource("/").toString();
		copyto = copyto.replaceAll("file:/", "");
		copyto = copyto.replaceAll("/", "\"\\\\\"");
		copyto = copyto.replaceAll("%20", " ");
		copyto = copyto.replaceFirst("\"", "");
		copyto = copyto.substring(0, copyto.length() - 1);
		copyto = copyto + "org\\utmost\\hbm\\";

		Runtime rt = Runtime.getRuntime();
		String cmd = "cmd /c copy /y " + copyfrom + " " + copyto;
		System.out.println(cmd);
		rt.exec(cmd);
	}

	/**
	 * linux unix 系统下进行文件拷贝
	 * 
	 * @param file
	 * @param tablename
	 * @throws IOException
	 */
	private void cpCopy(String file, String tablename) throws IOException {

	}
}
