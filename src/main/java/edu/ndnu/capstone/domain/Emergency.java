package edu.ndnu.capstone.domain;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "emergency", schema = "capstone")
@Configurable
@RooJavaBean
@RooJpaActiveRecord(versionField = "", table = "emergency")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "userId", "locationId", "typeId", "statusId" })
public class Emergency {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User userId;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    private Location locationId;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    private EmergencyType typeId;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    private EmergencyStatus statusId;

    @Column(name = "created", updatable = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar created = java.util.Calendar.getInstance();

    @Column(name = "description", length = 1024)
    @NotNull
    @Size(min=1, message = "Description can not be left blank")
    private String description;

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Location getLocationId() {
        return locationId;
    }

    public void setLocationId(Location locationId) {
        this.locationId = locationId;
    }

    public EmergencyType getTypeId() {
        return typeId;
    }

    public void setTypeId(EmergencyType typeId) {
        this.typeId = typeId;
    }

    public EmergencyStatus getStatusId() {
        return statusId;
    }

    public void setStatusId(EmergencyStatus statusId) {
        this.statusId = statusId;
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

    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("userId", "locationId", "typeId", "statusId").toString();
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

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("user_id", "location_id", "type_id", "status_id", "created");

    public static final EntityManager entityManager() {
        EntityManager em = new Emergency().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countEmergencies() {
        String jpaQuery = "SELECT COUNT(o) FROM Emergency o";
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        
        if (login.compareTo("anonymousUser")==0)
        {
            jpaQuery = jpaQuery + " JOIN o.statusId p WHERE p.name != 'Complete'";
        }
        
        return entityManager().createQuery(jpaQuery, Long.class).getSingleResult();
    }

    public static List<Emergency> findAllEmergencies() {
        String jpaQuery = getFindEmergenciesQuery() + " ORDER BY created desc";
        return entityManager().createQuery(jpaQuery, Emergency.class).getResultList();
    }

    public static List<Emergency> findAllEmergencies(String sortFieldName, String sortOrder) {
        String jpaQuery = getFindEmergenciesQuery() + " ORDER BY created desc";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + ", " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Emergency.class).getResultList();
    }

    public static Emergency findEmergency(Integer id) {
        if (id == null) return null;
        return entityManager().find(Emergency.class, id);
    }

    public static List<Emergency> findEmergencyEntries(int firstResult, int maxResults) {
        String jpaQuery = getFindEmergenciesQuery() + " ORDER BY created desc";
        return entityManager().createQuery(jpaQuery, Emergency.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<Emergency> findEmergencyEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = getFindEmergenciesQuery() + " ORDER BY created desc";

        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + ", " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Emergency.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Emergency attached = Emergency.findEmergency(this.id);
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
    public Emergency merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Emergency merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    // New method to load emergencies based on an anonymous user
    // A logged in user should be able to see all emergencies
    // An anonymous user should not see any in status "Complete"
    public static String getFindEmergenciesQuery()
    {
        String jpaQuery = "SELECT o FROM Emergency o";
        
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null)
        {
            String login = SecurityContextHolder.getContext().getAuthentication().getName();
            if (login.compareTo("anonymousUser")==0)
            {
                jpaQuery = jpaQuery + " JOIN o.statusId p WHERE p.name != 'Complete'";
            }
        }
        return jpaQuery;
    }
}
