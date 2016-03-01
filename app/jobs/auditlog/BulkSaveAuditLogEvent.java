package jobs.auditlog;

import java.util.Date;

import models.auditlog.AuditLogEvent;
import play.jobs.Job;
import play.modules.auditlog.Auditable.Operation;

public class BulkSaveAuditLogEvent extends Job {

    private String model;
    private Long[] modelIds;
    private Operation operation;
    private String property;
    private String oldValue;
    private String newValue;
    private String actor;
    private Long accountId;
    private Long userId;

    public BulkSaveAuditLogEvent(String model, Long[] modelIds, Operation operation, String property, String oldValue, String newValue, String actor, Long accountId, Long userId) {
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

    public void doJob() {
    	
    	for (Long modelId : this.modelIds) {
    	
	        AuditLogEvent event = new AuditLogEvent();
	        event.model = model;
	        event.modelId = modelId;
	        event.operation = operation;
	        event.property = property;
	        event.oldValue = oldValue;
	        event.newValue = newValue;
	        event.actor = actor;
	        event.userId = userId;
	        event.accountId = accountId;
	        event.createdAt = new Date();
	        event.save();
    	}
    }

}