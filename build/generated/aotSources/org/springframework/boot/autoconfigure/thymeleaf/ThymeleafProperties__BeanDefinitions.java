package org.springframework.boot.autoconfigure.thymeleaf;

import java.lang.Class;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link ThymeleafProperties}
 */
public class ThymeleafProperties__BeanDefinitions {
  /**
   * Get the bean definition for 'thymeleafProperties'
   */
  public static BeanDefinition getThymeleafPropertiesBeanDefinition() {
    Class<?> beanType = ThymeleafProperties.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(ThymeleafProperties::new);
    return beanDefinition;
  }
}
