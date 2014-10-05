package edu.ndnu.capstone.domain;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooJpaActiveRecord(versionField = "", table = "user")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "emergencies", "typeId" })
public class User 
{
	/*
	 * The set and get methods are stored in User_Roo_DbManaged.aj
	 * We use the two annotations below, PrePersist and PreUpdate,
	 * to execute the code below before creating/updating a user.
	 * 
	 * This code takes the password string and makes a sha-256 hash
	 * before saving it in the database.
	 * 
	 */
	@PrePersist
	@PreUpdate
	public void encryptPassword() 
	{
		String password = this.getPassword();
        if (password != null && (! password.matches("^[0-9a-fA-F]+$"))) 
        {
        	MessageDigest md;
			try {
				md = MessageDigest.getInstance("SHA-256");
			
        	md.update(password.getBytes());
        	byte[] shaDig = md.digest();
        	String hashedPassword = convertByteArrayToHexString(shaDig);
	        
			this.setPassword(hashedPassword);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	
	private String convertByteArrayToHexString(byte[] arrayBytes) {
	    StringBuffer stringBuffer = new StringBuffer();
	    for (int i = 0; i < arrayBytes.length; i++) {
	        stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
	                .substring(1));
	    }
	    return stringBuffer.toString();
	}
}
