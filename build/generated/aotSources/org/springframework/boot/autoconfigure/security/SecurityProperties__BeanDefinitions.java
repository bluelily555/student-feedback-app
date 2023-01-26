package org.springframework.boot.autoconfigure.security;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link SecurityProperties}
 */
public class SecurityProperties__BeanDefinitions {
  /**
   * Get the bean definition for 'securityProperties'
   */
  public static BeanDefinition getSecurityPropertiesBeanDefinition() {
    Class<?> beanType = SecurityProperties.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(SecurityProperties::new);
    return beanDefinition;
  }
}
