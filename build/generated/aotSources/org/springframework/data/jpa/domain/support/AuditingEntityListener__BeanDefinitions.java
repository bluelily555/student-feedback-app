package org.springframework.data.jpa.domain.support;

import java.lang.Class;
import org.springframework.beans.factory.ObjectFactory__BeanDefinitions;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link AuditingEntityListener}
 */
public class AuditingEntityListener__BeanDefinitions {
  /**
   * Get the bean definition for 'auditingEntityListener'
   */
  public static BeanDefinition getAuditingEntityListenerBeanDefinition() {
    Class<?> beanType = AuditingEntityListener.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    beanDefinition.getPropertyValues().addPropertyValue("auditingHandler", ObjectFactory__BeanDefinitions.getAuditingHandlerBeanDefinition());
    beanDefinition.setInstanceSupplier(AuditingEntityListener::new);
    return beanDefinition;
  }
}
