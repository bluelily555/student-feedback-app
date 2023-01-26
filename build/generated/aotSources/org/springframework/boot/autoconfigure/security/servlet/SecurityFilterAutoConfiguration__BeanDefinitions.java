package org.springframework.boot.autoconfigure.security.servlet;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;

/**
 * Bean definitions for {@link SecurityFilterAutoConfiguration}
 */
public class SecurityFilterAutoConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'securityFilterAutoConfiguration'
   */
  public static BeanDefinition getSecurityFilterAutoConfigurationBeanDefinition() {
    Class<?> beanType = SecurityFilterAutoConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(SecurityFilterAutoConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'securityFilterChainRegistration'.
   */
  private static BeanInstanceSupplier<DelegatingFilterProxyRegistrationBean> getSecurityFilterChainRegistrationInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<DelegatingFilterProxyRegistrationBean>forFactoryMethod(SecurityFilterAutoConfiguration.class, "securityFilterChainRegistration", SecurityProperties.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(SecurityFilterAutoConfiguration.class).securityFilterChainRegistration(args.get(0)));
  }

  /**
   * Get the bean definition for 'securityFilterChainRegistration'
   */
  public static BeanDefinition getSecurityFilterChainRegistrationBeanDefinition() {
    Class<?> beanType = DelegatingFilterProxyRegistrationBean.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getSecurityFilterChainRegistrationInstanceSupplier());
    return beanDefinition;
  }
}
