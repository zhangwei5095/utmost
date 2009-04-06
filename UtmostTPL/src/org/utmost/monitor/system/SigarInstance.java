package org.utmost.monitor.system;

import org.hyperic.sigar.Sigar;

public class SigarInstance {

	private Sigar sigar = null;

	public Sigar getInstance() {
		if (sigar == null)
			sigar = new Sigar();
		return sigar;
	}

	public static void main(String[] args) {
		// SigarFactory.getInstance();
	}

}
