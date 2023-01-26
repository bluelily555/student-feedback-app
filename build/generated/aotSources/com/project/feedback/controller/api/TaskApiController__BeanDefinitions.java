package com.project.feedback.controller.api;

import com.project.feedback.service.TaskService;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link TaskApiController}
 */
public class TaskApiController__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'taskApiController'.
   */
  private static BeanInstanceSupplier<TaskApiController> getTaskApiControllerInstanceSupplier() {
    return BeanInstanceSupplier.<TaskApiController>forConstructor(TaskService.class)
            .withGenerator((registeredBean, args) -> new TaskApiController(args.get(0)));
  }

  /**
   * Get the bean definition for 'taskApiController'
   */
  public static BeanDefinition getTaskApiControllerBeanDefinition() {
    Class<?> beanType = TaskApiController.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getTaskApiControllerInstanceSupplier());
    return beanDefinition;
  }
}
