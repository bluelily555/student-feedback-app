package com.project.feedback;

import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassUtils;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

/**
 * Bean definitions for {@link FeedbackApplication}
 */
public class FeedbackApplication__BeanDefinitions {
  /**
   * Get the bean definition for 'feedbackApplication'
   */
  public static BeanDefinition getFeedbackApplicationBeanDefinition() {
    Class<?> beanType = FeedbackApplication.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInitMethodNames("setTimeZone");
    ConfigurationClassUtils.initializeConfigurationClass(FeedbackApplication.class);
    beanDefinition.setInstanceSupplier(FeedbackApplication$$SpringCGLIB$$0::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'customize'.
   */
  private static BeanInstanceSupplier<PageableHandlerMethodArgumentResolverCustomizer> getCustomizeInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<PageableHandlerMethodArgumentResolverCustomizer>forFactoryMethod(FeedbackApplication.class, "customize")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean(FeedbackApplication.class).customize());
  }

  /**
   * Get the bean definition for 'customize'
   */
  public static BeanDefinition getCustomizeBeanDefinition() {
    Class<?> beanType = PageableHandlerMethodArgumentResolverCustomizer.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getCustomizeInstanceSupplier());
    return beanDefinition;
  }
}
