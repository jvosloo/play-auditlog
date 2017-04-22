package controllers.auditlog;

import jobs.auditlog.SaveAuditLogEvent;
import models.auditlog.AuditLogEvent;
import play.modules.auditlog.Auditable.Operation;
import play.modules.auditlog.IActorProvider;
import utils.ActorUtils;

public class DefaultAuditLogEvents {

    private static String getActor() {
    	IActorProvider provider = ActorUtils.getProvider();
    	if (provider == null) throw new RuntimeException("You need to implement the interface IActorProvider to tell me how to get the actor");
		return provider.getActor();
    }
    
    private static Long getUserId() {
    	IActorProvider provider = ActorUtils.getProvider();
    	if (provider == null) throw new RuntimeException("You need to implement the interface IActorProvider to tell me how to get the user ID");
		return provider.getUserId();
    }    
    
    private static Long getAccountId() {
    	IActorProvider provider = ActorUtils.getProvider();
    	if (provider == null) throw new RuntimeException("You need to implement the interface IActorProvider to tell me how to get the account ID");
		return provider.getAccountId();
    }    
    
    static void onCreate(String model, Long modelId) {
        String actor = getActor();
        Long userId = getUserId();
        Long accountId = getAccountId();
        
        AuditLogEvent event = new AuditLogEvent(accountId, userId, actor, model, modelId, Operation.CREATE, null, null, null);
        event.save();
//        new SaveAuditLogEvent(
//                model,
//                modelId,
//                Operation.CREATE,
//                null,
//                null,
//                null,
//                actor,
//                accountId,
//                userId
//        ).now();
    }

    static void onUpdate(String model, Long modelId, String property, String oldValue, String newValue) {
    	String actor = getActor();
    	Long userId = getUserId();
    	Long accountId = getAccountId();
    	
        AuditLogEvent event = new AuditLogEvent(accountId, userId, actor, model, modelId, Operation.UPDATE, property, oldValue, newValue);
        event.save();
        
//        new SaveAuditLogEvent(
//                model,
//                modelId,
//                Operation.UPDATE,
//                property,
//                oldValue,
//                newValue,
//                actor,
//                accountId,
//                userId
//        ).now();
    }

    static void onDelete(String model, Long modelId) {
    	String actor = getActor();
    	Long userId = getUserId();
    	Long accountId = getAccountId();
    	
        AuditLogEvent event = new AuditLogEvent(accountId, userId, actor, model, modelId, Operation.DELETE, null, null, null);
        event.save();    	
    	
//        new SaveAuditLogEvent(
//                model,
//                modelId,
//                Operation.DELETE,
//                null,
//                null,
//                null,
//                actor,
//                accountId,
//                userId
//        ).now();
    }

}
