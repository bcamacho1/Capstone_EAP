package edu.ndnu.capstone.domain;

import java.util.ArrayList;
import java.util.List;

public class UserActiveType {

	private String name;
	private Integer value;
	public UserActiveType(String name, Integer value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	public static List<UserActiveType> findTypes()
	{
		List<UserActiveType> list=new ArrayList<UserActiveType>();
		
		list.add(new  UserActiveType("active", 1));
		list.add(new  UserActiveType("not active", 0));
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
