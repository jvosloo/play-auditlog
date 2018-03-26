package models.auditlog;

import java.lang.reflect.Method;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang.StringUtils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import play.Play;
import play.modules.auditlog.Auditable.Operation;


public class AuditLogEvent {
    
    private Long accountId;
    
    private Long userId;  
    
    private String actor;    
    
    private String model;

    private Long modelId;

	@Enumerated(EnumType.STRING)
	private Operation operation;

	private String property;

	private String oldValue;

	private String newValue;

	private Long createdAt;
	
	private String model_modelId;
	
    private String url;	
	
	
    // ---	

	public AuditLogEvent(Long accountId, Long userId, String actor, String model, Long modelId, Operation operation, String property, String oldValue, String newValue) {
        super();
        this.accountId = accountId;
        this.userId = userId;
        this.actor = actor;
        this.model = model;
        this.modelId = modelId;
        this.operation = operation;
        this.property = property;
        this.oldValue = oldValue;
        this.newValue = StringUtils.abbreviate(newValue, 255);
        this.createdAt = new Date().getTime();
        this.model_modelId = model + "_" + modelId;
    }
	
    // ---	
	
    @Exclude
    public String getUrl() {
        
        if (url == null) {
        
            try {
                Class<?> cls = Class.forName(model);
                Method method = cls.getDeclaredMethod("findById", Object.class);
                Object modelObj = method.invoke(null, modelId);
                method = cls.getDeclaredMethod("getPath");
                url = (String) method.invoke(modelObj);
            } catch (Exception e) {
                // Do nothing
            }
        }
        
        return url;
    }	

    
    public Long getAccountId() {
    
        return accountId;
    }

    
    public Long getUserId() {
    
        return userId;
    }

    
    public String getActor() {
    
        return actor;
    }

    
    public String getModel() {
    
        return model;
    }

    
    public Long getModelId() {
    
        return modelId;
    }

    
    public Operation getOperation() {
    
        return operation;
    }

    
    public String getProperty() {
    
        return property;
    }

    
    public String getOldValue() {
    
        return oldValue;
    }

    
    public String getNewValue() {
    
        return newValue;
    }

    
    public Long getCreatedAt() {
    
        return createdAt;
    }

    public String getModel_modelId() {

        return model_modelId;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((actor == null) ? 0 : actor.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((modelId == null) ? 0 : modelId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result + ((newValue == null) ? 0 : newValue.hashCode());
		result = prime * result + ((oldValue == null) ? 0 : oldValue.hashCode());
		result = prime * result + ((operation == null) ? 0 : operation.hashCode());
		result = prime * result + ((property == null) ? 0 : property.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof AuditLogEvent)) {
			return false;
		}
		AuditLogEvent other = (AuditLogEvent) obj;
		if (actor == null) {
			if (other.actor != null) {
				return false;
			}
		} else if (!actor.equals(other.actor)) {
			return false;
		}
		if (createdAt == null) {
			if (other.createdAt != null) {
				return false;
			}
		} else if (!createdAt.equals(other.createdAt)) {
			return false;
		}
		if (model == null) {
			if (other.model != null) {
				return false;
			}
		} else if (!model.equals(other.model)) {
			return false;
		}
		if (modelId == null) {
			if (other.modelId != null) {
				return false;
			}
		} else if (!modelId.equals(other.modelId)) {
			return false;
		}
		if (userId == null) {
			if (other.userId != null) {
				return false;
			}
		} else if (!userId.equals(other.userId)) {
			return false;
		}			
		if (accountId == null) {
			if (other.accountId != null) {
				return false;
			}
		} else if (!accountId.equals(other.accountId)) {
			return false;
		}	
		if (newValue == null) {
			if (other.newValue != null) {
				return false;
			}
		} else if (!newValue.equals(other.newValue)) {
			return false;
		}
		if (oldValue == null) {
			if (other.oldValue != null) {
				return false;
			}
		} else if (!oldValue.equals(other.oldValue)) {
			return false;
		}
		if (operation != other.operation) {
			return false;
		}
		if (property == null) {
			if (other.property != null) {
				return false;
			}
		} else if (!property.equals(other.property)) {
			return false;
		}
		return true;
	}
	
    public void save() {
        
        // If Firebase not enabled... then we're out of luck
        if (!Boolean.valueOf(Play.configuration.getProperty("firebase.enabled")))
            return;
	    
	    final FirebaseDatabase database = FirebaseDatabase.getInstance();
	    
	    String path = "auditlog/" + (this.accountId == null ? "system" : this.accountId);
	    
	    DatabaseReference auditLogref = database.getReference(path);
	    auditLogref.push().setValueAsync(this);
        
    }

}
