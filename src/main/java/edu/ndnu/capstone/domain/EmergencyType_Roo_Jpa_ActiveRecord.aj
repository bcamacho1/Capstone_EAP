// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.ndnu.capstone.domain;

import edu.ndnu.capstone.domain.EmergencyType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect EmergencyType_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager EmergencyType.entityManager;
    
    public static final EntityManager EmergencyType.entityManager() {
        EntityManager em = new EmergencyType().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long EmergencyType.countEmergencyTypes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM EmergencyType o", Long.class).getSingleResult();
    }
    
    public static List<EmergencyType> EmergencyType.findAllEmergencyTypes() {
        return entityManager().createQuery("SELECT o FROM EmergencyType o", EmergencyType.class).getResultList();
    }
    
    public static EmergencyType EmergencyType.findEmergencyType(Integer id) {
        if (id == null) return null;
        return entityManager().find(EmergencyType.class, id);
    }
    
    public static List<EmergencyType> EmergencyType.findEmergencyTypeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM EmergencyType o", EmergencyType.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void EmergencyType.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void EmergencyType.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            EmergencyType attached = EmergencyType.findEmergencyType(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void EmergencyType.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void EmergencyType.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public EmergencyType EmergencyType.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        EmergencyType merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
