package edu.ndnu.capstone.domain;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.validation.constraints.Max;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.DataAccessException;
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
    @Size(min=1, max=256, message="Must be at least 1 character.")
    @Pattern(regexp = "([A-Z][a-z]+) ([A-Za-z']+[ '-]*[A-Za-z]*[ Jr.Sr.]*[I]*)", message="Please use only first and last name, can be hyphenated, names must be capital. Includes suffix: Sr., Jr., I, II, and III. If no last name type NA.")
    private String name;
    
    @Column(name = "id_number")
    @Min(0)
    @Max(1000000)
    private Integer idNumber;

    @Column(name = "email", length = 100, unique = true)
    @NotNull
    //@Pattern(regexp = "@[student]*[.]*[ndnu.edu]+", message="Email must end with either @student.ndnu.edu or @ndnu.edu as the extension.")
    private String email;

    @Column(name = "phone", length = 10, unique = true)
    @NotNull
    @Size(min=1, message="Phone number can not be left blank.")
    @Pattern(regexp = "^((?![5]{3})(\\d{3}))([-]*)(\\d{3})([-]*)\\d{4}$", message="Phone number can be of the form 888-123-4567")
    private String phone;

    @Column(name = "active", length = 1)
    @NotNull(message="Active can not be left blank.")
    @Min(0)
    @Max(1)
    private Integer active;

    @Column(name = "created", updatable = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar created = java.util.Calendar.getInstance();

    @Column(name = "emergency_contact_name", length = 256)
    @NotNull
    @Size(min=1, max=256, message="Must be at least 1 character.")
    @Pattern(regexp = "([A-Z][a-z]+) ([A-Za-z']+[ '-]*[A-Za-z]*[ Jr.Sr.]*[I]*)", message="Please use only first and last name, can be hyphenated, names must be capital. Includes suffix: Sr., Jr., I, II, and III. If no last name type NA.")
    private String emergencyContactName;
    
    @Column(name = "emergency_contact_phone", length = 10, unique = true)
    @NotNull
    @Size(min=1, message="Emergency contact phone number can not be left blank.")
    @Pattern(regexp = "^((?![5]{3})(\\d{3}))([-]*)(\\d{3})([-]*)\\d{4}$", message="Emergency contact phone number can be of the form 888-123-4567")
    private String emergencyContactPhone;
    
    @Column(name = "description", length = 1024)
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
    	System.out.println(typeId);
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Integer idNumber) {
        this.idNumber = idNumber;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }
    
    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
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

    @PersistenceContext
    transient EntityManager entityManager;

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("name", "email", "phone", "created");

    public static final EntityManager entityManager() {
        EntityManager em = new User().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countUsers() {
        return entityManager().createQuery("SELECT COUNT(o) FROM User o JOIN o.typeId p WHERE p.name in ('Student','Faculty')", Long.class).getSingleResult();
    }

    public static List<User> findAllUsers() {
        return entityManager().createQuery("SELECT o FROM User o JOIN o.typeId p WHERE p.name in ('Student','Faculty') ORDER BY active desc, type_id, o.name", User.class).getResultList();
    }

    public static List<User> findAllUsers(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM User o JOIN o.typeId p WHERE p.name in ('Student','Faculty') ORDER BY active desc, type_id";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + ", " + sortFieldName;
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
        return entityManager().createQuery("SELECT o FROM User o JOIN o.typeId p WHERE p.name in ('Student','Faculty') ORDER BY active desc, type_id, o.name", User.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<User> findUserEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM User o JOIN o.typeId p WHERE p.name in ('Student','Faculty') ORDER BY active desc, type_id";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + ", o." + sortFieldName;
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
    
    public static User findUserByEmail(String email2) {
        if (email2 == null) return null;
        try {
            return entityManager().createQuery("SELECT o FROM User o JOIN o.typeId p WHERE p.name in ('Student','Faculty') AND email = '" + email2 + "'", User.class).getSingleResult();
        } catch (DataAccessException e) {
            return null;
        }
    }
}
