package org.springframework.boot.autoconfigure.data.web;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.data.web.config.SortHandlerMethodArgumentResolverCustomizer;

/**
 * Bean definitions for {@link SpringDataWebAutoConfiguration}
 */
public class SpringDataWebAutoConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration'.
   */
  private static BeanInstanceSupplier<SpringDataWebAutoConfiguration> getSpringDataWebAutoConfigurationInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<SpringDataWebAutoConfiguration>forConstructor(SpringDataWebProperties.class)
            .withGenerator((registeredBean, args) -> new SpringDataWebAutoConfiguration(args.get(0)));
  }

  /**
   * Get the bean definition for 'springDataWebAutoConfiguration'
   */
  public static BeanDefinition getSpringDataWebAutoConfigurationBeanDefinition() {
    Class<?> beanType = SpringDataWebAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getSpringDataWebAutoConfigurationInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'sortCustomizer'.
   */
  private static BeanInstanceSupplier<SortHandlerMethodArgumentResolverCustomizer> getSortCustomizerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<SortHandlerMethodArgumentResolverCustomizer>forFactoryMethod(SpringDataWebAutoConfiguration.class, "sortCustomizer")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean(SpringDataWebAutoConfiguration.class).sortCustomizer());
  }

  /**
   * Get the bean definition for 'sortCustomizer'
   */
  public static BeanDefinition getSortCustomizerBeanDefinition() {
    Class<?> beanType = SortHandlerMethodArgumentResolverCustomizer.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getSortCustomizerInstanceSupplier());
    return beanDefinition;
  }
}
