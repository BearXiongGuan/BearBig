package com.cykj.ssh.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cykj.ssh.entity.Customer;
import com.cykj.ssh.service.BaseService;


public class Test {
/*	@Resource(name="baseService")
	public static BaseService b;*/
	public static void main(String args[]){
		ApplicationContext context = new ClassPathXmlApplicationContext("config/applicationContext.xml");
		BaseService<Customer> b = (BaseService<Customer>)context.getBean("baseService");
		List<Customer> cs = b.findListByHql("from Customer");
		for(int i=0;i<cs.size();i++)
			System.out.println(cs.get(i).getName());
		}
}
