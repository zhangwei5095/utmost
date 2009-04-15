package org.utmost.plugins.scripts;

import org.python.util.PythonInterpreter;
import org.utmost.common.SpringContext;
import org.utmost.util.PathUtil;

public class PythonPlugin {

	private static PythonInterpreter python = null;

	public void setPythonPath() {
		String url = PathUtil.getRootPath();
		if (url != null) {
			System.setProperty("python.home", url);
			System.out.println("is web!!");
		} else {
			System.out.println("not web!!");
			System.setProperty("python.home", PathUtil.getClassFilePath());
		}
	}

	public static PythonInterpreter getPython() {
		if (SpringContext.getContext() != null
				&& SpringContext.getBean("PythonInterpreter") != null) {
			return (PythonInterpreter) SpringContext
					.getBean("PythonInterpreter");
		}
		return new PythonInterpreter();
	}

	public void setPython(PythonInterpreter python) {
		this.python = python;
	}

	public static void main(String[] args) {

	}

}
