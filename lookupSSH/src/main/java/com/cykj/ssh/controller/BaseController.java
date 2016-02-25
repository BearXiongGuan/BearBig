package com.cykj.ssh.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class BaseController extends ActionSupport{

	private static final long serialVersionUID = 1L;

	public PrintWriter getPrintWriter() throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		return response.getWriter();
	}
	
	public void sendMsg(String msg){
		try {
			PrintWriter print = getPrintWriter();
			print.print(msg);
			print.close();
			print = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
