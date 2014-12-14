package edu.ndnu.capstone.domain;

import java.util.ArrayList;
import java.util.List;

public class EmergencyTypeCategory {

    private String name;
    private Integer value;
    public EmergencyTypeCategory(String name, Integer value) {
        super();
        this.name = name;
        this.value = value;
    }

    public static List<EmergencyTypeCategory> findTypes()
    {
        List<EmergencyTypeCategory> list=new ArrayList<EmergencyTypeCategory>();

        list.add(new  EmergencyTypeCategory("1", 1));
        list.add(new  EmergencyTypeCategory("2", 2));
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
