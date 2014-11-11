package jobs.auditlog;

import java.util.Date;

import models.auditlog.AuditLogEvent;
import play.jobs.Job;
import play.modules.auditlog.Auditable.Operation;

public class SaveAuditLogEvent extends Job {

    private String model;
    private Long modelId;
    private Operation operation;
    private String property;
    private String oldValue;
    private String newValue;
    private String actor;
    private Long accountId;
    private Long userId;
    private Boolean admin;

    public SaveAuditLogEvent(String model, Long modelId, Operation operation, String property, String oldValue, String newValue, String actor, Long accountId, Long userId, Boolean admin) {
        this.model = model;
        this.modelId = modelId;
        this.operation = operation;
        this.property = property;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.actor = actor;
        this.userId = userId;
        this.accountId = accountId;
        this.admin = admin;
    }

    public void doJob() {
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
        event.admin = admin;
        event.createdAt = new Date();
        event.save();
    }

}
