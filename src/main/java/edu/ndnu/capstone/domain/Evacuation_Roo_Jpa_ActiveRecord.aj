// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.ndnu.capstone.domain;

import edu.ndnu.capstone.domain.Evacuation;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Evacuation_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Evacuation.entityManager;
    
    public static final List<String> Evacuation.fieldNames4OrderClauseFilter = java.util.Arrays.asList("");
    
    public static final EntityManager Evacuation.entityManager() {
        EntityManager em = new Evacuation().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Evacuation.countEvacuations() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Evacuation o", Long.class).getSingleResult();
    }
    
    public static List<Evacuation> Evacuation.findAllEvacuations() {
        return entityManager().createQuery("SELECT o FROM Evacuation o", Evacuation.class).getResultList();
    }
    
    public static List<Evacuation> Evacuation.findAllEvacuations(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Evacuation o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Evacuation.class).getResultList();
    }
    
    public static Evacuation Evacuation.findEvacuation(Integer id) {
        if (id == null) return null;
        return entityManager().find(Evacuation.class, id);
    }
    
    public static List<Evacuation> Evacuation.findEvacuationEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Evacuation o", Evacuation.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<Evacuation> Evacuation.findEvacuationEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Evacuation o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Evacuation.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Evacuation.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Evacuation.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Evacuation attached = Evacuation.findEvacuation(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Evacuation.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Evacuation.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Evacuation Evacuation.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Evacuation merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}