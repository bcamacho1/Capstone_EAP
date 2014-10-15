package edu.ndnu.capstone.domain;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "emergency_status")
@Configurable
@RooJavaBean
@RooJpaActiveRecord(versionField = "", table = "emergency_status")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "emergencies" })
public class EmergencyStatus {

	@OneToMany(mappedBy = "statusId")
    private Set<Emergency> emergencies;

	@Column(name = "name", length = 256)
    @NotNull
    private String name;

	public Set<Emergency> getEmergencies() {
        return emergencies;
    }

	public void setEmergencies(Set<Emergency> emergencies) {
        this.emergencies = emergencies;
    }

	public String getName() {
        return name;
    }

	public void setName(String name) {
        this.name = name;
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

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("");

	public static final EntityManager entityManager() {
        EntityManager em = new EmergencyStatus().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countEmergencyStatuses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM EmergencyStatus o", Long.class).getSingleResult();
    }

	public static List<EmergencyStatus> findAllEmergencyStatuses() {
        return entityManager().createQuery("SELECT o FROM EmergencyStatus o", EmergencyStatus.class).getResultList();
    }

	public static List<EmergencyStatus> findAllEmergencyStatuses(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM EmergencyStatus o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, EmergencyStatus.class).getResultList();
    }

	public static EmergencyStatus findEmergencyStatus(Integer id) {
        if (id == null) return null;
        return entityManager().find(EmergencyStatus.class, id);
    }

	public static List<EmergencyStatus> findEmergencyStatusEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM EmergencyStatus o", EmergencyStatus.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	public static List<EmergencyStatus> findEmergencyStatusEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM EmergencyStatus o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, EmergencyStatus.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            EmergencyStatus attached = EmergencyStatus.findEmergencyStatus(this.id);
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
    public EmergencyStatus merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        EmergencyStatus merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("emergencies").toString();
    }
}
