package com.cykj.ssh.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cykj.ssh.dao.impl.TestImpl;
import com.cykj.ssh.entity.Customer;
import com.cykj.ssh.service.BaseService;
import com.cykj.ssh.util.PageObject;

@Controller
@Scope("prototype")
/*@Namespace("/user")
@ParentPackage("struts-default")*/
public class UserController extends BaseController {

	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	BaseService<Customer> baseService;
	@Autowired
	TestImpl test;
	
	//@Action(value="loginAction",results={@Result(name="success",location="/index.jsp"),@Result(name="error",location="/error.jsp")})
	public String login(){
		try {
			//ApplicationContext context = new ClassPathXmlApplicationContext("config/applicationContext.xml");
			//baseService = (BaseService<Customer>)context.getBean("baseService");
			log.info("hello i am a boy");
			test.print();
			List<Customer> customers = baseService.findListBySql("select * from customer");
			List<Customer> customersPage = baseService.findPageListByHql("from Customer", new PageObject<Customer>());
			Customer c = new Customer();
			c.setName("¶«·½²»°Ü");
			c.setBirthday(new Date());
			c.setHeight(175.0);
			c.setSex(1);
			c.setWeight(65.8);
			baseService.save(c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		return "success";
	}	
}
