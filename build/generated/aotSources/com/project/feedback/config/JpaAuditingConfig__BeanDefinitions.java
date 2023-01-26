package com.project.feedback.config;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassUtils;

/**
 * Bean definitions for {@link JpaAuditingConfig}
 */
public class JpaAuditingConfig__BeanDefinitions {
  /**
   * Get the bean definition for 'jpaAuditingConfig'
   */
  public static BeanDefinition getJpaAuditingConfigBeanDefinition() {
    Class<?> beanType = JpaAuditingConfig.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    ConfigurationClassUtils.initializeConfigurationClass(JpaAuditingConfig.class);
    beanDefinition.setInstanceSupplier(JpaAuditingConfig$$SpringCGLIB$$0::new);
    return beanDefinition;
  }
}
