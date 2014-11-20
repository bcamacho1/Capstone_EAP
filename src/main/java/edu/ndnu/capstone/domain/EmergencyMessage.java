package edu.ndnu.capstone.domain;
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

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.DataAccessException;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "emergency_message")
@Configurable
@RooJavaBean
@RooJpaActiveRecord(versionField = "", table = "emergency_message")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "userId", "emergencyId", "emergencyAlertLogs", "emergencyTypeId" })
public class EmergencyMessage {

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
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("userId", "emergencyId", "emergencyAlertLogs", "emergencyTypeId").toString();
    }

    @PersistenceContext
    transient EntityManager entityManager;

    public static final EntityManager entityManager() {
        EntityManager em = new EmergencyMessage().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countEmergencyMessages() {
        return entityManager().createQuery("SELECT COUNT(o) FROM EmergencyMessage o", Long.class).getSingleResult();
    }

    public static long countEmergencyMessagesByUser(Integer user_id) {
        return entityManager().createQuery("SELECT COUNT(o) FROM EmergencyMessage o WHERE user_id = " + user_id, Long.class).getSingleResult();
    }

    public static List<EmergencyMessage> findAllEmergencyMessages() {
        return entityManager().createQuery("SELECT o FROM EmergencyMessage o", EmergencyMessage.class).getResultList();
    }

    public static EmergencyMessage findEmergencyMessage(Integer id) {
        if (id == null) return null;
        return entityManager().find(EmergencyMessage.class, id);
    }

    // When writing queries, these methods need to catch the DataAccessException exception and not the NoResultException as expected
    // This is because Spring converts the exceptions into ones that it uses
    // See Class DataAccessException in the Spring documentation for more details
    public static EmergencyMessage findEmergencyMessageByUserAndType(Integer user_id, Integer type_id) {
        if (user_id == null) return null;
        if (type_id == null) return null;
        try
        {
            return entityManager().createQuery("SELECT o FROM EmergencyMessage o WHERE user_id = " + user_id + " and emergency_type_id = " + type_id, EmergencyMessage.class).getSingleResult();
        }
        catch (DataAccessException e)
        {
            return null;
        }
    }

    public static List<EmergencyMessage> findEmergencyMessageByUser(Integer user_id) {
        if (user_id == null) return null;
        try
        {
            return entityManager().createQuery("SELECT o FROM EmergencyMessage o WHERE user_id = " + user_id, EmergencyMessage.class).getResultList();
        }
        catch (DataAccessException e)
        {
            return null;
        }
    }

    public static EmergencyMessage findDefaultEmergencyMessageByType(Integer type_id) {
        if (type_id == null) return null;
        try
        {
            return entityManager().createQuery("SELECT o FROM EmergencyMessage o WHERE user_id is null and emergency_type_id = " + type_id, EmergencyMessage.class).getSingleResult();
        }
        catch (DataAccessException e)
        {
            return null;
        }
    }

    public static List<EmergencyMessage> findEmergencyMessageEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM EmergencyMessage o", EmergencyMessage.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public static List<EmergencyMessage> findEmergencyMessageEntriesByUser(Integer user_id, int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM EmergencyMessage o where user_id = " + user_id, EmergencyMessage.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            EmergencyMessage attached = EmergencyMessage.findEmergencyMessage(this.id);
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
    public EmergencyMessage merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        EmergencyMessage merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    @OneToMany(mappedBy = "emergencyMessageId")
    private Set<EmergencyAlertLog> emergencyAlertLogs;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "emergency_type_id", referencedColumnName = "id")
    private EmergencyType emergencyTypeId;

    @Column(name = "message")
    private String message;

    public Set<EmergencyAlertLog> getEmergencyAlertLogs() {
        return emergencyAlertLogs;
    }

    public void setEmergencyAlertLogs(Set<EmergencyAlertLog> emergencyAlertLogs) {
        this.emergencyAlertLogs = emergencyAlertLogs;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public EmergencyType getEmergencyTypeId() {
        return emergencyTypeId;
    }

    public void setEmergencyTypeId(EmergencyType emergencyTypeId) {
        this.emergencyTypeId = emergencyTypeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
