package org.utmost.tpl.test;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

public class EmbedPython {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PythonInterpreter interp = new PythonInterpreter();
		// System.out.println("Hello, brave new world");
		// interp.exec("import sys");
		// interp.exec("print sys");
		//String s="s";
		//interp.set("a", s);
		interp.exec("from java.util import Random");
		interp.exec("r = Random()");
		interp.exec("print r");
		// System.out.println("Goodbye, cruel world");
		// interp.set("a", "abcd");
		// interp.execfile(EmbedPython.class.getResourceAsStream("a.py"));
		// interp.execfile("");
		// System.out.println(interp.get("a"));

		// interp.exec("x = 2+2");
		// PyObject x = interp.get("x");
		// System.out.println("x:" + x);

		// Map map = new HashMap();
		// map.put("1", Long.parseLong("22"));
		// System.out.println(map.get("1").getClass());
	}

}
