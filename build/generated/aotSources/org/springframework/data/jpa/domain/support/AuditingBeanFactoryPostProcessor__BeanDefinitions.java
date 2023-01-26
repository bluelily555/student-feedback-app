package org.springframework.data.jpa.domain.support;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link AuditingBeanFactoryPostProcessor}
 */
public class AuditingBeanFactoryPostProcessor__BeanDefinitions {
  /**
   * Get the bean definition for 'auditingBeanFactoryPostProcessor'
   */
  public static BeanDefinition getAuditingBeanFactoryPostProcessorBeanDefinition() {
    Class<?> beanType = AuditingBeanFactoryPostProcessor.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    beanDefinition.setInstanceSupplier(AuditingBeanFactoryPostProcessor::new);
    return beanDefinition;
  }
}
