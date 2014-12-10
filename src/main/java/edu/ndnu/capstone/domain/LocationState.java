package edu.ndnu.capstone.domain;

import java.util.ArrayList;
import java.util.List;

public class LocationState {
	
	private String name;
	private Integer value;
	
	public LocationState(String name, Integer value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getValue() { return value; }
    public void setValue(Integer value) { this.value = value; }
	
	public static List<LocationState> findState()
	{
		List<LocationState> list = new ArrayList<LocationState>();
		
		list.add(new LocationState("AL", 1));
		list.add(new LocationState("AK", 2));
		list.add(new LocationState("AZ", 3));
		list.add(new LocationState("AR", 4));
		list.add(new LocationState("CA", 5));
		list.add(new LocationState("CO", 6));
		list.add(new LocationState("CT", 7));
		list.add(new LocationState("DE", 8));
		list.add(new LocationState("FL", 9));
		list.add(new LocationState("GA", 10));
		list.add(new LocationState("HI", 11));
		list.add(new LocationState("ID", 12));
		list.add(new LocationState("IL", 13));
		list.add(new LocationState("IN", 14));
		list.add(new LocationState("IA", 15));
		list.add(new LocationState("KS", 16));
		list.add(new LocationState("KY", 17));
		list.add(new LocationState("LA", 18));
		list.add(new LocationState("ME", 19));
		list.add(new LocationState("MD", 20));
		list.add(new LocationState("MA", 21));
		list.add(new LocationState("MI", 22));
		list.add(new LocationState("MN", 23));
		list.add(new LocationState("MS", 24));
		list.add(new LocationState("MO", 25));
		list.add(new LocationState("MT", 26));
		list.add(new LocationState("NE", 27));
		list.add(new LocationState("NV", 28));
		list.add(new LocationState("NH", 29));
		list.add(new LocationState("NJ", 30));
		list.add(new LocationState("NM", 31));
		list.add(new LocationState("NY", 32));
		list.add(new LocationState("NC", 33));
		list.add(new LocationState("ND", 34));
		list.add(new LocationState("OH", 35));
		list.add(new LocationState("OK", 36));
		list.add(new LocationState("OR", 37));
		list.add(new LocationState("PA", 38));
		list.add(new LocationState("RI", 39));
		list.add(new LocationState("SC", 40));
		list.add(new LocationState("SD", 41));
		list.add(new LocationState("TN", 42));
		list.add(new LocationState("TX", 43));
		list.add(new LocationState("UT", 44));
		list.add(new LocationState("VT", 45));
		list.add(new LocationState("VA", 46));
		list.add(new LocationState("WA", 47));
		list.add(new LocationState("WV", 48));
		list.add(new LocationState("WI", 49));
		list.add(new LocationState("WY", 50));
	
		return list;
	}
}
