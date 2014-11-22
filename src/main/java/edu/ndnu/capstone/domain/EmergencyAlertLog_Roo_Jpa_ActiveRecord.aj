// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.ndnu.capstone.domain;

import edu.ndnu.capstone.domain.EmergencyAlertLog;
import java.util.List;

privileged aspect EmergencyAlertLog_Roo_Jpa_ActiveRecord {
    
    public static final List<String> EmergencyAlertLog.fieldNames4OrderClauseFilter = java.util.Arrays.asList("userId", "emergencyId", "emergencyMessageId", "ts", "sent", "id", "entityManager");
    
    public static List<EmergencyAlertLog> EmergencyAlertLog.findAllEmergencyAlertLogs(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM EmergencyAlertLog o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, EmergencyAlertLog.class).getResultList();
    }
    
    public static List<EmergencyAlertLog> EmergencyAlertLog.findEmergencyAlertLogEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM EmergencyAlertLog o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, EmergencyAlertLog.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}