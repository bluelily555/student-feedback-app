package org.springframework.beans.factory.aspectj;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link AnnotationBeanConfigurerAspect}
 */
public class AnnotationBeanConfigurerAspect__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'internalBeanConfigurerAspect'
   */
  public static BeanDefinition getInternalBeanConfigurerAspectBeanDefinition() {
    Class<?> beanType = AnnotationBeanConfigurerAspect.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
    beanDefinition.setInstanceSupplier(BeanInstanceSupplier.<AnnotationBeanConfigurerAspect>forFactoryMethod(AnnotationBeanConfigurerAspect.class, "aspectOf").withGenerator(AnnotationBeanConfigurerAspect::aspectOf));
    return beanDefinition;
  }
}
