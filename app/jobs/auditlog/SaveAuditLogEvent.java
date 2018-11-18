package jobs.auditlog;

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

    public SaveAuditLogEvent(String model, Long modelId, Operation operation, String property, String oldValue, String newValue, String actor, Long accountId, Long userId) {
        this.model = model;
        this.modelId = modelId;
        this.operation = operation;
        this.property = property;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.actor = actor;
        this.userId = userId;
        this.accountId = accountId;
    }

    @Override
    public void doJob() {
        AuditLogEvent event = new AuditLogEvent(accountId, userId, actor, model, modelId, operation, property, oldValue, newValue);
        event.save();
    }

}
