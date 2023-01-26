package org.springframework.security.config.annotation.web.configuration;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

/**
 * Bean definitions for {@link WebMvcSecurityConfiguration}
 */
public class WebMvcSecurityConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'webMvcSecurityConfiguration'
   */
  public static BeanDefinition getWebMvcSecurityConfigurationBeanDefinition() {
    Class<?> beanType = WebMvcSecurityConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(WebMvcSecurityConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'requestDataValueProcessor'.
   */
  private static BeanInstanceSupplier<RequestDataValueProcessor> getRequestDataValueProcessorInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<RequestDataValueProcessor>forFactoryMethod(WebMvcSecurityConfiguration.class, "requestDataValueProcessor")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean(WebMvcSecurityConfiguration.class).requestDataValueProcessor());
  }

  /**
   * Get the bean definition for 'requestDataValueProcessor'
   */
  public static BeanDefinition getRequestDataValueProcessorBeanDefinition() {
    Class<?> beanType = RequestDataValueProcessor.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getRequestDataValueProcessorInstanceSupplier());
    return beanDefinition;
  }
}
