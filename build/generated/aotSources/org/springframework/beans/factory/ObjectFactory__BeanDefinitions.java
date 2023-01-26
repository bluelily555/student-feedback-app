package org.springframework.beans.factory;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ObjectFactoryCreatingFactoryBean;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ObjectFactory}
 */
public class ObjectFactory__BeanDefinitions {
  /**
   * Get the inner-bean definition for 'auditingHandler'
   */
  public static BeanDefinition getAuditingHandlerBeanDefinition() {
    Class<?> beanType = ObjectFactoryCreatingFactoryBean.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    beanDefinition.getPropertyValues().addPropertyValue("targetBeanName", "jpaAuditingHandler");
    beanDefinition.setInstanceSupplier(ObjectFactoryCreatingFactoryBean::new);
    return beanDefinition;
  }
}
