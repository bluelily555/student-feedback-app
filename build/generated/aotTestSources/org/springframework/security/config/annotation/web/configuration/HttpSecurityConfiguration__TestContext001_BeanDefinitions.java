package org.springframework.security.config.annotation.web.configuration;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * Bean definitions for {@link HttpSecurityConfiguration}
 */
public class HttpSecurityConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'httpSecurityConfiguration'
   */
  public static BeanDefinition getHttpSecurityConfigurationBeanDefinition() {
    Class<?> beanType = HttpSecurityConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    InstanceSupplier<HttpSecurityConfiguration> instanceSupplier = InstanceSupplier.using(HttpSecurityConfiguration::new);
    instanceSupplier = instanceSupplier.andThen(HttpSecurityConfiguration__TestContext001_Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'org.springframework.security.config.annotation.web.configuration.HttpSecurityConfiguration.httpSecurity'.
   */
  private static BeanInstanceSupplier<HttpSecurity> getHttpSecurityInstanceSupplier() {
    return BeanInstanceSupplier.<HttpSecurity>forFactoryMethod(HttpSecurityConfiguration.class, "httpSecurity")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean(HttpSecurityConfiguration.class).httpSecurity());
  }

  /**
   * Get the bean definition for 'httpSecurity'
   */
  public static BeanDefinition getHttpSecurityBeanDefinition() {
    Class<?> beanType = HttpSecurity.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setScope("prototype");
    beanDefinition.setInstanceSupplier(getHttpSecurityInstanceSupplier());
    return beanDefinition;
  }
}
