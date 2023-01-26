package com.project.feedback.service;

import com.project.feedback.repository.TaskRepository;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link TaskService}
 */
public class TaskService__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'taskService'.
   */
  private static BeanInstanceSupplier<TaskService> getTaskServiceInstanceSupplier() {
    return BeanInstanceSupplier.<TaskService>forConstructor(TaskRepository.class, FindService.class)
            .withGenerator((registeredBean, args) -> new TaskService(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'taskService'
   */
  public static BeanDefinition getTaskServiceBeanDefinition() {
    Class<?> beanType = TaskService.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getTaskServiceInstanceSupplier());
    return beanDefinition;
  }
}
