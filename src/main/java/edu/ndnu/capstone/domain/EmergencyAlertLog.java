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
@Table(name = "emergency_alert_log")
@RooJavaBean
@RooJpaActiveRecord(versionField = "", table = "emergency_alert_log")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "userId", "emergencyId", "emergencyMessageId" })
public class EmergencyAlertLog {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AuthorizedUser userId;

    @ManyToOne
    @JoinColumn(name = "emergency_id", referencedColumnName = "id", nullable = false)
    private Emergency emergencyId;

    @ManyToOne
    @JoinColumn(name = "emergency_message_id", referencedColumnName = "id")
    private EmergencyMessage emergencyMessageId;

    @Column(name = "ts")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Calendar ts;

    @Column(name = "sent")
    private Integer sent;

    public AuthorizedUser getUserId() {
        return userId;
    }

    public void setUserId(AuthorizedUser userId) {
        this.userId = userId;
    }

    public Emergency getEmergencyId() {
        return emergencyId;
    }

    public void setEmergencyId(Emergency emergencyId) {
        this.emergencyId = emergencyId;
    }

    public EmergencyMessage getEmergencyMessageId() {
        return emergencyMessageId;
    }

    public void setEmergencyMessageId(EmergencyMessage emergencyMessageId) {
        this.emergencyMessageId = emergencyMessageId;
    }

    public Calendar getTs() {
        return ts;
    }

    public void setTs(Calendar ts) {
        this.ts = ts;
    }

    public Integer getSent() {
        return sent;
    }

    public void setSent(Integer sent) {
        this.sent = sent;
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
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("userId", "emergencyId", "emergencyMessageId").toString();
    }

    @PersistenceContext
    transient EntityManager entityManager;

    public static final EntityManager entityManager() {
        EntityManager em = new EmergencyAlertLog().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countEmergencyAlertLogs() {
        return entityManager().createQuery("SELECT COUNT(o) FROM EmergencyAlertLog o", Long.class).getSingleResult();
    }

    public static List<EmergencyAlertLog> findAllEmergencyAlertLogs() {
        return entityManager().createQuery("SELECT o FROM EmergencyAlertLog o ORDER BY ts desc", EmergencyAlertLog.class).getResultList();
    }

    public static EmergencyAlertLog findEmergencyAlertLog(Integer id) {
        if (id == null) return null;
        return entityManager().find(EmergencyAlertLog.class, id);
    }

    public static List<EmergencyAlertLog> findEmergencyAlertLogEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM EmergencyAlertLog o ORDER BY ts desc", EmergencyAlertLog.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            EmergencyAlertLog attached = EmergencyAlertLog.findEmergencyAlertLog(this.id);
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
    public EmergencyAlertLog merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        EmergencyAlertLog merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
