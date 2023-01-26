package com.project.feedback.config;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Bean definitions for {@link EncrypterConfig}
 */
public class EncrypterConfig__BeanDefinitions {
  /**
   * Get the bean definition for 'encrypterConfig'
   */
  public static BeanDefinition getEncrypterConfigBeanDefinition() {
    Class<?> beanType = EncrypterConfig.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    ConfigurationClassUtils.initializeConfigurationClass(EncrypterConfig.class);
    beanDefinition.setInstanceSupplier(EncrypterConfig$$SpringCGLIB$$0::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'encoder'.
   */
  private static BeanInstanceSupplier<BCryptPasswordEncoder> getEncoderInstanceSupplier() {
    return BeanInstanceSupplier.<BCryptPasswordEncoder>forFactoryMethod(EncrypterConfig.class, "encoder")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean(EncrypterConfig.class).encoder());
  }

  /**
   * Get the bean definition for 'encoder'
   */
  public static BeanDefinition getEncoderBeanDefinition() {
    Class<?> beanType = BCryptPasswordEncoder.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getEncoderInstanceSupplier());
    return beanDefinition;
  }
}
