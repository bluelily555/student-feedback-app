package com.project.feedback.service;

import com.project.feedback.repository.CourseRepository;
import com.project.feedback.repository.TaskRepository;
import com.project.feedback.repository.UserRepository;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link FindService}
 */
public class FindService__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'findService'.
   */
  private static BeanInstanceSupplier<FindService> getFindServiceInstanceSupplier() {
    return BeanInstanceSupplier.<FindService>forConstructor(UserRepository.class, TaskRepository.class, CourseRepository.class)
            .withGenerator((registeredBean, args) -> new FindService(args.get(0), args.get(1), args.get(2)));
  }

  /**
   * Get the bean definition for 'findService'
   */
  public static BeanDefinition getFindServiceBeanDefinition() {
    Class<?> beanType = FindService.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getFindServiceInstanceSupplier());
    return beanDefinition;
  }
}
