package edu.ndnu.capstone.domain;

import java.util.ArrayList;
import java.util.List;

public class LocationState {
	
	private String name;
	private String value;
	
	public LocationState(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
	
	public static List<LocationState> findState()
	{
		List<LocationState> list = new ArrayList<LocationState>();
		
		list.add(new LocationState("AL", "AL"));
		list.add(new LocationState("AK", "AK"));
		list.add(new LocationState("AZ", "AZ"));
		list.add(new LocationState("AR", "AR"));
		list.add(new LocationState("CA", "CA"));
		list.add(new LocationState("CO", "CO"));
		list.add(new LocationState("CT", "CT"));
		list.add(new LocationState("DE", "DE"));
		list.add(new LocationState("FL", "FL"));
		list.add(new LocationState("GA", "GA"));
		list.add(new LocationState("HI", "HI"));
		list.add(new LocationState("ID", "ID"));
		list.add(new LocationState("IL", "IL"));
		list.add(new LocationState("IN", "IN"));
		list.add(new LocationState("IA", "IA"));
		list.add(new LocationState("KS", "KS"));
		list.add(new LocationState("KY", "KY"));
		list.add(new LocationState("LA", "LA"));
		list.add(new LocationState("ME", "ME"));
		list.add(new LocationState("MD", "MD"));
		list.add(new LocationState("MA", "MA"));
		list.add(new LocationState("MI", "MI"));
		list.add(new LocationState("MN", "MN"));
		list.add(new LocationState("MS", "MS"));
		list.add(new LocationState("MO", "MO"));
		list.add(new LocationState("MT", "MT"));
		list.add(new LocationState("NE", "NE"));
		list.add(new LocationState("NV", "NV"));
		list.add(new LocationState("NH", "NH"));
		list.add(new LocationState("NJ", "NJ"));
		list.add(new LocationState("NM", "NM"));
		list.add(new LocationState("NY", "NY"));
		list.add(new LocationState("NC", "NC"));
		list.add(new LocationState("ND", "ND"));
		list.add(new LocationState("OH", "OH"));
		list.add(new LocationState("OK", "OK"));
		list.add(new LocationState("OR", "OR"));
		list.add(new LocationState("PA", "PA"));
		list.add(new LocationState("RI", "RI"));
		list.add(new LocationState("SC", "SC"));
		list.add(new LocationState("SD", "SD"));
		list.add(new LocationState("TN", "TN"));
		list.add(new LocationState("TX", "TX"));
		list.add(new LocationState("UT", "UT"));
		list.add(new LocationState("VT", "VT"));
		list.add(new LocationState("VA", "VA"));
		list.add(new LocationState("WA", "WA"));
		list.add(new LocationState("WV", "WV"));
		list.add(new LocationState("WI", "WI"));
		list.add(new LocationState("WY", "WY"));
	
		return list;
	}
}
