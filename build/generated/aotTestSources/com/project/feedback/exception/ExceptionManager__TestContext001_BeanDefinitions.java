package com.project.feedback.exception;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ExceptionManager}
 */
public class ExceptionManager__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'exceptionManager'
   */
  public static BeanDefinition getExceptionManagerBeanDefinition() {
    Class<?> beanType = ExceptionManager.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(ExceptionManager::new);
    return beanDefinition;
  }
}
