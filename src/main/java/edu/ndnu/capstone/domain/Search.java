package edu.ndnu.capstone.domain;

import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Email;

public class Search
{
    private Search searchId;
    private UserType typeId;
    private Integer active;
    
    @Pattern(regexp = "[a-zA-Z\\.,]+", message="Name must contain valid characters")
    private String name;
    
    @Email
    private String email;

    public Search getSearchId() {
        return searchId;
    }

    public void setSearchId(Search searchId) {
        this.searchId = searchId;
    }
    
    public UserType getTypeId() {
        return typeId;
    }

    public void setTypeId(UserType typeId) {
        this.typeId = typeId;
    }
    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
}
