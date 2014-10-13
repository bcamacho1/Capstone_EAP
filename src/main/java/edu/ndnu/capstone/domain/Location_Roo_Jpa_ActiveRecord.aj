// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.ndnu.capstone.domain;

import edu.ndnu.capstone.domain.Location;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Location_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Location.entityManager;
    
    public static final List<String> Location.fieldNames4OrderClauseFilter = java.util.Arrays.asList("id", "name", "evacuation_area", "latitude", "longitude", "description");
    
    public static final EntityManager Location.entityManager() {
        EntityManager em = new Location().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Location.countLocations() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Location o", Long.class).getSingleResult();
    }
    
    public static List<Location> Location.findAllLocations() {
        return entityManager().createQuery("SELECT o FROM Location o", Location.class).getResultList();
    }
    
    public static List<Location> Location.findAllLocations(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Location o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Location.class).getResultList();
    }
    
    public static Location Location.findLocation(Integer id_) {
        if (id_ == null) return null;
        return entityManager().find(Location.class, id_);
    }
    
    public static List<Location> Location.findLocationEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Location o", Location.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<Location> Location.findLocationEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Location o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Location.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Location.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Location.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Location attached = Location.findLocation(this.id_);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Location.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Location.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Location Location.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Location merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
