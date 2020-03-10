package jobs.auditlog;

import java.util.List;

import models.auditlog.AuditLogEvent;
import play.jobs.Job;
import play.modules.auditlog.Auditable.Operation;

public class BulkSaveAuditLogEvent extends Job {

    private String model;
    private List<Long> modelIds;
    private Operation operation;
    private String property;
    private String oldValue;
    private List<String> oldValues;
    private String newValue;
    private String actor;
    private Long accountId;
    private Long userId;

    public BulkSaveAuditLogEvent(String model, List<Long> modelIds, Operation operation, String property, String oldValue, String newValue, String actor, Long accountId, Long userId) {
        this.model = model;
        this.modelIds = modelIds;
        this.operation = operation;
        this.property = property;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.actor = actor;
        this.userId = userId;
        this.accountId = accountId;
    }
    
    public BulkSaveAuditLogEvent(String model, List<Long> modelIds, Operation operation, String property, List<String> oldValues, String newValue, String actor, Long accountId, Long userId) {
        this.model = model;
        this.modelIds = modelIds;
        this.operation = operation;
        this.property = property;
        this.oldValues = oldValues;
        this.newValue = newValue;
        this.actor = actor;
        this.userId = userId;
        this.accountId = accountId;
    }    

    @Override
    public void doJob() {
    	
        int n = 0;
    	for (Long modelId : this.modelIds)
	        new AuditLogEvent(accountId, userId, actor, model, modelId, operation, property, (oldValues == null ? oldValue : oldValues.get(n++)), newValue).save();
    }

}
