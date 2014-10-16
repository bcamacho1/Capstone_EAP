package edu.ndnu.capstone.domain;

import java.util.ArrayList;
import java.util.List;

public class UserrActiveType {

	private String name;
	private Integer value;
	public UserrActiveType(String name, Integer value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	public static List<UserrActiveType> findTypes()
	{
		List<UserrActiveType> list=new ArrayList<UserrActiveType>();
		
		list.add(new  UserrActiveType("active", 1));
		list.add(new  UserrActiveType("not active", 0));
		return list;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	
}
