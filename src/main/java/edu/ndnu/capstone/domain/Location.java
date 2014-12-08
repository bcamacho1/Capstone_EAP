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

@Configurable
@Entity
@Table(name = "location", schema = "capstone")
@RooJavaBean
@RooJpaActiveRecord(versionField = "", table = "location")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "emergencies" })
public class Location {

    @PersistenceContext
    transient EntityManager entityManager;

    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("name");

    public static final EntityManager entityManager() {
        EntityManager em = new Location().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countLocations() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Location o", Long.class).getSingleResult();
    }

    public static List<Location> findAllLocations() {
        return entityManager().createQuery("SELECT o FROM Location o ORDER BY state, city, address, name", Location.class).getResultList();
    }

    public static List<Location> findAllLocations(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Location o ORDER BY state, city, address";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + ", " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Location.class).getResultList();
    }

    public static Location findLocation(Integer id) {
        if (id == null) return null;
        return entityManager().find(Location.class, id);
    }

    public static List<Location> findLocationEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Location o ORDER BY state, city, address, name", Location.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<Location> findLocationEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Location o ORDER BY state, city, address";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + ", " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Location.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Location attached = Location.findLocation(this.id);
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
    public Location merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Location merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    @OneToMany(mappedBy = "locationId")
    private Set<Emergency> emergencies;

    @Column(name = "name", length = 256)
    @NotNull()
    @Size(min=2, max=30, message = "Name must be between 2 and 30 characters.")
    @Pattern(regexp = "([A-Z][a-z]*) ([A-za-z]+([ '-][A-Za-z]+)*)", message="Name must contain only letters, spaces, dashes, and apostraphies.")
    private String name;

    @Column(name = "address", length = 1024)
    @NotNull
    @Size(min=2, max=30, message = "Address must be between 2 and 30 characters.")
    @Pattern(regexp = "[0-9]+ [A-Za-z]+ ?[a-z*[A-Za-z]* *[A-Za-z]*", message="Address can be both letters and numbers. Address can be three words.")
    private String address;

    @Column(name = "city", length = 1024)
    @NotNull
    @Size(min=2, max=30, message="City must be between 2 and 30 characters.")
    @Pattern(regexp = "([A-Za-z]+) ?([a-z*[A-Za-z]*) *([A-Za-z]*)", message="City must contain only letters, and can be three words.")
    private String city;

    @Column(name = "state", length = 64)
    @NotNull
    @Size(min=2, max=30, message="State must be between 2 and 30 characters.")
    @Pattern(regexp = "([A-Za-z]+) ?([a-z*[A-Za-z]*) *([A-Za-z]*)", message="State must contain only letters, and can be three words.")
    private String state;


    @Column(name = "zipcode", length = 64)
    @NotNull
    @Size(min=5, max=5, message = "Zipcode can only be 5 characters.")
    @Pattern(regexp = "[0-9]{5}", message="Zipcode can only contain numbers")
    private String zipcode;

    @Column(name = "latitude", length = 64)
    @Size(min=8, max=8, message="Latitude must be of the form 66-55-44")
    @Pattern(regexp = "^(\\d{2}-\\d{2}-\\d{2})", message="Latitude must be of the form 66-55-44")
    private String latitude;

    @Column(name = "longitude", length = 64)
    @Size(min=8, max=8, message="Latitude must be of the form 66-55-44")
    @Pattern(regexp = "^(\\d{2}-\\d{2}-\\d{2})", message="Longitude must be of the form 66-55-44")
    private String longitude;

    @Column(name = "description", length = 1024)
    private String description;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("emergencies").toString();
    }
}
