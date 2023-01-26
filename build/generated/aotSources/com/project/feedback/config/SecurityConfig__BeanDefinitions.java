package com.project.feedback.config;

import com.project.feedback.service.FindService;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassUtils;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Bean definitions for {@link SecurityConfig}
 */
public class SecurityConfig__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'securityConfig'.
   */
  private static BeanInstanceSupplier<SecurityConfig> getSecurityConfigInstanceSupplier() {
    return BeanInstanceSupplier.<SecurityConfig>forConstructor(FindService.class)
            .withGenerator((registeredBean, args) -> new SecurityConfig$$SpringCGLIB$$0(args.get(0)));
  }

  /**
   * Get the bean definition for 'securityConfig'
   */
  public static BeanDefinition getSecurityConfigBeanDefinition() {
    Class<?> beanType = SecurityConfig.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    ConfigurationClassUtils.initializeConfigurationClass(SecurityConfig.class);
    InstanceSupplier<SecurityConfig> instanceSupplier = getSecurityConfigInstanceSupplier();
    instanceSupplier = instanceSupplier.andThen(SecurityConfig__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'securityFilterChain'.
   */
  private static BeanInstanceSupplier<SecurityFilterChain> getSecurityFilterChainInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<SecurityFilterChain>forFactoryMethod(SecurityConfig.class, "securityFilterChain", HttpSecurity.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean(SecurityConfig.class).securityFilterChain(args.get(0)));
  }

  /**
   * Get the bean definition for 'securityFilterChain'
   */
  public static BeanDefinition getSecurityFilterChainBeanDefinition() {
    Class<?> beanType = SecurityFilterChain.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getSecurityFilterChainInstanceSupplier());
    return beanDefinition;
  }
}
