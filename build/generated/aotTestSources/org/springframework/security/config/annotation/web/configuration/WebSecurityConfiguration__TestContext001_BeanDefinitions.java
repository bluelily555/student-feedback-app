package org.springframework.security.config.annotation.web.configuration;

import jakarta.servlet.Filter;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.ResolvableType;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.context.DelegatingApplicationListener;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;

/**
 * Bean definitions for {@link WebSecurityConfiguration}
 */
public class WebSecurityConfiguration__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'webSecurityConfiguration'
   */
  public static BeanDefinition getWebSecurityConfigurationBeanDefinition() {
    Class<?> beanType = WebSecurityConfiguration.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    InstanceSupplier<WebSecurityConfiguration> instanceSupplier = InstanceSupplier.using(WebSecurityConfiguration::new);
    instanceSupplier = instanceSupplier.andThen(WebSecurityConfiguration__TestContext001_Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }

  /**
   * Get the bean definition for 'delegatingApplicationListener'
   */
  public static BeanDefinition getDelegatingApplicationListenerBeanDefinition() {
    Class<?> beanType = DelegatingApplicationListener.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(BeanInstanceSupplier.<DelegatingApplicationListener>forFactoryMethod(WebSecurityConfiguration.class, "delegatingApplicationListener").withGenerator(WebSecurityConfiguration::delegatingApplicationListener));
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'webSecurityExpressionHandler'.
   */
  private static BeanInstanceSupplier<SecurityExpressionHandler> getWebSecurityExpressionHandlerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<SecurityExpressionHandler>forFactoryMethod(WebSecurityConfiguration.class, "webSecurityExpressionHandler")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean(WebSecurityConfiguration.class).webSecurityExpressionHandler());
  }

  /**
   * Get the bean definition for 'webSecurityExpressionHandler'
   */
  public static BeanDefinition getWebSecurityExpressionHandlerBeanDefinition() {
    ResolvableType beanType = ResolvableType.forClassWithGenerics(SecurityExpressionHandler.class, FilterInvocation.class);
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setDependsOn("springSecurityFilterChain");
    beanDefinition.setInstanceSupplier(getWebSecurityExpressionHandlerInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'springSecurityFilterChain'.
   */
  private static BeanInstanceSupplier<Filter> getSpringSecurityFilterChainInstanceSupplier() {
    return BeanInstanceSupplier.<Filter>forFactoryMethod(WebSecurityConfiguration.class, "springSecurityFilterChain")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean(WebSecurityConfiguration.class).springSecurityFilterChain());
  }

  /**
   * Get the bean definition for 'springSecurityFilterChain'
   */
  public static BeanDefinition getSpringSecurityFilterChainBeanDefinition() {
    Class<?> beanType = Filter.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getSpringSecurityFilterChainInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'privilegeEvaluator'.
   */
  private static BeanInstanceSupplier<WebInvocationPrivilegeEvaluator> getPrivilegeEvaluatorInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<WebInvocationPrivilegeEvaluator>forFactoryMethod(WebSecurityConfiguration.class, "privilegeEvaluator")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean(WebSecurityConfiguration.class).privilegeEvaluator());
  }

  /**
   * Get the bean definition for 'privilegeEvaluator'
   */
  public static BeanDefinition getPrivilegeEvaluatorBeanDefinition() {
    Class<?> beanType = WebInvocationPrivilegeEvaluator.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setDependsOn("springSecurityFilterChain");
    beanDefinition.setInstanceSupplier(getPrivilegeEvaluatorInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean definition for 'conversionServicePostProcessor'
   */
  public static BeanDefinition getConversionServicePostProcessorBeanDefinition() {
    Class<?> beanType = BeanFactoryPostProcessor.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(BeanInstanceSupplier.<BeanFactoryPostProcessor>forFactoryMethod(WebSecurityConfiguration.class, "conversionServicePostProcessor").withGenerator(WebSecurityConfiguration::conversionServicePostProcessor));
    return beanDefinition;
  }
}
