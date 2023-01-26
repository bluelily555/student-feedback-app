package org.springframework.boot.autoconfigure.security.servlet;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link SpringBootWebSecurityConfiguration}
 */
public class SpringBootWebSecurityConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'springBootWebSecurityConfiguration'
   */
  public static BeanDefinition getSpringBootWebSecurityConfigurationBeanDefinition() {
    Class<?> beanType = SpringBootWebSecurityConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(SpringBootWebSecurityConfiguration::new);
    return beanDefinition;
  }
}
