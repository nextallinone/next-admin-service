package studio.clouthin.next.miniadmin.framework.audit.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The controller or the method of controller which is annotated with this , the audit behavior will be ignored.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AuditIgnored {

}
