package edu.ndnu.capstone.domain;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
@Table(name = "user")
@RooJavaBean
@RooJpaActiveRecord(versionField = "", table = "user")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "emergencies", "typeId" })
public class User 
{
	
	@OneToMany(mappedBy = "userId")
    private Set<Emergency> emergencies;

	@ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    private UserType typeId;

	@Column(name = "name", length = 256)
    @NotNull
    private String name;

	@Column(name = "email", length = 256, unique = true)
    @NotNull
    private String email;

	@Column(name = "username", length = 150, unique = true)
    @NotNull
    private String username;

	@Column(name = "password")
    @NotNull
    private String password;

	@Column(name = "phone", length = 10, unique = true)
    @NotNull
    private String phone;

	@Column(name = "year", length = 4)
    @NotNull
    @Pattern(regexp = "20[0-9][0-9]")
    private String year;

	@Column(name = "active", length = 1)
    @NotNull
    private Integer active;

	@Column(name = "created", updatable = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar created = java.util.Calendar.getInstance();

	@Column(name = "description", length = 1024)
    @NotNull
    private String description;

	public Set<Emergency> getEmergencies() {
        return emergencies;
    }

	public void setEmergencies(Set<Emergency> emergencies) {
        this.emergencies = emergencies;
    }

	public UserType getTypeId() {
        return typeId;
    }

	public void setTypeId(UserType typeId) {
        this.typeId = typeId;
    }

	public String getName() {
        return name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public String getEmail() {
        return email;
    }

	public void setEmail(String email) {
        this.email = email;
    }

	public String getUsername() {
        return username;
    }

	public void setUsername(String username) {
        this.username = username;
    }

	public String getPassword() {
        return password;
    }

	public void setPassword(String password) {
        this.password = password;
    }

	public String getPhone() {
        return phone;
    }

	public void setPhone(String phone) {
        this.phone = phone;
    }

	public String getYear() {
        return year;
    }

	public void setYear(String year) {
        this.year = year;
    }

	public Integer getActive() {
        return active;
    }

	public void setActive(Integer active) {
    	if(active==0 || active==1)
        this.active = active;
    	else
    		 this.active = 2;
    }

	public Calendar getCreated() {
        return created;
    }

	public void setCreated(Calendar created) {
        this.created = created;
    }

	public String getDescription() {
        return description;
    }

	public void setDescription(String description) {
        this.description = description;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

	public Integer getId() {
        return this.id;
    }

	public void setId(Integer id) {
        this.id = id;
    }
	
	/*
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
	        try 
	        {
	            md = MessageDigest.getInstance("SHA-256");
	            md.update(password.getBytes());
	            byte[] shaDig = md.digest();
	            String hashedPassword = convertByteArrayToHexString(shaDig);
	            this.setPassword(hashedPassword);
	        } 
	        catch (NoSuchAlgorithmException e) 
	        {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	}
	
	private String convertByteArrayToHexString(byte[] arrayBytes)
	{
	    StringBuffer stringBuffer = new StringBuffer();
	    for (int i = 0; i < arrayBytes.length; i++)
	    {
	        stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
	    }
	    return stringBuffer.toString();
	}

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("");

	public static final EntityManager entityManager() {
        EntityManager em = new User().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countUsers() {
        return entityManager().createQuery("SELECT COUNT(o) FROM User o", Long.class).getSingleResult();
    }

	public static List<User> findAllUsers() {
        return entityManager().createQuery("SELECT o FROM User o", User.class).getResultList();
    }

	public static List<User> findAllUsers(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM User o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, User.class).getResultList();
    }

	public static User findUser(Integer id) {
        if (id == null) return null;
        return entityManager().find(User.class, id);
    }

	public static List<User> findUserEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM User o", User.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<User> findUserEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM User o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, User.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            User attached = User.findUser(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public User merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        User merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("emergencies", "typeId").toString();
    }
}
