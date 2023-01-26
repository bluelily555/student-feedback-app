package com.project.feedback.controller.ui;

import com.project.feedback.service.CourseService;
import com.project.feedback.service.FindService;
import com.project.feedback.service.TaskService;
import java.lang.Class;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link TaskController}
 */
public class TaskController__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'taskController'.
   */
  private static BeanInstanceSupplier<TaskController> getTaskControllerInstanceSupplier() {
    return BeanInstanceSupplier.<TaskController>forConstructor(TaskService.class, FindService.class, CourseService.class)
            .withGenerator((registeredBean, args) -> new TaskController(args.get(0), args.get(1), args.get(2)));
  }

  /**
   * Get the bean definition for 'taskController'
   */
  public static BeanDefinition getTaskControllerBeanDefinition() {
    Class<?> beanType = TaskController.class;
    RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
    beanDefinition.setInstanceSupplier(getTaskControllerInstanceSupplier());
    return beanDefinition;
  }
}
