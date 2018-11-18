package controllers.auditlog;

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
        
        // The Firebase-based save() method is already async, no need for the Job-based method below
        new AuditLogEvent(getAccountId(), getUserId(), getActor(), model, modelId, Operation.CREATE, null, null, null).save();
        
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
    	
        // The Firebase-based save() method is already async, no need for the Job-based method below    	
        new AuditLogEvent(getAccountId(), getUserId(), getActor(), model, modelId, Operation.UPDATE, property, oldValue, newValue).save();
        
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
    	
        // The Firebase-based save() method is already async, no need for the Job-based method below
        new AuditLogEvent(getAccountId(), getUserId(), getActor(), model, modelId, Operation.DELETE, null, null, null).save();
    	
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
