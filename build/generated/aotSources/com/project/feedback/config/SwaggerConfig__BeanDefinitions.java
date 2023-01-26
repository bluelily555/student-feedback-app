package com.project.feedback.config;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassUtils;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Bean definitions for {@link SwaggerConfig}
 */
public class SwaggerConfig__BeanDefinitions {
  /**
   * Get the bean definition for 'swaggerConfig'
   */
  public static BeanDefinition getSwaggerConfigBeanDefinition() {
    Class<?> beanType = SwaggerConfig.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    ConfigurationClassUtils.initializeConfigurationClass(SwaggerConfig.class);
    beanDefinition.setInstanceSupplier(SwaggerConfig$$SpringCGLIB$$0::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'api'.
   */
  private static BeanInstanceSupplier<Docket> getApiInstanceSupplier() {
    return BeanInstanceSupplier.<Docket>forFactoryMethod(SwaggerConfig.class, "api")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean(SwaggerConfig.class).api());
  }

  /**
   * Get the bean definition for 'api'
   */
  public static BeanDefinition getApiBeanDefinition() {
    Class<?> beanType = Docket.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getApiInstanceSupplier());
    return beanDefinition;
  }
}
