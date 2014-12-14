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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "emergency_type")
@Configurable
@RooJavaBean
@RooJpaActiveRecord(versionField = "", table = "emergency_type")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "emergencies" })
public class EmergencyType {

    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("emergencies").toString();
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

    @OneToMany(mappedBy = "typeId")
    private Set<Emergency> emergencies;

    @Column(name = "name", length = 256)
    @NotNull
    @Size(min = 1, message="Name can not be left blank.")
    @Pattern(regexp="[A-Za-z0-9\\. -,]*",message="Name can be both letters, numbers and puntuation. Please Verify You Input.")
    private String name;
    
    @Column(name = "category", length = 1)
    @NotNull(message="Category can not be left blank.")
    @Min(1)
    @Max(3)
    private Integer category;

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
    
    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    @PersistenceContext
    transient EntityManager entityManager;

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("name", "category");

    public static final EntityManager entityManager() {
        EntityManager em = new EmergencyType().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countEmergencyTypes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM EmergencyType o", Long.class).getSingleResult();
    }

    public static List<EmergencyType> findAllEmergencyTypes() {
        return entityManager().createQuery("SELECT o FROM EmergencyType o", EmergencyType.class).getResultList();
    }

    public static List<EmergencyType> findAllEmergencyTypes(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM EmergencyType o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, EmergencyType.class).getResultList();
    }

    public static EmergencyType findEmergencyType(Integer id) {
        if (id == null) return null;
        return entityManager().find(EmergencyType.class, id);
    }

    public static List<EmergencyType> findEmergencyTypeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM EmergencyType o", EmergencyType.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<EmergencyType> findEmergencyTypeEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM EmergencyType o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, EmergencyType.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            EmergencyType attached = EmergencyType.findEmergencyType(this.id);
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
    public EmergencyType merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        EmergencyType merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
