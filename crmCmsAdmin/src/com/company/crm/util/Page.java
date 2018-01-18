package com.company.crm.util;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Page<T> {

	private List<T> rows;

	private int page;
	private int max;
	private int total;
	public Page(){}
	public Page(List<T> rows, int page, int max, int total) {
		super();
		this.rows = rows;
		this.page = page;
		this.max = max;
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	

}