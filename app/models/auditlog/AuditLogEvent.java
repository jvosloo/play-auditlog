package models.auditlog;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import play.data.validation.Required;
import play.db.jpa.Model;
import play.modules.auditlog.Auditable.Operation;

@Entity
@Table(name = "audit_log_event")
public class AuditLogEvent extends Model {

	@Required
	public String model;

	@Required
	public Long modelId;
	
	public Long accountId;
	
	public Long userId;	

	@Required
	@Enumerated(EnumType.STRING)
	public Operation operation;

	public String property;

	public String oldValue;

	public String newValue;

	public String actor;

	public Date createdAt;
	
	public Boolean admin = false;	

	/**
	 * the key that can be used to identify same operations on the same object
	 * 
	 * @return
	 */
	public String getReferenceKey() {
		return this.model + ":" + this.modelId + ":" + this.property + ":" + this.operation;
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
		result = prime * result + ((admin == null) ? 0 : admin.hashCode());
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
		if (admin == null) {
			if (other.admin != null) {
				return false;
			}
		} else if (!admin.equals(other.admin)) {
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

}
