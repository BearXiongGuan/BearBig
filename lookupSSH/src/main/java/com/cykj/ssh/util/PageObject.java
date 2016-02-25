package com.cykj.ssh.util;

import java.io.Serializable;
import java.util.List;

public class PageObject<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer size = 5;
	
	private Integer page = 1;
	
	private Integer count;
	
	private Integer sum;
	
	private List<T> resultList;

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		if(sum%size == 0){
			count = sum/size;
		}else{
			count = sum/size+1;
		}
		this.count = count;
	}

	public Integer getSum() {
		return sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	
}
