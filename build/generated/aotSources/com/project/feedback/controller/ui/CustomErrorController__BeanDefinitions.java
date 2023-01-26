package com.project.feedback.controller.ui;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link CustomErrorController}
 */
public class CustomErrorController__BeanDefinitions {
  /**
   * Get the bean definition for 'customErrorController'
   */
  public static BeanDefinition getCustomErrorControllerBeanDefinition() {
    Class<?> beanType = CustomErrorController.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(CustomErrorController::new);
    return beanDefinition;
  }
}
