package com.project.feedback.service;

import com.project.feedback.repository.CodeRepository;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link CodeService}
 */
public class CodeService__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'codeService'.
   */
  private static BeanInstanceSupplier<CodeService> getCodeServiceInstanceSupplier() {
    return BeanInstanceSupplier.<CodeService>forConstructor(CodeRepository.class)
            .withGenerator((registeredBean, args) -> new CodeService(args.get(0)));
  }

  /**
   * Get the bean definition for 'codeService'
   */
  public static BeanDefinition getCodeServiceBeanDefinition() {
    Class<?> beanType = CodeService.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getCodeServiceInstanceSupplier());
    return beanDefinition;
  }
}
