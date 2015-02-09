package play.modules.auditlog;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Date;

import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;

import play.db.jpa.Model;
import play.modules.auditlog.Auditable.Operation;
import play.templates.JavaExtensions;

public class AuditLogListener implements PostInsertEventListener, PostUpdateEventListener, PostDeleteEventListener {

    public void onPostInsert(PostInsertEvent event) {
    	Model entity = (Model) event.getEntity();
    	if (hasAnnotation(entity.getClass(),Operation.CREATE)) {
            String model = entity.getClass().getName();
            Long modelId = entity.id;
            AuditLog.invoke("onCreate",model,modelId);
        }
    }

    public void onPostUpdate(PostUpdateEvent event) {
        Model entity = (Model) event.getEntity();
        if (hasAnnotation(entity.getClass(),Operation.UPDATE)) {
            String model = entity.getClass().getName();
            Long modelId = entity.id;
            String[] properties = event.getPersister().getPropertyNames();
            Object[] oldValues = event.getOldState();
            Object[] values = event.getState();
            
            for (int i=0; i<properties.length; i++) {
            	
            	// Skip if the property is marked as not auditable
            	if (hasAnnotation(entity.getClass(), properties[i], NotAuditable.class))
            		continue;
            	
                boolean updated = false;
                if (oldValues[i] == null) {
                    if (values[i] != null) {
                        updated = true;
                    }
                } else if (!oldValues[i].equals(values[i])) {
                    updated = true;
                }
                if (updated) {
                	
                	String oldValue  = (oldValues[i] == null ? "NULL" : oldValues[i].toString());
                	String value = (values[i] == null ? "NULL" : format(values[i]));
                	
                	// If the property is marked as masked
                	if (hasAnnotation(entity.getClass(), properties[i], Mask.class)) {
                		oldValue = mask(oldValue);
                		value = mask(value);
                	}
                	
                    AuditLog.invoke("onUpdate", model, modelId, properties[i], oldValue, value);
                }
            }
        }
    }

    private String format(Object object) {
    	if (object instanceof Date)
    		return JavaExtensions.format((Date) object, "yyyy-MM-dd");
    	
		return object.toString();
	}

	public void onPostDelete(PostDeleteEvent event) {
    	Model entity = (Model) event.getEntity();
        if (hasAnnotation(entity.getClass(),Operation.DELETE)) {
            String model = entity.getClass().getName();
            Long modelId = entity.id;
            AuditLog.invoke("onDelete",model,modelId);
        }
    }
    
    
    /**
     * Checks the annotations on the class
     * @param clazz
     * @param type
     * @return
     */
    private boolean hasAnnotation(Class <? extends Model> clazz, Operation type){
		if (clazz.isAnnotationPresent(Auditable.class)) {
			Auditable annotation = clazz.getAnnotation(Auditable.class);
			return Arrays.asList(annotation.recordOn()).contains(type);
		}
		return false;
	}
    
    /**
     * Checks whether the specified property has the specified annotation
     * @param clazz
     * @param propertyName
     * @param annotationClass
     * @return
     */
    private boolean hasAnnotation(Class <? extends Model> clazz, String propertyName, Class<? extends Annotation> annotationClass) {
    	
		try {
			return clazz.getField(propertyName).isAnnotationPresent(annotationClass);
		} catch (Exception e) {
			// Just swallow and move on - shouldn't happen
		}
		return false;
    }
    
    private String mask(String value) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < value.length(); i++)
            sb.append('*');
        return sb.toString();
    }    

}
