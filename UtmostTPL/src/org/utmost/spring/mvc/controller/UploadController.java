package org.utmost.spring.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class UploadController extends MultiActionController {
	public void deployJbpm3(HttpServletRequest rq, HttpServletResponse rp) {
		System.out.println("rq-->" + rq);
	}

	public void upload(HttpServletRequest rq, HttpServletResponse rp) {
		System.out.println("upload rq-->" + rq);
	}
}
